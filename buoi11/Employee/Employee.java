//package buoi11.Employee;

import java.time.LocalDate;

public class Employee {
    private String maNV;
    private String tenDem;
    private String ho;
    private double luongThang;
    private LocalDate ngayThue;

    public Employee(String maNV, String tenDem, String ho,
                    double luongThang, LocalDate ngayThue) {
        this.maNV = maNV;
        this.tenDem = tenDem;
        this.ho = ho;
        setLuongThang(luongThang);
        this.ngayThue = ngayThue;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenDem() {
        return tenDem;
    }

    public void setTenDem(String tenDem) {
        this.tenDem = tenDem;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public double getLuongThang() {
        return luongThang;
    }

    public void setLuongThang(double luongThang) {
        if (luongThang >= 0) {
            this.luongThang = luongThang;
        } else {
            this.luongThang = 0;
        }
    }

    public LocalDate getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(LocalDate ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getHoTen() {
        return ho + " " + tenDem;
    }

    @Override
    public String toString() {
        return String.format(
                "Ma NV: %s | Ho: %s | Ten dem: %s | Luong: %.2f | Ngay thue: %s",
                maNV, ho, tenDem, luongThang, ngayThue
        );
    }
}
