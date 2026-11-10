import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    static final double VAT = 1.10;          // Thành tiền = SL * Đơn giá * 1.1 (theo hình mẫu: 21,500,000 -> 23,650,000)
    static final boolean CONFIRM_NAV = true; // false nếu không muốn hỏi Yes/No cho 4 nút điều hướng
    static final DecimalFormat MONEY = new DecimalFormat("#,###");
    static final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
    static { DF.setLenient(false); }

    static java.sql.Date parseDate(String s) throws ParseException {
        return new java.sql.Date(DF.parse(s.trim()).getTime());
    }

    interface DbAction { void run() throws SQLException; }

    private List<Xe> xes = new ArrayList<>();
    private List<ChiTiet> details = new ArrayList<>();
    private int idx = 0;
    private boolean online = true;

    private final JTextField txtMaXe = new JTextField(), txtTenXe = new JTextField(),
            txtDungTich = new JTextField(), txtDonGia = new JTextField();
    private final JTextField txtCount = new JTextField(5), txtTong = new JTextField(12), txtIdx = new JTextField(6);
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"STT", "Mã KH", "Tên khách hàng", "Số CMND", "Giới tính", "Ngày mua", "Số lượng", "Thành tiền"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);

    public MainFrame() {
        super("Quản lý khách hàng mua xe");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { ketThuc(); }
        });
        buildUI();
        setSize(900, 640);
        setLocationRelativeTo(null);
    }

    // ================= GIAO DIỆN =================
    private void buildUI() {
        JPanel banner = new JPanel(new GridLayout(2, 1));
        banner.setBackground(new Color(40, 50, 160));
        banner.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        JLabel l1 = new JLabel("Trung tâm xe gắn máy NVT");
        l1.setForeground(Color.WHITE);
        JLabel l2 = new JLabel("QUẢN LÝ KHÁCH HÀNG MUA XE");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("Serif", Font.BOLD, 30));
        banner.add(l1); banner.add(l2);

        JPanel info = new JPanel(new GridLayout(2, 4, 10, 8));
        info.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        info.add(bold("Mã xe")); info.add(txtMaXe);
        info.add(bold("Tên xe")); info.add(txtTenXe);
        info.add(bold("Dung tích")); info.add(txtDungTich);
        info.add(bold("Đơn giá")); info.add(txtDonGia);
        guard(txtMaXe, true); guard(txtTenXe, false); guard(txtDungTich, true); guard(txtDonGia, true);

        JPanel north = new JPanel(new BorderLayout());
        north.add(banner, BorderLayout.NORTH);
        north.add(info, BorderLayout.CENTER);

        // Khung chi tiết + nút Thêm/Sửa/Xóa khách hàng
        JButton bThemKH = new JButton("Thêm KH"), bSuaKH = new JButton("Sửa KH"), bXoaKH = new JButton("Xóa KH");
        JPanel crud = new JPanel(new FlowLayout(FlowLayout.LEFT));
        crud.add(bThemKH); crud.add(bSuaKH); crud.add(bXoaKH);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
        center.add(new JScrollPane(table), BorderLayout.CENTER);
        center.add(crud, BorderLayout.SOUTH);

        // Thống kê
        txtCount.setEditable(false); txtTong.setEditable(false); txtIdx.setEditable(false);
        txtIdx.setHorizontalAlignment(JTextField.CENTER);
        JPanel stat = new JPanel(new BorderLayout());
        JPanel sl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sl.add(bold("Tổng số khách hàng:")); sl.add(txtCount);
        JPanel sr = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sr.add(bold("Tổng thành tiền:")); sr.add(txtTong); sr.add(new JLabel("VNĐ"));
        stat.add(sl, BorderLayout.WEST); stat.add(sr, BorderLayout.EAST);

        // Nút điều hướng + chức năng
        JButton bDau = new JButton("|<"), bTruoc = new JButton("<"), bSau = new JButton(">"), bCuoi = new JButton(">|");
        JButton bThem = new JButton("Thêm Xe"), bMoi = new JButton("Nhập mới"), bXoa = new JButton("Xóa xe"),
                bBaoCao = new JButton("Báo cáo"), bGhi = new JButton("Ghi file"), bKetThuc = new JButton("Kết thúc");
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        btns.add(bDau); btns.add(bTruoc); btns.add(txtIdx); btns.add(bSau); btns.add(bCuoi);
        btns.add(Box.createHorizontalStrut(20));
        btns.add(bThem); btns.add(bMoi); btns.add(bXoa); btns.add(bBaoCao); btns.add(bGhi); btns.add(bKetThuc);

        JPanel south = new JPanel(new BorderLayout());
        south.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
        south.add(stat, BorderLayout.NORTH);
        south.add(btns, BorderLayout.CENTER);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        bDau.addActionListener(e -> nav("về bản ghi đầu tiên", () -> idx = 0));
        bTruoc.addActionListener(e -> nav("về bản ghi trước", () -> idx--));
        bSau.addActionListener(e -> nav("về bản ghi sau", () -> idx++));
        bCuoi.addActionListener(e -> nav("về bản ghi cuối cùng", () -> idx = xes.size() - 1));
        bThem.addActionListener(e -> themXe());
        bMoi.addActionListener(e -> nhapMoi());
        bXoa.addActionListener(e -> xoaXe());
        bBaoCao.addActionListener(e -> baoCao());
        bGhi.addActionListener(e -> ghiFile());
        bKetThuc.addActionListener(e -> ketThuc());
        bThemKH.addActionListener(e -> themKH());
        bSuaKH.addActionListener(e -> suaKH());
        bXoaKH.addActionListener(e -> xoaKH());
    }

    private JLabel bold(String s) {
        JLabel l = new JLabel(s);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 14f));
        return l;
    }

    /** Kiểm tra dữ liệu từ bàn phím (chỉ cho số) và khi thoát control (không được để trống). */
    private void guard(JTextField f, boolean numeric) {
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusLost(FocusEvent e) {
                f.setBackground(f.getText().trim().isEmpty() ? new Color(255, 215, 215) : Color.WHITE);
            }
        });
        if (numeric) f.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE)
                    e.consume();
            }
        });
    }

    // ================= TIỆN ÍCH =================
    private boolean ask(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    private void info(String m) { JOptionPane.showMessageDialog(this, m, "Thông báo", JOptionPane.INFORMATION_MESSAGE); }
    private void error(String m) { JOptionPane.showMessageDialog(this, m, "Lỗi", JOptionPane.ERROR_MESSAGE); }

    private void doDb(DbAction a) {
        if (!online) { error("Kết nối CSDL không được!"); return; }
        try { a.run(); showCurrent(); }
        catch (SQLException e) { error("Lỗi CSDL: " + e.getMessage()); }
    }

    // ================= NẠP DỮ LIỆU =================
    /** Gọi sau khi cửa sổ hiện: đặt con trỏ vào ô Mã xe và nạp dữ liệu. */
    public void start() {
        loadAll();
        showCurrent();
        txtMaXe.requestFocusInWindow();
    }

    private void loadAll() {
        try {
            xes = Dao.getAllXe();
            online = true;
        } catch (SQLException e) {
            online = false;
            xes = new ArrayList<>();
            xes.add(new Xe(0, "", 0, 0));   // dòng dữ liệu ảo
            error("Kết nối CSDL không được!\n" + e.getMessage());
        }
    }

    private void reloadAndGoto(int maXe) {
        loadAll();
        for (int i = 0; i < xes.size(); i++) if (xes.get(i).maXe == maXe) idx = i;
        showCurrent();
    }

    private void showCurrent() {
        details = new ArrayList<>();
        if (xes.isEmpty() || !online) {
            txtMaXe.setText(""); txtTenXe.setText(""); txtDungTich.setText(""); txtDonGia.setText("");
            txtIdx.setText(online ? "0/0" : "0/0");
            fillTable(0);
            return;
        }
        idx = Math.max(0, Math.min(idx, xes.size() - 1));
        Xe x = xes.get(idx);
        txtMaXe.setText(String.valueOf(x.maXe));
        txtTenXe.setText(x.tenXe);
        txtDungTich.setText(String.valueOf(x.dungTich));
        txtDonGia.setText(MONEY.format(x.donGia));
        for (JTextField f : new JTextField[]{txtMaXe, txtTenXe, txtDungTich, txtDonGia}) f.setBackground(Color.WHITE);
        try { details = Dao.getChiTiet(x.maXe, null); }
        catch (SQLException e) { error("Lỗi CSDL: " + e.getMessage()); }
        txtIdx.setText((idx + 1) + "/" + xes.size());
        fillTable(x.donGia);
    }

    /** Đổ khung chi tiết (thêm cột STT) và cập nhật 2 control thống kê. */
    private void fillTable(int donGia) {
        model.setRowCount(0);
        long tong = 0;
        int stt = 1;
        for (ChiTiet c : details) {
            long tt = Math.round(c.soLuong * (double) donGia * VAT);
            tong += tt;
            model.addRow(new Object[]{stt++, c.maKH, c.tenKH, String.valueOf(c.cmnd), c.gioiTinh,
                    DF.format(c.ngayMua), c.soLuong, MONEY.format(tt)});
        }
        txtCount.setText(String.valueOf(details.size()));
        txtTong.setText(MONEY.format(tong));
    }

    // ================= ĐIỀU HƯỚNG =================
    private void nav(String what, Runnable r) {
        if (CONFIRM_NAV && !ask("Bạn có chắc chắn muốn " + what + "?")) return;
        if (xes.isEmpty()) return;
        r.run();
        showCurrent();
    }

    // ================= XE =================
    private Xe readXeFields() {
        JTextField[] f = {txtMaXe, txtTenXe, txtDungTich, txtDonGia};
        String[] n = {"Mã xe", "Tên xe", "Dung tích", "Đơn giá"};
        for (int i = 0; i < f.length; i++) {
            if (f[i].getText().trim().isEmpty()) {
                f[i].setBackground(new Color(255, 215, 215));
                error(n[i] + " không được để trống!");
                f[i].requestFocusInWindow();
                return null;
            }
        }
        try {
            int ma = Integer.parseInt(txtMaXe.getText().replace(",", "").trim());
            int dt = Integer.parseInt(txtDungTich.getText().replace(",", "").trim());
            int dg = Integer.parseInt(txtDonGia.getText().replace(",", "").trim());
            if (ma <= 0 || dt <= 0 || dg < 0) { error("Mã xe, Dung tích phải > 0 và Đơn giá phải >= 0!"); return null; }
            return new Xe(ma, txtTenXe.getText().trim(), dt, dg);
        } catch (NumberFormatException e) {
            error("Mã xe, Dung tích, Đơn giá phải là số nguyên!");
            return null;
        }
    }

    private void nhapMoi() {
        for (JTextField f : new JTextField[]{txtMaXe, txtTenXe, txtDungTich, txtDonGia}) {
            f.setText(""); f.setBackground(Color.WHITE);
        }
        txtMaXe.requestFocusInWindow();
    }

    private void themXe() {
        Xe x = readXeFields();
        if (x == null) return;
        if (!online) { error("Kết nối CSDL không được, không thể thêm xe!"); return; }
        try {
            if (Dao.existsXe(x.maXe)) {
                error("Mã xe đã tồn tại, vui lòng nhập lại!");
                txtMaXe.requestFocusInWindow(); txtMaXe.selectAll();
                return;
            }
            if (!ask("Bạn có chắc chắn muốn thêm xe " + x.tenXe + "?")) return;
            Dao.insertXe(x);
            reloadAndGoto(x.maXe);
        } catch (SQLException e) { error("Lỗi CSDL: " + e.getMessage()); }
    }

    private void xoaXe() {
        if (xes.isEmpty() || !online) { error("Không có xe để xóa hoặc kết nối CSDL không được!"); return; }
        Xe x = xes.get(idx);
        if (!ask("Bạn có chắc chắn muốn xóa xe " + x.maXe + " (" + x.tenXe + ") và các lượt mua của xe này?")) return;
        try {
            Dao.deleteXe(x.maXe);
            loadAll();        // giữ nguyên idx => hiện xe kế tiếp (nếu xóa xe cuối thì lùi lại 1)
            showCurrent();
        } catch (SQLException e) { error("Lỗi CSDL: " + e.getMessage()); }
    }

    // ================= KHÁCH HÀNG ĐÃ MUA (khung chi tiết) =================
    private boolean coXe() {
        if (!online || xes.isEmpty()) { error("Chưa có xe nào hoặc kết nối CSDL không được!"); return false; }
        return true;
    }

    private ChiTiet dialogChiTiet(ChiTiet old) {
        JTextField tMa = new JTextField(), tTen = new JTextField(), tCmnd = new JTextField(),
                tDiaChi = new JTextField(), tNgay = new JTextField(), tSL = new JTextField(), tGhi = new JTextField();
        JComboBox<String> cbGT = new JComboBox<>(new String[]{"Nam", "Nữ"});
        if (old != null) {
            tMa.setText(String.valueOf(old.maKH)); tMa.setEditable(false);
            tTen.setText(old.tenKH); tCmnd.setText(String.valueOf(old.cmnd));
            cbGT.setSelectedItem(old.gioiTinh); tDiaChi.setText(old.diaChi);
            tNgay.setText(DF.format(old.ngayMua)); tSL.setText(String.valueOf(old.soLuong));
            tGhi.setText(old.ghiChu == null ? "" : old.ghiChu);
        } else {
            tNgay.setText(DF.format(new java.util.Date())); tSL.setText("1");
        }
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        p.add(new JLabel("Mã KH:")); p.add(tMa);
        p.add(new JLabel("Tên khách hàng:")); p.add(tTen);
        p.add(new JLabel("Số CMND:")); p.add(tCmnd);
        p.add(new JLabel("Giới tính:")); p.add(cbGT);
        p.add(new JLabel("Địa chỉ:")); p.add(tDiaChi);
        p.add(new JLabel("Ngày mua (dd/MM/yyyy):")); p.add(tNgay);
        p.add(new JLabel("Số lượng:")); p.add(tSL);
        p.add(new JLabel("Ghi chú:")); p.add(tGhi);
        while (true) {
            int r = JOptionPane.showConfirmDialog(this, p,
                    old == null ? "Thêm khách hàng mua xe" : "Sửa thông tin", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (r != JOptionPane.OK_OPTION) return null;
            try {
                JTextField[] must = {tMa, tTen, tCmnd, tDiaChi, tNgay, tSL};
                for (JTextField f : must)
                    if (f.getText().trim().isEmpty()) throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin (trừ Ghi chú)!");
                ChiTiet c = new ChiTiet();
                c.maKH = Integer.parseInt(tMa.getText().trim());
                c.tenKH = tTen.getText().trim();
                c.cmnd = Long.parseLong(tCmnd.getText().trim());
                c.gioiTinh = (String) cbGT.getSelectedItem();
                c.diaChi = tDiaChi.getText().trim();
                c.ngayMua = parseDate(tNgay.getText());
                c.soLuong = Integer.parseInt(tSL.getText().trim());
                c.ghiChu = tGhi.getText().trim().isEmpty() ? null : tGhi.getText().trim();
                if (c.soLuong <= 0) throw new IllegalArgumentException("Số lượng phải > 0!");
                return c;
            } catch (NumberFormatException e) {
                error("Mã KH, CMND, Số lượng phải là số!");
            } catch (ParseException e) {
                error("Ngày mua phải đúng dạng dd/MM/yyyy!");
            } catch (IllegalArgumentException e) {
                error(e.getMessage());
            }
        }
    }

    private void themKH() {
        if (!coXe()) return;
        ChiTiet c = dialogChiTiet(null);
        if (c == null) return;
        c.maXe = xes.get(idx).maXe;
        if (!ask("Bạn có chắc chắn muốn thêm khách hàng này?")) return;
        doDb(() -> Dao.insertChiTiet(c));
    }

    private void suaKH() {
        if (!coXe()) return;
        int row = table.getSelectedRow();
        if (row < 0) { info("Hãy chọn một dòng trong bảng để sửa!"); return; }
        ChiTiet old = details.get(row);
        ChiTiet c = dialogChiTiet(old);
        if (c == null) return;
        c.maXe = old.maXe;
        if (!ask("Bạn có chắc chắn muốn lưu thay đổi?")) return;
        doDb(() -> Dao.updateChiTiet(c, old.ngayMua));
    }

    private void xoaKH() {
        if (!coXe()) return;
        int row = table.getSelectedRow();
        if (row < 0) { info("Hãy chọn một dòng trong bảng để xóa!"); return; }
        ChiTiet old = details.get(row);
        if (!ask("Bạn có chắc chắn muốn xóa lượt mua của khách hàng " + old.tenKH + "?")) return;
        doDb(() -> Dao.deleteChiTiet(old.maKH, old.maXe, old.ngayMua));
    }

    // ================= BÁO CÁO / GHI FILE / KẾT THÚC =================
    private void baoCao() {
        if (!ask("Bạn có chắc chắn muốn mở cửa sổ báo cáo?")) return;
        new ReportDialog(this, xes, online, idx).setVisible(true);
    }

    private void ghiFile() {
        if (!ask("Bạn có chắc chắn muốn ghi dữ liệu ra file txt?")) return;
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("KhachHangMuaXe_" + txtMaXe.getText().trim() + ".txt"));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (PrintWriter w = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(fc.getSelectedFile()), StandardCharsets.UTF_8))) {
            w.print('\uFEFF');
            w.println("Ma xe: " + txtMaXe.getText() + "\tTen xe: " + txtTenXe.getText()
                    + "\tDung tich: " + txtDungTich.getText() + "\tDon gia: " + txtDonGia.getText());
            StringBuilder h = new StringBuilder();
            for (int c = 0; c < model.getColumnCount(); c++) h.append(model.getColumnName(c)).append(c < model.getColumnCount() - 1 ? "\t" : "");
            w.println(h);
            for (int r = 0; r < model.getRowCount(); r++) {
                StringBuilder s = new StringBuilder();
                for (int c = 0; c < model.getColumnCount(); c++) s.append(model.getValueAt(r, c)).append(c < model.getColumnCount() - 1 ? "\t" : "");
                w.println(s);
            }
            w.println("Tong so khach hang: " + txtCount.getText());
            w.println("Tong thanh tien: " + txtTong.getText() + " VND");
            info("Đã ghi file: " + fc.getSelectedFile().getAbsolutePath());
        } catch (IOException e) {
            error("Không ghi được file: " + e.getMessage());
        }
    }

    private void ketThuc() {
        if (ask("Bạn có chắc chắn muốn thoát chương trình?")) { dispose(); System.exit(0); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame f = new MainFrame();
            f.setVisible(true);
            f.start();
        });
    }
}
