import java.io.*;


public class Bai1 {
    public static final int ROWS = 100;
    public static final int COLS = 200;
    public static final int[][] table = new int[ROWS][COLS];


    // Method (i) Viet ham nhap 0 < X <= value
    public static int NhapSo(String str, int value) throws IOException {
        int n;
        System.out.print(str + " Nhap so tu (1- 100): ");
        do {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
            String s;
            s = in.readLine();
            n = Integer.parseInt(s);
            if ((n<=0)||(n>value)) System.out.print("Nhap lai: ");
            } while((n <= 0)||(n > value));

        //System.out.println("So vua nhap: "+n);
        return n;
    }
    // Method (ii) Viet ham nhap vao mang 2 chieu
    public static void NhapMang2Chieu (int[][] a, int m, int n) {
        int rows,cols;
        for (rows = 0; rows < m; ++rows) {
            for (cols = 0; cols < n; ++cols) {
                a[rows][cols] = (rows * n) + cols + 1;
            }
        }
    }

    // Method (iii) Viet ham xuat mang hai chieu
    public static void XuatMang2Chieu(int[][] a, int m, int n) {
        System.out.println("Ma tran vua tao voi " + m + " dong va " + n + " cot!");
        int rows, cols;
        for (rows = 0; rows < m; ++rows) {
            for (cols = 0; cols < n; ++cols) {
                System.out.print(" "+ a[rows][cols]);
            }
            System.out.println();
        }
    }

    // Method (iv) Viet ham tinh tong tat ca cac phan tu co trong mang
    public static int TongMang(int[][] a, int m, int n) {
        int sum = 0;
        for (int rows = 0; rows < m; ++rows) {
            for (int cols = 0; cols < n; ++cols) sum += a[rows][cols];
        }
        return sum;
    }

    // Method (v) Viet ham tinh tong cac phan tu duong bien
    public static int TongBienMang(int[][] a, int m, int n) {
        int tmp = 0;
        for (int rows = 0; rows < m; ++rows) {
            for (int cols = 0; cols < n; ++cols) {
                if (rows == 0 || rows == m - 1 || cols == 0 || cols == n - 1) tmp = a[rows][cols];
            }
        }
        return tmp;
    }
    static boolean LaSoMayMan (int input) {
        while (input > 0) {
            int luck = input % 10;
            if (luck != 6 && luck != 8) return false;
            input = input / 10;
        }
        return true;
    }

    public static void LietKeSoMayMan (int a[][], int m, int n) {
        for (int rows = 0; rows < m; ++rows) {
            for (int cols = 0; cols < n; ++cols) {
                if (LaSoMayMan(a[rows][cols])) System.out.printf(" %2d ", a[rows][cols]);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        int rows, cols;
        rows = NhapSo("[Dong]", ROWS);
        cols = NhapSo("[Cot]", COLS);
        NhapMang2Chieu(table, rows, cols);
        XuatMang2Chieu(table, rows, cols);
        System.out.println("Tong tat ca trong mang: " + TongMang(table, rows, cols));
        System.out.println("Tong bien mang: "+ TongBienMang(table, rows, cols));
        System.out.println("Liet ke so may man: ");
        LietKeSoMayMan(table, rows, cols);
        System.out.println();
        System.out.println("Complete!");
    }
}
