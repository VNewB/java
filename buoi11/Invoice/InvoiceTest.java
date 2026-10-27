//package buoi11.Invoice;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class InvoiceTest {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Invoice> list = new ArrayList<>();

    // Tim vi tri hoa don theo ma hang
    static int timViTri(String ma) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPartNumber().equalsIgnoreCase(ma)) {
                return i;
            }
        }
        return -1;
    }

    // Nhap danh sach it nhat 10 hoa don
    static void nhapDanhSach() {
        int n;

        do {
            System.out.print("Nhap so hoa don (>= 10): ");
            n = sc.nextInt();
            sc.nextLine();
        } while (n < 10);

        list.clear();

        for (int i = 0; i < n; i++) {
            System.out.println("\nHoa don thu " + (i + 1));

            String ma;
            do {
                System.out.print("Ma hang: ");
                ma = sc.nextLine().trim();

                if (ma.isEmpty()) {
                    System.out.println("Ma hang khong duoc rong!");
                } else if (timViTri(ma) != -1) {
                    System.out.println("Ma hang da ton tai!");
                } else {
                    break;
                }
            } while (true);

            System.out.print("Mo ta: ");
            String moTa = sc.nextLine();

            System.out.print("So luong: ");
            int soLuong = sc.nextInt();

            System.out.print("Don gia: ");
            double donGia = sc.nextDouble();
            sc.nextLine();

            Invoice hd = new Invoice(ma, moTa, soLuong, donGia);
            list.add(hd);
        }

        System.out.println("Da nhap danh sach thanh cong.");
    }

    // Xuat toan bo danh sach
    static void xuatDanhSach() {
        if (list.isEmpty()) {
            System.out.println("Danh sach rong!");
            return;
        }

        for (Invoice hd : list) {
            System.out.println(hd);
        }
    }

    // Xuat mot hoa don
    static void xuatMotHoaDon() {
        System.out.print("Nhap ma hang can xem: ");
        String ma = sc.nextLine();

        int vt = timViTri(ma);

        if (vt == -1) {
            System.out.println("Khong tim thay hoa don.");
        } else {
            System.out.println(list.get(vt));
        }
    }

    // Sap xep noi bot theo ma hang tang dan
    static void bubbleSort() {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getPartNumber()
                        .compareToIgnoreCase(
                                list.get(j + 1).getPartNumber()) > 0) {
                    Collections.swap(list, j, j + 1);
                }
            }
        }

        System.out.println("Da sap xep tang dan theo ma hang.");
        xuatDanhSach();
    }

    // Sap xep chen theo so luong tang dan
    static void insertionSort() {
        for (int i = 1; i < list.size(); i++) {
            Invoice key = list.get(i);
            int j = i - 1;

            while (j >= 0
                    && list.get(j).getQuantity() > key.getQuantity()) {
                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }

        System.out.println("Da sap xep tang dan theo so luong.");
        xuatDanhSach();
    }

    // Sap xep theo ma hang, neu trung thi theo so luong
    static void sapXepHaiTieuChi() {
        list.sort(
                Comparator.comparing(
                        Invoice::getPartNumber,
                        String.CASE_INSENSITIVE_ORDER
                ).thenComparingInt(Invoice::getQuantity)
        );

        System.out.println("Da sap xep theo ma hang, sau do so luong.");
        xuatDanhSach();
    }

    // Tim kiem tuyen tinh
    static void timKiem() {
        System.out.print("Nhap ma hang can tim: ");
        String ma = sc.nextLine();

        int vt = timViTri(ma);

        if (vt == -1) {
            System.out.println("Khong tim thay hoa don.");
        } else {
            System.out.println("Tim thay:");
            System.out.println(list.get(vt));
        }
    }

    // Xoa hoa don theo ma hang
    static void xoaHoaDon() {
        System.out.print("Nhap ma hang can xoa: ");
        String ma = sc.nextLine();

        int vt = timViTri(ma);

        if (vt == -1) {
            System.out.println("Khong tim thay hoa don.");
        } else {
            list.remove(vt);
            System.out.println("Da xoa hoa don.");
        }
    }

    public static void main(String[] args) {
        int chon;

        do {
            System.out.println("\n===== QUAN LY HOA DON =====");
            System.out.println("1. Nhap danh sach hoa don");
            System.out.println("2. Xuat danh sach hoa don");
            System.out.println("3. Xuat mot hoa don");
            System.out.println("4. Bubble Sort theo ma hang");
            System.out.println("5. Insertion Sort theo so luong");
            System.out.println("6. Sap xep theo ma hang va so luong");
            System.out.println("7. Tim kiem hoa don");
            System.out.println("8. Xoa hoa don");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            chon = sc.nextInt();
            sc.nextLine();

            switch (chon) {
                case 1:
                    nhapDanhSach();
                    break;
                case 2:
                    xuatDanhSach();
                    break;
                case 3:
                    xuatMotHoaDon();
                    break;
                case 4:
                    bubbleSort();
                    break;
                case 5:
                    insertionSort();
                    break;
                case 6:
                    sapXepHaiTieuChi();
                    break;
                case 7:
                    timKiem();
                    break;
                case 8:
                    xoaHoaDon();
                    break;
                case 0:
                    System.out.println("Ket thuc chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);

        sc.close();
    }
}
