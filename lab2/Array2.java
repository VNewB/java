package lab2;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.util.Random;

public class Array2 {
    public static final int ROWS = 10;
    public static final int COLS = 20;

    static int currentRow = 0;
    static int currentCol = 0;

    public static int rows = 0;
    public static int cols = 0;

    static int[][] table;
    static int gtemp_prev;
    static int gtemp_now;
    static boolean running;
    static boolean stopped;

    static boolean nextDuyetMang() {
        if (!running || stopped) {
            return false;
        }
        if (currentRow >= rows) {
            return false;
        }

        gtemp_prev = table[currentRow][currentCol];
        currentCol++;
        if (currentCol >= cols) {
            currentCol = 0;
            currentRow++;
        }

        if (currentRow < rows && currentCol < cols) {
            gtemp_now = table[currentRow][currentCol];
        }
        return true;
    }

    static void startDuyetMang() {
        currentRow = 0;
        currentCol = 0;
        running = true;
        stopped = false;
    }

//    static void pauseDuyetMang() {
//        running = false;
//    }
//
//    static void continueDuyetMang() {
//        if (!stopped) {
//            running = true;
//        }
//    }

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
                table[temp_rows][temp_cols] = (temp_rows * icols) - temp_cols * irows;
            }
        }
    }

    public static void TaoMang() {
        TaoMang(ROWS, COLS);
    }

    public static void XuatMang () {
        startDuyetMang();
        while (nextDuyetMang()) {
            System.out.print(gtemp_prev + " ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
    }

    static boolean LaSoNguyenTo(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    static boolean LaSoChinhPhuong(int n) {
        if (n < 0) {
            return false;
        }

        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }

    public static boolean LaSoHoanThien(int n) {
        if (n <= 1) {
            return false;
        }

        int sum = 0;

        for (int temp = 1; temp <= n / 2; temp++) {
            if (n % temp == 0) {
                sum += temp;
            }
        }

        return sum == n;
    }
    public static int SoLonNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev > result) result = gtemp_prev;
        }
        stopDuyetMang();
        return result;
    }

    public static int SoChanDauTien() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev > 0 && gtemp_prev % 2 == 0) result = gtemp_prev;
        }
        stopDuyetMang();
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
        startDuyetMang();

        while (nextDuyetMang()) {
            if (LaSoNguyenTo(gtemp_prev)) {
                return gtemp_prev;
            }
        }
        stopDuyetMang();
        return -1;
    }

    public static int SoChanLonNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev % 2 == 0 && gtemp_prev > result) result = gtemp_prev;
        }
        stopDuyetMang();
        return result;
    }

    public static int SoDuongNhoNhat() {
        int result = table[0][0];
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev > 0 && gtemp_prev < result) result = gtemp_prev;
        }
        stopDuyetMang();
        return result;
    }

    public static int SoNguyenToLonNhat() {
        int result = -1;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (LaSoNguyenTo(gtemp_prev)) {
                if (gtemp_prev > result) {
                    result = gtemp_prev;
                }
            }
        }
        stopDuyetMang();
        return result;
    }
    public static int SoHoanThienNhoNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            int temp = 0;
            if (LaSoHoanThien(gtemp_prev)) temp = gtemp_prev;
            if (result > temp) {
                result = temp;
            }
        }
        stopDuyetMang();
        return result;
    }

    public static int SoChinhPhuongLonNhat() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (LaSoChinhPhuong(gtemp_prev)) {
                if (result < gtemp_prev) result = gtemp_prev;
            }
        }
        stopDuyetMang();
        return result;
    }

    public static int SoHoanThienDauTien() {
        int result = -1;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (LaSoHoanThien(gtemp_prev)) result = gtemp_prev;
        }
        stopDuyetMang();
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
        stopDuyetMang();
        return result;
    }

    public static int DongCoTongLonNhat() {
        int result = -1;
        int temp_result = -1;
        int temp = 0;
        startDuyetMang();
        while (nextDuyetMang()) {
            temp += gtemp_prev;
            if (currentCol == cols - 1) {
                if (result == -1 || temp_result < temp) {
                    temp_result = temp;
                    result = currentRow;
                }
                temp = 0;
            }
        }
        stopDuyetMang();
        return result;
    }

    public static void LietKeDongCoGiaTriAm() {
        startDuyetMang();
        boolean below_zero = false;
        while (nextDuyetMang()) {
            if (gtemp_prev < 0) {
                below_zero = true;
            }

            if (currentCol == 0) {
                if (below_zero) {
                    System.out.println("Dong " + currentRow + " co gia tri am!");
                }
                below_zero = false;
            }
        }
        stopDuyetMang();
    }

    public static void LietKeDongCoSoNguyenTo() {
        boolean Prime = false;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (LaSoNguyenTo(gtemp_prev)) {
                Prime = true;
            }
            // currentCol == 0 nghĩa là vừa duyệt xong một dòng
            if (currentCol == 0) {
                if (Prime) {
                    System.out.println("Dong " + currentRow + " co so nguyen to!");
                }
                Prime = false;
            }
        }
        stopDuyetMang();
    }
    public static void LietKeDongChan() {
        boolean isEven = false;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev % 2 == 0) {
                isEven = true;
            }

            if (currentCol == 0) {
                if (isEven) {
                    System.out.println("Dong " + currentRow + " co so chan!");
                }
                isEven = false;
            }
        }
        stopDuyetMang();
    }

    public static void LietKeCotSoChinhPhuong() {
        boolean[] isSquare = new boolean[cols];

        startDuyetMang();
        while (nextDuyetMang()) {
            int temp_col = (currentCol == 0) ? cols - 1 : currentCol - 1;

            if (LaSoChinhPhuong(gtemp_prev)) {
                isSquare[temp_col] = true;
            }
        }
        stopDuyetMang();

        for (int temp = 0; temp < cols; temp++) {
            if (isSquare[temp]) {
                System.out.println("Cot " + temp + " co so chinh phuong!");
            }
        }
    }
    
    public static void LietKeDongToanAm() {
        boolean all_Below0 = true;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev >= 0) {
                all_Below0 = false;
            }

            if (currentCol == 0) {
                if (all_Below0) {
                    System.out.println("Dong " + currentRow + " toan so am!");
                }
                all_Below0 = true;
            }
        }

        stopDuyetMang();
    }

    public static void LietKeDongToanChan() {
        boolean allEven = true;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev % 2 != 0) {
                allEven = false;
            }

            // Vừa duyệt xong một dòng
            if (currentCol == 0) {
                if (allEven) {
                    System.out.println("Dong " + currentRow + " toan so chan!");
                }
                allEven = true;
            }
        }
        stopDuyetMang();
    }

    public static void LietKeDongGiamDan() {
        startDuyetMang();

        boolean decrease = true;
        int prev = 0;
        while (nextDuyetMang()) {
            if (currentCol == 1) {
                prev = gtemp_prev;
            }
            else {
                if (gtemp_prev > prev) {
                    decrease = false;
                }
                prev = gtemp_prev;
            }

            if (currentCol == 0) {
                if (decrease) {
                    System.out.println("Dong " + currentRow + " co gia tri giam dan!");
                }
                decrease = true;
            }
        }

        stopDuyetMang();
    }

    public static void LietKeDongCoTongLonNhat() {
        int dongMax = DongCoTongLonNhat();
        int max = 0;
        int temp = 0;

        // Tính tổng của dòng lớn nhất
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentRow == dongMax) {
                max += gtemp_prev;
            }
        }

        // Liệt kê các dòng có tổng bằng max
        startDuyetMang();
        while (nextDuyetMang()) {
            temp += gtemp_prev;

            if (currentCol == cols - 1) {
                if (temp == max) {
                    System.out.println("Dong " + currentRow + " co tong dong lon nhat!");
                }
                temp = 0;
            }
        }

        stopDuyetMang();
    }

    public static void LietKeCotCoTongNhoNhat() {
        int cotMin = CotCoTongNhoNhat();
        int min = 0;
        int temp = 0;

        // Tính tổng của cột nhỏ nhất
        for (int temp_row = 0; temp_row < rows; temp_row++) {
            min += table[temp_row][cotMin];
        }

        // Liệt kê các cột có tổng bằng min
        for (int temp_col = 0; temp_col < cols; temp_col++) {
            temp = 0;
            for (int temp_row = 0; temp_row < rows; temp_row++) {
                temp += table[temp_row][temp_col];
            }

            if (temp == min) {
                System.out.println("Cot " + temp_col + " co tong cot nho nhat!");
            }
        }
    }

    public static void LietKeDongNhieuSoChanNhat() {
        int max = 0;
        int count = 0;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev % 2 == 0) {
                count++;
            }

            // currentCol == 0: vừa duyệt xong một dòng
            if (currentCol == 0) {
                if (count > max) {
                    max = count;
                }
                count = 0;
            }
        }

        // Duyệt lại để liệt kê các dòng có nhiều số chẵn nhất
        startDuyetMang();
        count = 0;

        while (nextDuyetMang()) {
            if (gtemp_prev % 2 == 0) {
                count++;
            }

            if (currentCol == 0) {
                if (count == max) {
                    System.out.println("Dong " + currentRow + " co nhieu so chan nhat!");
                }
                count = 0;
            }
        }

        stopDuyetMang();
    }

    public static void LietKeDongCoNhieuSoNguyenToNhat() {
        int max = 0;
        int soNguyenTo = 0;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (LaSoNguyenTo(gtemp_prev)) {
                soNguyenTo++;
            }

            // Vừa duyệt xong một dòng
            if (currentCol == 0) {
                if (soNguyenTo > max) {
                    max = soNguyenTo;
                }
                soNguyenTo = 0;
            }
        }

        // Duyệt lại để in các dòng có số nguyên tố = max
        startDuyetMang();
        int temp_row = 0;

        while (nextDuyetMang()) {
            if (LaSoNguyenTo(gtemp_prev)) {
                soNguyenTo++;
            }

            if (currentCol == 0) {
                if (soNguyenTo == max) {
                    System.out.println("Dong " + currentRow + " co nhieu so nguyen to nhat!");
                }
                soNguyenTo = 0;
                temp_row++;
            }
        }

        stopDuyetMang();
    }

    public static void LietKeDongNhieuSoHoanThienNhat() {
        int max = 0;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (currentCol == 0) {
                int count = 0;

                // Dòng vừa duyệt xong là currentRow - 1
                for (int temp = 0; temp < cols; temp++) {
                    if (LaSoHoanThien(table[currentRow - 1][temp])) {
                        count++;
                    }
                }

                if (count > max) {
                    max = count;
                }
            }
        }

        stopDuyetMang();

        // Duyệt lại để in các dòng có số lượng lớn nhất
        startDuyetMang();

        while (nextDuyetMang()) {
            if (currentCol == 0) {
                int count = 0;

                for (int temp = 0; temp < cols; temp++) {
                    if (LaSoHoanThien(table[currentRow - 1][temp])) {
                        count++;
                    }
                }

                if (count == max) {
                    System.out.println("Dong " + currentRow + " co nhieu so hoan thien nhat!");
                }
            }
        }

        stopDuyetMang();
    }

    public static int SoLuongSoDuong() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev > 0) {
                result++;
            }
        }

        stopDuyetMang();
        return result;
    }

    public static int SoLuongSoAm() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev < 0) {
                result++;
            }
        }

        stopDuyetMang();
        return result;
    }

    public static int SoLuongSoChanDuong() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev > 0 && gtemp_prev % 2 == 0) {
                result++;
            }
        }

        stopDuyetMang();
        return result;
    }


    // Bài 29. Số lượng số âm và lẻ
    public static int SoLuongSoAmLe() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev < 0 && gtemp_prev % 2 != 0) {
                result++;
            }
        }

        stopDuyetMang();
        return result;
    }


    // Bài 30. Nhỏ nhất trong mảng hai chiều
    public static int NhoNhat() {
        startDuyetMang();

        // Lấy phần tử đầu tiên làm giá trị nhỏ nhất ban đầu
        nextDuyetMang();
        int result = gtemp_prev;

        while (nextDuyetMang()) {
            if (gtemp_prev < result) {
                result = gtemp_prev;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int SoLuongXuatHien(int x) {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev == x) {
                result++;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int SoLuongNhoHonX(int x) {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev < x) {
                result++;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int SoLuongSoNguyenTo() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (LaSoNguyenTo(gtemp_prev)) {
                result++;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int SoLuongSoNguyenToNhoHon(int x) {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev < x && LaSoNguyenTo(gtemp_prev)) {
                result++;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static void DemAmTungDong() {
        int result = 0;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev < 0) {
                result++;
            }

            if (currentCol == 0) {
                System.out.println("Dong " + (currentRow - 1) + ": " + result);
                result = 0;
            }
        }

        stopDuyetMang();
    }
    public static void DemNguyenToTungCot() {
        for (int temp_col = 0; temp_col < cols; temp_col++) {
            int result = 0;

            for (int temp_row = 0; temp_row < rows; temp_row++) {
                if (LaSoNguyenTo(table[temp_row][temp_col])) {
                    result++;
                }
            }

            System.out.println("Cot " + temp_col + ": " + result);
        }
    }
    public static int DemDongGiam() {
        int result = 0;

        for (int temp_row = 0; temp_row < rows; temp_row++) {
            boolean giam = true;

            for (int temp_col = 0; temp_col < cols - 1; temp_col++) {
                if (table[temp_row][temp_col] < table[temp_row][temp_col + 1]) {
                    giam = false;
                    break;
                }
            }

            if (giam) {
                result++;
            }
        }

        return result;
    }
    public static int DemPhanTuCucDai() {
        int max = table[0][0];
        int result = 0;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev > max) {
                max = gtemp_prev;
            }
        }

        stopDuyetMang();

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev == max) {
                result++;
            }
        }

        stopDuyetMang();

        return result;
    }
    public static int DemGiaTriNhoNhat() {
        int min = table[0][0];
        int result = 0;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev < min) {
                min = gtemp_prev;
            }
        }

        stopDuyetMang();

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev == min) {
                result++;
            }
        }

        stopDuyetMang();

        return result;
    }
    public static int DemGiaTriChanNhoNhat() {
        int min = 0;
        boolean coSoChan = false;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev % 2 == 0) {
                if (!coSoChan || gtemp_prev < min) {
                    min = gtemp_prev;
                    coSoChan = true;
                }
            }
        }

        stopDuyetMang();

        if (!coSoChan) {
            return 0;
        }

        int result = 0;

        startDuyetMang();

        while (nextDuyetMang()) {
            if (gtemp_prev == min) {
                result++;
            }
        }

        stopDuyetMang();

        return result;
    }
    public static void HoanViHaiDong(int r1, int r2) {
        if (r1 < 0 || r1 >= rows || r2 < 0 || r2 >= rows) {
            System.out.println("Chi so dong khong hop le.");
            return;
        }

        for (int temp_col = 0; temp_col < cols; temp_col++) {
            int temp = table[r1][temp_col];
            table[r1][temp_col] = table[r2][temp_col];
            table[r2][temp_col] = temp;
        }
    }
    public static void HoanViHaiCot(int c1, int c2) {
        if (c1 < 0 || c1 >= cols || c2 < 0 || c2 >= cols) {
            System.out.println("Chi so cot khong hop le.");
            return;
        }

        for (int temp_row = 0; temp_row < rows; temp_row++) {
            int temp = table[temp_row][c1];
            table[temp_row][c1] = table[temp_row][c2];
            table[temp_row][c2] = temp;
        }
    }

    public static void TaoNgauNhienMaTranVuong(int n) {
        rows = n;
        cols = n;
        table = new int[n][n];

        Random random = new Random();

        for (int temp_row = 0; temp_row < rows; temp_row++) {
            for (int temp_col = 0; temp_col < cols; temp_col++) {
                table[temp_row][temp_col] = random.nextInt(100) + 1;
            }
        }
    }
    public static void TaoNgauNhienMaTranVuongTangDan(int n) {
        rows = n;
        cols = n;
        table = new int[n][n];

        Random random = new Random();

        int value = random.nextInt(10) + 1;

        for (int temp_row = 0; temp_row < rows; temp_row++) {
            for (int temp_col = 0; temp_col < cols; temp_col++) {
                table[temp_row][temp_col] = value;
                value += random.nextInt(10);
            }
        }
    }
    public static void InSoLeMTV() {
        startDuyetMang();
        while (nextDuyetMang()) {
            if (gtemp_prev % 2 == 0) System.out.print("X ");
            else System.out.print(gtemp_prev + " ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
    }
    // Update gtemp_now and gtemp_prev
    public static void In2DuongCheo() {
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == currentRow + 1) System.out.print(gtemp_prev + " ");
            else if (currentRow == 0 && currentCol == cols - 1) System.out.print("  " + table[currentRow][cols - 1] + " ");
            else if (currentCol == cols - currentRow) System.out.print(gtemp_prev + " ");
            else System.out.print("   ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
    }
    public static void KhongIn2DuongCheo() {
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == currentRow + 1) System.out.print("  ");
            else if (currentRow == 0 && currentCol == cols - 1) {
                System.out.print(gtemp_prev + " ");
                currentRow++;
                currentCol = 0;
            }
            else if (currentCol == cols - currentRow) System.out.print("  ");
            else System.out.print(gtemp_prev + " ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
    }
    public static int TongDuongCheoChinh() {
        int result = 0;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == currentRow + 1) {
                System.out.print(gtemp_prev + " ");
                result += gtemp_prev;
            }
            if (currentRow == rows - 1 && currentCol == cols - 1) {
                System.out.print(table[currentRow][currentCol] + " ");
                result += table[currentRow][currentCol];
            }
            else System.out.print(" ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
        return result;
    }
    public static int TongCacSoLeDuongCheoChinh() {
        int result = 0;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == currentRow + 1) {
                if (gtemp_prev % 2 != 0) {
                    System.out.print(gtemp_prev + " ");
                    result += gtemp_prev;
                }
            }
            if (currentRow == rows - 1 && currentCol == cols - 1) {
                if (table[currentRow][currentCol] % 2 != 0) {
                    System.out.print(table[currentRow][currentCol] + " ");
                    result += table[currentRow][currentCol];
                }
            }
            else System.out.print(" ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
        return result;
    }
    public static int TongDuongCheoPhu() {
        int result = 0;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == cols - currentRow && currentRow != rows) {
                System.out.print(gtemp_prev + " ");
                result += gtemp_prev;
            }
            if (currentRow == 0 && currentCol == cols - 1) {
                System.out.print(table[currentRow][currentCol] + " ");
                result += table[currentRow][currentCol];
            }
            else System.out.print(" ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
        return result;
    }
    public static int TongSoNguyenToDuongCheoPhu() {
        int result = 0;
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == cols - currentRow && currentRow != rows) {
                if (LaSoNguyenTo(gtemp_prev)) {
                    System.out.print(gtemp_prev + " ");
                    result += gtemp_prev;
                }
            }
            if (currentRow == 0 && currentCol == cols - 1) {
                if (LaSoNguyenTo(table[currentRow][currentCol])) {
                    System.out.print(table[currentRow][currentCol] + " ");
                    result += table[currentRow][currentCol];
                }
            }
            else System.out.print(" ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
        return result;
    }
    // Debug dong ho cat
//    public static int TinhDongHoCat() {
//        int result = 0;
//
//        startDuyetMang();
//        while (nextDuyetMang()) {
//
//            if (currentCol > currentRow && currentCol < cols + 1 - currentRow) {
//                System.out.print(gtemp_prev + " ");
//                result += gtemp_prev;
//                if (currentRow == 0 && currentCol == cols - 1) {
//                    System.out.print(table[currentRow][currentCol] + " ");
//                    result += table[currentRow][currentCol];
//                }
//            }
//
//            else if (currentCol <= currentRow + 1 && currentCol >= cols - currentRow) {
//                System.out.print(gtemp_prev + " ");
//                result += gtemp_prev;
//            }
//            else {
//                System.out.print(" ");
//            }
//
//            if (currentCol == 0)
//                System.out.println();
//        }
//
//        stopDuyetMang();
//        System.out.println(result);
//
//        return result;
//    }
    // Debug DuongCheoChinh
//    public static int TinhTongTamGiacTrenDuongCheoChinh() {
//        int result = 0;
//
//        startDuyetMang();
//        System.out.print(table[0][0] + " ");
//        result += table[0][0];
//        while (nextDuyetMang()) {
//            if (currentCol >= currentRow) {
//                System.out.print(gtemp_now + " ");
//                result += gtemp_now;
//            }
//
//            else {
//                System.out.print("   ");
//            }
//
//            if (currentCol == 0)
//                System.out.println();
//        }
//
//        stopDuyetMang();
//        System.out.println(result);
//
//        return result;
//    }
    public static int TinhTongTamGiacTrenDuongCheoChinh() {
        int result = 0;

        startDuyetMang();
        result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol >= currentRow) {
                result += gtemp_now;
            }
            if (currentCol == 0) System.out.println();
        }

        stopDuyetMang();
        return result;
    }
        public static int TinhTongTamGiacDuoiDuongCheoChinh() {
        int result = 0;

        startDuyetMang();
        result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol == 1 && currentRow < rows) {
                result += gtemp_now;
            }
            else if (currentCol <= currentRow && currentRow < rows) {
                result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTongTamGiacDuoiDuongCheoPhu() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == cols && currentRow < rows) {
                result += gtemp_now;
            }
            else if (currentCol >= cols - 1 - currentRow && currentRow < rows) {
                result += gtemp_now;
            }
        }


        stopDuyetMang();
        return result;
    }
    public static int TinhTongTamGiacTrenDuongCheoPhu() {
        int result = 0;

        startDuyetMang();
        result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol == 0 && currentRow < rows) {
                result += gtemp_now;
            }
            else if (currentCol < cols - currentRow && currentRow < rows) {
                result += gtemp_now;
            }
        }


        stopDuyetMang();
        return result;
    }

    public static int TinhTongSoLeTamGiacTrenDuongCheoChinh() {
        int result = 0;

        startDuyetMang();
        if (table[0][0] % 2 != 0) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol >= currentRow) {
                if (gtemp_now % 2 != 0) result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoLeTamGiacDuoiDuongCheoChinh() {
        int result = 0;

        startDuyetMang();
        if (table[0][0] % 2 != 0) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol == 1 && currentRow < rows) {
                if (gtemp_now % 2 != 0) result += gtemp_now;
            }
            else if (currentCol <= currentRow && currentRow < rows) {
                if (gtemp_now % 2 != 0) result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoLeTamGiacDuoiDuongCheoPhu() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == cols && currentRow < rows) {
                if (gtemp_now % 2 != 0) result += gtemp_now;
            }
            else if (currentCol >= cols - 1 - currentRow && currentRow < rows) {
                if (gtemp_now % 2 != 0) result += gtemp_now;
            }
        }


        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoLeTamGiacTrenDuongCheoPhu() {
        int result = 0;

        startDuyetMang();
        if (table[0][0] % 2 != 0) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol == 0 && currentRow < rows) {
                if (gtemp_now % 2 != 0) result += gtemp_now;
            }
            else if (currentCol < cols - currentRow && currentRow < rows) {
                if (gtemp_now % 2 != 0) result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoDuongTamGiacTrenDuongCheoChinh() {
        int result = 0;

        startDuyetMang();
        if (table[0][0] >= 0) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol >= currentRow) {
                if (gtemp_now >= 0) result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoDuongTamGiacDuoiDuongCheoChinh() {
        int result = 0;

        startDuyetMang();
        if (table[0][0] >= 0) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol == 1 && currentRow < rows) {
                if (gtemp_now >= 0) result += gtemp_now;
            }
            else if (currentCol <= currentRow && currentRow < rows) {
                if (gtemp_now >= 0) result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoDuongTamGiacDuoiDuongCheoPhu() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == cols && currentRow < rows) {
                if (gtemp_now >= 0) result += gtemp_now;
            }
            else if (currentCol >= cols - 1 - currentRow && currentRow < rows) {
                if (gtemp_now >= 0) result += gtemp_now;
            }
        }


        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoDuongTamGiacTrenDuongCheoPhu() {
        int result = 0;

        startDuyetMang();
        if (table[0][0] >= 0) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol == 0 && currentRow < rows) {
                if (gtemp_now >= 0) result += gtemp_now;
            }
            else if (currentCol < cols - currentRow && currentRow < rows) {
                if (gtemp_now >= 0) result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTrungBinhCongTamGiacTrenDuongCheoChinh() {
        int result = 0;
        int count = 0;

        startDuyetMang();
        result += table[0][0];
        count++;

        while (nextDuyetMang()) {
            if (currentCol >= currentRow) {
                result += gtemp_now;
                count++;
            }
        }

        stopDuyetMang();
        return result / count;
    }
    public static int TinhTrungBinhCongTamGiacDuoiDuongCheoChinh() {
        int result = 0;
        int count = 0;

        startDuyetMang();
        result += table[0][0];
        count++;

        while (nextDuyetMang()) {
            if (currentCol == 1 && currentRow < rows) {
                result += gtemp_now;
                count++;
            }
            else if (currentCol <= currentRow && currentRow < rows) {
                result += gtemp_now;
                count++;
            }
        }

        stopDuyetMang();
        return result / count;
    }
    public static int TinhTrungBinhCongTamGiacDuoiDuongCheoPhu() {
        int result = 0;
        int count = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == cols && currentRow < rows) {
                result += gtemp_now;
                count++;
            }
            else if (currentCol >= cols - 1 - currentRow && currentRow < rows) {
                result += gtemp_now;
                count++;
            }
        }


        stopDuyetMang();
        return result / count;
    }
    public static int TinhTrungBinhCongTamGiacTrenDuongCheoPhu() {
        int result = 0;
        int count = 0;

        startDuyetMang();
        result += table[0][0];
        count++;
        while (nextDuyetMang()) {
            if (currentCol == 0 && currentRow < rows) {
                result += gtemp_now;
                count++;
            }
            else if (currentCol < cols - currentRow && currentRow < rows) {
                result += gtemp_now;
                count++;
            }
        }

        stopDuyetMang();
        return result / count;
    }

    public static int TinhTongSoHoanThienTamGiacTrenDuongCheoChinh() {
        int result = 0;

        startDuyetMang();
        if (LaSoHoanThien(table[0][0])) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol >= currentRow) {
                if (LaSoHoanThien(gtemp_now)) result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoHoanThienTamGiacDuoiDuongCheoChinh() {
        int result = 0;

        startDuyetMang();
        if (LaSoHoanThien(table[0][0])) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol == 1 && currentRow < rows) {
                if (LaSoHoanThien(gtemp_now)) result += gtemp_now;
            }
            else if (currentCol <= currentRow && currentRow < rows) {
                if (LaSoHoanThien(gtemp_now)) result += gtemp_now;
            }
        }

        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoHoanThienTamGiacDuoiDuongCheoPhu() {
        int result = 0;

        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == cols && currentRow < rows) {
                if (LaSoHoanThien(gtemp_now)) result += gtemp_now;
            }
            else if (currentCol >= cols - 1 - currentRow && currentRow < rows) {
                if (LaSoHoanThien(gtemp_now)) result += gtemp_now;
            }
        }


        stopDuyetMang();
        return result;
    }
    public static int TinhTongSoHoanThienTamGiacTrenDuongCheoPhu() {
        int result = 0;

        startDuyetMang();
        if (LaSoHoanThien(table[0][0])) result += table[0][0];
        while (nextDuyetMang()) {
            if (currentCol == 0 && currentRow < rows) {
                if (LaSoHoanThien(gtemp_now)) result += gtemp_now;
            }
            else if (currentCol < cols - currentRow && currentRow < rows) {
                if (LaSoHoanThien(gtemp_now)) result += gtemp_now;
            }
        }


        stopDuyetMang();
        return result;
    }
    public static void TimXDuongCheoChinh(int x) {
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == currentRow + 1) {
                if (gtemp_prev == x) {
                    System.out.print("[" + gtemp_prev + "] ");
                    System.out.print("table[" + (currentRow) + "][" + (currentRow) + "] ");
                }
                else System.out.print(gtemp_prev + " ");
            }
            else if (currentRow == rows - 1 && currentCol == cols - 1) {
                if (table[currentRow][currentCol] == x) {
                    System.out.print(" [" + x + "] ");
                    System.out.print("table[" + currentRow + "][" + currentCol + "] ");
                }
                else System.out.print(table[currentRow][currentCol] + " ");
            }
            else System.out.print("   ");
            if (currentCol == 0) System.out.println();
        }
        stopDuyetMang();
        System.out.println();
    }
    public static void TimXDuongCheoPhu(int x) {
        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == 0) System.out.println();
            else if (currentCol == cols - currentRow) {
                if (gtemp_prev == x) {
                    System.out.print("[" + gtemp_prev + "] ");
                    System.out.print("table[" + (currentRow) + "][" + (currentCol - 1) + "] ");
                }
                else System.out.print(gtemp_prev + " ");
            }
            else if (currentRow == 0 && currentCol == cols - 1) {
                if (table[currentRow][currentCol] == x) {
                    System.out.print(" [" + x + "] ");
                    System.out.print("table[" + currentRow + "][" + currentCol + "] ");
                }
                else System.out.print(" " + table[currentRow][currentCol] + " ");
            }
            else System.out.print("   ");
        }
        stopDuyetMang();
        System.out.println();
    }
    public static void TimXTamGiacTrenDuongCheoChinh(int x) {
        // Thuc te se dung ArrayList hoac mot phuong phap mang dong (Dynamic Array) nao khac
        String[] result = new String[rows*cols];
        startDuyetMang();
        if (table[0][0] == x) {
            System.out.print("[" + table[0][0] + "] ");
            result[0] = "table[0][0]";
        }
        else System.out.print(table[0][0] + " ");
        while (nextDuyetMang()) {
            if (currentCol == 0) System.out.println();
            else if (currentCol >= currentRow) {
                if (gtemp_now == x) {
                    System.out.print("[" + gtemp_now + "] ");
                    for (int temp = 0; temp < rows*cols; temp++) {
                        if (result[temp] == null) {
                            result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                            break;
                        }
                        else continue;
                    }
                }
                else System.out.print(gtemp_now + " ");
            }
            else System.out.print("   ");
        }
        stopDuyetMang();
        for (int temp = 0; temp < rows*cols; temp++) {
            if (result[temp] != null) {
                System.out.println(result[temp]);
            }
        }
        result = null;
    }
    public static void TimXTamGiacDuoiDuongCheoChinh(int x) {
        String[] result = new String[rows*cols];

        startDuyetMang();
        if (table[0][0] == x) {
            System.out.print("[" + table[0][0] + "] ");
            result[0] = "table[0][0]";
        }
        else System.out.print(table[0][0] + " ");
        while (nextDuyetMang()) {
            if (currentCol == 0) {
                System.out.println();
                if (gtemp_now == x) {
                    System.out.print("[" + gtemp_now + "] ");
                    for (int temp = 0; temp < rows*cols; temp++) {
                        if (result[temp] == null) {
                            result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                            break;
                        }
                        else continue;
                    }
                }
                else if (currentRow != rows) System.out.print(gtemp_now + " ");
            }
            else if (currentCol == 1 && currentRow < rows && currentRow != 0) {
                if (gtemp_now == x) {
                    System.out.print("[" + gtemp_now + "] ");
                    for (int temp = 0; temp < rows*cols; temp++) {
                        if (result[temp] == null) {
                            result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                            break;
                        }
                        else continue;
                    }
                }
                else System.out.print(gtemp_now + " ");
            }
            else if (currentCol <= currentRow && currentRow < rows) {
                if (gtemp_now == x) {
                    System.out.print("[" + gtemp_now + "] ");
                    for (int temp = 0; temp < rows*cols; temp++) {
                        if (result[temp] == null) {
                            result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                            break;
                        }
                        else continue;
                    }
                }
                else System.out.print(gtemp_now + " ");
            }
        }

        stopDuyetMang();
        for (int temp = 0; temp < rows*cols; temp++) {
            if (result[temp] != null) {
                System.out.println(result[temp]);
            }
        }
        result = null;
    }
    public static void TimXTamGiacDuoiDuongCheoPhu(int x) {
        String[] result = new String[rows*cols];

        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == 0) {
                System.out.println();
                if (currentRow == rows - 1) {
                    System.out.print(gtemp_now + " ");
                    if (gtemp_now == x) {
                        for (int temp = 0; temp < rows * cols; temp++) {
                            if (result[temp] == null) {
                                result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                                break;
                            } else continue;
                        }
                    }
                }
            }
            else if (currentCol == cols && currentRow < rows) {
                if (gtemp_now == x) {
                    System.out.print("[" + gtemp_now + "] ");
                    for (int temp = 0; temp < rows*cols; temp++) {
                        if (result[temp] == null) {
                            result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                            break;
                        }
                        else continue;
                    }
                }
                else System.out.print(gtemp_now + " ");
            }
            else if (currentCol >= cols - 1 - currentRow && currentRow < rows) {
                if (gtemp_now == x) {
                    System.out.print("[" + gtemp_now + "] ");
                    for (int temp = 0; temp < rows*cols; temp++) {
                        if (result[temp] == null) {
                            result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                            break;
                        }
                        else continue;
                    }
                }
                else System.out.print(gtemp_now + " ");
            }
            else System.out.print("   ");
        }


        stopDuyetMang();
        for (int temp = 0; temp < rows*cols; temp++) {
            if (result[temp] != null) {
                System.out.println(result[temp]);
            }
        }
        result = null;
    }
    public static void TimXTamGiacTrenDuongCheoPhu(int x) {
        String[] result = new String[rows*cols];

        startDuyetMang();
        if (table[0][0] == x) {
                for (int temp = 0; temp < rows*cols; temp++) {
                    if (result[temp] == null) {
                        result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                        break;
                    }
                    else continue;
                }
        }
        else System.out.print(table[0][0] + " ");
        while (nextDuyetMang()) {
            if (currentCol == 0 && currentRow < rows) {
                System.out.println();
                if (gtemp_now == x) {
                    System.out.print("[" + gtemp_now + "] ");
                    for (int temp = 0; temp < rows*cols; temp++) {
                        if (result[temp] == null) {
                            result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                            break;
                        }
                        else continue;
                    }
                }
                else System.out.print(gtemp_now + " ");
            }
            else if (currentCol < cols - currentRow && currentRow < rows) {
                if (gtemp_now == x) {
                    System.out.print("[" + gtemp_now + "] ");
                    for (int temp = 0; temp < rows*cols; temp++) {
                        if (result[temp] == null) {
                            result[temp] = "table[" + currentRow + "][" + currentCol + "] ";
                            break;
                        }
                        else continue;
                    }
                }
                else System.out.print(gtemp_now + " ");
            }
        }
        System.out.println();
        stopDuyetMang();
        for (int temp = 0; temp < rows*cols; temp++) {
            if (result[temp] != null) {
                System.out.println(result[temp]);
            }
        }
        result = null;
    }
    public static int MaxTamGiacTrenDuongCheoChinh() {
        int result = table[0][0];

        startDuyetMang();
        System.out.print(table[0][0] + " ");
        while (nextDuyetMang()) {
            if (currentCol == 0) {
                System.out.println();
            }
            else if (currentCol >= currentRow) {
                if (gtemp_now >= result) {
                    result = gtemp_now;
                    System.out.print("[" + result + "] ");
                }
                else System.out.print(gtemp_now + " ");
            }
            else System.out.print("   ");
        }

        stopDuyetMang();
        System.out.println(result);
        return result;
    }

    public static int MinTamGiacDuoiDuongCheoPhu() {
        int result = table[0][cols - 1];

        startDuyetMang();
        while (nextDuyetMang()) {
            if (currentCol == 0) {
                System.out.println();
                if (currentRow == rows - 1) {
                    if (gtemp_now <= result) {
                        result = gtemp_now;
                        System.out.print("[" + result + "] ");
                    }
                    else System.out.print(gtemp_now + " ");
                }
            }
            else if (currentCol == cols && currentRow < rows) {
                if (gtemp_now <= result) {
                    result = gtemp_now;
                    System.out.print("[" + result + "] ");
                }
                else System.out.print(gtemp_now + " ");
            }
            else if (currentCol >= cols - 1 - currentRow && currentRow < rows) {
                if (gtemp_now <= result) {
                    result = gtemp_now;
                    System.out.print("[" + result + "] ");
                }
                else System.out.print(gtemp_now + " ");
            }
            else System.out.print("   ");
        }


        stopDuyetMang();
        System.out.println(result);
        return result;
    }


    public static void main(String[] args) {
//        TaoMang();
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
//        table[9][3] = 6;
//        table[9][2] = 28;
//        System.out.println(SoHoanThienNhoNhat());
//        System.out.println(SoHoanThienDauTien());
//        System.out.println(SoChinhPhuongLonNhat());
//        System.out.println(CotCoTongNhoNhat());
//        table[3][4] = 999999999;
//        table[9][2] = 2;
//        System.out.println(DongCoTongLonNhat());
//        LietKeCotSoChinhPhuong();
//        LietKeDongCoSoNguyenTo();
//        table[0][0] = -1;
//        LietKeDongToanAm();
//        LietKeDongGiamDan();
//        LietKeDongCoTongLonNhat();
//        LietKeDongNhieuSoChanNhat();
//        LietKeDongCoNhieuSoNguyenToNhat();
//        LietKeDongNhieuSoHoanThienNhat();
        TaoNgauNhienMaTranVuong(11);
        In2DuongCheo();
//        KhongIn2DuongCheo();
//        System.out.println(TongDuongCheoChinh());
//        System.out.println(TongCacSoLeDuongCheoChinh());
//        System.out.println(TongDuongCheoPhu());
//        System.out.println(TongSoNguyenToDuongCheoPhu());
//        TinhDongHoCat();
//        System.out.println(TinhTongTamGiacDuoiDuongCheoChinh());
//        System.out.print(TinhTongTamGiacDuoiDuongCheoPhu());
//        System.out.print(TinhTongTamGiacTrenDuongCheoPhu());
//        System.out.println(TinhTrungBinhCongTamGiacTrenDuongCheoChinh());
//        table[rows - 1][cols - 1] = 12;
//        table[5][2] = 12;
//        TimXDuongCheoPhu(12);
//        TimXTamGiacTrenDuongCheoPhu(12);
//        TimXTamGiacTrenDuongCheoChinh(12);
//        MaxTamGiacTrenDuongCheoChinh();
        MinTamGiacDuoiDuongCheoPhu();
//        TimXTamGiacDuoiDuongCheoPhu(12);
        System.out.println();

        XuatMang();
    }
}
