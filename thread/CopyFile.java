package thread;

import java.io.*;
class CopyFile
{
    public static void main(String args[])throws IOException
    {
        int i; FileInputStream fin; FileOutputStream fout;
        try { // open input file
            try { fin = new FileInputStream("./source.txt");}
            catch(FileNotFoundException exc) { System.out.println("Input File Not Found"); return; }
// open output file
            try { fout = new FileOutputStream("./dest.txt"); }
            catch(FileNotFoundException exc) { System.out.println("Error Opening Output File"); return; }
        } catch(ArrayIndexOutOfBoundsException exc)
        { System.out.println("Usage: CopyFile From To"); return; }
        try {// Copy File
            do { i = fin.read();
                if(i != -1) fout.write(i);
            } while(i != -1);
        } catch(IOException exc)
        { System.out.println("File Error"); }
        fin.close(); fout.close();
    }
} //Kết quả, chương trình sẽ copy nội dung của file D:\source.txt và ghi vào một file mới D:\dest.txt.