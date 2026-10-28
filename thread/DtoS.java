package thread;

import java.io.*;
class DtoS
{
    public static void main(String args[]) throws Exception
    {
        FileReader fr = new FileReader("./test.txt");
        BufferedReader br = new BufferedReader(fr);
        String s;
        while((s = br.readLine()) != null)
        {
            System.out.println(s);
        }
        fr.close();
    }
}