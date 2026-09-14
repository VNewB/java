package lab2;
import java.io.*;

public class Array2 {
    public static final int ROWS = 10;
    public static final int COLS = 20;

    static int currentRow = 0;
    static int currentCol = 0;

    public static int rows = 0;
    public static int cols = 0;

    static int[][] table;
    static int gtemp;
    static boolean running;
    static boolean stopped;

    static boolean nextDuyetMang() {
        if (!running || stopped) {
            return false;
        }
        if (currentRow >= rows) {
            return false;
        }

        gtemp = table[currentRow][currentCol];
        currentCol++;
        if (currentCol >= cols) {
            currentCol = 0;
            currentRow++;
        }
        return true;
    }

    static void startDuyetMang() {
        currentRow = 0;
        currentCol = 0;
        running = true;
        stopped = false;
    }

    static void pauseDuyetMang() {
        running = false;
    }

    static void continueDuyetMang() {
        if (!stopped) {
            running = true;
        }
    }

    static void stopDuyetMang() {
        running = false;
        stopped = true;
    }

    public static void TaoMang(int irows, int icols) {
        rows = irows;
        cols = icols;
        table = new int[rows][cols];
        for (int temp_rows = 0; temp_rows < rows; temp_rows++) {
            for (int temp_cols = 0; temp_cols < cols; temp_cols++) {
                table[temp_rows][temp_cols] = (temp_rows * icols) + temp_cols + 1;
            }
        }
    }

    public static void TaoMang() {
        TaoMang(ROWS, COLS);
    }

    public static void XuatMang () {
        startDuyetMang();
        while (nextDuyetMang()) {
            System.out.print(gtemp + " ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
    }

    public static int SoLonNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp > result) result = gtemp;
        }
        stopDuyetMang();
        return result;
    }

    public static int SoChanDauTien() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp > 0 && gtemp % 2 == 0) result = gtemp;
        }
        return result;
    }

    public static int SoDuongLonNhat() {
        int result = -1;
        if (SoLonNhat() > 0) {
            result = SoLonNhat();
        }
        else System.out.println("Khong co so duong lon nhat mang!");
        return result;
    }
    public static int SoNguyenToDauTien() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp < 2) {
                continue;
            }
            boolean isPrime = true;
            for (int temp = 2; temp * temp <= gtemp; temp++) {
                if (gtemp % temp == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) return gtemp;
        }
        return 0;
    }

    public static int SoChanLonNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp % 2 == 0 && gtemp > result) result = gtemp;
        }
        return result;
    }

    public static int SoDuongNhoNhat() {
        int result = table[0][0];
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp > 0 && gtemp < result) result = gtemp;
        }
        return result;
    }

    public static int SoNguyenToLonNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp < 2) {
                continue;
            }
            boolean isPrime = true;
            for (int temp = 2; temp * temp <= gtemp; temp++) {
                if (gtemp % temp == 0) {
                    isPrime = false;
                }
            }
            if (isPrime) result = gtemp;
        }
        return result;
    }

    public static int SoHoanThienNhoNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            int sum = 0;
            for (int temp = 1; temp < gtemp; temp++) {
                if (gtemp % temp == 0)
                    sum += temp;
            }

            if (sum == gtemp) {
                if (result == -1 || gtemp < result)
                    result = gtemp;
            }
        }
        return result;
    }

    public static int SoChinhPhuongLonNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            int sqrt = (int) Math.sqrt(gtemp);
            if (sqrt * sqrt == gtemp) {
                if (result < gtemp) result = gtemp;
            }
        }
        return result;
    }

    public static int SoHoanThienDauTien() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            int sum = 0;
            for (int temp = 1; temp < gtemp; temp++) {
                if (gtemp % temp == 0) sum += temp;
                if (sum == gtemp) return sum;
            }
        }
        return result;
    }

    public static int CotCoTongNhoNhat() {
        int result = -1;
        int min = 0;
        for (int temp_col = 0; temp_col < cols; temp_col++) {
            int temp = 0;
            for (int temp_row = 0; temp_row < rows; temp_row++) {
                temp += table[temp_row][temp_col];
            }
            if (temp_col == 0 || temp < min) {
                min = temp;
                result = temp_col;
            }
        }
        return result;
    }

    public static int DongCoTongLonNhat() {
        int result = -1;
        int temp_result = -1;
        int temp = 0;
        startDuyetMang();
        while (nextDuyetMang()) {
            temp += gtemp;
            if (currentCol == cols - 1) {
                if (result == -1 || temp_result < temp) {
                    temp_result = temp;
                    result = currentRow;
                }
                temp = 0;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        TaoMang();
//        table[3][5] = 234;
//        System.out.println(SoLonNhat());
//        System.out.println(SoChanDauTien());
//        System.out.println(SoDuongLonNhat());
//        System.out.println(SoNguyenToDauTien());
//        System.out.println(SoChanLonNhat());
//        table[4][2] = 0;
//        table[0][0] = 11;
//        System.out.println(SoDuongNhoNhat());
//        System.out.println(SoNguyenToLonNhat());
//        table[9][3] = 225;
//        System.out.println(SoHoanThienNhoNhat());
//        System.out.println(SoHoanThienNhoNhat());
//        System.out.println(SoChinhPhuongLonNhat());
//        System.out.println(CotCoTongNhoNhat());
//        table[3][4] = 999999999;
//        System.out.println(DongCoTongLonNhat());
//        XuatMang();
    }
}
