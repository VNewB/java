package onlan1;

public class MayTinh {
    private String maMay;
    private String tenMay;
    private double donGia;
    private int soLuong;
    private String loaiMay;
    private String phuKien;
    private double giaTri;

    public MayTinh(String maMay, String tenMay, double donGia, int soLuong, String loaiMay, String phuKien, double giaTri) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.loaiMay = loaiMay;
        this.phuKien = phuKien;
        this.giaTri = giaTri;
    }

    // Các hàm Getter/Setter (nếu cần)
    public String getMaMay() { return maMay; }
    public String getTenMay() { return tenMay; }
    public double getDonGia() { return donGia; }
    public int getSoLuong() { return soLuong; }
    public String getLoaiMay() { return loaiMay; }
    public String getPhuKien() { return phuKien; }
    public double getGiaTri() { return giaTri; }
}
