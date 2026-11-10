import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

/** Cửa sổ báo cáo: lọc theo Mã xe + Ngày mua (có thể để trống ngày). */
public class ReportDialog extends JDialog {
    private final List<Xe> xes;
    private final boolean online;
    private final JComboBox<Integer> cbMaXe = new JComboBox<>();
    private final JTextField txtNgay = new JTextField(10), txtNguoiLap = new JTextField("Nguyễn Kiều Nhung", 14);
    private final JLabel lblMa = new JLabel(), lblTen = new JLabel(), lblGia = new JLabel(),
            lblDT = new JLabel(), lblCount = new JLabel("0"), lblNgayLap = new JLabel();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"STT", "Mã khách hàng", "Ngày mua", "Số lượng", "Ghi chú"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);

    public ReportDialog(JFrame owner, List<Xe> xes, boolean online, int selectedIdx) {
        super(owner, "Báo cáo thống kê khách hàng đã mua xe", true);
        this.xes = xes;
        this.online = online;
        for (Xe x : xes) cbMaXe.addItem(x.maXe);
        if (online && !xes.isEmpty()) cbMaXe.setSelectedIndex(Math.max(0, Math.min(selectedIdx, xes.size() - 1)));

        JButton bRun = new JButton("Thực hiện báo cáo"), bPrint = new JButton("In"), bClose = new JButton("Đóng");
        JPanel filter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filter.setBorder(BorderFactory.createTitledBorder("Tiêu chí"));
        filter.add(new JLabel("Mã xe:")); filter.add(cbMaXe);
        filter.add(new JLabel("Ngày mua (dd/MM/yyyy):")); filter.add(txtNgay);
        filter.add(bRun);

        JPanel head = new JPanel(new GridLayout(2, 4, 8, 4));
        head.add(new JLabel("Mã xe:")); head.add(lblMa);
        head.add(new JLabel("Tên xe:")); head.add(lblTen);
        head.add(new JLabel("Đơn giá:")); head.add(lblGia);
        head.add(new JLabel("Dung tích:")); head.add(lblDT);

        JLabel shop = new JLabel("Cửa hàng Mô tô PKL");
        shop.setFont(shop.getFont().deriveFont(Font.BOLD | Font.ITALIC));
        JLabel title = new JLabel("THỐNG KÊ KHÁCH HÀNG ĐÃ MUA XE", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(shop, BorderLayout.NORTH);
        top.add(title, BorderLayout.CENTER);
        JPanel topAll = new JPanel(new BorderLayout(0, 8));
        topAll.setOpaque(false);
        topAll.add(top, BorderLayout.NORTH);
        topAll.add(head, BorderLayout.CENTER);

        LocalDate d = LocalDate.now();
        lblNgayLap.setText("TP.HCM, ngày " + d.getDayOfMonth() + " tháng " + d.getMonthValue() + " năm " + d.getYear());
        JPanel foot = new JPanel();
        foot.setOpaque(false);
        foot.setLayout(new BoxLayout(foot, BoxLayout.Y_AXIS));
        JPanel cnt = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cnt.setOpaque(false);
        cnt.add(new JLabel("Số lượng khách hàng:")); cnt.add(lblCount);
        JPanel sign = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sign.setOpaque(false);
        sign.add(new JLabel("Người lập:")); sign.add(txtNguoiLap);
        JPanel dl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        dl.setOpaque(false);
        dl.add(lblNgayLap);
        foot.add(cnt); foot.add(dl); foot.add(sign);

        JPanel report = new JPanel(new BorderLayout(0, 8));
        report.setBackground(Color.WHITE);
        report.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        report.add(topAll, BorderLayout.NORTH);
        report.add(new JScrollPane(table), BorderLayout.CENTER);
        report.add(foot, BorderLayout.SOUTH);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(bPrint); south.add(bClose);

        setLayout(new BorderLayout());
        add(filter, BorderLayout.NORTH);
        add(report, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        setSize(760, 560);
        setLocationRelativeTo(owner);

        bRun.addActionListener(e -> run());
        bClose.addActionListener(e -> dispose());
        bPrint.addActionListener(e -> {
            try { table.print(); } catch (PrinterException ex) { err("Không in được: " + ex.getMessage()); }
        });
    }

    private void err(String m) { JOptionPane.showMessageDialog(this, m, "Lỗi", JOptionPane.ERROR_MESSAGE); }

    private void run() {
        if (!online) { err("Kết nối CSDL không được, không thể lập báo cáo!"); return; }
        if (cbMaXe.getSelectedItem() == null) { err("Chưa chọn Mã xe!"); return; }
        int ma = (Integer) cbMaXe.getSelectedItem();
        java.sql.Date ngay = null;
        if (!txtNgay.getText().trim().isEmpty()) {
            try { ngay = MainFrame.parseDate(txtNgay.getText()); }
            catch (ParseException e) { err("Ngày mua phải đúng dạng dd/MM/yyyy!"); return; }
        }
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thực hiện báo cáo?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        Xe x = null;
        for (Xe t : xes) if (t.maXe == ma) x = t;
        try {
            List<ChiTiet> ds = Dao.getChiTiet(ma, ngay);
            lblMa.setText(String.valueOf(x.maXe)); lblTen.setText(x.tenXe);
            lblGia.setText(MainFrame.MONEY.format(x.donGia)); lblDT.setText(String.valueOf(x.dungTich));
            model.setRowCount(0);
            int stt = 1;
            for (ChiTiet c : ds)
                model.addRow(new Object[]{stt++, c.maKH, MainFrame.DF.format(c.ngayMua), c.soLuong,
                        c.ghiChu == null ? "" : c.ghiChu});
            lblCount.setText(String.valueOf(ds.size()));
        } catch (SQLException e) {
            err("Lỗi CSDL: " + e.getMessage());
        }
    }
}
