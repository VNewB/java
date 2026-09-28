import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MayTinhLapRap extends JFrame {

    // ===================== HẰNG SỐ =====================
    private static final String FILE_CSV = "DSMT.csv";
    private static final String TIEU_DE_CSV = "Mã Máy,Tên Máy,Đơn Giá,Số Lượng,Loại Máy,Phụ Kiện,Giá Trị";
    private static final int DO_DAI_MA_MAY = 5;

    private static final String LOAI_LAP_RAP = "Lắp ráp";
    private static final String LOAI_NHAP_KHAU = "Nhập khẩu";
    private static final double TY_LE_LAP_RAP = 1.10;
    private static final double TY_LE_NHAP_KHAU = 1.20;

    private static final String PK_VGA = "VGA 2 GB";
    private static final String PK_DVD = "DVD";
    private static final String PK_LOA = "LOA";
    private static final double TY_LE_VGA = 0.30;
    private static final double TY_LE_DVD = 0.05;
    private static final double TY_LE_LOA = 0.10;

    private static final Font FONT_TIEU_DE = new Font("Serif", Font.BOLD, 20);
    private static final Font FONT_NHAN = new Font("Serif", Font.BOLD, 16);
    private static final Color MAU_DO = Color.RED;
    private static final Color MAU_XANH = Color.BLUE;
    private static final Color MAU_TIM = new Color(128, 0, 128);

    // ===================== MODEL =====================
    static class MayTinh {
        final String maMay;
        final String tenMay;
        final double donGia;
        final int soLuong;
        final String loaiMay;
        final String phuKien;
        final double giaTri;

        MayTinh(String maMay, String tenMay, double donGia, int soLuong,
                String loaiMay, String phuKien, double giaTri) {
            this.maMay = maMay;
            this.tenMay = tenMay;
            this.donGia = donGia;
            this.soLuong = soLuong;
            this.loaiMay = loaiMay;
            this.phuKien = phuKien;
            this.giaTri = giaTri;
        }
    }

    static class DanhSachMayTinh {
        private final List<MayTinh> danhSach = new ArrayList<>();

        int soLuong() { return danhSach.size(); }
        MayTinh layTai(int viTri) { return danhSach.get(viTri); }
        void them(MayTinh mayTinh) { danhSach.add(mayTinh); }
        void xoaTai(int viTri) { danhSach.remove(viTri); }

        boolean tonTaiMaMay(String maMay) {
            for (MayTinh mayTinh : danhSach) {
                if (mayTinh.maMay.equalsIgnoreCase(maMay.trim())) return true;
            }
            return false;
        }

        double tongGiaTri() {
            double tong = 0;
            for (MayTinh mayTinh : danhSach) tong += mayTinh.giaTri;
            return tong;
        }

        int tongSoLuong() {
            int tong = 0;
            for (MayTinh mayTinh : danhSach) tong += mayTinh.soLuong;
            return tong;
        }

        boolean luuCsv(String duongDan) {
            try (BufferedWriter ghi = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(duongDan), StandardCharsets.UTF_8))) {
                ghi.write("\uFEFF" + TIEU_DE_CSV);
                ghi.newLine();
                for (MayTinh m : danhSach) {
                    ghi.write(String.format(Locale.US, "%s,\"%s\",%.0f,%d,%s,\"%s\",%.2f",
                            m.maMay, m.tenMay.replace("\"", ""), m.donGia, m.soLuong,
                            m.loaiMay, m.phuKien, m.giaTri));
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
                    danhSach.add(new MayTinh(
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
    private final DanhSachMayTinh danhSach = new DanhSachMayTinh();
    private final List<JLabel> danhSachNhan = new ArrayList<>();
    private int viTriHienTai = -1;

    // ===================== THÀNH PHẦN GIAO DIỆN =====================
    private JTextField txtMaMay, txtTenMay, txtDonGia, txtSoLuong;
    private JTextField txtGiaTri, txtTongGiaTri, txtTongSoLuong, txtViTri;
    private JRadioButton rbLapRap, rbNhapKhau;
    private JRadioButton rbMauDo, rbMauXanh, rbMauTim;
    private JCheckBox cbVga, cbDvd, cbLoa;
    private JButton btnNhapMay, btnNhapMoi, btnXoa, btnThoat;
    private JButton btnVeDau, btnVeTruoc, btnVeSau, btnVeCuoi;

    // ===================== KHỞI TẠO =====================
    public MayTinhLapRap() {
        khoiTaoCuaSo();
        taoGiaoDien();
        ganSuKien();
        napDuLieuBanDau();
        setVisible(true);
        txtMaMay.requestFocusInWindow();
    }

    private void khoiTaoCuaSo() {
        setTitle("Quản lý máy tính lắp ráp");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void napDuLieuBanDau() {
        danhSach.docCsv(FILE_CSV);
        if (danhSach.soLuong() > 0) {
            viTriHienTai = 0;
            hienThiMayTinh(viTriHienTai);
        }
        capNhatTrangThai();
    }

    // ===================== DỰNG GIAO DIỆN =====================
    private void taoGiaoDien() {
        JLabel tieuDe = new JLabel("MÁY TÍNH LẮP RÁP", SwingConstants.CENTER);
        tieuDe.setForeground(Color.BLUE);
        tieuDe.setFont(FONT_TIEU_DE);
        add(tieuDe, BorderLayout.NORTH);

        JPanel noiDung = new JPanel(new GridBagLayout());
        GridBagConstraints vitri = new GridBagConstraints();
        vitri.anchor = GridBagConstraints.NORTH;
        vitri.gridx = 0;
        noiDung.add(taoPanelTrai(), vitri);
        vitri.gridx = 1;
        noiDung.add(taoPanelPhai(), vitri);
        add(noiDung, BorderLayout.CENTER);
    }

    private JPanel taoPanelTrai() {
        txtMaMay = new JTextField(15);
        txtTenMay = new JTextField(15);
        txtDonGia = new JTextField(15);
        txtSoLuong = new JTextField(15);

        JPanel thongTin = new JPanel(new GridBagLayout());
        thongTin.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        themDong(thongTin, "Mã máy:", txtMaMay, 0);
        themDong(thongTin, "Tên máy:", txtTenMay, 1);
        themDong(thongTin, "Đơn giá:", txtDonGia, 2);
        themDong(thongTin, "Số lượng:", txtSoLuong, 3);

        rbLapRap = new JRadioButton(LOAI_LAP_RAP, true);
        rbNhapKhau = new JRadioButton(LOAI_NHAP_KHAU);
        JPanel panelLoaiMay = taoPanelNhom("Loại máy", rbLapRap, rbNhapKhau);
        nhomRadio(rbLapRap, rbNhapKhau);

        cbVga = new JCheckBox(PK_VGA);
        cbDvd = new JCheckBox(PK_DVD);
        cbLoa = new JCheckBox(PK_LOA);
        JPanel panelPhuKien = taoPanelNhom("Phụ kiện", cbVga, cbDvd, cbLoa);

        JPanel hangDuoi = new JPanel(new GridBagLayout());
        GridBagConstraints vitri = new GridBagConstraints();
        vitri.anchor = GridBagConstraints.NORTH;
        vitri.gridx = 0;
        hangDuoi.add(panelLoaiMay, vitri);
        vitri.gridx = 1;
        hangDuoi.add(panelPhuKien, vitri);

        JPanel panelTrai = new JPanel(new BorderLayout());
        panelTrai.add(thongTin, BorderLayout.NORTH);
        panelTrai.add(hangDuoi, BorderLayout.CENTER);
        return panelTrai;
    }

    private JPanel taoPanelPhai() {
        btnNhapMay = new JButton("Nhập máy");
        btnNhapMoi = new JButton("Nhập mới");
        JPanel hangNhap = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hangNhap.add(btnNhapMay);
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
        themDong(panelThongKe, "Tổng giá:", txtTongGiaTri, 0);
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
        for (JTextField oNhap : new JTextField[]{txtMaMay, txtTenMay, txtDonGia, txtSoLuong}) {
            khiNoiDungThayDoi(oNhap, this::capNhatTrangThai);
        }

        btnNhapMoi.addActionListener(e -> xuLyNhapMoi());
        btnNhapMay.addActionListener(e -> xuLyNhapMay());
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
        txtMaMay.requestFocusInWindow();
    }

    private void xuLyNhapMay() {
        MayTinh mayMoi = docMayTinhTuForm();
        danhSach.them(mayMoi);
        danhSach.luuCsv(FILE_CSV);

        viTriHienTai = danhSach.soLuong() - 1;
        hienThiMayTinh(viTriHienTai);
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
            hienThiMayTinh(viTriHienTai);
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
        hienThiMayTinh(viTriHienTai);
        capNhatTrangThai();
    }

    // ===================== NGHIỆP VỤ =====================
    private boolean formHopLe() {
        String maMay = txtMaMay.getText().trim();
        return maMay.length() == DO_DAI_MA_MAY
                && !danhSach.tonTaiMaMay(maMay)
                && !txtTenMay.getText().trim().isEmpty()
                && laSoNguyenDuong(txtDonGia.getText())
                && laSoNguyenDuong(txtSoLuong.getText());
    }

    private boolean laSoNguyenDuong(String noiDung) {
        try {
            return Long.parseLong(noiDung.trim()) > 0;
        } catch (NumberFormatException loi) {
            return false;
        }
    }

    private MayTinh docMayTinhTuForm() {
        double donGia = Long.parseLong(txtDonGia.getText().trim());
        int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        boolean laLapRap = rbLapRap.isSelected();

        double tyLePhuKien = 0;
        List<String> phuKien = new ArrayList<>();
        if (cbVga.isSelected()) { tyLePhuKien += TY_LE_VGA; phuKien.add(PK_VGA); }
        if (cbDvd.isSelected()) { tyLePhuKien += TY_LE_DVD; phuKien.add(PK_DVD); }
        if (cbLoa.isSelected()) { tyLePhuKien += TY_LE_LOA; phuKien.add(PK_LOA); }

        double tyLeLoaiMay = laLapRap ? TY_LE_LAP_RAP : TY_LE_NHAP_KHAU;
        double giaTri = soLuong * donGia * (tyLeLoaiMay + tyLePhuKien);

        return new MayTinh(
                txtMaMay.getText().trim(),
                txtTenMay.getText().trim(),
                donGia, soLuong,
                laLapRap ? LOAI_LAP_RAP : LOAI_NHAP_KHAU,
                String.join(", ", phuKien),
                giaTri);
    }

    // ===================== CẬP NHẬT GIAO DIỆN =====================
    private void hienThiMayTinh(int viTri) {
        MayTinh may = danhSach.layTai(viTri);
        txtMaMay.setText(may.maMay);
        txtTenMay.setText(may.tenMay);
        txtDonGia.setText(String.format(Locale.US, "%.0f", may.donGia));
        txtSoLuong.setText(String.valueOf(may.soLuong));
        txtGiaTri.setText(String.format(Locale.US, "%.2f", may.giaTri));

        rbLapRap.setSelected(LOAI_LAP_RAP.equalsIgnoreCase(may.loaiMay));
        rbNhapKhau.setSelected(!rbLapRap.isSelected());
        cbVga.setSelected(may.phuKien.contains(PK_VGA));
        cbDvd.setSelected(may.phuKien.contains(PK_DVD));
        cbLoa.setSelected(may.phuKien.contains(PK_LOA));
    }

    private void xoaForm() {
        for (JTextField oNhap : new JTextField[]{txtMaMay, txtTenMay, txtDonGia, txtSoLuong, txtGiaTri}) {
            oNhap.setText("");
        }
        rbLapRap.setSelected(true);
        cbVga.setSelected(false);
        cbDvd.setSelected(false);
        cbLoa.setSelected(false);
    }

    private void capNhatTrangThai() {
        int tongSoMay = danhSach.soLuong();
        boolean coDuLieu = tongSoMay > 0;
        boolean dangXemMay = viTriHienTai >= 0;

        txtViTri.setText(dangXemMay ? (viTriHienTai + 1) + "/" + tongSoMay : "0/" + tongSoMay);
        txtTongGiaTri.setText(String.format(Locale.US, "%.2f", danhSach.tongGiaTri()));
        txtTongSoLuong.setText(String.valueOf(danhSach.tongSoLuong()));

        btnNhapMay.setEnabled(formHopLe());
        btnXoa.setEnabled(dangXemMay);
        btnVeDau.setEnabled(coDuLieu && viTriHienTai > 0);
        btnVeTruoc.setEnabled(coDuLieu && viTriHienTai > 0);
        btnVeSau.setEnabled(coDuLieu && viTriHienTai < tongSoMay - 1);
        btnVeCuoi.setEnabled(coDuLieu && viTriHienTai < tongSoMay - 1);
    }

    private void capNhatMauNhan() {
        Color mauChon = rbMauXanh.isSelected() ? MAU_XANH
                : rbMauTim.isSelected() ? MAU_TIM
                : MAU_DO;
        for (JLabel nhan : danhSachNhan) nhan.setForeground(mauChon);
    }

    private void thongBao(String noiDung) {
        JOptionPane.showMessageDialog(this, noiDung, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================== CHẠY CHƯƠNG TRÌNH =====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MayTinhLapRap::new);
    }
}