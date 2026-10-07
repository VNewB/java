import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Quản lý hàng hóa - Java Swing + SQL Server (JDBC).
 * Biên dịch: javac -encoding UTF-8 -cp mssql-jdbc.jar QuanLyHangHoa.java
 */
public class QuanLyHangHoa extends JFrame {

    // ====== CẤU HÌNH KẾT NỐI - sửa lại cho đúng máy của bạn ======
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=QLHangHoa;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "YourStrongPassword123!";
    // ==============================================================

    private static final double TIEN_CHUAN = 1000;

    /** Một loại hàng (node của JTree). */
    static class LoaiHang {
        int ma; String ten; int tyLe;
        LoaiHang(int ma, String ten, int tyLe) { this.ma = ma; this.ten = ten; this.tyLe = tyLe; }
        @Override public String toString() { return ten; }
    }

    /** Chỉ cho nhập chữ số, giới hạn độ dài. */
    static class DigitFilter extends DocumentFilter {
        private final int max;
        DigitFilter(int max) { this.max = max; }
        @Override public void insertString(FilterBypass fb, int off, String s, AttributeSet a) throws BadLocationException {
            replace(fb, off, 0, s, a);
        }
        @Override public void replace(FilterBypass fb, int off, int len, String s, AttributeSet a) throws BadLocationException {
            if (s == null) { super.replace(fb, off, len, s, a); return; }
            if (!s.matches("\\d*")) { Toolkit.getDefaultToolkit().beep(); return; }
            int newLen = fb.getDocument().getLength() - len + s.length();
            if (newLen > max) { Toolkit.getDefaultToolkit().beep(); return; }
            super.replace(fb, off, len, s, a);
        }
    }

    private final JTextField txtMa = new JTextField(), txtTen = new JTextField(),
            txtLoai = new JTextField(), txtTyLe = new JTextField(), txtTinhChat = new JTextField(),
            txtTong = new JTextField(), txtTB = new JTextField(),
            txtStart = new JTextField(), txtNow = new JTextField(),
            txtCount = new JTextField("0"), txtInterval = new JTextField();
    private final JLabel lblMaLoai = new JLabel("");
    private final JComboBox<String> cboTinhChat = new JComboBox<>(
            new String[]{"Chống ẩm", "Chống nước", "Chống va đập"});
    private final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Loại hàng");
    private final DefaultTreeModel treeModel = new DefaultTreeModel(root);
    private final JTree tree = new JTree(treeModel);
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Số thứ tự", "Mã Hàng", "Tên hàng", "Loại hàng", "Tính chất"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final List<LoaiHang> loaiList = new ArrayList<>();
    private final DecimalFormat money = new DecimalFormat("#,##0.##");
    private final SimpleDateFormat dtf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private int counter = 0;
    private Timer timer;
    private boolean adjusting = false;

    public QuanLyHangHoa() {
        super("Quản lý hàng hóa");
        setSize(950, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { doExit(); }
        });
        setLayout(null);
        buildUI();
        // a) Khởi tạo: con trỏ vào ô Mã hàng hóa + nạp dữ liệu
        SwingUtilities.invokeLater(() -> txtMa.requestFocusInWindow());
        txtStart.setText(dtf.format(new Date()));
        if (!loadAll()) showError("Không kết nối được Database Server. Kiểm tra lại URL/USER/PASS.");
        startTimer();
    }

    // ===================== GIAO DIỆN =====================
    private JLabel lbl(String text, int x, int y, int w, int h, Color c, int size) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, w, h);
        l.setForeground(c);
        l.setFont(new Font("Tahoma", Font.BOLD, size));
        add(l);
        return l;
    }

    private void place(JComponent c, Container parent, int x, int y, int w, int h) {
        c.setBounds(x, y, w, h);
        parent.add(c);
    }

    private JButton btn(String text, Container parent, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        place(b, parent, x, y, w, h);
        return b;
    }

    private void buildUI() {
        Font big = new Font("Tahoma", Font.PLAIN, 16);
        Color red = new Color(200, 0, 0);

        // Thời gian
        txtStart.setEditable(false); txtNow.setEditable(false); txtCount.setEditable(false);
        place(txtStart, getContentPane(), 10, 8, 170, 24);
        place(txtNow, getContentPane(), 190, 8, 170, 24);
        place(txtCount, getContentPane(), 830, 8, 90, 24);
        place(txtInterval, getContentPane(), 830, 38, 90, 24);
        txtCount.setHorizontalAlignment(JTextField.RIGHT);
        txtInterval.setHorizontalAlignment(JTextField.RIGHT);
        ((AbstractDocument) txtInterval.getDocument()).setDocumentFilter(new DigitFilter(5));
        txtInterval.setToolTipText("Số giây để tự động cập nhật dữ liệu từ Database Server");
        lbl("QUẢN LÝ HÀNG HÓA", 380, 35, 300, 30, new Color(0, 0, 200), 22);

        // Thông tin hàng hóa
        lbl("Mã Hàng hóa:", 15, 85, 150, 30, red, 17);
        lbl("Tên Hàng hóa:", 15, 130, 150, 30, red, 17);
        lbl("Loại hàng:", 15, 177, 150, 30, red, 17);
        place(txtMa, getContentPane(), 170, 83, 215, 32);
        place(txtTen, getContentPane(), 170, 128, 215, 32);
        txtMa.setFont(big); txtTen.setFont(big); txtLoai.setFont(big); txtTyLe.setFont(big);
        ((AbstractDocument) txtMa.getDocument()).setDocumentFilter(new DigitFilter(5));
        ((AbstractDocument) txtTyLe.getDocument()).setDocumentFilter(new DigitFilter(3));
        lblMaLoai.setBounds(135, 177, 25, 30);
        lblMaLoai.setFont(big);
        add(lblMaLoai);
        place(txtLoai, getContentPane(), 170, 175, 160, 32);
        place(txtTyLe, getContentPane(), 335, 175, 50, 32);
        txtTyLe.setToolTipText("Tỷ lệ (%)");

        // Nhóm Tính chất
        JPanel pTC = new JPanel(null);
        pTC.setBorder(new TitledBorder("Tính chất"));
        pTC.setBounds(15, 225, 150, 200);
        add(pTC);
        place(txtTinhChat, pTC, 10, 25, 130, 28);
        place(cboTinhChat, pTC, 10, 62, 130, 26);
        JButton btnTCThem = btn("Thêm", pTC, 20, 105, 110, 26);
        JButton btnTCXoa = btn("Xóa", pTC, 20, 145, 110, 26);
        cboTinhChat.setSelectedIndex(-1);

        // Nhóm Loại hàng
        JPanel pL = new JPanel(null);
        pL.setBorder(new TitledBorder("Loại hàng"));
        pL.setBounds(175, 225, 215, 200);
        add(pL);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        place(new JScrollPane(tree), pL, 10, 22, 195, 120);
        JButton btnLThem = btn("Thêm", pL, 10, 155, 90, 30);
        JButton btnLXoa = btn("Xóa", pL, 115, 155, 90, 30);

        // Thống kê
        lbl("Thống kê", 405, 70, 100, 22, Color.BLACK, 14);
        lbl("Tổng giá trị:", 405, 95, 130, 26, red, 15);
        lbl("Trung bình:", 700, 95, 120, 26, red, 15);
        txtTong.setEditable(false); txtTB.setEditable(false);
        place(txtTong, getContentPane(), 535, 95, 150, 26);
        place(txtTB, getContentPane(), 810, 95, 110, 26);

        // Nút chức năng
        JButton btnNew = btn("Nhập mới", getContentPane(), 405, 135, 95, 28);
        JButton btnEdit = btn("Sửa", getContentPane(), 505, 135, 85, 28);
        JButton btnDel = btn("Xóa", getContentPane(), 595, 135, 85, 28);
        JButton btnExit = btn("Thoát", getContentPane(), 685, 135, 85, 28);
        JButton btnFile = btn("Lưu ra file", getContentPane(), 775, 135, 110, 28);

        // Bảng
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(22);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(405, 175, 515, 355);
        add(sp);

        // ===== Sự kiện =====
        cboTinhChat.addActionListener(e -> {
            if (cboTinhChat.getSelectedItem() != null) txtTinhChat.setText(cboTinhChat.getSelectedItem().toString());
        });
        btnTCThem.addActionListener(e -> addTinhChat());
        btnTCXoa.addActionListener(e -> removeTinhChat());

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (n != null && n.getUserObject() instanceof LoaiHang) showLoai((LoaiHang) n.getUserObject());
        });
        btnLThem.addActionListener(e -> addLoai());
        btnLXoa.addActionListener(e -> deleteLoai());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showRow(table.getSelectedRow());
        });
        btnNew.addActionListener(e -> insertHang());
        btnEdit.addActionListener(e -> updateHang());
        btnDel.addActionListener(e -> deleteHang());
        btnFile.addActionListener(e -> exportFile());
        btnExit.addActionListener(e -> doExit());
    }

    // ===================== KẾT NỐI / NẠP DỮ LIỆU =====================
    private Connection conn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private boolean loadAll() {
        try {
            loadLoai();
            loadHangHoa();
            updateStats();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void loadLoai() throws SQLException {
        loaiList.clear();
        root.removeAllChildren();
        try (Connection c = conn(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT MaLoai, TLH, TyLe FROM LoaiHang ORDER BY MaLoai")) {
            while (rs.next()) {
                LoaiHang l = new LoaiHang(rs.getInt(1), rs.getString(2), rs.getInt(3));
                loaiList.add(l);
                root.add(new DefaultMutableTreeNode(l));
            }
        }
        treeModel.reload();
    }

    private void loadHangHoa() throws SQLException {
        model.setRowCount(0);
        String sql = "SELECT h.MHH, h.THH, l.TLH, h.TinhChat FROM HangHoa h "
                + "JOIN LoaiHang l ON h.MaLoai = l.MaLoai ORDER BY h.MHH";
        try (Connection c = conn(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            int stt = 1;
            while (rs.next()) {
                String tc = rs.getString(4);
                model.addRow(new Object[]{stt++, rs.getInt(1), rs.getString(2), rs.getString(3), tc});
                boolean has = false;
                for (int i = 0; i < cboTinhChat.getItemCount(); i++)
                    if (cboTinhChat.getItemAt(i).equalsIgnoreCase(tc)) { has = true; break; }
                if (!has) {
                    Object keep = cboTinhChat.getSelectedItem();
                    cboTinhChat.addItem(tc);
                    cboTinhChat.setSelectedItem(keep);
                }
            }
        }
    }

    /** TongGiaTri = n * (TienLoai + TienChuan); TrungBinh = TongGiaTri / n. */
    private void updateStats() {
        int n = model.getRowCount();
        double tyLe = 0;
        try { tyLe = Double.parseDouble(txtTyLe.getText().trim()); } catch (NumberFormatException ignored) { }
        double tienLoai = TIEN_CHUAN * (tyLe / 100.0);
        double tong = n * (tienLoai + TIEN_CHUAN);
        double tb = n == 0 ? 0 : tong / n;
        txtTong.setText(money.format(tong));
        txtTB.setText(money.format(tb));
    }

    // ===================== TIỆN ÍCH =====================
    private void msg(String s) { JOptionPane.showMessageDialog(this, s, "Thông báo", JOptionPane.INFORMATION_MESSAGE); }
    private void showError(String s) { JOptionPane.showMessageDialog(this, s, "Lỗi", JOptionPane.ERROR_MESSAGE); }
    private boolean confirm(String s) {
        return JOptionPane.showConfirmDialog(this, s, "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private LoaiHang findLoai(String ten) {
        for (LoaiHang l : loaiList) if (l.ten.equalsIgnoreCase(ten)) return l;
        return null;
    }

    private void showLoai(LoaiHang l) {
        lblMaLoai.setText(String.valueOf(l.ma));
        txtLoai.setText(l.ten);
        txtTyLe.setText(String.valueOf(l.tyLe));
        updateStats();
    }

    private boolean requireText(JTextField f, String name) {
        if (f.getText().trim().isEmpty()) {
            showError(name + " không được để trống!");
            f.requestFocusInWindow();
            return false;
        }
        return true;
    }

    /** Kiểm tra ràng buộc dữ liệu cho Nhập mới / Sửa. */
    private boolean validateInput() {
        if (!requireText(txtMa, "Mã hàng hóa")) return false;
        int ma;
        try { ma = Integer.parseInt(txtMa.getText().trim()); }
        catch (NumberFormatException e) { showError("Mã hàng hóa phải là số nguyên!"); txtMa.requestFocus(); return false; }
        if (ma < 1 || ma > 99999) { showError("Mã hàng hóa phải từ 1 đến 99999!"); txtMa.requestFocus(); return false; }
        if (!requireText(txtTen, "Tên hàng hóa")) return false;
        if (!requireText(txtLoai, "Loại hàng")) return false;
        if (!requireText(txtTyLe, "Tỷ lệ")) return false;
        if (!requireText(txtTinhChat, "Tính chất")) return false;
        if (findLoai(txtLoai.getText().trim()) == null) {
            showError("Loại hàng không tồn tại. Hãy chọn trong cây Loại hàng hoặc thêm loại mới!");
            txtLoai.requestFocus();
            return false;
        }
        return true;
    }

    // ===================== TÍNH CHẤT =====================
    private void addTinhChat() {
        String t = txtTinhChat.getText().trim();
        if (t.isEmpty()) { showError("Tính chất không được để trống!"); txtTinhChat.requestFocus(); return; }
        for (int i = 0; i < cboTinhChat.getItemCount(); i++)
            if (cboTinhChat.getItemAt(i).equalsIgnoreCase(t)) { msg("Tính chất này đã có!"); return; }
        cboTinhChat.addItem(t);
        cboTinhChat.setSelectedItem(t);
    }

    private void removeTinhChat() {
        int i = cboTinhChat.getSelectedIndex();
        if (i < 0) { showError("Hãy chọn một Tính chất để xóa!"); return; }
        cboTinhChat.removeItemAt(i);
        cboTinhChat.setSelectedIndex(-1);
        txtTinhChat.setText("");
    }

    // ===================== LOẠI HÀNG =====================
    private void addLoai() {
        if (!requireText(txtLoai, "Tên loại hàng") || !requireText(txtTyLe, "Tỷ lệ")) return;
        String ten = txtLoai.getText().trim();
        int tyLe = Integer.parseInt(txtTyLe.getText().trim());
        if (tyLe > 100) { showError("Tỷ lệ phải từ 0 đến 100 (%)!"); txtTyLe.requestFocus(); return; }
        if (findLoai(ten) != null) { showError("Loại hàng đã tồn tại!"); return; }
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("INSERT INTO LoaiHang(TLH, TyLe) VALUES(?, ?)")) {
            ps.setString(1, ten);
            ps.setInt(2, tyLe);
            ps.executeUpdate();           // MaLoai tự tăng (IDENTITY)
            loadLoai();
            LoaiHang l = findLoai(ten);
            if (l != null) {
                showLoai(l);
                for (int i = 0; i < root.getChildCount(); i++) {
                    DefaultMutableTreeNode n = (DefaultMutableTreeNode) root.getChildAt(i);
                    if (n.getUserObject() == l) tree.setSelectionPath(new TreePath(n.getPath()));
                }
            }
        } catch (SQLException ex) { showError("Lỗi thêm loại hàng: " + ex.getMessage()); }
    }

    private void deleteLoai() {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (n == null || !(n.getUserObject() instanceof LoaiHang)) { showError("Hãy chọn một Loại hàng để xóa!"); return; }
        LoaiHang l = (LoaiHang) n.getUserObject();
        if (!confirm("Xóa loại hàng \"" + l.ten + "\"?")) return;
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("DELETE FROM LoaiHang WHERE MaLoai = ?")) {
            ps.setInt(1, l.ma);
            ps.executeUpdate();
            loadLoai();
            lblMaLoai.setText(""); txtLoai.setText(""); txtTyLe.setText("");
            updateStats();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 547)  // vi phạm khóa ngoại
                showError("Không thể xóa: đang có hàng hóa thuộc loại này!");
            else showError("Lỗi xóa loại hàng: " + ex.getMessage());
        }
    }

    // ===================== HÀNG HÓA =====================
    private void showRow(int r) {
        if (r < 0) return;
        txtMa.setText(model.getValueAt(r, 1).toString());
        txtTen.setText(model.getValueAt(r, 2).toString());
        txtTinhChat.setText(String.valueOf(model.getValueAt(r, 4)));
        LoaiHang l = findLoai(model.getValueAt(r, 3).toString());
        if (l != null) showLoai(l);
    }

    private void insertHang() {
        if (!validateInput()) return;
        LoaiHang l = findLoai(txtLoai.getText().trim());
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO HangHoa(MHH, THH, MaLoai, TinhChat) VALUES(?, ?, ?, ?)")) {
            ps.setInt(1, Integer.parseInt(txtMa.getText().trim()));
            ps.setString(2, txtTen.getText().trim());
            ps.setInt(3, l.ma);
            ps.setString(4, txtTinhChat.getText().trim());
            ps.executeUpdate();
            loadHangHoa();
            updateStats();
            msg("Thêm hàng hóa thành công!");
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 2627) showError("Mã hàng hóa đã tồn tại!");
            else showError("Lỗi thêm hàng hóa: " + ex.getMessage());
        }
    }

    private void updateHang() {
        if (!validateInput()) return;
        LoaiHang l = findLoai(txtLoai.getText().trim());
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE HangHoa SET THH = ?, MaLoai = ?, TinhChat = ? WHERE MHH = ?")) {
            ps.setString(1, txtTen.getText().trim());
            ps.setInt(2, l.ma);
            ps.setString(3, txtTinhChat.getText().trim());
            ps.setInt(4, Integer.parseInt(txtMa.getText().trim()));
            if (ps.executeUpdate() == 0) { showError("Không tìm thấy mã hàng hóa này để sửa!"); return; }
            loadHangHoa();
            updateStats();
            msg("Cập nhật thành công!");
        } catch (SQLException ex) { showError("Lỗi cập nhật: " + ex.getMessage()); }
    }

    private void deleteHang() {
        if (!requireText(txtMa, "Mã hàng hóa")) return;
        if (!confirm("Xóa hàng hóa có mã " + txtMa.getText().trim() + "?")) return;
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("DELETE FROM HangHoa WHERE MHH = ?")) {
            ps.setInt(1, Integer.parseInt(txtMa.getText().trim()));
            if (ps.executeUpdate() == 0) { showError("Không tìm thấy mã hàng hóa này để xóa!"); return; }
            loadHangHoa();
            updateStats();
            txtMa.setText(""); txtTen.setText(""); txtTinhChat.setText("");
            msg("Xóa thành công!");
        } catch (SQLException ex) { showError("Lỗi xóa: " + ex.getMessage()); }
    }

    // ===================== LƯU RA FILE =====================
    private void exportFile() {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("HangHoa.txt"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (Connection c = conn(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT MHH, THH, MaLoai, TinhChat FROM HangHoa ORDER BY MHH");
             BufferedWriter w = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(fc.getSelectedFile()), StandardCharsets.UTF_8))) {
            w.write("MHH\tTHH\tMaLoai\tTinhChat");
            w.newLine();
            while (rs.next()) {
                w.write(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4));
                w.newLine();
            }
            msg("Đã lưu file: " + fc.getSelectedFile().getAbsolutePath());
        } catch (SQLException | IOException ex) { showError("Lỗi lưu file: " + ex.getMessage()); }
    }

    // ===================== THỜI GIAN / TỰ ĐỘNG CẬP NHẬT =====================
    private void startTimer() {
        txtNow.setText(dtf.format(new Date()));
        timer = new Timer(1000, e -> {
            txtNow.setText(dtf.format(new Date()));
            int interval = 0;
            try { interval = Integer.parseInt(txtInterval.getText().trim()); } catch (NumberFormatException ignored) { }
            if (interval <= 0) { counter = 0; txtCount.setText("0"); return; }
            counter++;
            if (counter >= interval) {
                counter = 0;
                timer.stop();                       // tránh chồng thông báo khi hộp thoại đang mở
                boolean ok = loadAll();
                txtCount.setText("0");
                if (ok) msg("Cập nhật dữ liệu từ Database Server thành công!");
                else showError("Cập nhật dữ liệu từ Database Server thất bại!");
                timer.start();
            } else txtCount.setText(String.valueOf(counter));
        });
        timer.start();
    }

    private void doExit() {
        if (confirm("Bạn có chắc chắn muốn thoát khỏi chương trình?")) System.exit(0);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) { }
        SwingUtilities.invokeLater(() -> new QuanLyHangHoa().setVisible(true));
    }
}