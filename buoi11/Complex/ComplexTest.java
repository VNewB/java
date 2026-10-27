//package buoi11.Complex;


import java.util.Scanner;

public class ComplexTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Complex a = new Complex();
        Complex b = new Complex();
        int chon;

        do {
            System.out.println("\n===== MENU SO PHUC =====");
            System.out.println("1. Nhap 2 so phuc");
            System.out.println("2. Cong 2 so phuc");
            System.out.println("3. Tru 2 so phuc");
            System.out.println("4. In 2 so phuc");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            chon = sc.nextInt();

            switch (chon) {
                case 1:
                    System.out.println("Nhap so phuc thu nhat:");
                    a.nhap(sc);

                    System.out.println("Nhap so phuc thu hai:");
                    b.nhap(sc);
                    break;

                case 2:
                    System.out.print("Tong = ");
                    a.cong(b).xuat();
                    break;

                case 3:
                    System.out.print("Hieu = ");
                    a.tru(b).xuat();
                    break;

                case 4:
                    System.out.print("So phuc thu nhat: ");
                    a.xuat();

                    System.out.print("So phuc thu hai: ");
                    b.xuat();
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
