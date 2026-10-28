package thread;

import java.io.*;
class KtoD
{ public static void main(String args[]) throws IOException
{ String str; FileWriter fw; BufferedReader br = new BufferedReader(new
        InputStreamReader(System.in));
    try{ fw = new FileWriter("./test.txt"); }
    catch(IOException exc){ System.out.println("Khong the mo file."); return; }
    System.out.println("Nhap ('stop' de ket thuc chuong trinh).");
    do
    {
        System.out.print(": ");
        str = br.readLine();
        if(str.compareTo("stop") == 0) break;
        str = str + "\r\n";
        fw.write(str);
    } while(str.compareTo("stop") != 0);
    fw.close();
}
}