package buoi2;
import java.util.ArrayList;
import java.util.Scanner;

public class Buoi2 {
    public static void main(String[] args) {
//        Bai1.main(args);
//        Bai2.main(args);
//        Bai3.main(args);
    }
}

// Bài 1 do không rõ phần bài 3.1.3 nên em giả định phần phân số như thế này
class PHANSO {
    int tu, mau;

    PHANSO(int tu, int mau) {
        this.tu = tu;
        this.mau = mau;
    }
}

class Bai1 {

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