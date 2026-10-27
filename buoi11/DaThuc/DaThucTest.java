//package buoi11.DaThuc;

import java.util.Scanner;

public class DaThucTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DaThuc a = null, b = null;
        int chon;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Nhap da thuc A");
            System.out.println("2. Xuat da thuc A");
            System.out.println("3. Tinh gia tri da thuc A");
            System.out.println("4. Nhap da thuc B");
            System.out.println("5. Tinh tong A + B");
            System.out.println("6. Tinh tich A * B");
            System.out.println("7. Xuat da thuc B");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            chon = sc.nextInt();

            switch (chon) {
                case 1:
                    System.out.print("Nhap bac da thuc A: ");
                    int n = sc.nextInt();
                    a = new DaThuc(n);
                    a.nhap(sc);
                    break;

                case 2:
                    if (a != null) a.xuat();
                    else System.out.println("Chua nhap A!");
                    break;

                case 3:
                    if (a != null) {
                        System.out.print("Nhap x0: ");
                        double x = sc.nextDouble();
                        System.out.println("Gia tri = " + a.tinhGiaTri(x));
                    } else System.out.println("Chua nhap A!");
                    break;

                case 4:
                    System.out.print("Nhap bac da thuc B: ");
                    int m = sc.nextInt();
                    b = new DaThuc(m);
                    b.nhap(sc);
                    break;

                case 5:
                    if (a != null && b != null) {
                        DaThuc tong = a.cong(b);
                        System.out.print("A + B = ");
                        tong.xuat();
                    } else System.out.println("Hay nhap ca A va B!");
                    break;

                case 6:
                    if (a != null && b != null) {
                        DaThuc tich = a.nhan(b);
                        System.out.print("A * B = ");
                        tich.xuat();
                    } else System.out.println("Hay nhap ca A va B!");
                    break;

                case 7:
                    if (b != null) b.xuat();
                    else System.out.println("Chua nhap B!");
                    break;

                case 0:
                    System.out.println("Ket thuc.");
                    break;

                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);

        sc.close();
    }
}