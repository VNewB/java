import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThietBiNhapKhau extends JFrame {

    // ===================== HẰNG SỐ =====================
    private static final String FILE_CSV = "DSTB.csv";
    private static final String TIEU_DE_CSV = "Mã TB,Tên TB,Giá Nhập,Số Lượng,Loại TB,Phụ Kiện,Giá Trị";
    private static final int DO_DAI_MA_TB = 8;

    private static final String LOAI_LAO_DONG = "Lao động";
    private static final String LOAI_SAN_XUAT = "Sản xuất";
    private static final double TY_LE_LAO_DONG = 1.10;
    private static final double TY_LE_SAN_XUAT = 1.30;

    private static final String PK_DAYCAP = "Dây cáp";
    private static final String PK_BANHXE = "Bánh xe";
    private static final String PK_THAYTHE = "Thay thế";
    private static final double TY_LE_DAYCAP = 0.1;
    private static final double TY_LE_BANHXE = 0.05;
    private static final double TY_LE_THAYTHE = 0.08;

    private static final Font FONT_TIEU_DE = new Font("Serif", Font.BOLD, 36);
    private static final Font FONT_NHAN = new Font("Serif", Font.BOLD, 16);
    private static final Color MAU_DO = Color.RED;
    private static final Color MAU_XANH = Color.BLUE;
    private static final Color MAU_TIM = new Color(128, 0, 128);

    // ===================== MODEL =====================
    static class ThietBi {
        final String maTB;
        final String tenTB;
        final double giaNhap;
        final int soLuong;
        final String loaiTB;
        final String phuKien;
        final double giaTri;


        ThietBi(String maTB, String tenTB, double giaNhap, int soLuong,
                String loaiTB, String phuKien, double giaTri) {
            this.maTB = maTB;
            this.tenTB = tenTB;
            this.giaNhap = giaNhap;
            this.soLuong = soLuong;
            this.loaiTB = loaiTB;
            this.phuKien = phuKien;
            this.giaTri = giaTri;
        }
    }

    static class DanhSachThietBi {
        private final List<ThietBi> danhSach = new ArrayList<>();

        int soLuong() { return danhSach.size(); }
        ThietBi layTai(int viTri) { return danhSach.get(viTri); }
        void them(ThietBi ThietBi) { danhSach.add(ThietBi); }
        void xoaTai(int viTri) { danhSach.remove(viTri); }

        boolean tonTaimaTB(String maTB) {
            for (ThietBi ThietBi : danhSach) {
                if (ThietBi.maTB.equalsIgnoreCase(maTB.trim())) return true;
            }
            return false;
        }

        double tongGiaTri() {
            double tong = 0;
            for (ThietBi ThietBi : danhSach) tong += ThietBi.giaTri;
            return tong;
        }

        int tongSoLuong() {
            int tong = 0;
            for (ThietBi ThietBi : danhSach) tong += ThietBi.soLuong;
            return tong;
        }

        boolean luuCsv(String duongDan) {
            try (BufferedWriter ghi = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(duongDan), StandardCharsets.UTF_8))) {
                ghi.write("\uFEFF" + TIEU_DE_CSV);
                ghi.newLine();
                for (ThietBi m : danhSach) {
                    ghi.write(String.format(Locale.US, "%s,\"%s\",%.0f,%d,%s,\"%s\",%.2f",
                            m.maTB, m.tenTB.replace("\"", ""), m.giaNhap, m.soLuong,
                            m.loaiTB, m.phuKien, m.giaTri));
                    ghi.newLine();
                }
                return true;
            } catch (IOException loi) {
                System.err.println("Lỗi ghi file CSV '" + duongDan + "': " + loi.getMessage());
                return false;
            }
        }

        boolean docCsv(String duongDan) {
            danhSach.clear();
            if (!new File(duongDan).exists()) return false;

            try (BufferedReader doc = new BufferedReader(
                    new InputStreamReader(new FileInputStream(duongDan), StandardCharsets.UTF_8))) {
                doc.readLine();
                String dong;
                while ((dong = doc.readLine()) != null) {
                    String[] cot = dong.replace("\uFEFF", "")
                            .split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    if (cot.length < 7) continue;
                    danhSach.add(new ThietBi(
                            cot[0].trim(),
                            cot[1].replace("\"", "").trim(),
                            Double.parseDouble(cot[2].trim()),
                            Integer.parseInt(cot[3].trim()),
                            cot[4].trim(),
                            cot[5].replace("\"", "").trim(),
                            Double.parseDouble(cot[6].trim())));
                }
                return true;
            } catch (IOException | NumberFormatException loi) {
                System.err.println("Lỗi đọc file CSV '" + duongDan + "': " + loi.getMessage());
                return false;
            }
        }
    }

    // ===================== TRẠNG THÁI =====================
    private final DanhSachThietBi danhSach = new DanhSachThietBi();
    private final List<JLabel> danhSachNhan = new ArrayList<>();
    private int viTriHienTai = -1;

    // ===================== THÀNH PHẦN GIAO DIỆN =====================
    private JTextField txtmaTB, txttenTB, txtgiaNhap, txtSoLuong;
    private JTextField txtGiaTri, txtTongGiaTri, txtTongSoLuong, txtViTri;
    private JRadioButton rbLaoDong, rbSanXuat;
    private JRadioButton rbMauDo, rbMauXanh, rbMauTim;
    private JCheckBox cbDayCap, cbBanhXe, cbThayThe;
    private JButton btnNhapVao, btnNhapMoi, btnXoa, btnThoat;
    private JButton btnVeDau, btnVeTruoc, btnVeSau, btnVeCuoi;


    private JLabel tieuDe;

    JLabel thoiGian;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // ===================== KHỞI TẠO =====================
    public ThietBiNhapKhau() {
        khoiTaoCuaSo();
        taoGiaoDien();
        ganSuKien();
        napDuLieuBanDau();
        setVisible(true);
        txtmaTB.requestFocusInWindow();
    }

    private void khoiTaoCuaSo() {
        setTitle("Giao diện");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void napDuLieuBanDau() {
        danhSach.docCsv(FILE_CSV);
        if (danhSach.soLuong() > 0) {
            viTriHienTai = 0;
            hienThiThietBi(viTriHienTai);
        }
        capNhatTrangThai();
    }

    // ===================== DỰNG GIAO DIỆN =====================
    private void taoGiaoDien() {
        // Thời gian
        thoiGian = new JLabel();
        thoiGian.setHorizontalAlignment(SwingConstants.LEFT);

        // Tiêu đề
        tieuDe = new JLabel("Thiết bị nhập khẩu", SwingConstants.CENTER);
        tieuDe.setForeground(Color.RED);
        tieuDe.setFont(FONT_TIEU_DE);

        // Panel phía trên
        JPanel phiaTren = new JPanel(new BorderLayout());
        phiaTren.add(thoiGian, BorderLayout.WEST);
        phiaTren.add(tieuDe, BorderLayout.CENTER);
        add(phiaTren, BorderLayout.NORTH);
        JPanel noiDung = new JPanel(new GridBagLayout());
        GridBagConstraints vitri = new GridBagConstraints();

        vitri.anchor = GridBagConstraints.NORTH;

        vitri.gridx = 0;
        noiDung.add(taoPanelTrai(), vitri);

        vitri.gridx = 1;
        noiDung.add(taoPanelPhai(), vitri);
        add(noiDung, BorderLayout.CENTER);

        capNhatThoiGian();
        Timer timer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();

            if (now.getSecond() == 0) {
                capNhatThoiGian();
            }
        });
        timer.start();
    }

    private void capNhatThoiGian() {
        DateTimeFormatter dinhDang =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        thoiGian.setText(
                LocalDateTime.now().format(dinhDang)
        );
    }

    private JPanel taoPanelTrai() {
        txtmaTB = new JTextField(15);
        txttenTB = new JTextField(15);
        txtgiaNhap = new JTextField(15);
        txtSoLuong = new JTextField(15);

        JPanel thongTin = new JPanel(new GridBagLayout());
        thongTin.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        themDong(thongTin, "Mã TB:", txtmaTB, 0);
        themDong(thongTin, "Tên TB:", txttenTB, 1);
        themDong(thongTin, "Giá nhập:", txtgiaNhap, 2);
        themDong(thongTin, "Số lượng:", txtSoLuong, 3);

        rbLaoDong = new JRadioButton(LOAI_LAO_DONG, true);
        rbSanXuat = new JRadioButton(LOAI_SAN_XUAT);
        JPanel panelloaiTB = taoPanelNhom("Loại TB", rbLaoDong, rbSanXuat);
        nhomRadio(rbLaoDong, rbSanXuat);

        cbDayCap = new JCheckBox(PK_DAYCAP);
        cbBanhXe = new JCheckBox(PK_BANHXE);
        cbThayThe = new JCheckBox(PK_THAYTHE);
        JPanel panelPhuKien = taoPanelNhom("Phụ kiện", cbDayCap, cbBanhXe, cbThayThe);

        JPanel hangDuoi = new JPanel(new GridBagLayout());
        GridBagConstraints vitri = new GridBagConstraints();
        vitri.anchor = GridBagConstraints.NORTH;
        vitri.gridx = 0;
        hangDuoi.add(panelloaiTB, vitri);
        vitri.gridx = 1;
        hangDuoi.add(panelPhuKien, vitri);

        JPanel panelTrai = new JPanel(new BorderLayout());
        panelTrai.add(thongTin, BorderLayout.NORTH);
        panelTrai.add(hangDuoi, BorderLayout.CENTER);
        return panelTrai;
    }

    private JPanel taoPanelPhai() {
        btnNhapVao = new JButton("Nhập máy");
        btnNhapMoi = new JButton("Nhập mới");
        JPanel hangNhap = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hangNhap.add(btnNhapVao);
        hangNhap.add(btnNhapMoi);

        txtGiaTri = new JTextField(15);
        txtGiaTri.setEditable(false);
        JPanel panelGiaTri = new JPanel(new GridBagLayout());
        themDong(panelGiaTri, "Giá trị:", txtGiaTri, 0);

        txtTongGiaTri = new JTextField(15);
        txtTongSoLuong = new JTextField(15);
        txtTongGiaTri.setEditable(false);
        txtTongSoLuong.setEditable(false);
        JPanel panelThongKe = new JPanel(new GridBagLayout());
        panelThongKe.setBorder(BorderFactory.createTitledBorder("Thống kê"));
        themDong(panelThongKe, "Tổng giá trị:", txtTongGiaTri, 0);
        themDong(panelThongKe, "Tổng lượng:", txtTongSoLuong, 1);

        JPanel panelDieuHuong = taoPanelDieuHuong();

        rbMauDo = new JRadioButton("Đỏ", true);
        rbMauXanh = new JRadioButton("Xanh");
        rbMauTim = new JRadioButton("Tím");
        nhomRadio(rbMauDo, rbMauXanh, rbMauTim);
        JPanel panelMauSac = taoPanelNhom("Màu sắc", rbMauDo, rbMauXanh, rbMauTim);

        JPanel phanGiua = new JPanel(new GridBagLayout());
        GridBagConstraints vitri = new GridBagConstraints();
        vitri.anchor = GridBagConstraints.WEST;
        vitri.gridy = 0;
        phanGiua.add(panelGiaTri, vitri);
        vitri.gridy = 1;
        phanGiua.add(panelThongKe, vitri);
        vitri.gridy = 2;
        phanGiua.add(panelDieuHuong, vitri);
        vitri.gridy = 3;
        phanGiua.add(panelMauSac, vitri);

        btnXoa = new JButton("Xóa");
        btnThoat = new JButton("Thoát");
        JPanel hangCuoi = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hangCuoi.add(btnXoa);
        hangCuoi.add(btnThoat);

        JPanel panelPhai = new JPanel(new BorderLayout());
        panelPhai.add(hangNhap, BorderLayout.NORTH);
        panelPhai.add(phanGiua, BorderLayout.CENTER);
        panelPhai.add(hangCuoi, BorderLayout.SOUTH);
        return panelPhai;
    }

    private JPanel taoPanelDieuHuong() {
        btnVeDau = new JButton("Về đầu");
        btnVeTruoc = new JButton("Về trước");
        btnVeSau = new JButton("Về sau");
        btnVeCuoi = new JButton("Về cuối");
        txtViTri = new JTextField("0/0", 4);
        txtViTri.setHorizontalAlignment(JTextField.CENTER);
        txtViTri.setEditable(false);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(btnVeDau);
        panel.add(btnVeTruoc);
        panel.add(txtViTri);
        panel.add(btnVeSau);
        panel.add(btnVeCuoi);
        return panel;
    }

    private JPanel taoPanelNhom(String tieuDe, JComponent... thanhPhan) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(tieuDe));
        GridBagConstraints vitri = new GridBagConstraints();
        vitri.anchor = GridBagConstraints.WEST;
        for (int hang = 0; hang < thanhPhan.length; hang++) {
            vitri.gridy = hang;
            panel.add(thanhPhan[hang], vitri);
        }
        return panel;
    }

    private void nhomRadio(AbstractButton... cacNut) {
        ButtonGroup nhom = new ButtonGroup();
        for (AbstractButton nut : cacNut) nhom.add(nut);
    }

    private void themDong(JPanel panel, String noiDungNhan, JTextField oNhap, int hang) {
        GridBagConstraints vitri = new GridBagConstraints();
        vitri.anchor = GridBagConstraints.WEST;
        vitri.gridy = hang;

        JLabel nhan = new JLabel(noiDungNhan);
        nhan.setForeground(MAU_DO);
        nhan.setFont(FONT_NHAN);
        danhSachNhan.add(nhan);

        vitri.gridx = 0;
        panel.add(nhan, vitri);
        vitri.gridx = 1;
        vitri.fill = GridBagConstraints.HORIZONTAL;
        panel.add(oNhap, vitri);
    }

    // ===================== SỰ KIỆN =====================
    private void ganSuKien() {
        for (JTextField oNhap : new JTextField[]{txtmaTB, txttenTB, txtgiaNhap, txtSoLuong}) {
            khiNoiDungThayDoi(oNhap, this::capNhatTrangThai);
        }

        btnNhapMoi.addActionListener(e -> xuLyNhapMoi());
        btnNhapVao.addActionListener(e -> xuLyNhapTB());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnThoat.addActionListener(e -> xuLyThoat());

        btnVeDau.addActionListener(e -> diDenViTri(0));
        btnVeTruoc.addActionListener(e -> diDenViTri(viTriHienTai - 1));
        btnVeSau.addActionListener(e -> diDenViTri(viTriHienTai + 1));
        btnVeCuoi.addActionListener(e -> diDenViTri(danhSach.soLuong() - 1));

        for (JRadioButton nutMau : new JRadioButton[]{rbMauDo, rbMauXanh, rbMauTim}) {
            nutMau.addActionListener(e -> capNhatMauNhan());
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) { xuLyThoat(); }
        });
    }

    private void khiNoiDungThayDoi(JTextField oNhap, Runnable hanhDong) {
        oNhap.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { hanhDong.run(); }
            @Override public void removeUpdate(DocumentEvent e) { hanhDong.run(); }
            @Override public void changedUpdate(DocumentEvent e) { hanhDong.run(); }
        });
    }

    private void xuLyNhapMoi() {
        xoaForm();
        txtmaTB.requestFocusInWindow();
    }

    private void xuLyNhapTB() {
        ThietBi TBMoi = docThietBiTuForm();
        danhSach.them(TBMoi);
        danhSach.luuCsv(FILE_CSV);

        viTriHienTai = danhSach.soLuong() - 1;
        hienThiThietBi(viTriHienTai);
        capNhatTrangThai();
        thongBao("Thêm máy tính thành công!");
    }

    private void xuLyXoa() {
        if (viTriHienTai < 0) return;
        int luaChon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa máy tính đang hiển thị không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (luaChon != JOptionPane.YES_OPTION) return;

        danhSach.xoaTai(viTriHienTai);
        danhSach.luuCsv(FILE_CSV);

        if (danhSach.soLuong() == 0) {
            viTriHienTai = -1;
            xoaForm();
        } else {
            viTriHienTai = Math.min(viTriHienTai, danhSach.soLuong() - 1);
            hienThiThietBi(viTriHienTai);
        }
        capNhatTrangThai();
        thongBao("Đã xóa máy tính thành công!");
    }

    private void xuLyThoat() {
        int luaChon = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn thoát chương trình không?",
                "Xác nhận thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (luaChon == JOptionPane.YES_OPTION) System.exit(0);
    }

    private void diDenViTri(int viTriMoi) {
        if (viTriMoi < 0 || viTriMoi >= danhSach.soLuong()) return;
        viTriHienTai = viTriMoi;
        hienThiThietBi(viTriHienTai);
        capNhatTrangThai();
    }

    // ===================== NGHIỆP VỤ =====================
    private boolean formHopLe() {
        String maTB = txtmaTB.getText().trim();
        return maTB.length() == DO_DAI_MA_TB
                && !danhSach.tonTaimaTB(maTB)
                && !txttenTB.getText().trim().isEmpty()
                && laSoNguyenDuong(txtgiaNhap.getText())
                && laSoNguyenDuong(txtSoLuong.getText());
    }

    private boolean laSoNguyenDuong(String noiDung) {
        try {
            return Long.parseLong(noiDung.trim()) > 0;
        } catch (NumberFormatException loi) {
            return false;
        }
    }

    private ThietBi docThietBiTuForm() {
        double giaNhap = Long.parseLong(txtgiaNhap.getText().trim());
        int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        boolean laLapRap = rbLaoDong.isSelected();

        double tyLePhuKien = 0;
        List<String> phuKien = new ArrayList<>();
        if (cbDayCap.isSelected()) { tyLePhuKien += TY_LE_DAYCAP; phuKien.add(PK_DAYCAP); }
        if (cbBanhXe.isSelected()) { tyLePhuKien += TY_LE_BANHXE; phuKien.add(PK_BANHXE); }
        if (cbThayThe.isSelected()) { tyLePhuKien += TY_LE_THAYTHE; phuKien.add(PK_THAYTHE); }

        double tyLeloaiTB = laLapRap ? TY_LE_LAO_DONG : TY_LE_SAN_XUAT;
        double giaTri = soLuong * giaNhap * (tyLeloaiTB + tyLePhuKien);

        return new ThietBi(
                txtmaTB.getText().trim(),
                txttenTB.getText().trim(),
                giaNhap, soLuong,
                laLapRap ? LOAI_LAO_DONG : LOAI_SAN_XUAT,
                String.join(", ", phuKien),
                giaTri);
    }

    // ===================== CẬP NHẬT GIAO DIỆN =====================
    private void hienThiThietBi(int viTri) {
        ThietBi TB = danhSach.layTai(viTri);
        txtmaTB.setText(TB.maTB);
        txttenTB.setText(TB.tenTB);
        txtgiaNhap.setText(String.format(Locale.US, "%.0f", TB.giaNhap));
        txtSoLuong.setText(String.valueOf(TB.soLuong));
        txtGiaTri.setText(String.format(Locale.US, "%.2f", TB.giaTri));

        rbLaoDong.setSelected(LOAI_LAO_DONG.equalsIgnoreCase(TB.loaiTB));
        rbSanXuat.setSelected(!rbLaoDong.isSelected());
        cbDayCap.setSelected(TB.phuKien.contains(PK_DAYCAP));
        cbBanhXe.setSelected(TB.phuKien.contains(PK_BANHXE));
        cbThayThe.setSelected(TB.phuKien.contains(PK_THAYTHE));
    }

    private void xoaForm() {
        for (JTextField oNhap : new JTextField[]{txtmaTB, txttenTB, txtgiaNhap, txtSoLuong, txtGiaTri}) {
            oNhap.setText("");
        }
        rbLaoDong.setSelected(true);
        cbDayCap.setSelected(false);
        cbBanhXe.setSelected(false);
        cbThayThe.setSelected(false);
    }

    private void capNhatTrangThai() {
        int tongSoTB = danhSach.soLuong();
        boolean coDuLieu = tongSoTB > 0;
        boolean dangXemTB = viTriHienTai >= 0;

        txtViTri.setText(dangXemTB ? (viTriHienTai + 1) + "/" + tongSoTB : "0/" + tongSoTB);
        txtTongGiaTri.setText(String.format(Locale.US, "%.2f", danhSach.tongGiaTri()));
        txtTongSoLuong.setText(String.valueOf(danhSach.tongSoLuong()));

        btnNhapVao.setEnabled(formHopLe());
        btnXoa.setEnabled(dangXemTB);
        btnVeDau.setEnabled(coDuLieu && viTriHienTai > 0);
        btnVeTruoc.setEnabled(coDuLieu && viTriHienTai > 0);
        btnVeSau.setEnabled(coDuLieu && viTriHienTai < tongSoTB - 1);
        btnVeCuoi.setEnabled(coDuLieu && viTriHienTai < tongSoTB - 1);
    }

    private void capNhatMauNhan() {
        // Em thấy theo như câu c thì phần tử quan trọng trên form có màu đỏ bao gồm cả tiêu đề
        Color mauChon = rbMauXanh.isSelected() ? MAU_XANH
                : rbMauTim.isSelected() ? MAU_TIM
                : MAU_DO;
        for (JLabel nhan : danhSachNhan) nhan.setForeground(mauChon);
        tieuDe.setForeground(mauChon);
    }

    private void thongBao(String noiDung) {
        JOptionPane.showMessageDialog(this, noiDung, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================== CHẠY CHƯƠNG TRÌNH =====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ThietBiNhapKhau::new);
    }
}