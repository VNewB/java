package onlan1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DSMT {
    ArrayList<MayTinh> danhSach;

    public DSMT() {
        this.danhSach = new ArrayList<>();
    }

    // ---------------------------------------------------------
    // HÀM TÍNH TỔNG GIÁ TRỊ: ∑ Giá trị máy
    // ---------------------------------------------------------
    public double tinhTongGiaTri() {
        double tongGia = 0;
        for (MayTinh mt : danhSach) {
            tongGia += mt.getGiaTri();
        }
        return tongGia;
    }

    // ---------------------------------------------------------
    // HÀM TÍNH TỔNG SỐ LƯỢNG: ∑ Số lượng
    // ---------------------------------------------------------
    public int tinhTongSoLuong() {
        int tongLuong = 0;
        for (MayTinh mt : danhSach) {
            tongLuong += mt.getSoLuong();
        }
        return tongLuong;
    }

    public ArrayList<MayTinh> getDanhSach() {
        return danhSach;
    }

    public void themMayTinh(MayTinh mt) {
        if (this.danhSach == null) {
            this.danhSach = new ArrayList<>(); // Kiểm tra an toàn
        }
        this.danhSach.add(mt);
    }

    public int getSoLuong() {
        return danhSach.size();
    }

    public MayTinh getMayTinhAt(int index) {
        if (index >= 0 && index < danhSach.size()) {
            return danhSach.get(index);
        }
        return null;
    }

    // ---------------------------------------------------------
    // LƯU DANH SÁCH RA FILE CSV (MỞ BẰNG EXCEL HỖ TRỢ TIẾNG VIỆT)
    // ---------------------------------------------------------
    public boolean luuFileCSV(String pathFile) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(pathFile), StandardCharsets.UTF_8))) {

            // Ghi UTF-8 BOM để Excel hiển thị đúng tiếng Việt
            bw.write("\uFEFF");

            // Dòng tiêu đề cột
            bw.write("Mã Máy,Tên Máy,Đơn Giá,Số Lượng,Loại Máy,Phụ Kiện,Giá Trị");
            bw.newLine();

            // Ghi từng máy tính
            for (MayTinh mt : danhSach) {
                String phuKienStr = mt.getPhuKien().isEmpty() ? "Không" : mt.getPhuKien();

                String line = String.format("%s,%s,%.2f,%d,%s,\"%s\",%.2f",
                        mt.getMaMay(),
                        mt.getTenMay(),
                        mt.getDonGia(),
                        mt.getSoLuong(),
                        mt.getLoaiMay(),
                        phuKienStr,
                        mt.getGiaTri());

                bw.write(line);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------------------------------------------
    // ĐỌC DỮ LIỆU TỪ FILE CSV VÀO DSMT
    // ---------------------------------------------------------
    public boolean docFileCSV(String pathFile) {
        danhSach.clear();
        File file = new File(pathFile);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(pathFile), StandardCharsets.UTF_8))) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }

                if (isHeader) {
                    isHeader = false;
                    continue; // Bỏ qua dòng tiêu đề
                }

                // Tách cột bằng dấu phẩy
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (parts.length >= 7) {
                    String maMay = parts[0].trim();
                    String tenMay = parts[1].trim();
                    double donGia = Double.parseDouble(parts[2].trim().replace(",", "."));
                    int soLuong = Integer.parseInt(parts[3].trim());
                    String loaiMay = parts[4].trim();
                    String phuKien = parts[5].replace("\"", "").trim();
                    double giaTri = Double.parseDouble(parts[6].trim().replace(",", "."));

                    MayTinh mt = new MayTinh(maMay, tenMay, donGia, soLuong, loaiMay, phuKien, giaTri);
                    danhSach.add(mt);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // Xóa máy tính tại chỉ số index
    public boolean xoaMayTinhAt(int index) {
        if (index >= 0 && index < danhSach.size()) {
            danhSach.remove(index);
            return true;
        }
        return false;
    }
    // Kiểm tra xem mã máy đã tồn tại trong DSMT chưa
    public boolean isMaMayTonTai(String maMay) {
        for (MayTinh mt : danhSach) {
            if (mt.getMaMay().equalsIgnoreCase(maMay.trim())) {
                return true;
            }
        }
        return false;
    }
}