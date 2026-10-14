package buoi3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Buoi3 {
    public static void main(String[] args) {
//        Bai11.main(args);
//        Bai21.main(args);
//        Bai31.main(args);
//        Bai41.main(args);
//        Bai12.main(args);
//        Bai22.main(args);
//        Bai32.main(args);
//        Bai122.main(args);
//        Bai222.main(args);
//        Bai322.main(args);
//        Bai422.main(args);
//        Bai522.main(args);
//        Bai63.main(args);
//        Bai73.main(args);
//        Bai83.main(args);
//        Bai93.main(args);
//        Bai103.main(args);
//        Bai133.main(args);
//        Bai233.main(args);
//        Bai243.main(args);
//        Bai343.main(args);
//        Bai443.main(args);
//        Bai114.main(args);
//        Bai124.main(args);
//        Bai324.main(args);
//        Bai134.main(args);
//        Bai234.main(args);
//        Bai35.main(args);
//        Bai145.main(args);
//        Bai245.main(args);
    }
}

class Bai11 {
    private int tu;
    private int mau;

    public Bai11(int tu, int mau) {
        if (mau == 0) {
            throw new IllegalArgumentException("Mau so phai khac 0!");
        }

        this.tu = tu;
        this.mau = mau;
        rutGon();
    }

    private void rutGon() {
        int a = Math.abs(tu);
        int b = Math.abs(mau);

        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        if (a != 0) {
            tu /= a;
            mau /= a;
        }

        if (mau < 0) {
            tu = -tu;
            mau = -mau;
        }
    }

    // Nhap mot phan so
    public static Bai11 nhap(Scanner sc) {
        System.out.print("Nhap tu so: ");
        int tu = sc.nextInt();

        int mau;
        do {
            System.out.print("Nhap mau so khac 0: ");
            mau = sc.nextInt();
        } while (mau == 0);

        return new Bai11(tu, mau);
    }

    // Qua tai phep cong
    public Bai11 cong(Bai11 ps) {
        return new Bai11(
                tu * ps.mau + ps.tu * mau,
                mau * ps.mau
        );
    }

    // Qua tai phep tru
    public Bai11 tru(Bai11 ps) {
        return new Bai11(
                tu * ps.mau - ps.tu * mau,
                mau * ps.mau
        );
    }

    // Qua tai phep nhan
    public Bai11 nhan(Bai11 ps) {
        return new Bai11(tu * ps.tu, mau * ps.mau);
    }

    // Qua tai phep chia
    public Bai11 chia(Bai11 ps) {
        if (ps.tu == 0) {
            throw new ArithmeticException(
                    "Khong the chia cho phan so 0!"
            );
        }

        return new Bai11(tu * ps.mau, mau * ps.tu);
    }

    public void xuat() {
        System.out.println(
                mau == 1 ? tu : tu + "/" + mau
        );
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Nhap phan so thu nhat:");
        Bai11 ps1 = nhap(sc);

        System.out.println("Nhap phan so thu hai:");
        Bai11 ps2 = nhap(sc);

        System.out.println("\nChon phep toan:");
        System.out.println("1. Cong (+)");
        System.out.println("2. Tru (-)");
        System.out.println("3. Nhan (*)");
        System.out.println("4. Chia (/)");

        System.out.print("Lua chon: ");
        int chon = sc.nextInt();

        Bai11 ketQua;

        switch (chon) {
            case 1:
                ketQua = ps1.cong(ps2);
                break;
            case 2:
                ketQua = ps1.tru(ps2);
                break;
            case 3:
                ketQua = ps1.nhan(ps2);
                break;
            case 4:
                ketQua = ps1.chia(ps2);
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                return;
        }

        System.out.print("Ket qua: ");
        ketQua.xuat();
    }
}

class Bai21 {

    static class NhietDo {
        private double doC;
        private double doF;

        public NhietDo(double giaTri, int loai) {
            if (loai == 0) {
                this.doC = giaTri;
                this.doF = doC * 9 / 5 + 32;
            } else {
                this.doF = giaTri;
                this.doC = (doF - 32) * 5 / 9;
            }
        }

        public double getDoC() {
            return doC;
        }

        public double getDoF() {
            return doF;
        }

//        public static double c2f(double c) {
//            return c * 9 / 5 + 32;
//        }

//        public static double f2c(double f) {
//            return (f - 32) * 5 / 9;
//        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap nhiet do C: ");
        double c = sc.nextDouble();

        System.out.print("Nhap nhiet do F: ");
        double f = sc.nextDouble();

        NhietDo nhietC = new NhietDo(c, 0);
        NhietDo nhietF = new NhietDo(f, 1);

        System.out.printf("%.2f do C = %.2f do F%n",
                c, nhietC.getDoF());

        System.out.printf("%.2f do F = %.2f do C%n",
                f, nhietF.getDoC());

        sc.close();
    }
}


class Bai31 {

    static class SinhVien implements Serializable {
        int mssv;
        String hoTen;
        float diemTB;

        SinhVien(int mssv, String hoTen, float diemTB) {
            this.mssv = mssv;
            this.hoTen = hoTen;
            this.diemTB = diemTB;
        }

        void hienThi() {
            System.out.printf("%-12d %-30s %.2f%n",
                    mssv, hoTen, diemTB);
        }
    }

    static ArrayList<SinhVien> ds = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // a. Hiển thị danh sách sinh viên
    static void hienThiDanhSach() {
        if (ds.isEmpty()) {
            System.out.println("Danh sach sinh vien rong.");
            return;
        }

        System.out.printf("%-12s %-30s %s%n",
                "MSSV", "Ho ten", "Diem TB");

        for (SinhVien sv : ds) {
            sv.hienThi();
        }
    }

    // b. Cập nhật thông tin sinh viên theo MSSV
    static void capNhatSinhVien() {
        System.out.print("Nhap MSSV can cap nhat: ");
        int ma = Integer.parseInt(sc.nextLine());

        for (SinhVien sv : ds) {
            if (sv.mssv == ma) {
                System.out.print("Nhap ho ten moi: ");
                sv.hoTen = sc.nextLine();

                System.out.print("Nhap diem TB moi: ");
                sv.diemTB = Float.parseFloat(sc.nextLine());

                System.out.println("Cap nhat thanh cong.");
                return;
            }
        }

        System.out.println("Khong tim thay sinh vien.");
    }

    // c. Tạo mới danh sách sinh viên
    static void taoDanhSachMoi() {
        System.out.print("Nhap so luong sinh vien: ");
        int n = Integer.parseInt(sc.nextLine());

        if (n < 0) {
            System.out.println("So luong khong hop le.");
            return;
        }

        ArrayList<SinhVien> dsMoi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println("\nSinh vien thu " + (i + 1));

            System.out.print("Nhap MSSV: ");
            int ma = Integer.parseInt(sc.nextLine());

            boolean trung = false;
            for (SinhVien sv : dsMoi) {
                if (sv.mssv == ma) {
                    trung = true;
                    break;
                }
            }

            if (trung) {
                System.out.println("MSSV bi trung, nhap lai sinh vien nay.");
                i--;
                continue;
            }

            System.out.print("Nhap ho ten: ");
            String ten = sc.nextLine();

            System.out.print("Nhap diem TB: ");
            float diem = Float.parseFloat(sc.nextLine());

            dsMoi.add(new SinhVien(ma, ten, diem));
        }

        ds = dsMoi;
        System.out.println("Da tao danh sach moi.");
    }

    // d. Lưu danh sách sinh viên ra file
    static void luuDanhSach() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("danhsachsinhvien.dat"))) {

            out.writeObject(ds);
            System.out.println("Da luu danh sach vao danhsachsinhvien.dat");

        } catch (IOException e) {
            System.out.println("Loi khi luu file: " + e.getMessage());
        }
    }

    // e. Xóa sinh viên theo MSSV
    static void xoaSinhVien() {
        System.out.print("Nhap MSSV can xoa: ");
        int ma = Integer.parseInt(sc.nextLine());

        for (SinhVien sv : ds) {
            if (sv.mssv == ma) {
                ds.remove(sv);
                System.out.println("Da xoa sinh vien.");
                return;
            }
        }

        System.out.println("Khong tim thay sinh vien.");
    }

    // Hàm main
    public static void main(String[] args) {
        int chon;

        do {
            System.out.println("\n===== QUAN LY SINH VIEN =====");
            System.out.println("1. Hien thi danh sach sinh vien");
            System.out.println("2. Cap nhat sinh vien theo MSSV");
            System.out.println("3. Tao moi danh sach sinh vien");
            System.out.println("4. Luu danh sach sinh vien");
            System.out.println("5. Xoa sinh vien theo MSSV");
            System.out.println("0. Thoat");
            System.out.print("Nhap lua chon: ");

            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                chon = -1;
            }

            switch (chon) {
                case 1:
                    hienThiDanhSach();
                    break;
                case 2:
                    capNhatSinhVien();
                    break;
                case 3:
                    taoDanhSachMoi();
                    break;
                case 4:
                    luuDanhSach();
                    break;
                case 5:
                    xoaSinhVien();
                    break;
                case 0:
                    System.out.println("Ket thuc chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }

        } while (chon != 0);
    }
}

class Bai41 {

    // Lop Sinh vien
    private static class SinhVien {
        private int mssv;
        private String tenSV;
        private double diemRL;

        public SinhVien(int mssv, String tenSV, double diemRL) {
            this.mssv = mssv;
            this.tenSV = tenSV;
            this.diemRL = diemRL;
        }

        public void hienThi() {
            System.out.printf("%-10d %-25s %.2f%n",
                    mssv, tenSV, diemRL);
        }
    }

    // Lop Danh sach sinh vien chua cac SinhVien
    private static class DanhSachSinhVien {
        private ArrayList<SinhVien> ds = new ArrayList<>();

        public void them(SinhVien sv) {
            ds.add(sv);
        }

        public void nhapTuBanPhim(Scanner sc) {
            System.out.print("Nhap so sinh vien: ");
            int n = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < n; i++) {
                System.out.println("\nSinh vien " + (i + 1));

                System.out.print("MSSV: ");
                int mssv = Integer.parseInt(sc.nextLine());

                System.out.print("Ten sinh vien: ");
                String ten = sc.nextLine();

                System.out.print("Diem ren luyen: ");
                double diem = Double.parseDouble(sc.nextLine());

                them(new SinhVien(mssv, ten, diem));
            }
        }

        public void hienThi() {
            System.out.printf("%-10s %-25s %s%n",
                    "MSSV", "Ho ten", "Diem RL");

            for (SinhVien sv : ds) {
                sv.hienThi();
            }
        }

        // Doc CSV duoc luu tu Excel
        public void nhapTuCSV(String tenFile) throws IOException {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(tenFile), "UTF-8"))) {

                String line;
                boolean dongDau = true;

                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    // Bo qua dong tieu de
                    if (dongDau) {
                        dongDau = false;
                        if (line.toLowerCase().contains("mssv")) {
                            continue;
                        }
                    }

                    String[] p = line.split(",", -1);

                    if (p.length < 3) {
                        continue;
                    }

                    int mssv = Integer.parseInt(p[0].trim());
                    String ten = p[1].trim();
                    double diem = Double.parseDouble(
                            p[2].trim().replace(',', '.'));

                    them(new SinhVien(mssv, ten, diem));
                }
            }
        }

        // Chuyen danh sach sinh vien sang XML
        public void xuatXML(String tenFile) throws Exception {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document doc = builder.newDocument();

            Element root = doc.createElement("DanhSachSinhVien");
            doc.appendChild(root);

            for (SinhVien sv : ds) {
                Element sinhVien = doc.createElement("SinhVien");
                root.appendChild(sinhVien);

                Element ma = doc.createElement("MSSV");
                ma.setTextContent(String.valueOf(sv.mssv));
                sinhVien.appendChild(ma);

                Element ten = doc.createElement("TenSV");
                ten.setTextContent(sv.tenSV);
                sinhVien.appendChild(ten);

                Element diem = doc.createElement("DiemRL");
                diem.setTextContent(String.valueOf(sv.diemRL));
                sinhVien.appendChild(diem);
            }

            Transformer transformer = TransformerFactory
                    .newInstance().newTransformer();

            transformer.setOutputProperty(
                    OutputKeys.INDENT, "yes");

            transformer.setOutputProperty(
                    OutputKeys.ENCODING, "UTF-8");

            transformer.transform(
                    new DOMSource(doc),
                    new StreamResult(new File(tenFile)));

            System.out.println("Da xuat XML: " + tenFile);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DanhSachSinhVien ds = new DanhSachSinhVien();

        while (true) {
            System.out.println("\n===== MENU BAI 4 =====");
            System.out.println("1. Nhap sinh vien tu ban phim");
            System.out.println("2. Doc danh sach tu file CSV");
            System.out.println("3. Hien thi danh sach");
            System.out.println("4. Xuat danh sach ra XML");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            String chon = sc.nextLine();

            try {
                switch (chon) {
                    case "1":
                        ds.nhapTuBanPhim(sc);
                        break;

                    case "2":
                        System.out.print("Ten file CSV: ");
                        String file = sc.nextLine();
                        ds.nhapTuCSV(file);
                        System.out.println("Da doc file.");
                        break;

                    case "3":
                        ds.hienThi();
                        break;

                    case "4":
                        ds.xuatXML("sinhvien.xml");
                        break;

                    case "0":
                        return;

                    default:
                        System.out.println("Lua chon khong hop le.");
                }
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
            }
        }
    }
}


// Buoi 2
class Bai12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap so nguyen: ");
        int n = sc.nextInt();

        if (n > 0)
            System.out.println("So duong");
        else if (n < 0)
            System.out.println("So am");
        else
            System.out.println("So 0, khong am khong duong");
    }
}

class Bai22 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        if (n < 0) {
            System.out.println("n phai >= 0");
            return;
        }

        long tich = 1;

        for (int i = 1; i <= n; i++) {
            tich *= i;
        }

        System.out.println("Tich = " + tich);
    }
}

class Bai32 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap a: ");
        int a = sc.nextInt();

        System.out.print("Nhap b: ");
        int b = sc.nextInt();

        long x = Math.abs((long) a);
        long y = Math.abs((long) b);

        while (y != 0) {
            long r = x % y;
            x = y;
            y = r;
        }

        System.out.println("UCLN = " + x);
    }
}

class Bai122 {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        System.out.print("Nhap don gia: ");
        double donGia = sc.nextDouble();

        System.out.print("Nhap so luong ban: ");
        int soLuong = sc.nextInt();

        double thanhTien = donGia * soLuong;
        double giamGia = 0;

        if (thanhTien > 100) {
            giamGia = thanhTien * 0.03;
        }

        double tongTien = thanhTien - giamGia;

        System.out.println("Thanh tien: " + thanhTien);
        System.out.println("Giam gia: " + giamGia);
        System.out.println("Tong tien phai tra: " + tongTien);
    }
}

class Bai222 {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);

        System.out.print("Nhap so KW dien tieu thu: ");
        int kw = sc.nextInt();

        if (kw < 0) {
            System.out.println("So KW khong hop le!");
            return;
        }

        int tien = 0;

        if (kw <= 100) {
            tien = kw * 5;
        } else if (kw <= 150) {
            tien = 100 * 5 + (kw - 100) * 7;
        } else if (kw <= 200) {
            tien = 100 * 5 + 50 * 7 + (kw - 150) * 10;
        } else if (kw <= 300) {
            tien = 100 * 5 + 50 * 7 + 50 * 10
                    + (kw - 200) * 15;
        } else {
            tien = 100 * 5 + 50 * 7 + 50 * 10
                    + 100 * 15 + (kw - 300) * 20;
        }

        System.out.println("Tien dien phai tra: " + tien + "$");
    }
}

 class Bai322 {
    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            System.out.println("Bang cuu chuong " + i + ":");
            for (int j = 1; j <= 10; j++) {
                System.out.println(i + " x " + j + " = " + (i * j));
            }
            System.out.println();
        }
    }
}

class Bai422 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                System.out.printf("%2d x %2d = %2d\t", j, i, i * j);
            }
            System.out.println();
        }
    }
}

class Bai522 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap chieu dai d: ");
        int d = sc.nextInt();

        System.out.print("Nhap chieu rong r: ");
        int r = sc.nextInt();

        if (d <= 0 || r <= 0) {
            System.out.println("Kich thuoc phai lon hon 0.");
            return;
        }

        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= d; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}

class Bai133 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double a, b, c;
        System.out.print("Nhap a: ");
        a = sc.nextDouble();
        System.out.print("Nhap b: ");
        b = sc.nextDouble();
        System.out.print("Nhap c: ");
        c = sc.nextDouble();

        if (a == 0) {
            if (b == 0) {
                if (c == 0)
                    System.out.println("Phuong trinh vo so nghiem");
                else
                    System.out.println("Phuong trinh vo nghiem");
            } else {
                System.out.println("Phuong trinh co nghiem x = " + (-c / b));
            }
        } else {
            double delta = b * b - 4 * a * c;

            if (delta < 0) {
                System.out.println("Phuong trinh vo nghiem thuc");
            } else if (delta == 0) {
                System.out.println("Nghiem kep x = " + (-b / (2 * a)));
            } else {
                double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                double x2 = (-b - Math.sqrt(delta)) / (2 * a);

                System.out.println("x1 = " + x1);
                System.out.println("x2 = " + x2);
            }
        }

        sc.close();
    }
}

class Bai233 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int h;
        System.out.print("Nhap chieu cao tam giac: ");
        h = sc.nextInt();

        if (h < 1) {
            System.out.println("Chieu cao khong hop le");
        } else {
            for (int i = 1; i <= h; i++) {
                for (int j = 1; j <= 2 * h - 1; j++) {
                    if (j == h - i + 1
                            || j == h + i - 1
                            || i == h) {
                        System.out.print("*");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }

        sc.close();
    }
}

class Bai63 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap chieu dai d: ");
        int d = sc.nextInt();

        System.out.print("Nhap chieu rong r: ");
        int r = sc.nextInt();

        if (d <= 0 || r <= 0) {
            System.out.println("Kich thuoc phai lon hon 0!");
            return;
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < d; j++) {
                if (i == 0 || i == r - 1 ||
                        j == 0 || j == d - 1) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}


class Bai73 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;
        do {
            System.out.print("Nhap n > 0: ");
            n = sc.nextInt();
        } while (n <= 0);

        long sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += (long) i * i;
        }

        System.out.println("S = " + sum);
    }
}


class Bai83 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        boolean prime = n >= 2;

        for (int i = 2; i <= n / i && prime; i++) {
            if (n % i == 0) {
                prime = false;
            }
        }

        if (prime) {
            System.out.println(n + " la so nguyen to.");
        } else {
            System.out.println(n + " khong phai so nguyen to.");
        }
    }
}


class Bai93 {
    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i <= n / i; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        System.out.println("Cac so nguyen to nho hon " + n + ":");

        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }

        System.out.println();
    }
}


class Bai103 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long sum = 0;
        int n;

        System.out.println("Nhap cac so nguyen (0 de dung):");

        while (true) {
            n = sc.nextInt();

            if (n == 0) {
                break;
            }

            sum += n;
        }

        System.out.println("Tong day so = " + sum);
    }
}
class Bai243 {
    public static void main(String[] args) {
        System.out.println("Danh sach cac so co 2 chu so doi mot khac nhau la:");

        for (int i = 10; i <= 99; i++) {
            int hangChuc = i / 10;
            int hangDonVi = i % 10;

            if (hangChuc != hangDonVi) {
                System.out.print(i + ", ");
            }
        }
        System.out.println();
    }
}

class Bai343 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        System.out.println("Cac so le nho hon " + n + " la:");

        for (int i = 1; i < n; i += 2) {
            System.out.print(i + " ");
        }

        System.out.println();
    }
}

class Bai443 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        System.out.print("Nhap m: ");
        int m = sc.nextInt();

        int dem = 0;

        if (n > m) {
            int temp = n;
            n = m;
            m = temp;
        }

        for (int i = n; i <= m; i++) {
            if (i % 2 == 0) {
                dem++;
            }
        }

        System.out.println("So luong so chan trong [" + n + ", " + m + "] la: " + dem);
    }
}


class Bai114 {
    static Scanner sc = new Scanner(System.in);

    static void NhapMang(int[] a, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("Nhap a[" + i + "]: ");
            a[i] = sc.nextInt();
        }
    }

    static void XuatMang(int[] a, int n) {
        System.out.print("Mang a: ");
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.print("Nhap so phan tu n: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("n phai lon hon 0!");
            return;
        }

        int[] a = new int[n];

        NhapMang(a, n);
        XuatMang(a, n);
    }
}


class Bai124 {
    static Scanner sc = new Scanner(System.in);

    static int[] NhapMang(int n) {
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("a[" + i + "] = ");
            a[i] = sc.nextInt();
        }

        return a;
    }

    static void XuatMang(int[] a) {
        System.out.print("Mang a: ");
        for (int x : a) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    // Liet ke cac phan tu le o vi tri chan
    static void LietKeLeViTriChan(int[] a) {
        System.out.print("Cac phan tu le o vi tri chan: ");

        for (int i = 0; i < a.length; i++) {
            if (i % 2 == 0 && a[i] % 2 != 0) {
                System.out.print(a[i] + " ");
            }
        }

        System.out.println();
    }

    // Kiem tra so nguyen to
    static boolean LaSoNguyenTo(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i <= n / i; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    // Liet ke cac so nguyen to
    static void LietKeSoNguyenTo(int[] a) {
        System.out.print("Cac so nguyen to: ");

        for (int x : a) {
            if (LaSoNguyenTo(x)) {
                System.out.print(x + " ");
            }
        }

        System.out.println();
    }

    // Tim phan tu am dau tien
    static void TimAmDauTien(int[] a) {
        for (int x : a) {
            if (x < 0) {
                System.out.println("Phan tu am dau tien: " + x);
                return;
            }
        }

        System.out.println("Mang khong co phan tu am.");
    }

    // Tim gia tri lon nhat
    static int TimMax(int[] a) {
        int max = a[0];

        for (int x : a) {
            if (x > max) {
                max = x;
            }
        }

        return max;
    }

    // Tim gia tri nho nhat
    static int TimMin(int[] a) {
        int min = a[0];

        for (int x : a) {
            if (x < min) {
                min = x;
            }
        }

        return min;
    }

    // Tinh tong cac phan tu
    static int TinhTong(int[] a) {
        int tong = 0;

        for (int x : a) {
            tong += x;
        }

        return tong;
    }

    public static void main(String[] args) {
        int n;

        do {
            System.out.print("Nhap so phan tu n (> 0): ");
            n = sc.nextInt();
        } while (n <= 0);

        int[] a = NhapMang(n);

        XuatMang(a);
        LietKeLeViTriChan(a);
        LietKeSoNguyenTo(a);
        TimAmDauTien(a);

        System.out.println("Gia tri lon nhat: " + TimMax(a));
        System.out.println("Gia tri nho nhat: " + TimMin(a));
        System.out.println("Tong cac phan tu: " + TinhTong(a));
    }
}

class Bai324 {
    static Scanner sc = new Scanner(System.in);

    static String nhapChuoi() {
        System.out.print("Nhap ho ten: ");
        return sc.nextLine();
    }

    static boolean doiXung(String s) {
        s = s.replaceAll("\\s+", "").toLowerCase();
        int i = 0, j = s.length() - 1;

        while (i < j) {
            if (s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    static String vietHoaDauTu(String s) {
        String[] tu = s.trim().toLowerCase().split("\\s+");
        StringBuilder kq = new StringBuilder();

        for (String t : tu) {
            if (!t.isEmpty()) {
                kq.append(Character.toUpperCase(t.charAt(0)));
                kq.append(t.substring(1));
                kq.append(" ");
            }
        }
        return kq.toString().trim();
    }

    static String doiHoaThuong(String s) {
        StringBuilder kq = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c))
                kq.append(Character.toLowerCase(c));
            else if (Character.isLowerCase(c))
                kq.append(Character.toUpperCase(c));
            else
                kq.append(c);
        }
        return kq.toString();
    }

    static boolean laNguyenAm(char c) {
        c = Character.toLowerCase(c);
        return "aeiouáàảãạăắằẳẵặâấầẩẫậ"
                .concat("éèẻẽẹêếềểễệ")
                .concat("íìỉĩị")
                .concat("óòỏõọôốồổỗộơớờởỡợ")
                .concat("úùủũụưứừửữự")
                .concat("ýỳỷỹỵ")
                .indexOf(c) >= 0;
    }

    static void demKyTu(String s) {
        int nguyenAm = 0, phuAm = 0, khoangTrang = 0;

        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c))
                khoangTrang++;
            else if (Character.isLetter(c)) {
                if (laNguyenAm(c))
                    nguyenAm++;
                else
                    phuAm++;
            }
        }

        System.out.println("So nguyen am: " + nguyenAm);
        System.out.println("So phu am: " + phuAm);
        System.out.println("So khoang trang: " + khoangTrang);
    }

    public static void main(String[] args) {
        String s = nhapChuoi();

        System.out.println("Chuoi doi xung: "
                + (doiXung(s) ? "Co" : "Khong"));

        System.out.println("Viet hoa dau tu: "
                + vietHoaDauTu(s));

        System.out.println("Doi hoa thuong: "
                + doiHoaThuong(s));

        demKyTu(s);
    }
}

class Bai134 {
    static Scanner sc = new Scanner(System.in);

    static class DaThuc {
        int Bac;
        int[] ChiSoDaThuc;

        DaThuc(int bac) {
            Bac = bac;
            ChiSoDaThuc = new int[Bac + 1];
        }
    }

    static void Nhap(DaThuc p) {
        for (int i = p.Bac; i >= 0; i--) {
            System.out.print("Nhap he so cua x^" + i + ": ");
            p.ChiSoDaThuc[i] = sc.nextInt();
        }
    }

    static void Xuat(DaThuc p) {
        boolean first = true;

        for (int i = p.Bac; i >= 0; i--) {
            int hs = p.ChiSoDaThuc[i];

            if (hs == 0)
                continue;

            if (!first && hs > 0)
                System.out.print(" + ");
            else if (hs < 0)
                System.out.print(first ? "-" : " - ");

            int abs = Math.abs(hs);

            if (i == 0 || abs != 1)
                System.out.print(abs);

            if (i > 0) {
                System.out.print("x");
                if (i > 1)
                    System.out.print("^" + i);
            }

            first = false;
        }

        if (first)
            System.out.print("0");

        System.out.println();
    }

    static DaThuc Cong(DaThuc p, DaThuc q) {
        int bacMax = Math.max(p.Bac, q.Bac);
        DaThuc tong = new DaThuc(bacMax);

        for (int i = 0; i <= bacMax; i++) {
            int a = i <= p.Bac ? p.ChiSoDaThuc[i] : 0;
            int b = i <= q.Bac ? q.ChiSoDaThuc[i] : 0;

            tong.ChiSoDaThuc[i] = a + b;
        }

        while (tong.Bac > 0
                && tong.ChiSoDaThuc[tong.Bac] == 0) {
            tong.Bac--;
        }

        return tong;
    }

    public static void main(String[] args) {
        System.out.print("Nhap bac da thuc P: ");
        int bacP = sc.nextInt();
        while (bacP < 0) {
            System.out.print("Bac phai >= 0. Nhap lai: ");
            bacP = sc.nextInt();
        }

        DaThuc P = new DaThuc(bacP);
        System.out.println("Nhap da thuc P:");
        Nhap(P);

        System.out.print("Nhap bac da thuc Q: ");
        int bacQ = sc.nextInt();
        while (bacQ < 0) {
            System.out.print("Bac phai >= 0. Nhap lai: ");
            bacQ = sc.nextInt();
        }

        DaThuc Q = new DaThuc(bacQ);
        System.out.println("Nhap da thuc Q:");
        Nhap(Q);

        System.out.print("P(x) = ");
        Xuat(P);

        System.out.print("Q(x) = ");
        Xuat(Q);

        DaThuc R = Cong(P, Q);

        System.out.print("Tong P(x) + Q(x) = ");
        Xuat(R);
    }
}

class Bai234 {
    static Scanner sc = new Scanner(System.in);

    static void nhap(int[][] a, int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("a[" + i + "][" + j + "] = ");
                a[i][j] = sc.nextInt();
            }
        }
    }

    static void xuat(int[][] a, int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%5d", a[i][j]);
            }
            System.out.println();
        }
    }

    // Tim min, max cua ma tran va chi so
    static void minMax(int[][] a, int m, int n) {
        int min = a[0][0], max = a[0][0];
        int minI = 0, minJ = 0, maxI = 0, maxJ = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] < min) {
                    min = a[i][j];
                    minI = i;
                    minJ = j;
                }
                if (a[i][j] > max) {
                    max = a[i][j];
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        System.out.println("Min = " + min + " tai [" + minI + "][" + minJ + "]");
        System.out.println("Max = " + max + " tai [" + maxI + "][" + maxJ + "]");
    }

    // Tim min, max cua tung hang va chi so cot
    static void minMaxTungHang(int[][] a, int m, int n) {
        for (int i = 0; i < m; i++) {
            int min = a[i][0], max = a[i][0];
            int minJ = 0, maxJ = 0;

            for (int j = 1; j < n; j++) {
                if (a[i][j] < min) {
                    min = a[i][j];
                    minJ = j;
                }
                if (a[i][j] > max) {
                    max = a[i][j];
                    maxJ = j;
                }
            }

            System.out.println("Hang " + i + ": Min = " + min
                    + " tai cot " + minJ + ", Max = " + max
                    + " tai cot " + maxJ);
        }
    }

    // Tim min, max tren duong cheo chinh
    static void minMaxCheoChinh(int[][] a, int m, int n) {
        int k = Math.min(m, n);

        int min = a[0][0], max = a[0][0];
        int minI = 0, maxI = 0;

        for (int i = 1; i < k; i++) {
            if (a[i][i] < min) {
                min = a[i][i];
                minI = i;
            }
            if (a[i][i] > max) {
                max = a[i][i];
                maxI = i;
            }
        }

        System.out.println("Min cheo chinh = " + min
                + " tai [" + minI + "][" + minI + "]");
        System.out.println("Max cheo chinh = " + max
                + " tai [" + maxI + "][" + maxI + "]");
    }

    // Tim phan tu xuat hien nhieu nhat tren hang i
    static void phanTuNhieuNhat(int[][] a, int m, int n, int i) {
        if (i < 0 || i >= m) {
            System.out.println("Hang khong hop le!");
            return;
        }

        int giaTriMax = a[i][0];
        int soLanMax = 0;

        for (int j = 0; j < n; j++) {
            int soLan = 0;

            for (int k = 0; k < n; k++) {
                if (a[i][k] == a[i][j]) {
                    soLan++;
                }
            }

            if (soLan > soLanMax) {
                soLanMax = soLan;
                giaTriMax = a[i][j];
            }
        }

        System.out.println("Phan tu xuat hien nhieu nhat tren hang "
                + i + " la " + giaTriMax
                + " (" + soLanMax + " lan)");
    }

    public static void main(String[] args) {
        System.out.print("Nhap so hang m: ");
        int m = sc.nextInt();

        System.out.print("Nhap so cot n: ");
        int n = sc.nextInt();

        if (m <= 0 || n <= 0) {
            System.out.println("Kich thuoc ma tran khong hop le!");
            return;
        }

        int[][] a = new int[m][n];

        nhap(a, m, n);

        System.out.println("\nMa tran vua nhap:");
        xuat(a, m, n);

        System.out.println("\n1. Min, max cua ma tran:");
        minMax(a, m, n);

        System.out.println("\n2. Min, max tung hang:");
        minMaxTungHang(a, m, n);

        System.out.println("\n3. Min, max duong cheo chinh:");
        minMaxCheoChinh(a, m, n);

        System.out.print("\nNhap chi so hang i (bat dau tu 0): ");
        int i = sc.nextInt();
        phanTuNhieuNhat(a, m, n, i);
    }
}

class Bai35 {
    private static String s;

    private static void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ho ten: ");
        s = sc.nextLine();
    }

    // Xoa khoang trang thua
    private static String xoaKhoangTrangThua(String s) {
        return s.trim().replaceAll("\\s+", " ");
    }

    // Dem so tu
    private static int demSoTu(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        return s.split(" ").length;
    }

    // Lay ho
    private static String layHo(String s) {
        String[] tu = s.split(" ");
        return tu[0];
    }

    // Lay ten
    private static String layTen(String s) {
        String[] tu = s.split(" ");
        return tu[tu.length - 1];
    }

    // Lay ho lot
    private static String layHoLot(String s) {
        String[] tu = s.split(" ");
        if (tu.length <= 2) {
            return "";
        }

        StringBuilder hoLot = new StringBuilder();
        for (int i = 1; i < tu.length - 1; i++) {
            if (hoLot.length() > 0) {
                hoLot.append(" ");
            }
            hoLot.append(tu[i]);
        }
        return hoLot.toString();
    }

    public static void main(String[] args) {
        nhap();

        s = xoaKhoangTrangThua(s);

        System.out.println("Ho ten sau khi xoa khoang trang thua: " + s);
        System.out.println("So tu: " + demSoTu(s));
        System.out.println("Ho: " + (s.isEmpty() ? "" : layHo(s)));
        System.out.println("Ten: " + (s.isEmpty() ? "" : layTen(s)));
        System.out.println("Ho lot: " + (s.isEmpty() ? "" : layHoLot(s)));
    }
}

class Bai145 {
    static Scanner sc = new Scanner(System.in);

    static void NhapMang(int[] a, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("a[" + i + "] = ");
            a[i] = sc.nextInt();
        }
    }

    static void XuatMang(int[] a, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    static boolean LaCucDai(int[] a, int n, int i) {
        if (i == 0) {
            return n > 1 && a[i] > a[i + 1];
        }

        if (i == n - 1) {
            return a[i] > a[i - 1];
        }

        return a[i] > a[i - 1] && a[i] > a[i + 1];
    }

    static int TongCucDai(int[] a, int n) {
        int tong = 0;

        for (int i = 0; i < n; i++) {
            if (LaCucDai(a, n, i)) {
                tong += a[i];
            }
        }

        return tong;
    }

    public static void main(String[] args) {
        int n;

        do {
            System.out.print("Nhap n (n > 0): ");
            n = sc.nextInt();
        } while (n <= 0);

        int[] a = new int[n];

        NhapMang(a, n);

        System.out.print("Mang vua nhap: ");
        XuatMang(a, n);

        System.out.print("Tong cac phan tu cuc dai: ");
        System.out.println(TongCucDai(a, n));
    }
}

class Bai245 {
    static Scanner sc = new Scanner(System.in);
    static int[][] a;
    static int n;

    static void Nhap() {
        do {
            System.out.print("Nhap cap ma tran n: ");
            n = sc.nextInt();
        } while (n <= 0);

        a = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("a[%d][%d] = ", i, j);
                a[i][j] = sc.nextInt();
            }
        }
    }

    static void Xuat() {
        System.out.println("Ma tran:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%5d", a[i][j]);
            }
            System.out.println();
        }
    }

    // Liet ke duong cheo chinh
    static void LietKeCheoChinh() {
        System.out.print("Duong cheo chinh: ");
        for (int i = 0; i < n; i++) {
            System.out.print(a[i][i] + " ");
        }
        System.out.println();
    }

    // Liet ke duong cheo phu
    static void LietKeCheoPhu() {
        System.out.print("Duong cheo phu: ");
        for (int i = 0; i < n; i++) {
            System.out.print(a[i][n - 1 - i] + " ");
        }
        System.out.println();
    }

    // Liet ke tam giac tren (khong gom duong cheo chinh)
    static void LietKeTamGiacTren() {
        System.out.println("Tam giac tren:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i < j) {
                    System.out.print(a[i][j] + " ");
                }
            }
        }
        System.out.println();
    }

    // Liet ke tam giac duoi (khong gom duong cheo chinh)
    static void LietKeTamGiacDuoi() {
        System.out.println("Tam giac duoi:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    System.out.print(a[i][j] + " ");
                }
            }
        }
        System.out.println();
    }

    // Tinh tong duong cheo chinh
    static int TongCheoChinh() {
        int tong = 0;
        for (int i = 0; i < n; i++) {
            tong += a[i][i];
        }
        return tong;
    }

    // Tinh tong duong cheo phu
    static int TongCheoPhu() {
        int tong = 0;
        for (int i = 0; i < n; i++) {
            tong += a[i][n - 1 - i];
        }
        return tong;
    }

    // Tinh tong tam giac tren (khong gom duong cheo chinh)
    static int TongTamGiacTren() {
        int tong = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                tong += a[i][j];
            }
        }
        return tong;
    }

    // Tinh tong tam giac duoi (khong gom duong cheo chinh)
    static int TongTamGiacDuoi() {
        int tong = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                tong += a[i][j];
            }
        }
        return tong;
    }

    public static void main(String[] args) {
        Nhap();
        Xuat();

        LietKeCheoChinh();
        LietKeCheoPhu();
        LietKeTamGiacTren();
        LietKeTamGiacDuoi();

        System.out.println("Tong duong cheo chinh: " + TongCheoChinh());
        System.out.println("Tong duong cheo phu: " + TongCheoPhu());
        System.out.println("Tong tam giac tren: " + TongTamGiacTren());
        System.out.println("Tong tam giac duoi: " + TongTamGiacDuoi());
    }
}