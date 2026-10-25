import java.lang.String;
import java.io.*;
public class InverstString
{ public static void main(String arg[])
{ System.out.println("\n *** CHUONG TRINH IN CHUOI NGUOC *** ");

try
{ System.out.println("\n *** Nhap chuoi:");
BufferedReader in = new BufferedReader(new

InputStreamReader(System.in));
// Class BufferedReader cho phép đọc text từ luồng nhập ký //tự, tạo bộ đệm cho những ký tự để hỗ trợ cho việc đọc
//những ký tự, những mảng hay những dòng.
// Doc 1 dong tu BufferReadered ket thuc bang dau ket thuc dong.
String str = in.readLine();
System.out.println("\n Chuoi vua nhap la:" + str);
// Xuat chuoi nghich dao
System.out.println("\n Chuoi nghich dao la:");
for (int i=str.length()-1; i>=0; i--)
{

System.out.print(str.charAt(i));

}
}
catch (IOException e)
{
System.out.println(e.toString());
}
}