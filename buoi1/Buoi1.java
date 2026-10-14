package buoi1;

import java.util.Random;
import java.util.Scanner;

public class Buoi1 {
    public static void main(String[] args) {
//        Bai1.main(args);
//        Bai2.main(args);
//        Bai3.main(args);
//        Bai431.main(args);
//        Bai432.main(args);
//        Bai433.main(args);
//        Bai15.main(args);
    }
}

class Bai1 {

    public static int[][] nhapMang(Scanner sc) {
        int n, m;

        // Nhap so dong
        do {
            System.out.print("Nhap so cot (6 <= n <= 199): ");
            while (!sc.hasNextInt()) {
                System.out.println("Loi: Vui long nhap so nguyen!");
                sc.next();
                System.out.print("Nhap lai so cot: ");
            }
            n = sc.nextInt();

            if (n <= 5 || n >= 200) {
                System.out.println("Loi: so cot phai lon hon 5 va nho hon 200!");
            }
        } while (n <= 5 || n >= 200);

        // Nhap so cot
        do {
            System.out.print("Nhap so dong (6 <= m <= 199): ");
            while (!sc.hasNextInt()) {
                System.out.println("Loi: Vui long nhap so nguyen!");
                sc.next();
                System.out.print("Nhap lai so dong: ");
            }
            m = sc.nextInt();

            if (m <= 5 || m >= 200) {
                System.out.println("Loi: so dong phai lon hon 5 va nho hon 200!");
            }
        } while (m <= 5 || m >= 200);

        int[][] a = new int[n][m];

        // Nhap cac phan tu cua mang
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                while (true) {
                    System.out.print("a[" + i + "][" + j + "] = ");

                    if (sc.hasNextInt()) {
                        int x = sc.nextInt();

                        if (x > 0) {
                            a[i][j] = x;
                            break;
                        }
                    } else {
                        sc.next();
                    }

                    System.out.println("Loi: Phai nhap so nguyen duong! Vui long nhap lai.");
                }
            }
        }

        return a;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][] a = nhapMang(sc);

        System.out.println("\nMang vua nhap:");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + "\t");
            }
            System.out.println();
        }

        sc.close();
    }
}

class Bai2 {
    static Random rd = new Random();

    // a. Gia tri ngau nhien tu -100 den 100
    public static int[][] cauA(int n, int m) {
        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = rd.nextInt(201) - 100;
            }
        }
        return a;
    }

    // b. Gia tri a[i][j] = i * j
    public static int[][] cauB(int n, int m) {
        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = i * j;
            }
        }
        return a;
    }

    // c. Gia tri ngau nhien tu 0 den 99
    public static int[][] cauC(int n, int m) {
        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = rd.nextInt(100);
            }
        }
        return a;
    }

    // d. Gia tri ngau nhien toan so le
    public static int[][] cauD(int n, int m) {
        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = rd.nextInt(100) * 2 + 1;
            }
        }
        return a;
    }

    // Kiem tra so nguyen to
    public static boolean laSoNguyenTo(int x) {
        if (x < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    // e. Gia tri ngau nhien toan so nguyen to
    public static int[][] cauE(int n, int m) {
        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int x;
                do {
                    x = rd.nextInt(100) + 2;
                } while (!laSoNguyenTo(x));

                a[i][j] = x;
            }
        }
        return a;
    }

    // f. i+j le thi a[i][j] chan
    //    i+j chan thi a[i][j] le
    public static int[][] cauF(int n, int m) {
        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int x = rd.nextInt(100);

                if ((i + j) % 2 == 1) {
                    a[i][j] = x * 2;
                } else {
                    a[i][j] = x * 2 + 1;
                }
            }
        }
        return a;
    }

    // Xuat mang hai chieu
    public static void xuatMang(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.printf("%5d", a[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int n = 6;
        int m = 6;

        System.out.println("Cau A:");
        xuatMang(cauA(n, m));

        System.out.println("\nCau B:");
        xuatMang(cauB(n, m));

        System.out.println("\nCau C:");
        xuatMang(cauC(n, m));

        System.out.println("\nCau D:");
        xuatMang(cauD(n, m));

        System.out.println("\nCau E:");
        xuatMang(cauE(n, m));

        System.out.println("\nCau F:");
        xuatMang(cauF(n, m));
    }
}

class Bai3 {

    // Kiểm tra số nguyên tố
    static boolean laSoNguyenTo(double x) {
        if (x < 2 || x != (int) x) {
            return false;
        }

        int n = (int) x;

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    // Câu a: Ẩn các số nguyên tố
    static void cauA(double[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (laSoNguyenTo(A[i][j])) {
                    System.out.printf("%10s", " ");
                } else {
                    System.out.printf("%10.2f", A[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("Done");
    }

    // Câu b: Ẩn các phần tử không nằm trên đường biên
    static void cauB(double[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (i == 0 || i == A.length - 1
                        || j == 0 || j == A[i].length - 1) {
                    System.out.printf("%10.2f", A[i][j]);
                } else {
                    System.out.printf("%10s", " ");
                }
            }
            System.out.println();
        }
        System.out.println("Done");
    }

    // Câu c: Ẩn phần tử có tổng chỉ số dòng và cột là số chẵn
    static void cauC(double[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.printf("%10s", " ");
                } else {
                    System.out.printf("%10.2f", A[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("Done");
    }

    public static void main(String[] args) {
        double[][] A = {
                {2, 3, 4},
                {5, 6, 7},
                {8, 9, 10}
        };

        cauA(A);
        cauB(A);
        cauC(A);
    }
}

class Bai431 {

    // Bài 4: Tính tổng tất cả các số
    public static int bai4(int[][] A) {
        int tong = 0;

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                tong += A[i][j];
            }
        }

        return tong;
    }

    // Bài 5: Tính tổng các số chẵn
    public static int bai5(int[][] A) {
        int tong = 0;

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] % 2 == 0) {
                    tong += A[i][j];
                }
            }
        }

        return tong;
    }

    // Bài 6: Tính giá trị trung bình cộng
    public static double bai6(int[][] A) {
        int tong = bai4(A);
        int dem = A.length * A[0].length;

        return (double) tong / dem;
    }

    // Bài 7: Tính giá trị trung bình nhân
    public static double bai7(int[][] A) {
        double tich = 1;
        int dem = 0;

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] <= 0) {
                    return Double.NaN;
                }

                tich *= A[i][j];
                dem++;
            }
        }

        return Math.pow(tich, 1.0 / dem);
    }

    // Kiểm tra số nguyên tố
    private static boolean laSoNguyenTo(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    // Bài 8: Tính tổng các số nguyên tố
    public static int bai8(int[][] A) {
        int tong = 0;

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (laSoNguyenTo(A[i][j])) {
                    tong += A[i][j];
                }
            }
        }

        return tong;
    }

    // Kiểm tra số hoàn thiện
    private static boolean laSoHoanThien(int n) {
        if (n <= 1) {
            return false;
        }

        int tong = 1;

        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) {
                tong += i;
            }
        }

        return tong == n;
    }

    // Bài 9: Tính tổng các số hoàn thiện
    public static int bai9(int[][] A) {
        int tong = 0;

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (laSoHoanThien(A[i][j])) {
                    tong += A[i][j];
                }
            }
        }

        return tong;
    }
    public static void main(String[] args) {
        int[][] A = {
                {6, 2, 3},
                {4, 5, 28},
                {7, 8, 496}
        };

        System.out.println("Bai 4 - Tong tat ca: " + bai4(A));
        System.out.println("Bai 5 - Tong so chan: " + bai5(A));
        System.out.println("Bai 6 - Trung binh cong: " + bai6(A));
        System.out.println("Bai 7 - Trung binh nhan: " + bai7(A));
        System.out.println("Bai 8 - Tong so nguyen to: " + bai8(A));
        System.out.println("Bai 9 - Tong so hoan thien: " + bai9(A));
    }
}

class Bai432 {

    // Bài 10: Tính tổng mỗi dòng, in tổng dòng lớn nhất
    static void bai10(int[][] A) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < A.length; i++) {
            int tong = 0;

            for (int j = 0; j < A[i].length; j++) {
                tong += A[i][j];
            }

            System.out.println("Tong dong " + i + ": " + tong);

            if (tong > max) {
                max = tong;
            }
        }

        System.out.println("Tong dong lon nhat: " + max);
    }

    // Bài 11: Tính tổng số chẵn mỗi dòng
    static void bai11(int[][] A) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < A.length; i++) {
            int tong = 0;

            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] % 2 == 0) {
                    tong += A[i][j];
                }
            }

            System.out.println("Tong chan dong " + i + ": " + tong);

            if (tong > max) {
                max = tong;
            }
        }

        System.out.println("Tong chan lon nhat: " + max);
    }

    // Bài 12: Tính tổng số âm mỗi dòng
    static void bai12(int[][] A) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < A.length; i++) {
            int tong = 0;

            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] < 0) {
                    tong += A[i][j];
                }
            }

            System.out.println("Tong am dong " + i + ": " + tong);

            if (tong > max) {
                max = tong;
            }
        }

        System.out.println("Tong am lon nhat: " + max);
    }

    // Bài 13: Tính trung bình cộng mỗi dòng
    static void bai13(int[][] A) {
        double max = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < A.length; i++) {
            int tong = 0;

            for (int j = 0; j < A[i].length; j++) {
                tong += A[i][j];
            }

            double tbc = (double) tong / A[i].length;

            System.out.println("TBC dong " + i + ": " + tbc);

            if (tbc > max) {
                max = tbc;
            }
        }

        System.out.println("TBC dong lon nhat: " + max);
    }

    // Kiểm tra số nguyên tố
    private static boolean laSoNguyenTo(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    // Bài 14: Tính tổng số nguyên tố mỗi dòng
    static void bai14(int[][] A) {
        for (int i = 0; i < A.length; i++) {
            int tong = 0;

            for (int j = 0; j < A[i].length; j++) {
                if (laSoNguyenTo(A[i][j])) {
                    tong += A[i][j];
                }
            }

            System.out.println("Tong nguyen to dong " + i + ": " + tong);
        }
    }

    // Hàm main
    public static void main(String[] args) {
        int[][] A = {
                {1, 2, 3, -4},
                {5, -6, 7, 8},
                {-2, -3, 0, 11}
        };

        System.out.println("Bai 10:");
        bai10(A);

        System.out.println("\nBai 11:");
        bai11(A);

        System.out.println("\nBai 12:");
        bai12(A);

        System.out.println("\nBai 13:");
        bai13(A);

        System.out.println("\nBai 14:");
        bai14(A);
    }
}

class Bai433 {

    // Bài 10: Tính tổng mỗi cột, in tổng cột lớn nhất
    static void bai10(int[][] A) {
        int max = Integer.MIN_VALUE;

        for (int j = 0; j < A[0].length; j++) {
            int tong = 0;

            for (int i = 0; i < A.length; i++) {
                tong += A[i][j];
            }

            System.out.println("Tong cot " + j + ": " + tong);

            if (tong > max) {
                max = tong;
            }
        }

        System.out.println("Tong cot lon nhat: " + max);
    }

    // Bài 11: Tính tổng số chẵn mỗi cột
    static void bai11(int[][] A) {
        int max = Integer.MIN_VALUE;

        for (int j = 0; j < A[0].length; j++) {
            int tong = 0;

            for (int i = 0; i < A.length; i++) {
                if (A[i][j] % 2 == 0) {
                    tong += A[i][j];
                }
            }

            System.out.println("Tong chan cot " + j + ": " + tong);

            if (tong > max) {
                max = tong;
            }
        }

        System.out.println("Tong chan lon nhat: " + max);
    }

    // Bài 12: Tính tổng số âm mỗi cột
    static void bai12(int[][] A) {
        int max = Integer.MIN_VALUE;

        for (int j = 0; j < A[0].length; j++) {
            int tong = 0;

            for (int i = 0; i < A.length; i++) {
                if (A[i][j] < 0) {
                    tong += A[i][j];
                }
            }

            System.out.println("Tong am cot " + j + ": " + tong);

            if (tong > max) {
                max = tong;
            }
        }

        System.out.println("Tong am lon nhat: " + max);
    }

    // Bài 13: Tính trung bình cộng mỗi cột
    static void bai13(int[][] A) {
        double max = Double.NEGATIVE_INFINITY;

        for (int j = 0; j < A[0].length; j++) {
            int tong = 0;

            for (int i = 0; i < A.length; i++) {
                tong += A[i][j];
            }

            double tbc = (double) tong / A.length;

            System.out.println("TBC cot " + j + ": " + tbc);

            if (tbc > max) {
                max = tbc;
            }
        }

        System.out.println("TBC cot lon nhat: " + max);
    }

    // Kiểm tra số nguyên tố
    private static boolean laSoNguyenTo(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    // Bài 14: Tính tổng số nguyên tố mỗi cột
    static void bai14(int[][] A) {
        for (int j = 0; j < A[0].length; j++) {
            int tong = 0;

            for (int i = 0; i < A.length; i++) {
                if (laSoNguyenTo(A[i][j])) {
                    tong += A[i][j];
                }
            }

            System.out.println("Tong nguyen to cot " + j + ": " + tong);
        }
    }

    // Hàm main
    public static void main(String[] args) {
        int[][] A = {
                {1, 2, 3, -4},
                {5, -6, 7, 8},
                {-2, -3, 0, 11}
        };

        System.out.println("Bai 10:");
        bai10(A);

        System.out.println("\nBai 11:");
        bai11(A);

        System.out.println("\nBai 12:");
        bai12(A);

        System.out.println("\nBai 13:");
        bai13(A);

        System.out.println("\nBai 14:");
        bai14(A);
    }
}

class Bai15 {

    // Bài 15a: Tính tổng tất cả các số trên đường biên
    static int cauA(int[][] A) {
        int tong = 0;

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (i == 0 || i == A.length - 1
                        || j == 0 || j == A[i].length - 1) {
                    tong += A[i][j];
                }
            }
        }

        return tong;
    }

    // Bài 15b: Tính tổng tất cả các số không thuộc đường biên
    static int cauB(int[][] A) {
        int tong = 0;

        for (int i = 1; i < A.length - 1; i++) {
            for (int j = 1; j < A[i].length - 1; j++) {
                tong += A[i][j];
            }
        }

        return tong;
    }

    public static void main(String[] args) {
        int[][] A = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        System.out.println("Tong cac so tren duong bien: " + cauA(A));
        System.out.println("Tong cac so khong thuoc duong bien: " + cauB(A));
    }
}