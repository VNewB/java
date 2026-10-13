import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * QUẢN LÝ NHÂN SỰ - Java Swing + SQL Server (CSDL QLNhanSu)
 * Cần thêm thư viện: mssql-jdbc (vd: mssql-jdbc-12.x.x.jre11.jar)
 */
public class QLNhanSu extends JFrame {

    // ====== CẤU HÌNH KẾT NỐI (sửa lại cho đúng máy của bạn) ======
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=QLNhanSu;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "YourStrongPassword123!";

    private static final int BH_TOI_THIEU = 100;
    private static final SimpleDateFormat FMT = new SimpleDateFormat("d/M/yyyy H:mm:ss");

    // ====== Controls ======
    private JLabel lblClock;
    private JTextField txtGiayChay, txtGiayMoc;
    private JTextField txtMNS, txtTNS, txtLuong, txtMaCap, txtCap, txtBH;
    private JTextField txtTongThuLao, txtTrungBinh;
    private JButton btnNhapMoi, btnSua, btnXoa, btnThoat, btnLuuFile, btnThem, btnXoaCap;
    private JTable table;
    private DefaultTableModel model;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode root;

    // Dữ liệu cấp: tên -> {MaCap, BaoHiem}
    private final Map<String, int[]> capMap = new LinkedHashMap<>();
    private int dem = 0;

    public QLNhanSu() {
        // Cửa sổ (thuộc tính Text) = ngày giờ bắt đầu chạy
        setTitle(FMT.format(new java.util.Date()));
        setSize(880, 580);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { thoat(); }
        });
        buildUI();
        wireEvents();

        loadAll(false);                         // a) lấy dữ liệu từ DB
        startTimers();
    }

    // ====================== GIAO DIỆN ======================
    private void buildUI() {
        Font fLabel = new Font("Times New Roman", Font.BOLD, 16);
        Color red = new Color(200, 0, 0);

        lblClock = new JLabel();
        lblClock.setBounds(10, 5, 200, 20);
        add(lblClock);

        JLabel title = new JLabel("QUẢN LÝ NHÂN SỰ", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 18));
        title.setForeground(new Color(0, 0, 160));
        title.setBounds(320, 10, 250, 25);
        add(title);

        txtGiayChay = new JTextField("0");
        txtGiayChay.setEnabled(false);
        txtGiayChay.setBounds(790, 5, 70, 20);
        add(txtGiayChay);
        txtGiayMoc = new JTextField("100");
        txtGiayMoc.setBounds(790, 28, 70, 20);
        add(txtGiayMoc);

        addLabel("Mã Nhân sự:", 10, 50, fLabel, red);
        addLabel("Tên Nhân sự:", 10, 88, fLabel, red);
        addLabel("Tiền lương:", 10, 126, fLabel, red);
        addLabel("Cấp:", 10, 164, fLabel, red);

        txtMNS = tf(120, 50, 160, 28);
        txtTNS = tf(120, 88, 160, 28);
        txtLuong = tf(120, 126, 160, 28);
        txtMaCap = tf(120, 164, 40, 28);
        txtCap = tf(165, 164, 80, 28);
        txtBH = tf(250, 164, 30, 28);

        // Khung Cấp
        JPanel pCap = new JPanel(null);
        pCap.setBorder(BorderFactory.createTitledBorder("Cấp:"));
        pCap.setBounds(5, 205, 276, 200);
        add(pCap);
        root = new DefaultMutableTreeNode("Cấp");
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        tree.setRootVisible(false);
        JScrollPane spTree = new JScrollPane(tree);
        spTree.setBounds(10, 20, 200, 130);
        pCap.add(spTree);
        btnThem = new JButton("Thêm");
        btnThem.setBounds(10, 160, 80, 30);
        pCap.add(btnThem);
        btnXoaCap = new JButton("Xóa");
        btnXoaCap.setBounds(100, 160, 80, 30);
        pCap.add(btnXoaCap);

        // Thống kê
        JPanel pTK = new JPanel(null);
        pTK.setBorder(BorderFactory.createTitledBorder("Thống kê"));
        pTK.setBounds(300, 45, 390, 60);
        add(pTK);
        JLabel l1 = new JLabel("Tổng Thù lao:");
        l1.setFont(fLabel); l1.setForeground(red); l1.setBounds(10, 20, 110, 25);
        pTK.add(l1);
        txtTongThuLao = new JTextField("0");
        txtTongThuLao.setEnabled(false);
        txtTongThuLao.setBounds(120, 20, 90, 25);
        pTK.add(txtTongThuLao);
        JLabel l2 = new JLabel("Trung bình:");
        l2.setFont(fLabel); l2.setForeground(red); l2.setBounds(220, 20, 90, 25);
        pTK.add(l2);
        txtTrungBinh = new JTextField("0");
        txtTrungBinh.setEnabled(false);
        txtTrungBinh.setBounds(310, 20, 70, 25);
        pTK.add(txtTrungBinh);

        // Nút chức năng
        btnNhapMoi = btn("Nhập mới",280 + 20, 115, 80);
        btnSua = btn("Sửa",362 + 20, 115, 55);
        btnXoa = btn("Xóa",419 + 20, 115, 55);
        btnThoat = btn("Thoát",476 + 20, 115, 65);
        btnLuuFile = btn("Lưu ra file",543 + 20, 115, 90);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);

        // Khung dữ liệu
        model = new DefaultTableModel(
                new Object[]{"Số thứ tự", "Mã nhân sự", "Tên nhân sự", "Tiền lương", "Cấp", "Bảo hiểm"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);   // giữ đúng độ rộng mình đặt
        int[] w = {70, 80, 160, 90, 90, 67};               // theo thứ tự cột
        for (int i = 0; i < w.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(w[i]);
        }
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(300, 150, 560, 254);
        add(sp);
    }

    private void addLabel(String s, int x, int y, Font f, Color c) {
        JLabel l = new JLabel(s);
        l.setFont(f); l.setForeground(c); l.setBounds(x, y, 110, 28);
        add(l);
    }
    private JTextField tf(int x, int y, int w, int h) {
        JTextField t = new JTextField();
        t.setBounds(x, y, w, h);
        add(t);
        return t;
    }
    private JButton btn(String s, int x, int y, int w) {
        JButton b = new JButton(s);
        b.setMargin(new Insets(2, 4, 2, 4));
        b.setBounds(x, y, w, 28);
        add(b);
        return b;
    }

    // ====================== SỰ KIỆN ======================
    private void wireEvents() {
        // Kiểm tra dữ liệu bàn phím: chỉ cho nhập số ở các ô số
        soOnly(txtMNS, 5);
        soOnly(txtLuong, 9);
        soOnly(txtMaCap, 3);
        soOnly(txtBH, 3);
        soOnly(txtGiayMoc, 5);
        // Tên: không cho nhập chữ số
        txtTNS.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c) || (txtTNS.getText().length() >= 50 && !Character.isISOControl(c)))
                    e.consume();
            }
        });
        txtCap.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                if (txtCap.getText().length() >= 50 && !Character.isISOControl(e.getKeyChar())) e.consume();
            }
        });

        // h) chọn dòng -> đưa lên control
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) hienDongHienHanh();
        });

        // c) chọn Cấp trên cây -> ô Cấp
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (n != null && n.isLeaf() && n != root) {
                String ten = n.getUserObject().toString();
                int[] v = capMap.get(ten);
                if (v != null) {
                    txtMaCap.setText(String.valueOf(v[0]));
                    txtCap.setText(ten);
                    txtBH.setText(String.valueOf(v[1]));
                }
            }
        });

        btnThem.addActionListener(e -> themCap());
        btnXoaCap.addActionListener(e -> xoaCap());
        btnNhapMoi.addActionListener(e -> nhapMoi());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnThoat.addActionListener(e -> thoat());
        btnLuuFile.addActionListener(e -> luuFile());
    }

    private void soOnly(JTextField t, int max) {
        t.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isISOControl(c)) return;
                if (!Character.isDigit(c) || t.getText().length() >= max) e.consume();
            }
        });
    }

    // ====================== TIMER (g) ======================
    private void startTimers() {
        lblClock.setText(FMT.format(new java.util.Date()));
        new javax.swing.Timer(1000, e -> {
            lblClock.setText(FMT.format(new java.util.Date()));
            int moc;
            try { moc = Integer.parseInt(txtGiayMoc.getText().trim()); } catch (Exception ex) { moc = 0; }
            if (moc <= 0) { dem = 0; txtGiayChay.setText("0"); return; }
            dem++;
            if (dem >= moc) {
                dem = 0;
                boolean ok = loadAll(false);
                JOptionPane.showMessageDialog(this, ok
                        ? "Cập nhật dữ liệu từ Database Server lên Cửa sổ thành công!"
                        : "Cập nhật dữ liệu từ Database Server thất bại!");
            }
            txtGiayChay.setText(String.valueOf(dem));
        }).start();
    }

    // ====================== DATABASE ======================
    private Connection conn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /** Lấy Cấp + Nhân sự từ DB lên Cửa sổ. */
    private boolean loadAll(boolean showErr) {
        try (Connection c = conn()) {
            // Cấp
            capMap.clear();
            root.removeAllChildren();
            try (Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery("SELECT MaCap, Ten, BaoHiem FROM Cap ORDER BY MaCap")) {
                while (rs.next()) {
                    capMap.put(rs.getString("Ten"), new int[]{rs.getInt("MaCap"), rs.getInt("BaoHiem")});
                    root.add(new DefaultMutableTreeNode(rs.getString("Ten")));
                }
            }
            treeModel.reload();
            for (int i = 0; i < tree.getRowCount(); i++) tree.expandRow(i);

            // Nhân sự
            model.setRowCount(0);
            String sql = "SELECT n.MNS, n.TNS, n.TienLuong, c.Ten, c.BaoHiem "
                    + "FROM NhanSu n JOIN Cap c ON n.MaCap = c.MaCap ORDER BY n.MNS";
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                int stt = 1;
                while (rs.next()) {
                    model.addRow(new Object[]{stt++, rs.getInt("MNS"), rs.getString("TNS"),
                            rs.getInt("TienLuong"), rs.getString("Ten"), rs.getInt("BaoHiem")});
                }
            }
            capNhatThongKe();
            return true;
        } catch (SQLException ex) {
            if (showErr) loi(ex);
            return false;
        }
    }

    // b) Tổng thù lao, Trung bình
    private void capNhatThongKe() {
        double tong = 0;
        int n = model.getRowCount();
        for (int i = 0; i < n; i++) {
            int luong = (Integer) model.getValueAt(i, 3);
            int bh = (Integer) model.getValueAt(i, 5);
            double tienBH = (luong + BH_TOI_THIEU) * (bh / 100.0);
            tong += luong + tienBH;
        }
        txtTongThuLao.setText(String.format("%.0f", tong));
        txtTrungBinh.setText(n == 0 ? "0" : String.format("%.0f", tong / n));
    }

    // ====================== KIỂM TRA RÀNG BUỘC ======================
    private boolean kiemTraNhanSu() {
        if (txtMNS.getText().trim().isEmpty()) return bao("Mã nhân sự không được để trống!", txtMNS);
        if (txtTNS.getText().trim().isEmpty()) return bao("Tên nhân sự không được để trống!", txtTNS);
        if (txtLuong.getText().trim().isEmpty()) return bao("Tiền lương không được để trống!", txtLuong);
        if (txtMaCap.getText().trim().isEmpty() || txtCap.getText().trim().isEmpty()
                || txtBH.getText().trim().isEmpty())
            return bao("Vui lòng chọn Cấp (không được để trống)!", txtCap);
        int mns = Integer.parseInt(txtMNS.getText().trim());
        if (mns < 1) return bao("Mã nhân sự phải lớn hơn 0!", txtMNS);
        if (!capMap.containsKey(txtCap.getText().trim()))
            return bao("Cấp không tồn tại, hãy chọn Cấp trong khung Cấp!", txtCap);
        return true;
    }

    private boolean bao(String msg, JComponent focus) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi dữ liệu", JOptionPane.WARNING_MESSAGE);
        focus.requestFocusInWindow();
        return false;
    }

    private void loi(SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // ====================== NHÂN SỰ: NHẬP MỚI / SỬA / XÓA ======================
    private void nhapMoi() {
        if (!kiemTraNhanSu()) return;
        int maCap = capMap.get(txtCap.getText().trim())[0];
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO NhanSu(MNS, TNS, TienLuong, MaCap) VALUES (?,?,?,?)")) {
            ps.setInt(1, Integer.parseInt(txtMNS.getText().trim()));
            ps.setString(2, txtTNS.getText().trim());
            ps.setInt(3, Integer.parseInt(txtLuong.getText().trim()));
            ps.setInt(4, maCap);
            ps.executeUpdate();
            loadAll(true);
            JOptionPane.showMessageDialog(this, "Thêm nhân sự thành công!");
            xoaTrangNhanSu();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 2627 || ex.getErrorCode() == 2601)
                bao("Mã nhân sự đã tồn tại!", txtMNS);
            else loi(ex);
        }
    }

    private void sua() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để sửa!"); return; }
        if (!kiemTraNhanSu()) return;
        int mnsCu = (Integer) model.getValueAt(r, 1);
        int mnsMoi = Integer.parseInt(txtMNS.getText().trim());
        if (mnsCu != mnsMoi) { bao("Không được sửa Mã nhân sự (khóa chính)!", txtMNS); txtMNS.setText(String.valueOf(mnsCu)); return; }
        int maCap = capMap.get(txtCap.getText().trim())[0];
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE NhanSu SET TNS=?, TienLuong=?, MaCap=? WHERE MNS=?")) {
            ps.setString(1, txtTNS.getText().trim());
            ps.setInt(2, Integer.parseInt(txtLuong.getText().trim()));
            ps.setInt(3, maCap);
            ps.setInt(4, mnsCu);
            ps.executeUpdate();
            loadAll(true);
            selectByMns(mnsCu);
            JOptionPane.showMessageDialog(this, "Cập nhật nhân sự thành công!");
        } catch (SQLException ex) { loi(ex); }
    }

    private void xoa() {
        int r = table.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để xóa!"); return; }
        int mns = (Integer) model.getValueAt(r, 1);
        if (JOptionPane.showConfirmDialog(this, "Xóa nhân sự có mã " + mns + "?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("DELETE FROM NhanSu WHERE MNS=?")) {
            ps.setInt(1, mns);
            ps.executeUpdate();
            loadAll(true);
            xoaTrangNhanSu();
        } catch (SQLException ex) { loi(ex); }
    }

    // ====================== CẤP: THÊM / XÓA ======================
    private void themCap() {
        String ma = txtMaCap.getText().trim(), ten = txtCap.getText().trim(), bh = txtBH.getText().trim();
        if (ma.isEmpty()) { bao("Mã cấp không được để trống!", txtMaCap); return; }
        if (ten.isEmpty()) { bao("Tên cấp không được để trống!", txtCap); return; }
        if (bh.isEmpty()) { bao("Bảo hiểm (%) không được để trống!", txtBH); return; }
        if (Integer.parseInt(bh) > 100) { bao("Bảo hiểm phải từ 0 đến 100 (%)!", txtBH); return; }
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("INSERT INTO Cap(MaCap, Ten, BaoHiem) VALUES (?,?,?)")) {
            ps.setInt(1, Integer.parseInt(ma));
            ps.setString(2, ten);
            ps.setInt(3, Integer.parseInt(bh));
            ps.executeUpdate();
            loadAll(true);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 2627 || ex.getErrorCode() == 2601) bao("Mã cấp hoặc tên cấp đã tồn tại!", txtMaCap);
            else loi(ex);
        }
    }

    private void xoaCap() {
        TreePath p = tree.getSelectionPath();
        if (p == null) { JOptionPane.showMessageDialog(this, "Hãy chọn một Cấp để xóa!"); return; }
        String ten = p.getLastPathComponent().toString();
        int[] v = capMap.get(ten);
        if (v == null) return;
        if (JOptionPane.showConfirmDialog(this, "Xóa cấp \"" + ten + "\"?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("DELETE FROM Cap WHERE MaCap=?")) {
            ps.setInt(1, v[0]);
            ps.executeUpdate();
            txtMaCap.setText(""); txtCap.setText(""); txtBH.setText("");
            loadAll(true);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 547)
                JOptionPane.showMessageDialog(this, "Không thể xóa: còn nhân sự thuộc cấp này!", "Lỗi",
                        JOptionPane.WARNING_MESSAGE);
            else loi(ex);
        }
    }

    // ====================== HIỂN THỊ / TIỆN ÍCH ======================
    private void hienDongHienHanh() {
        int r = table.getSelectedRow();
        boolean co = r >= 0;
        btnSua.setEnabled(co);
        btnXoa.setEnabled(co);
        if (!co) return;
        txtMNS.setText(String.valueOf(model.getValueAt(r, 1)));
        txtTNS.setText(String.valueOf(model.getValueAt(r, 2)));
        txtLuong.setText(String.valueOf(model.getValueAt(r, 3)));
        String ten = String.valueOf(model.getValueAt(r, 4));
        txtCap.setText(ten);
        txtBH.setText(String.valueOf(model.getValueAt(r, 5)));
        int[] v = capMap.get(ten);
        txtMaCap.setText(v == null ? "" : String.valueOf(v[0]));
    }

    private void selectByMns(int mns) {
        for (int i = 0; i < model.getRowCount(); i++)
            if ((Integer) model.getValueAt(i, 1) == mns) { table.setRowSelectionInterval(i, i); return; }
    }

    private void xoaTrangNhanSu() {
        table.clearSelection();
        txtMNS.setText(""); txtTNS.setText(""); txtLuong.setText("");
        txtMaCap.setText(""); txtCap.setText(""); txtBH.setText("");
        txtMNS.requestFocusInWindow();
    }

    private void luuFile() {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("NhanSu.txt"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (PrintWriter pw = new PrintWriter(fc.getSelectedFile(), StandardCharsets.UTF_8)) {
            pw.println("STT\tMaNS\tTenNS\tTienLuong\tCap\tBaoHiem(%)");
            for (int i = 0; i < model.getRowCount(); i++) {
                List<String> row = new ArrayList<>();
                for (int j = 0; j < model.getColumnCount(); j++) row.add(String.valueOf(model.getValueAt(i, j)));
                pw.println(String.join("\t", row));
            }
            pw.println("Tong thu lao: " + txtTongThuLao.getText() + "\tTrung binh: " + txtTrungBinh.getText());
            JOptionPane.showMessageDialog(this, "Đã lưu ra file thành công!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi ghi file: " + ex.getMessage());
        }
    }

    // i) Thoát
    private void thoat() {
        int ch = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát chương trình?",
                "Thoát", JOptionPane.YES_NO_OPTION);
        if (ch == JOptionPane.YES_OPTION) System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QLNhanSu app = new QLNhanSu();
            app.setVisible(true);
            app.txtMNS.requestFocusInWindow(); // a) Đưa con trỏ văn bản vào ô Mã nhân sự
        });
    }
}