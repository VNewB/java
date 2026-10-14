import java.io.*;
import java.util.Scanner;
class Bai1 {
    public static final int ROWS = 5;
    public static final int COLS = 200;
    public static int rows;
    public static int cols;
    public static int[][] table;
    public static Scanner scanner = new Scanner(System.in);

    public static void NhapSoDong () {
        int irows;
        System.out.print("Nhap so dong: ");
        do {
            irows = scanner.nextInt();
            if (irows < ROWS) System.out.print("Nhap lai so dong: ");
        }
        while (irows < ROWS);
        System.out.println();
        rows = irows;
    }

    public static void NhapSoCot () {
        int icols;
        System.out.print("Nhap so cot: ");
        do {
            icols = scanner.nextInt();
            if (icols > COLS) System.out.print("Nhap lai so cot: ");
        }
        while (icols > COLS);
        System.out.println();
        cols = icols;
    }
    public static void Mang2Chieu () {
        int irows, icols;
        table = new int[rows][cols];
        for (irows = 0; irows < rows; ++irows) {
            for (icols = 0; icols < cols; ++icols) {
                table[irows][icols] = (irows * cols) + icols + 1;
                System.out.print(" " + table[irows][icols]);
            }
            System.out.println();
        }
    }
    public static void main (String[] args) {
        NhapSoDong();
        NhapSoCot();
        Mang2Chieu();
    }
}
public class Buoi1 {
    public static void main(String[] args) {
        Bai1.main(args);
    }
}