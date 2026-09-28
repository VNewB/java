package onlan1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
// $$$$$$$  Chạy file này  $$$$$$$$
// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

public class MayTinhLapRap extends JFrame {
    // Khai báo danh sách các Label quan trọng ở phạm vi class
    java.util.List<JLabel> danhSachLabelQuanTrong = new java.util.ArrayList<>();

    // Khởi tạo đối tượng DSMT
    DSMT dsmt = new DSMT();
    final String FILE_PATH = "DSMT.csv";

    JTextField txtMaMay;
    JTextField txtTenMay;
    JTextField txtDonGia;
    JTextField txtSoLuong;

    JTextField txtGiaTri;
    JTextField txtTongGia;
    JTextField txtTongLuong;

    JTextField txtTrang;

    JRadioButton rbLapRap;
    JRadioButton rbNhapKhau;

    JRadioButton rbDo;
    JRadioButton rbXanh;
    JRadioButton rbTim;

    JCheckBox cbVGA;
    JCheckBox cbDVD;
    JCheckBox cbLoa;

    JButton btnNhapMay;
    JButton btnNhapMoi;
    JButton btnVeDau;
    JButton btnVeTruoc;
    JButton btnVeSau;
    JButton btnVeCuoi;
    JButton btnXoa;
    JButton btnThoat;
    // Biến lưu vị trí hiện thời trong danh sách (mặc định -1 khi chưa chọn/danh sách rỗng)
    int vitriHienTai = -1;

    static Font font = new Font("Serif", Font.BOLD, 20);

    public MayTinhLapRap() {



        setTitle("Giao diện");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // =========================
        // TIÊU ĐỀ
        // =========================

        JLabel tieuDe = new JLabel("MÁY TÍNH LẮP RÁP");

        tieuDe.setHorizontalAlignment(SwingConstants.CENTER);
        tieuDe.setForeground(Color.BLUE);
        tieuDe.setFont(font);

        add(tieuDe, BorderLayout.NORTH);


        // =========================
        // PANEL CHÍNH
        // =========================

        JPanel main = new JPanel(new GridBagLayout());

        GridBagConstraints mainBag = new GridBagConstraints();
        mainBag.anchor = GridBagConstraints.NORTH;
        mainBag.gridx = 0;
        main.add(taoPanelTrai(), mainBag);
        mainBag.gridx = 1;
        main.add(taoPanelPhai(), mainBag);

        add(main, BorderLayout.CENTER);

        // Đọc dữ liệu từ file CSV cũ (nếu có) khi khởi động
        dsmt.docFileCSV(FILE_PATH);
        // Nếu danh sách có phần tử thì trỏ về vị trí cuối cùng/đầu tiên
        if (dsmt.getSoLuong() > 0) {
            vitriHienTai = 0;
            hienThiMayTinhAt(vitriHienTai);
        }
        capNhatThongKeVaTrangThai();
        // Đăng ký sự kiện lắng nghe thay đổi ô nhập để tự bật/tắt nút Nhập máy
        ganSuKienKiemTraInput();


        setVisible(true);

        txtMaMay.requestFocusInWindow();
    }

    // ---------------------------------------------------------
    // KIỂM TRA TÍNH HỢP LỆ CỦA DỮ LIỆU ĐẦU VÀO
    // ---------------------------------------------------------
    boolean kiemTraFormHopLe() {
        String ma = txtMaMay.getText().trim();
        String ten = txtTenMay.getText().trim();
        String donGiaStr = txtDonGia.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();

        // 1. Mã máy: Không rỗng, phải đủ 5 ký tự
        if (ma.length() != 5) {
            return false;
        }

        // 2. Tên máy: Không rỗng
        if (ten.isEmpty()) {
            return false;
        }

        // 3. Đơn giá: Số nguyên > 0
        try {
            long donGia = Long.parseLong(donGiaStr);
            if (donGia <= 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        // 4. Số lượng: Số nguyên > 0
        try {
            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    // Gắn sự kiện tự động kiểm tra mỗi khi gõ phím vào các ô TextField
    void ganSuKienKiemTraInput() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { capNhatTrangThaiBtnNhapMay(); }
            @Override
            public void removeUpdate(DocumentEvent e) { capNhatTrangThaiBtnNhapMay(); }
            @Override
            public void changedUpdate(DocumentEvent e) { capNhatTrangThaiBtnNhapMay(); }
        };

        txtMaMay.getDocument().addDocumentListener(listener);
        txtTenMay.getDocument().addDocumentListener(listener);
        txtDonGia.getDocument().addDocumentListener(listener);
        txtSoLuong.getDocument().addDocumentListener(listener);
    }

    void capNhatTrangThaiBtnNhapMay() {
        // Nút Nhập máy chỉ được sáng (enabled) khi dữ liệu đã hợp lệ hoàn toàn
        btnNhapMay.setEnabled(kiemTraFormHopLe());
    }
    // ---------------------------------------------------------
    // HÀM HIỂN THỊ DỮ LIỆU CỦA MÁY TÍNH TẠI VỊ TRÍ INDEX
    // ---------------------------------------------------------
    void hienThiMayTinhAt(int index) {
        MayTinh mt = dsmt.getMayTinhAt(index);
        if (mt != null) {
            txtMaMay.setText(mt.getMaMay());
            txtTenMay.setText(mt.getTenMay());
            txtDonGia.setText(String.format("%.0f", mt.getDonGia()));
            txtSoLuong.setText(String.valueOf(mt.getSoLuong()));
            txtGiaTri.setText(String.format("%.2f", mt.getGiaTri()));

            if (mt.getLoaiMay().equalsIgnoreCase("Lắp ráp")) {
                rbLapRap.setSelected(true);
            } else {
                rbNhapKhau.setSelected(true);
            }

            cbVGA.setSelected(mt.getPhuKien().contains("VGA 2 GB"));
            cbDVD.setSelected(mt.getPhuKien().contains("DVD"));
            cbLoa.setSelected(mt.getPhuKien().contains("LOA"));
        }
    }

    // ---------------------------------------------------------
    // HÀM CẬP NHẬT THỐNG KÊ VÀ BẬT/TẮT CÁC NÚT BẤM
    // ---------------------------------------------------------
    void capNhatThongKeVaTrangThai() {
        int tongSo = dsmt.getSoLuong();

        if (tongSo == 0) {
            txtTrang.setText("0/0");
            txtTongGia.setText("0");
            txtTongLuong.setText("0");
            vitriHienTai = -1;

            // Khi DSMT rỗng
            btnXoa.setEnabled(false); // Nút xóa bị mờ
            btnVeDau.setEnabled(false);
            btnVeTruoc.setEnabled(false);
            btnVeSau.setEnabled(false);
            btnVeCuoi.setEnabled(false);
        } else {
            txtTrang.setText((vitriHienTai + 1) + "/" + tongSo);
            txtTongGia.setText(String.format("%.2f", dsmt.tinhTongGiaTri()));
            txtTongLuong.setText(String.valueOf(dsmt.tinhTongSoLuong()));

            // KIỂM TRA ĐIỀU KIỆN: Khi DSMT có ít nhất 1 phần tử
            btnXoa.setEnabled(true); // Nút Xóa SÁNG LÊN

            // Bật/tắt các nút điều hướng
            btnVeDau.setEnabled(vitriHienTai > 0);
            btnVeTruoc.setEnabled(vitriHienTai > 0);
            btnVeSau.setEnabled(vitriHienTai < tongSo - 1);
            btnVeCuoi.setEnabled(vitriHienTai < tongSo - 1);
        }

        // Nút Nhập mới và Thoát luôn sáng
        btnNhapMoi.setEnabled(true);
        btnThoat.setEnabled(true);
    }

    JLabel taoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.RED); // Màu mặc định ban đầu là Đỏ
        label.setFont(new Font("Serif", Font.BOLD, 16));

        // Thêm vào danh sách để quản lý màu sắc chung
        danhSachLabelQuanTrong.add(label);
        return label;
    }
    void capNhatMauSacQuanTrong() {
        Color mauChon = Color.RED; // Mặc định

        if (rbDo.isSelected()) {
            mauChon = Color.RED;
        } else if (rbXanh.isSelected()) {
            mauChon = Color.BLUE; // Hoặc Color.GREEN tùy theo yêu cầu đề bài
        } else if (rbTim.isSelected()) {
            mauChon = new Color(128, 0, 128); // Màu Tím (Magenta hoặc RGB tùy chỉnh)
        }

        // Đổi màu cho tất cả Label quan trọng trong danh sách
        for (JLabel label : danhSachLabelQuanTrong) {
            label.setForeground(mauChon);
        }
    }

    JPanel taoPanelTrai() {

        JPanel main = new JPanel(new BorderLayout());

        JPanel thongTin = new JPanel(new GridBagLayout());
        thongTin.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        txtMaMay = new JTextField(15);
        txtTenMay = new JTextField(15);
        txtDonGia = new JTextField(15);
        txtSoLuong = new JTextField(15);

        them(thongTin, taoLabel("Mã máy:"), txtMaMay, 0);
        them(thongTin, taoLabel("Tên máy:"), txtTenMay, 1);
        them(thongTin, taoLabel("Đơn giá:"), txtDonGia, 2);
        them(thongTin, taoLabel("Số lượng:"), txtSoLuong, 3);

        // Loại máy
        JPanel loaiMay = new JPanel(new GridBagLayout());
        GridBagConstraints loaiMayBag = new GridBagConstraints();
        loaiMayBag.anchor = GridBagConstraints.WEST;

        loaiMay.setBorder(BorderFactory.createTitledBorder("Loại máy"));

        rbLapRap = new JRadioButton("Lắp ráp", true);
        rbNhapKhau = new JRadioButton("Nhập khẩu");

        ButtonGroup groupLoai = new ButtonGroup();
        groupLoai.add(rbLapRap);
        groupLoai.add(rbNhapKhau);

        loaiMayBag.gridy = 0;
        loaiMay.add(rbLapRap, loaiMayBag);
        loaiMayBag.gridy = 1;
        loaiMay.add(rbNhapKhau, loaiMayBag);

        // Phụ kiện
        JPanel phuKien = new JPanel(new GridBagLayout());
        GridBagConstraints phuKienBag = new GridBagConstraints();
        phuKienBag.anchor = GridBagConstraints.WEST;
        phuKien.setBorder(BorderFactory.createTitledBorder("Phụ kiện"));

        cbVGA = new JCheckBox("VGA 2 GB");
        cbDVD = new JCheckBox("DVD");
        cbLoa = new JCheckBox("LOA");

        phuKienBag.gridy = 0;
        phuKien.add(cbVGA, phuKienBag);
        phuKienBag.gridy = 1;
        phuKien.add(cbDVD, phuKienBag);
        phuKienBag.gridy = 2;
        phuKien.add(cbLoa, phuKienBag);

        JPanel bottom = new JPanel(new GridBagLayout());
        GridBagConstraints bottomBag = new GridBagConstraints();
        bottomBag.anchor = GridBagConstraints.NORTH;
        bottomBag.gridx = 0;
        bottom.add(loaiMay, bottomBag);
        bottomBag.gridx = 1;
        bottom.add(phuKien, bottomBag);

        main.add(thongTin, BorderLayout.NORTH);
        main.add(bottom, BorderLayout.CENTER);

        return main;
    }

    JPanel taoPanelPhai() {
        JPanel main = new JPanel(new BorderLayout());

        JPanel nhap = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnNhapMay = new JButton("Nhập máy");
        btnNhapMoi = new JButton("Nhập mới");
        nhap.add(btnNhapMay);
        nhap.add(btnNhapMoi);

        // Ban đầu chưa bấm Nhập mới thì nút Nhập máy mờ
        btnNhapMay.setEnabled(false);

        JPanel thongKe = new JPanel(new GridBagLayout());
        txtGiaTri = new JTextField(15);
        txtTongGia = new JTextField(15);
        txtTongLuong = new JTextField(15);
        thongKe.setBorder(BorderFactory.createTitledBorder("Thống kê"));
        them(thongKe, taoLabel("Tổng giá:"), txtTongGia, 2);
        them(thongKe, taoLabel("Tổng lượng:"), txtTongLuong, 3);

        JPanel dieuHuong = new JPanel(new FlowLayout());
        btnVeDau = new JButton("Về đầu");
        btnVeTruoc = new JButton("Về trước");
        txtTrang = new JTextField("0/0", 4);
        txtTrang.setHorizontalAlignment(JTextField.CENTER);
        txtTrang.setEditable(false);
        btnVeSau = new JButton("Về sau");
        btnVeCuoi = new JButton("Về cuối");

        dieuHuong.add(btnVeDau);
        dieuHuong.add(btnVeTruoc);
        dieuHuong.add(txtTrang);
        dieuHuong.add(btnVeSau);
        dieuHuong.add(btnVeCuoi);

        JPanel mauSac = new JPanel(new GridBagLayout());
        mauSac.setBorder(BorderFactory.createTitledBorder("Màu sắc"));

        rbDo = new JRadioButton("Đỏ", true);
        rbXanh = new JRadioButton("Xanh");
        rbTim = new JRadioButton("Tím");

        ButtonGroup groupMau = new ButtonGroup();
        groupMau.add(rbDo);
        groupMau.add(rbXanh);
        groupMau.add(rbTim);

        // ---------------------------------------------------------
        // GẮN SỰ KIỆN THAY ĐỔI MÀU SẮC
        // ---------------------------------------------------------
        ActionListener mauSacListener = e -> capNhatMauSacQuanTrong();

        rbDo.addActionListener(mauSacListener);
        rbXanh.addActionListener(mauSacListener);
        rbTim.addActionListener(mauSacListener);

        GridBagConstraints mauSacBag = new GridBagConstraints();
        mauSacBag.anchor = GridBagConstraints.WEST;
        mauSacBag.gridy = 0; mauSac.add(rbDo, mauSacBag);
        mauSacBag.gridy = 1; mauSac.add(rbXanh, mauSacBag);
        mauSacBag.gridy = 2; mauSac.add(rbTim, mauSacBag);

        JPanel button = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXoa = new JButton("Xóa");
        btnThoat = new JButton("Thoát");
        button.add(btnXoa);
        button.add(btnThoat);

        JPanel GiaTri = new JPanel(new GridBagLayout());
        them(GiaTri, taoLabel("Giá trị: "), txtGiaTri, 0);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints centerBag = new GridBagConstraints();
        centerBag.anchor = GridBagConstraints.WEST;
        centerBag.gridy = 0; center.add(GiaTri, centerBag);
        centerBag.gridy = 1; center.add(thongKe, centerBag);
        centerBag.gridy = 2; center.add(dieuHuong, centerBag);
        centerBag.gridy = 3; center.add(mauSac, centerBag);

        main.add(nhap, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        main.add(button, BorderLayout.SOUTH);

        // ---------------------------------------------------------
        // SỰ KIỆN NÚT NHẬP MỚI (Xóa trắng ô nhập để chuẩn bị nhập)
        // ---------------------------------------------------------
        btnNhapMoi.addActionListener(e -> {
            txtMaMay.setText("");
            txtTenMay.setText("");
            txtDonGia.setText("");
            txtSoLuong.setText("");
            txtGiaTri.setText("");

            rbLapRap.setSelected(true);
            cbVGA.setSelected(false);
            cbDVD.setSelected(false);
            cbLoa.setSelected(false);

            txtMaMay.requestFocusInWindow();
        });

        // ---------------------------------------------------------
        // SỰ KIỆN NÚT NHẬP MÁY
        // ---------------------------------------------------------
        btnNhapMay.addActionListener(e -> {
            try {
                String maMay = txtMaMay.getText().trim();
                String tenMay = txtTenMay.getText().trim();
                String donGiaStr = txtDonGia.getText().trim();
                String soLuongStr = txtSoLuong.getText().trim();

                // 1. RÀNG BUỘC: Không được để trống
                if (maMay.isEmpty() || tenMay.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 2. RÀNG BUỘC: Mã máy phải đúng 5 ký tự
                if (maMay.length() != 5) {
                    JOptionPane.showMessageDialog(this, "Mã máy phải đúng 5 ký tự!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                    txtMaMay.requestFocusInWindow();
                    return;
                }

                // 3. RÀNG BUỘC: Kiểm tra trùng mã máy trong DSMT
                if (dsmt.isMaMayTonTai(maMay)) {
                    JOptionPane.showMessageDialog(this, "Mã máy '" + maMay + "' đã tồn tại! Vui lòng nhập mã khác.", "Lỗi trùng mã", JOptionPane.ERROR_MESSAGE);
                    txtMaMay.requestFocusInWindow();
                    return;
                }

                // 4. RÀNG BUỘC: Đơn giá và Số lượng phải là số nguyên > 0
                long donGia = Long.parseLong(donGiaStr);
                int soLuong = Integer.parseInt(soLuongStr);

                if (donGia <= 0 || soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Đơn giá và Số lượng phải là số nguyên lớn hơn 0!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 5. TÍNH TÓAN GIÁ TRỊ MÁY TÍNH
                double tyLeLoaiMay = rbLapRap.isSelected() ? 1.10 : 1.20;
                String loaiMayStr = rbLapRap.isSelected() ? "Lắp ráp" : "Nhập khẩu";

                double tyLePhuKien = 0.0;
                ArrayList<String> listPK = new ArrayList<>();
                if (cbVGA.isSelected()) { tyLePhuKien += 0.30; listPK.add("VGA 2 GB"); }
                if (cbDVD.isSelected()) { tyLePhuKien += 0.05; listPK.add("DVD"); }
                if (cbLoa.isSelected()) { tyLePhuKien += 0.10; listPK.add("LOA"); }
                String phuKienStr = String.join(", ", listPK);

                double giaTri = soLuong * (donGia * tyLeLoaiMay + donGia * tyLePhuKien);
                txtGiaTri.setText(String.format("%.2f", giaTri));

                // 6. THÊM VÀO CUỐI DSMT VÀ LƯU FILE
                MayTinh mt = new MayTinh(maMay, tenMay, (double) donGia, soLuong, loaiMayStr, phuKienStr, giaTri);
                dsmt.themMayTinh(mt); // Thêm vào cuối danh sách
                dsmt.luuFileCSV(FILE_PATH);

                // 7. CẬP NHẬT TRẠNG THÁI MÀN HÌNH & THỐNG KÊ (Tổng giá trị, Tổng lượng, Trang)
                vitriHienTai = dsmt.getSoLuong() - 1; // Chỉ đến máy mới ở cuối DSMT
                capNhatThongKeVaTrangThai(); // Cập nhật lại Tổng giá trị & Tổng lượng

                JOptionPane.showMessageDialog(this, "Thêm máy tính thành công!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Đơn giá và Số lượng phải là số nguyên hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ---------------------------------------------------------
        // SỰ KIỆN NÚT XÓA (Xóa phần tử hiện tại khỏi DSMT)
        // ---------------------------------------------------------
        btnXoa.addActionListener(e -> {
            if (vitriHienTai >= 0 && vitriHienTai < dsmt.getSoLuong()) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc muốn xóa Máy tính ở vị trí hiện tại không?",
                        "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    dsmt.xoaMayTinhAt(vitriHienTai);
                    dsmt.luuFileCSV(FILE_PATH); // Cập nhật lại file CSV

                    if (dsmt.getSoLuong() == 0) {
                        // Nếu danh sách rỗng sau khi xóa
                        txtMaMay.setText("");
                        txtTenMay.setText("");
                        txtDonGia.setText("");
                        txtSoLuong.setText("");
                        txtGiaTri.setText("");
                        vitriHienTai = -1;
                    } else {
                        // Lùi vị trí hiển thị nếu xóa phần tử cuối
                        if (vitriHienTai >= dsmt.getSoLuong()) {
                            vitriHienTai = dsmt.getSoLuong() - 1;
                        }
                        hienThiMayTinhAt(vitriHienTai);
                    }

                    capNhatThongKeVaTrangThai();
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                }
            }
        });

        // ---------------------------------------------------------
        // SỰ KIỆN NÚT ĐIỀU HƯỚNG
        // ---------------------------------------------------------
        btnVeDau.addActionListener(e -> {
            if (dsmt.getSoLuong() > 0) {
                vitriHienTai = 0;
                hienThiMayTinhAt(vitriHienTai);
                capNhatThongKeVaTrangThai();
            }
        });

        btnVeTruoc.addActionListener(e -> {
            if (vitriHienTai > 0) {
                vitriHienTai--;
                hienThiMayTinhAt(vitriHienTai);
                capNhatThongKeVaTrangThai();
            }
        });

        btnVeSau.addActionListener(e -> {
            if (vitriHienTai < dsmt.getSoLuong() - 1) {
                vitriHienTai++;
                hienThiMayTinhAt(vitriHienTai);
                capNhatThongKeVaTrangThai();
            }
        });

        btnVeCuoi.addActionListener(e -> {
            if (dsmt.getSoLuong() > 0) {
                vitriHienTai = dsmt.getSoLuong() - 1;
                hienThiMayTinhAt(vitriHienTai);
                capNhatThongKeVaTrangThai();
            }
        });

        btnThoat.addActionListener(e -> {
            // Hiển thị hộp thoại xác nhận với 2 lựa chọn Yes / No
            int luaChon = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn thoát khỏi chương trình không?",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            // Nếu người dùng chọn Yes
            if (luaChon == JOptionPane.YES_OPTION) {
                System.exit(0); // Kết thúc chương trình
            }
        });

        return main;
    }

    void them(JPanel panel, JLabel label, JTextField textField, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        if (textField != null) {
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(textField, gbc);
        }
    }
    // Phương thức cập nhật các ô thông tin thống kê và điều hướng
    void capNhatThongKe() {
        int tongSo = dsmt.getSoLuong();

        // Cập nhật số trang (ví dụ: 3/3)
        if (tongSo == 0) {
            txtTrang.setText("0/0");
            txtTongGia.setText("0");
            txtTongLuong.setText("0");
        } else {
            txtTrang.setText(tongSo + "/" + tongSo);

            // 1. Tính và hiển thị Tổng Giá Trị (∑ Giá trị máy)
            double tongGiaVal = dsmt.tinhTongGiaTri();
            txtTongGia.setText(String.format("%.2f", tongGiaVal));

            // 2. Tính và hiển thị Tổng Lượng (∑ Số lượng)
            int tongLuongVal = dsmt.tinhTongSoLuong();
            txtTongLuong.setText(String.valueOf(tongLuongVal));
        }
    }

    // Hàm điều khiển trạng thái bật/tắt (sáng/mờ) của các nút bấm
    void setTrangThaiNut(boolean isEditMode) {
        // Các nút bị mờ khi ở trạng thái ban đầu/mặc định
        btnNhapMay.setEnabled(isEditMode);
        btnVeDau.setEnabled(!isEditMode && dsmt.getSoLuong() > 0);
        btnVeTruoc.setEnabled(!isEditMode && dsmt.getSoLuong() > 0);
        btnVeSau.setEnabled(!isEditMode && dsmt.getSoLuong() > 0);
        btnVeCuoi.setEnabled(!isEditMode && dsmt.getSoLuong() > 0);
        btnXoa.setEnabled(isEditMode);

        // Hai nút luôn được sáng
        btnNhapMoi.setEnabled(true);
        btnThoat.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MayTinhLapRap());
    }
}
