//package buoi11.Employee;


import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Comparator;

public class EmployeeTest {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Employee> ds = new ArrayList<>();

    static Employee timKiem(String ma) {
        for (Employee e : ds) {
            if (e.getMaNV().equalsIgnoreCase(ma)) {
                return e;
            }
        }
        return null;
    }

    static void nhapNhanVien() {
        System.out.print("Ma nhan vien: ");
        String ma = sc.nextLine().trim();

        if (ma.isEmpty() || timKiem(ma) != null) {
            System.out.println("Ma rong hoac bi trung!");
            return;
        }

        System.out.print("Ho: ");
        String ho = sc.nextLine();

        System.out.print("Ten dem: ");
        String tenDem = sc.nextLine();

        System.out.print("Luong thang: ");
        double luong = Double.parseDouble(sc.nextLine());

        if (luong < 0) {
            System.out.println("Luong khong duoc am!");
            return;
        }

        System.out.print("Ngay thue (yyyy-mm-dd): ");
        LocalDate ngayThue = LocalDate.parse(sc.nextLine());

        ds.add(new Employee(ma, tenDem, ho, luong, ngayThue));
        System.out.println("Da them nhan vien.");
    }

    static void nhapDanhSach() {
        System.out.print("Nhap so nhan vien muon them (it nhat 10): ");
        int n = Integer.parseInt(sc.nextLine());

        if (n < 10) {
            System.out.println("Phai nhap it nhat 10 nhan vien!");
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.println("\nNhan vien thu " + (i + 1));
            nhapNhanVien();
        }
    }

    static void xuatDanhSach() {
        if (ds.isEmpty()) {
            System.out.println("Danh sach rong.");
            return;
        }

        for (Employee e : ds) {
            System.out.println(e);
        }
    }

    static void timNhanVien() {
        System.out.print("Nhap ma nhan vien can tim: ");
        String ma = sc.nextLine();

        Employee e = timKiem(ma);

        if (e != null) {
            System.out.println(e);
        } else {
            System.out.println("Khong tim thay nhan vien.");
        }
    }

    static void xoaNhanVien() {
        System.out.print("Nhap ma nhan vien can xoa: ");
        String ma = sc.nextLine();

        Employee e = timKiem(ma);

        if (e != null) {
            ds.remove(e);
            System.out.println("Da xoa nhan vien.");
        } else {
            System.out.println("Khong tim thay nhan vien.");
        }
    }

    static void sapXepMa() {
        ds.sort(Comparator.comparing(Employee::getMaNV,
                String.CASE_INSENSITIVE_ORDER));
        System.out.println("Da sap xep theo ma nhan vien.");
        xuatDanhSach();
    }

    static void sapXepTen() {
        ds.sort(Comparator.comparing(Employee::getHoTen,
                String.CASE_INSENSITIVE_ORDER));
        System.out.println("Da sap xep theo ten nhan vien.");
        xuatDanhSach();
    }

    public static void main(String[] args) {
        int chon;

        do {
            System.out.println("\n===== QUAN LY NHAN VIEN =====");
            System.out.println("1. Nhap danh sach nhan vien");
            System.out.println("2. Xuat danh sach nhan vien");
            System.out.println("3. Tim nhan vien theo ma");
            System.out.println("4. Xoa nhan vien theo ma");
            System.out.println("5. Sap xep tang dan theo ma");
            System.out.println("6. Sap xep tang dan theo ten");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            try {
                chon = Integer.parseInt(sc.nextLine());

                switch (chon) {
                    case 1:
                        nhapDanhSach();
                        break;
                    case 2:
                        xuatDanhSach();
                        break;
                    case 3:
                        timNhanVien();
                        break;
                    case 4:
                        xoaNhanVien();
                        break;
                    case 5:
                        sapXepMa();
                        break;
                    case 6:
                        sapXepTen();
                        break;
                    case 0:
                        System.out.println("Ket thuc chuong trinh.");
                        break;
                    default:
                        System.out.println("Lua chon khong hop le.");
                }
            } catch (Exception e) {
                System.out.println("Du lieu khong hop le!");
                chon = -1;
            }
        } while (chon != 0);

        sc.close();
    }
}
