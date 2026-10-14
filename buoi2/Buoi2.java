package buoi2;
import java.util.ArrayList;
import java.util.Scanner;

public class Buoi2 {
    public static void main(String[] args) {
//        Bai1.main(args);
//        Bai2.main(args);
//        Bai3.main(args);
//        Bai4.main(args);
//        Bai5.main(args);
//        Bai6.main(args);
//        Bai7.main(args);
//        Bai8.main(args);
//        Bai9.main(args);
    }
}

// Bài 1 do không rõ phần bài 3.1.3 nên em giả định phần phân số như thế này
class Bai1 {
    static class PHANSO {
        int tu, mau;

        PHANSO(int tu, int mau) {
            this.tu = tu;
            this.mau = mau;
        }
    }

    // Nhap mot phan so
    static PHANSO Nhap1PS(java.util.Scanner sc) {
        System.out.print("Nhap tu so: ");
        int tu = sc.nextInt();

        int mau;
        do {
            System.out.print("Nhap mau so (khac 0): ");
            mau = sc.nextInt();
        } while (mau == 0);

        return new PHANSO(tu, mau);
    }

    // Xuat mot phan so
    static void Xuat1PS(PHANSO p) {
        System.out.print(p.tu + "/" + p.mau);
    }

    // So sanh hai phan so
    static int SoSanhPS(PHANSO a, PHANSO b) {
        long x = (long) a.tu * b.mau;
        long y = (long) b.tu * a.mau;

        if (a.mau < 0) x = -x;
        if (a.mau < 0) y = -y;

        if (b.mau < 0) x = -x;
        if (b.mau < 0) y = -y;

        return Long.compare(x, y);
    }

    // a. Nhap mang phan so
    static void NhapMangPS(PHANSO A[], int n) {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        for (int i = 0; i < n; i++) {
            System.out.println("Nhap phan so thu " + (i + 1) + ":");
            A[i] = Nhap1PS(sc);
        }
    }

    // b. Xuat mang phan so
    static void XuatMangPS(PHANSO A[], int n) {
        for (int i = 0; i < n; i++) {
            Xuat1PS(A[i]);
            System.out.print("  ");
        }
        System.out.println();
    }

    // c. Tim phan so lon nhat
    static PHANSO TimPSMax(PHANSO A[], int n) {
        PHANSO max = A[0];

        for (int i = 1; i < n; i++) {
            if (SoSanhPS(A[i], max) > 0) {
                max = A[i];
            }
        }
        return max;
    }

    // Tim phan so nho nhat
    static PHANSO TimPSMin(PHANSO A[], int n) {
        PHANSO min = A[0];

        for (int i = 1; i < n; i++) {
            if (SoSanhPS(A[i], min) < 0) {
                min = A[i];
            }
        }
        return min;
    }

    // d. Sap xep mang tang dan
    static void SortMangPS(PHANSO A[], int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (SoSanhPS(A[i], A[j]) > 0) {
                    PHANSO temp = A[i];
                    A[i] = A[j];
                    A[j] = temp;
                }
            }
        }
    }

    // Rut gon phan so
    static PHANSO RutGon(PHANSO p) {
        int a = Math.abs(p.tu);
        int b = Math.abs(p.mau);

        while (b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }

        int ucln = (a == 0) ? 1 : a;
        int tu = p.tu / ucln;
        int mau = p.mau / ucln;

        if (mau < 0) {
            tu = -tu;
            mau = -mau;
        }

        return new PHANSO(tu, mau);
    }

    // e. Tong hai phan so
    static PHANSO TongPS(PHANSO a, PHANSO b) {
        return RutGon(new PHANSO(
                a.tu * b.mau + b.tu * a.mau,
                a.mau * b.mau
        ));
    }

    // Hieu hai phan so
    static PHANSO HieuPS(PHANSO a, PHANSO b) {
        return RutGon(new PHANSO(
                a.tu * b.mau - b.tu * a.mau,
                a.mau * b.mau
        ));
    }

    // Tich hai phan so
    static PHANSO TichPS(PHANSO a, PHANSO b) {
        return RutGon(new PHANSO(
                a.tu * b.tu,
                a.mau * b.mau
        ));
    }

    // Thuong hai phan so
    static PHANSO ThuongPS(PHANSO a, PHANSO b) {
        if (b.tu == 0) {
            throw new ArithmeticException("Khong the chia cho phan so 0");
        }

        return RutGon(new PHANSO(
                a.tu * b.mau,
                a.mau * b.tu
        ));
    }

    // Main kiem tra
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        System.out.print("Nhap so luong phan so: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("So luong phan so phai lon hon 0.");
            return;
        }

        PHANSO A[] = new PHANSO[n];

        NhapMangPS(A, n);

        System.out.print("Mang phan so: ");
        XuatMangPS(A, n);

        PHANSO max = TimPSMax(A, n);
        PHANSO min = TimPSMin(A, n);

        System.out.print("Phan so lon nhat: ");
        Xuat1PS(max);
        System.out.println();

        System.out.print("Phan so nho nhat: ");
        Xuat1PS(min);
        System.out.println();

        System.out.println("Tong max + min: ");
        Xuat1PS(TongPS(max, min));
        System.out.println();

        System.out.println("Hieu max - min: ");
        Xuat1PS(HieuPS(max, min));
        System.out.println();

        System.out.println("Tich max * min: ");
        Xuat1PS(TichPS(max, min));
        System.out.println();

        System.out.println("Thuong max / min: ");
        if (min.tu != 0) {
            Xuat1PS(ThuongPS(max, min));
        } else {
            System.out.print("Khong the chia cho 0");
        }
        System.out.println();

        SortMangPS(A, n);
        System.out.print("Mang sau khi sap xep tang dan: ");
        XuatMangPS(A, n);
    }
}

class Bai2 {

    static Scanner sc = new Scanner(System.in);

    static class Phim {
        String tenPhim;
        double doanhThu;
        int theLoai;
        int namSanXuat;
        boolean vietNam;

        Phim(String tenPhim, double doanhThu, int theLoai,
             int namSanXuat, boolean vietNam) {
            this.tenPhim = tenPhim;
            this.doanhThu = doanhThu;
            this.theLoai = theLoai;
            this.namSanXuat = namSanXuat;
            this.vietNam = vietNam;
        }
    }

    static ArrayList<Phim> danhSach = new ArrayList<>();

    // a. Nhap mot bo phim
    static Phim a() {
        System.out.print("Nhap ten phim: ");
        String ten = sc.nextLine();

        while (ten.length() > 50) {
            System.out.print("Ten phim toi da 50 ky tu. Nhap lai: ");
            ten = sc.nextLine();
        }

        System.out.print("Nhap doanh thu: ");
        double doanhThu = sc.nextDouble();

        System.out.print("Nhap the loai (0-Hinh su, 1-Tinh cam, 2-Hai): ");
        int theLoai = sc.nextInt();

        while (theLoai < 0 || theLoai > 2) {
            System.out.print("The loai khong hop le. Nhap lai: ");
            theLoai = sc.nextInt();
        }

        System.out.print("Nhap nam san xuat: ");
        int nam = sc.nextInt();

        System.out.print("Phim Viet Nam? (true/false): ");
        boolean vietNam = sc.nextBoolean();
        sc.nextLine();

        return new Phim(ten, doanhThu, theLoai, nam, vietNam);
    }

    // b. Nhap danh sach n phim
    static void b() {
        System.out.print("Nhap so luong phim: ");
        int n = sc.nextInt();
        sc.nextLine();

        danhSach.clear();

        for (int i = 0; i < n; i++) {
            System.out.println("\nNhap phim thu " + (i + 1) + ":");
            danhSach.add(a());
        }
    }

    // c. Xuat mot bo phim
    static void c(Phim p) {
        if (p == null) {
            System.out.println("Phim khong ton tai.");
            return;
        }

        System.out.println("Ten phim: " + p.tenPhim);
        System.out.println("Doanh thu: " + p.doanhThu);

        System.out.print("The loai: ");
        switch (p.theLoai) {
            case 0:
                System.out.println("Hinh su");
                break;
            case 1:
                System.out.println("Tinh cam");
                break;
            case 2:
                System.out.println("Hai");
                break;
        }

        System.out.println("Nam san xuat: " + p.namSanXuat);
        System.out.println("San xuat tai Viet Nam: "
                + (p.vietNam ? "Co" : "Khong"));
        System.out.println("-------------------------");
    }

    // d. Xuat danh sach phim
    static void d() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sach phim rong.");
            return;
        }

        for (Phim p : danhSach) {
            c(p);
        }
    }

    // e. Nhap the loai va xuat cac phim thuoc the loai do
    static void e() {
        System.out.print("Nhap the loai (0-Hinh su, 1-Tinh cam, 2-Hai): ");
        int loai = sc.nextInt();

        if (loai < 0 || loai > 2) {
            System.out.println("The loai khong hop le.");
            return;
        }

        boolean timThay = false;

        for (Phim p : danhSach) {
            if (p.theLoai == loai) {
                c(p);
                timThay = true;
            }
        }

        if (!timThay) {
            System.out.println("Khong co phim thuoc the loai nay.");
        }
    }

    // f. Tong doanh thu phim Viet Nam
    static double f() {
        double tong = 0;

        for (Phim p : danhSach) {
            if (p.vietNam) {
                tong += p.doanhThu;
            }
        }

        return tong;
    }

    // g. Doanh thu trung binh theo khoang nam
    static double g(int fromYear, int toYear) {
        double tong = 0;
        int dem = 0;

        for (Phim p : danhSach) {
            if (p.namSanXuat >= fromYear
                    && p.namSanXuat <= toYear) {
                tong += p.doanhThu;
                dem++;
            }
        }

        if (dem == 0) {
            return 0;
        }

        return tong / dem;
    }

    // h. Dem phim co doanh thu ngoai khoang
    static int h(int LowerBound, int UpperBound) {
        int dem = 0;

        for (Phim p : danhSach) {
            if (p.doanhThu < LowerBound
                    || p.doanhThu > UpperBound) {
                dem++;
            }
        }

        return dem;
    }

    // Ham main de chay thu
    public static void main(String[] args) {
        b();

        System.out.println("\n=== DANH SACH PHIM ===");
        d();

        System.out.println("\n=== PHIM THEO THE LOAI ===");
        e();

        System.out.println("\nTong doanh thu phim Viet Nam: " + f());

        System.out.print("\nNhap nam bat dau: ");
        int fromYear = sc.nextInt();

        System.out.print("Nhap nam ket thuc: ");
        int toYear = sc.nextInt();

        System.out.println("Doanh thu trung binh: "
                + g(fromYear, toYear));

        System.out.print("\nNhap can duoi doanh thu: ");
        int lower = sc.nextInt();

        System.out.print("Nhap can tren doanh thu: ");
        int upper = sc.nextInt();

        System.out.println("So phim co doanh thu ngoai khoang: "
                + h(lower, upper));
    }
}

class Bai3 {
    static Scanner sc = new Scanner(System.in);

    static class MayTinh {
        String model;
        String hangSanXuat;
        int baoHanh;
        float giaTien;
        boolean sanXuatVietNam;
    }

    static MayTinh[] ds;
    static int n;

    // a. Nhap thong tin mot may tinh
    static MayTinh cauA() {
        MayTinh mt = new MayTinh();

        System.out.print("Nhap model (toi da 10 ky tu): ");
        mt.model = sc.nextLine();
        while (mt.model.length() > 10 || mt.model.isEmpty()) {
            System.out.print("Model khong hop le, nhap lai: ");
            mt.model = sc.nextLine();
        }

        System.out.print("Nhap hang san xuat (toi da 30 ky tu): ");
        mt.hangSanXuat = sc.nextLine();
        while (mt.hangSanXuat.length() > 30 || mt.hangSanXuat.isEmpty()) {
            System.out.print("Hang san xuat khong hop le, nhap lai: ");
            mt.hangSanXuat = sc.nextLine();
        }

        System.out.print("Nhap thoi gian bao hanh (1-5): ");
        mt.baoHanh = Integer.parseInt(sc.nextLine());
        while (mt.baoHanh < 1 || mt.baoHanh > 5) {
            System.out.print("Gia tri khong hop le, nhap lai (1-5): ");
            mt.baoHanh = Integer.parseInt(sc.nextLine());
        }

        System.out.print("Nhap gia tien: ");
        mt.giaTien = Float.parseFloat(sc.nextLine());
        while (mt.giaTien < 0) {
            System.out.print("Gia tien khong hop le, nhap lai: ");
            mt.giaTien = Float.parseFloat(sc.nextLine());
        }

        char c;
        do {
            System.out.print("San xuat tai Viet Nam? (Y/N): ");
            String s = sc.nextLine();
            c = s.length() == 1 ? s.charAt(0) : ' ';
        } while (c != 'Y' && c != 'y' && c != 'N' && c != 'n');

        mt.sanXuatVietNam = (c == 'Y' || c == 'y');

        return mt;
    }

    // b. Nhap danh sach n may tinh
    static void cauB() {
        System.out.print("Nhap so luong may tinh: ");
        n = Integer.parseInt(sc.nextLine());

        while (n <= 0) {
            System.out.print("So luong phai lon hon 0, nhap lai: ");
            n = Integer.parseInt(sc.nextLine());
        }

        ds = new MayTinh[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nNhap may tinh thu " + (i + 1) + ":");
            ds[i] = cauA();
        }
    }

    // c. Xuat thong tin mot may tinh
    static void cauC(MayTinh mt) {
        String[] thang = {"", "3 thang", "6 thang",
                "12 thang", "24 thang", "36 thang"};

        System.out.println("Model: " + mt.model);
        System.out.println("Hang san xuat: " + mt.hangSanXuat);
        System.out.println("Bao hanh: " + thang[mt.baoHanh]);
        System.out.println("Gia tien: " + mt.giaTien);
        System.out.println("Xuat xu: " +
                (mt.sanXuatVietNam
                        ? "San xuat tai Viet Nam"
                        : "San xuat tai nuoc ngoai"));
    }

    // d. Xuat danh sach may tinh
    static void cauD() {
        if (ds == null) {
            System.out.println("Chua co danh sach may tinh.");
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.println("\nMay tinh thu " + (i + 1) + ":");
            cauC(ds[i]);
        }
    }

    // e. Tim may tinh theo hang san xuat
    static void cauE() {
        System.out.print("Nhap hang san xuat can tim: ");
        String hang = sc.nextLine();
        boolean timThay = false;

        for (int i = 0; i < n; i++) {
            if (ds[i].hangSanXuat.equalsIgnoreCase(hang)) {
                cauC(ds[i]);
                timThay = true;
            }
        }

        if (!timThay) {
            System.out.println("Khong tim thay may tinh nao.");
        }
    }

    // f. Dem so may tinh co gia trong khoang
    static int cauF(int fromPrice, int toPrice) {
        int dem = 0;

        for (int i = 0; i < n; i++) {
            if (ds[i].giaTien >= fromPrice
                    && ds[i].giaTien <= toPrice) {
                dem++;
            }
        }

        return dem;
    }

    // g. In may tinh Viet Nam co gia ngoai khoang
    static void cauG(int lowerPrice, int upperPrice) {
        boolean timThay = false;

        for (int i = 0; i < n; i++) {
            if (ds[i].sanXuatVietNam
                    && (ds[i].giaTien < lowerPrice
                    || ds[i].giaTien > upperPrice)) {
                cauC(ds[i]);
                timThay = true;
            }
        }

        if (!timThay) {
            System.out.println("Khong co may tinh phu hop.");
        }
    }

    public static void main(String[] args) {
        cauB();

        System.out.println("\n===== DANH SACH MAY TINH =====");
        cauD();

        System.out.println("\n===== TIM THEO HANG SAN XUAT =====");
        cauE();

        System.out.print("\nNhap fromPrice: ");
        int fromPrice = Integer.parseInt(sc.nextLine());

        System.out.print("Nhap toPrice: ");
        int toPrice = Integer.parseInt(sc.nextLine());

        System.out.println("So may tinh trong khoang gia: "
                + cauF(fromPrice, toPrice));

        System.out.print("\nNhap lowerPrice: ");
        int lowerPrice = Integer.parseInt(sc.nextLine());

        System.out.print("Nhap upperPrice: ");
        int upperPrice = Integer.parseInt(sc.nextLine());

        System.out.println("\n===== MAY TINH VIET NAM NGOAI KHOANG GIA =====");
        cauG(lowerPrice, upperPrice);
    }
}

class Bai4 {
    static Scanner sc = new Scanner(System.in);

    static class LinhKien {
        String ten;
        String quyCach;
        char loai;
        int donGia1;
        int donGia2;
    }

    // Nhap thong tin mot linh kien
    static void Nhap1LK(LinhKien lk) {
        sc.nextLine();

        System.out.print("Nhap ten linh kien: ");
        lk.ten = sc.nextLine();

        System.out.print("Nhap quy cach: ");
        lk.quyCach = sc.nextLine();

        System.out.print("Nhap loai linh kien (A/B): ");
        lk.loai = Character.toUpperCase(sc.next().charAt(0));

        System.out.print("Nhap don gia loai 1: ");
        lk.donGia1 = sc.nextInt();

        System.out.print("Nhap don gia loai 2: ");
        lk.donGia2 = sc.nextInt();
    }

    // Nhap danh sach linh kien
    static void NhapDS(LinhKien[] a, int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("\nLinh kien thu " + (i + 1) + ":");
            a[i] = new LinhKien();
            Nhap1LK(a[i]);
        }
    }

    // So sanh loai va ten linh kien
    static boolean LonHon(LinhKien a, LinhKien b) {
        if (a.loai != b.loai) {
            return a.loai > b.loai;
        }
        return a.ten.compareToIgnoreCase(b.ten) > 0;
    }

    // Sap xep tang dan theo loai va ten
    static void SapXep(LinhKien[] a, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (LonHon(a[i], a[j])) {
                    LinhKien temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    // Xuat danh sach linh kien
    static void XuatDS(LinhKien[] a, int n) {
        System.out.printf("%-25s %-20s %-8s %-15s %-15s%n",
                "Ten linh kien", "Quy cach", "Loai",
                "Don gia loai 1", "Don gia loai 2");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-25s %-20s %-8c %-15d %-15d%n",
                    a[i].ten, a[i].quyCach, a[i].loai,
                    a[i].donGia1, a[i].donGia2);
        }
    }

    // Kiem tra co du 10 linh kien loai A hay khong
    static boolean DuLinhKienA(LinhKien[] a, int n) {
        int dem = 0;

        for (int i = 0; i < n; i++) {
            if (a[i].loai == 'A') {
                dem++;
            }
        }

        return dem >= 10;
    }

    public static void main(String[] args) {
        System.out.print("Nhap so luong linh kien: ");
        int n = sc.nextInt();

        LinhKien[] a = new LinhKien[n];

        NhapDS(a, n);

        SapXep(a, n);

        System.out.println("\nDanh sach linh kien sau khi sap xep:");
        XuatDS(a, n);

        if (DuLinhKienA(a, n)) {
            System.out.println("\nCua hang da du 10 linh kien loai A.");
        } else {
            System.out.println("\nCua hang chua du 10 linh kien loai A.");
        }
    }
}

class Bai5 {
    private static class Sach {
        String MaSach;
        String TenSach;
        String TacGia;
        int SL;
    }

    // Nhap danh sach sach
    static void NhapDanhSach(Sach[] A, int n) {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        for (int i = 0; i < n; i++) {
            System.out.println("\nNhap thong tin sach thu " + (i + 1) + ":");

            System.out.print("Ma sach: ");
            A[i].MaSach = sc.nextLine();

            System.out.print("Ten sach: ");
            A[i].TenSach = sc.nextLine();

            System.out.print("Tac gia: ");
            A[i].TacGia = sc.nextLine();

            System.out.print("So luong: ");
            A[i].SL = Integer.parseInt(sc.nextLine());
        }
    }

    // Xuat thong tin mot sach
    static void XuatSach(Sach s) {
        System.out.println("Ma sach: " + s.MaSach);
        System.out.println("Ten sach: " + s.TenSach);
        System.out.println("Tac gia: " + s.TacGia);
        System.out.println("So luong: " + s.SL);
    }

    // Tim sach theo ten
    static void TimSach(Sach[] A, int n, String ten) {
        boolean timThay = false;

        for (int i = 0; i < n; i++) {
            if (A[i].TenSach.equalsIgnoreCase(ten)) {
                XuatSach(A[i]);
                System.out.println("--------------------");
                timThay = true;
            }
        }

        if (!timThay) {
            System.out.println("Khong tim thay");
        }
    }

    // Tinh tong so cuon sach trong thu vien
    static int TongSoSach(Sach[] A, int n) {
        int tong = 0;

        for (int i = 0; i < n; i++) {
            tong += A[i].SL;
        }

        return tong;
    }

    // Ham main
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        Sach[] A = new Sach[100];

        int n;
        do {
            System.out.print("Nhap so dau sach (1-100): ");
            n = Integer.parseInt(sc.nextLine());
        } while (n < 1 || n > 100);

        for (int i = 0; i < n; i++) {
            A[i] = new Sach();
        }

        NhapDanhSach(A, n);

        System.out.print("\nNhap ten sach can tim: ");
        String ten = sc.nextLine();

        System.out.println("\nThong tin sach tim duoc:");
        TimSach(A, n, ten);

        System.out.println("\nTong so cuon sach trong thu vien: "
                + TongSoSach(A, n));
    }
}


class Bai6 {
    private static Scanner sc = new Scanner(System.in);

    private static class MatHang {
        private String maHang;
        private String tenHang;
        private int soLuong;
        private int soLuongTon;
        private double donGia;
        private int thoiGianBaoHanh;

        public MatHang(String maHang, String tenHang, int soLuong,
                       int soLuongTon, double donGia,
                       int thoiGianBaoHanh) {
            this.maHang = maHang;
            this.tenHang = tenHang;
            this.soLuong = soLuong;
            this.soLuongTon = soLuongTon;
            this.donGia = donGia;
            this.thoiGianBaoHanh = thoiGianBaoHanh;
        }

        public void xuat() {
            System.out.printf(
                    "%-10s %-20s %-10d %-12d %-12.2f %-10d%n",
                    maHang, tenHang, soLuong, soLuongTon,
                    donGia, thoiGianBaoHanh
            );
        }
    }

    // Nhap danh sach mat hang
    public static void nhapDanhSach(MatHang[] a, int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("\nNhap mat hang thu " + (i + 1) + ":");

            System.out.print("Ma hang: ");
            String ma = sc.nextLine();

            System.out.print("Ten hang: ");
            String ten = sc.nextLine();

            System.out.print("So luong: ");
            int sl = Integer.parseInt(sc.nextLine());

            System.out.print("So luong ton: ");
            int ton = Integer.parseInt(sc.nextLine());

            System.out.print("Don gia: ");
            double gia = Double.parseDouble(sc.nextLine());

            System.out.print("Thoi gian bao hanh (thang): ");
            int bh = Integer.parseInt(sc.nextLine());

            a[i] = new MatHang(ma, ten, sl, ton, gia, bh);
        }
    }

    // Xuat danh sach mat hang
    public static void xuatDanhSach(MatHang[] a, int n) {
        System.out.printf("%-10s %-20s %-10s %-12s %-12s %-10s%n",
                "Ma hang", "Ten hang", "So luong",
                "So luong ton", "Don gia", "Bao hanh");

        for (int i = 0; i < n; i++) {
            a[i].xuat();
        }
    }

    // Tim mat hang co so luong ton nhieu nhat
    public static void tonNhieuNhat(MatHang[] a, int n) {
        int max = a[0].soLuongTon;

        for (int i = 1; i < n; i++) {
            if (a[i].soLuongTon > max) {
                max = a[i].soLuongTon;
            }
        }

        System.out.println("Mat hang co so luong ton nhieu nhat:");
        for (int i = 0; i < n; i++) {
            if (a[i].soLuongTon == max) {
                a[i].xuat();
            }
        }
    }

    // Tim mat hang co so luong ton it nhat
    public static void tonItNhat(MatHang[] a, int n) {
        int min = a[0].soLuongTon;

        for (int i = 1; i < n; i++) {
            if (a[i].soLuongTon < min) {
                min = a[i].soLuongTon;
            }
        }

        System.out.println("Mat hang co so luong ton it nhat:");
        for (int i = 0; i < n; i++) {
            if (a[i].soLuongTon == min) {
                a[i].xuat();
            }
        }
    }

    // Tim mat hang co don gia cao nhat
    public static void giaCaoNhat(MatHang[] a, int n) {
        double max = a[0].donGia;

        for (int i = 1; i < n; i++) {
            if (a[i].donGia > max) {
                max = a[i].donGia;
            }
        }

        System.out.println("Mat hang co don gia cao nhat:");
        for (int i = 0; i < n; i++) {
            if (a[i].donGia == max) {
                a[i].xuat();
            }
        }
    }

    // In mat hang co thoi gian bao hanh tren 12 thang
    public static void baoHanhTren12Thang(MatHang[] a, int n) {
        boolean timThay = false;

        System.out.println("Mat hang co bao hanh tren 12 thang:");
        for (int i = 0; i < n; i++) {
            if (a[i].thoiGianBaoHanh > 12) {
                a[i].xuat();
                timThay = true;
            }
        }

        if (!timThay) {
            System.out.println("Khong co mat hang nao.");
        }
    }

    // Sap xep tang dan theo so luong ton
    public static void sapXepTonTangDan(MatHang[] a, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (a[i].soLuongTon > a[j].soLuongTon) {
                    MatHang temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }

        System.out.println("Danh sach sau khi sap xep tang dan theo so luong ton:");
        xuatDanhSach(a, n);
    }

    // Ham main
    public static void main(String[] args) {
        System.out.print("Nhap so mat hang: ");
        int n = Integer.parseInt(sc.nextLine());

        if (n <= 0) {
            System.out.println("So mat hang phai lon hon 0.");
            return;
        }

        MatHang[] a = new MatHang[n];

        nhapDanhSach(a, n);

        System.out.println("\n=== DANH SACH MAT HANG ===");
        xuatDanhSach(a, n);

        System.out.println("\n=== KET QUA ===");
        tonNhieuNhat(a, n);
        tonItNhat(a, n);
        giaCaoNhat(a, n);
        baoHanhTren12Thang(a, n);

        System.out.println();
        sapXepTonTangDan(a, n);
    }
}

class Bai7 {

    private static class NhanVien {
        String hoTen;
        String ngaySinh;
        String phai;
        String diaChi;
        double luongCoBan;
        double thuong;
        double baoHiemXaHoi;
        double phat;

        double tinhLuongThucLinh() {
            return luongCoBan + thuong - baoHiemXaHoi - phat;
        }
    }

    // Nhap thong tin mot nhan vien
    private static void nhap1NV(NhanVien nv, Scanner sc) {
        sc.nextLine();

        System.out.print("Ho va ten: ");
        nv.hoTen = sc.nextLine();

        System.out.print("Ngay sinh: ");
        nv.ngaySinh = sc.nextLine();

        System.out.print("Phai: ");
        nv.phai = sc.nextLine();

        System.out.print("Dia chi: ");
        nv.diaChi = sc.nextLine();

        System.out.print("Luong co ban: ");
        nv.luongCoBan = sc.nextDouble();

        System.out.print("Thuong: ");
        nv.thuong = sc.nextDouble();

        System.out.print("Bao hiem xa hoi: ");
        nv.baoHiemXaHoi = sc.nextDouble();

        System.out.print("Phat: ");
        nv.phat = sc.nextDouble();
    }

    // Nhap danh sach nhan vien
    private static void nhapDS(NhanVien[] ds, int n, Scanner sc) {
        for (int i = 0; i < n; i++) {
            System.out.println("\nNhan vien thu " + (i + 1));
            ds[i] = new NhanVien();
            nhap1NV(ds[i], sc);
        }
    }

    // Xuat thong tin mot nhan vien
    private static void xuat1NV(NhanVien nv) {
        System.out.println("Ho ten: " + nv.hoTen);
        System.out.println("Ngay sinh: " + nv.ngaySinh);
        System.out.println("Phai: " + nv.phai);
        System.out.println("Dia chi: " + nv.diaChi);
        System.out.println("Luong co ban: " + nv.luongCoBan);
        System.out.println("Thuong: " + nv.thuong);
        System.out.println("Bao hiem xa hoi: " + nv.baoHiemXaHoi);
        System.out.println("Phat: " + nv.phat);
        System.out.println("Luong thuc linh: " + nv.tinhLuongThucLinh());
    }

    // Xuat danh sach nhan vien
    private static void xuatDS(NhanVien[] ds, int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("\nNhan vien thu " + (i + 1));
            xuat1NV(ds[i]);
        }
    }

    // Sap xep luong thuc linh giam dan
    private static void sapXepGiamDan(NhanVien[] ds, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (ds[i].tinhLuongThucLinh()
                        < ds[j].tinhLuongThucLinh()) {
                    NhanVien temp = ds[i];
                    ds[i] = ds[j];
                    ds[j] = temp;
                }
            }
        }
    }

    // Cach 1: Cap phat vung nho tinh
    private static void cachTinh(Scanner sc) {
        final int MAX = 100;
        NhanVien[] ds = new NhanVien[MAX];

        System.out.print("Nhap so nhan vien (1-" + MAX + "): ");
        int n = sc.nextInt();

        if (n < 1 || n > MAX) {
            System.out.println("So luong khong hop le!");
            return;
        }

        nhapDS(ds, n, sc);
        sapXepGiamDan(ds, n);

        System.out.println("\nDanh sach theo luong thuc linh giam dan:");
        xuatDS(ds, n);
    }

    // Cach 2: Cap phat vung nho dong
    private static void cachDong(Scanner sc) {
        System.out.print("Nhap so nhan vien: ");
        int n = sc.nextInt();

        if (n < 1) {
            System.out.println("So luong khong hop le!");
            return;
        }

        NhanVien[] ds = new NhanVien[n];

        nhapDS(ds, n, sc);
        sapXepGiamDan(ds, n);

        System.out.println("\nDanh sach theo luong thuc linh giam dan:");
        xuatDS(ds, n);
    }

    // Ham main
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Cap phat vung nho tinh");
        System.out.println("2. Cap phat vung nho dong");
        System.out.print("Chon cach thuc hien: ");

        int chon = sc.nextInt();

        switch (chon) {
            case 1:
                cachTinh(sc);
                break;
            case 2:
                cachDong(sc);
                break;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }
}

class Bai8 {

    private static class Ngay {
        int ngay;
        int thang;
        int nam;
    }

    private static void NhapNgay(Ngay n, java.util.Scanner sc) {
        System.out.print("Nhap ngay: ");
        n.ngay = sc.nextInt();

        System.out.print("Nhap thang: ");
        n.thang = sc.nextInt();

        System.out.print("Nhap nam: ");
        n.nam = sc.nextInt();
    }

    private static boolean NamNhuan(int nam) {
        return (nam % 400 == 0) ||
                (nam % 4 == 0 && nam % 100 != 0);
    }

    private static int SoNgayTrongThang(int thang, int nam) {
        switch (thang) {
            case 2:
                return NamNhuan(nam) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    private static long DoiRaNgay(Ngay n) {
        long tong = 0;

        for (int i = 1; i < n.nam; i++) {
            tong += NamNhuan(i) ? 366 : 365;
        }

        for (int i = 1; i < n.thang; i++) {
            tong += SoNgayTrongThang(i, n.nam);
        }

        tong += n.ngay;
        return tong;
    }

    private static void TinhKhoangCach(Ngay n1, Ngay n2) {
        if (DoiRaNgay(n1) > DoiRaNgay(n2)) {
            Ngay temp = n1;
            n1 = n2;
            n2 = temp;
        }

        int ngay = n1.ngay;
        int thang = n1.thang;
        int nam = n1.nam;

        int soNgay = n2.ngay - ngay;
        int soThang = n2.thang - thang;
        int soNam = n2.nam - nam;

        if (soNgay < 0) {
            soThang--;
            int thangTruoc = n2.thang - 1;
            int namTruoc = n2.nam;

            if (thangTruoc == 0) {
                thangTruoc = 12;
                namTruoc--;
            }

            soNgay += SoNgayTrongThang(thangTruoc, namTruoc);
        }

        if (soThang < 0) {
            soNam--;
            soThang += 12;
        }

        System.out.println("\nKhoang cach giua hai ngay:");
        System.out.println(soNgay + " ngay, "
                + soThang + " thang va "
                + soNam + " nam.");
    }

    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        Ngay ngay1 = new Ngay();
        Ngay ngay2 = new Ngay();

        System.out.println("Nhap ngay thu nhat:");
        NhapNgay(ngay1, sc);

        System.out.println("\nNhap ngay thu hai:");
        NhapNgay(ngay2, sc);

        TinhKhoangCach(ngay1, ngay2);
    }
}

class Bai9 {

    static class ThoiGian {
        int gio;
        int phut;
        int giay;
    }

    static void NhapThoiGian(ThoiGian tg, java.util.Scanner sc) {
        System.out.print("Nhap gio: ");
        tg.gio = sc.nextInt();

        System.out.print("Nhap phut: ");
        tg.phut = sc.nextInt();

        System.out.print("Nhap giay: ");
        tg.giay = sc.nextInt();
    }

    static void XuatThoiGian(ThoiGian tg) {
        System.out.printf("%02d:%02d:%02d%n",
                tg.gio, tg.phut, tg.giay);
    }

    static int DoiRaGiay(ThoiGian tg) {
        return tg.gio * 3600 + tg.phut * 60 + tg.giay;
    }

    static int KhoangCach(ThoiGian tg1, ThoiGian tg2) {
        return Math.abs(DoiRaGiay(tg1) - DoiRaGiay(tg2));
    }

    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        ThoiGian tg1 = new ThoiGian();
        ThoiGian tg2 = new ThoiGian();

        System.out.println("Nhap moc thoi gian thu nhat:");
        NhapThoiGian(tg1, sc);

        System.out.println("\nNhap moc thoi gian thu hai:");
        NhapThoiGian(tg2, sc);

        System.out.print("\nMoc thoi gian thu nhat: ");
        XuatThoiGian(tg1);

        System.out.print("Moc thoi gian thu hai: ");
        XuatThoiGian(tg2);

        int kc = KhoangCach(tg1, tg2);

        System.out.printf(
                "Khoang cach: %d gio, %d phut, %d giay%n",
                kc / 3600,
                (kc % 3600) / 60,
                kc % 60
        );
    }
}