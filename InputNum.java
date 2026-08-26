import java.io.*;
public class InputNum
{
    public static void main(String[] args)
    { 
        int n=0;
        try { 
            BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
            String s;
            s = in.readLine();
            n = Integer.parseInt(s);
        }
        catch(Exception e){
            System.out.println("Nhập dữ liệu bị lỗi !");
        }
    System.out.println("Bạn vừa nhập số :" + n);
    }
}