package lab2;
import java.io.*;

public class Array2 {
    public static final int ROWS = 10;
    public static final int COLS = 20;
    public static int rows = 0;
    public static int cols = 0;
    public static int[][] table;

    public static void TaoMang(int irows, int icols) {
        rows = irows;
        cols = icols;
        table = new int[rows][cols];
        for (int temp_rows = 0; temp_rows < rows; temp_rows++) {
            for (int temp_cols = 0; temp_cols < cols; temp_cols++) {
                table[temp_rows][temp_cols] = (temp_rows * cols) + temp_cols + 1;
            }
        }
    }
    public static void TaoMang() {
        TaoMang(ROWS, COLS);
    }
    public static void XuatMang () {
        for (int temp_rows = 0; temp_rows < rows; temp_rows++) {
            for (int temp_cols = 0; temp_cols < cols; temp_cols++) {
                System.out.print(" " + table[temp_rows][temp_cols]);
            }
            System.out.println();
        }
    }
    public static int SoLonNhatMang() {
        int result = 0;

        for (int temp_rows = 0; temp_rows < rows; temp_rows++) {
            for (int temp_cols = 0; temp_cols < cols; temp_cols++) {
                if (result < table[temp_rows][temp_cols]) {
                    result = table[temp_rows][temp_cols];
                }
            }
        }
        return result;
    }
    public static int SoChanDauTien() {
        int result = 0;
        for (int temp_rows = 0; temp_rows < rows; temp_rows++) {
            for (int temp_cols = 0; temp_cols < cols; temp_cols++) {
                if (table[temp_rows][temp_cols] % 2 == 0 && table[temp_rows][temp_cols] != 0) {
                    result = table[temp_rows][temp_cols];
                    return result;
                }
            }
        }
        return result;
    }
    public static int SoDuongLonNhat() {
        int result = 0;
        int temp = 0;
        for (int temp_rows = 0; temp_rows < rows; temp_rows++) {
            for (int temp_cols = 0; temp_cols < cols; temp_cols++) {
                if (table[temp_rows][temp_cols] > 0) {
                    result = table[temp_rows][temp_cols];
                    return result;
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        TaoMang();
        //table[3][5] = 234;
        XuatMang();
        System.out.println(SoLonNhatMang());
        System.out.println(SoChanDauTien());
    }
}
