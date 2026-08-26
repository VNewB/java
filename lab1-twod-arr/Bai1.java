import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Bai1 {
    // (i) Viet ham nhap 0 < X <= value
    public static int NhapSo() {
        int n = 0;
        int value = 100;
        System.out.print("Nhap so tu (1-"+ value + "): ");
        try {
            do { 
            BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
            String s;
            s = in.readLine();
            n = Integer.parseInt(s);
            if ((n<=0)||(n>100)) System.out.print("Nhap lai: ");
            } while((n <= 0)||(n > 100));
        } 
        catch (Exception e) {
            System.out.print("Nhap loi! ");
            return NhapSo();
        }
        System.out.println("So vua nhap: "+n);
        return n;
    }
    // TODO: Mục tiêu là nhập thủ công, không dùng vòng lặp  
    public static void NhapMang2Chieu () {
        int rows = 100;
        int cols = 200;
        int t,i;
        int[][] table = new int[rows][cols];
        for (t = 0; t < rows; ++t) {
            for (i = 0; i < cols; ++i) {
                table[t][i] = (t*cols)+i+1;
                // TODO: Đưa câu lệnh dưới đây vào hàm XuatMang2Chieu
                System.out.print(table[t][i]+" ");
            }
            // TODO: Đưa câu lệnh dưới đây vào hàm XuatMang2Chieu (tương tự)
            System.out.println();
        }
    }
    public static void main(String[] args) {
    NhapMang2Chieu();
        System.out.println("Complete!");
    }
}
