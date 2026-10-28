package thread;

import java.io.*;
class ShowFile
{
    public static void main(String args[]) throws IOException
    {
        int i; FileInputStream fin;
        try { fin = new FileInputStream("./text.txt"); }
        catch(FileNotFoundException exc){
            System.out.println("File Not Found"); return;
        }
        catch(ArrayIndexOutOfBoundsException exc){
            System.out.println("Usage: ShowFile File"); return;
        }
// read bytes until EOF is encountered
        do
        {
            i = fin.read();
            if(i != -1) System.out.print((char) i);
        } while(i != -1);
        fin.close();
    }
}