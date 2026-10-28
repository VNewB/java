package thread;

import java.awt.*;
import java.io.*;
public class FileDemo
{
    public static void main(String args[]) {
        Frame fr = new Frame("File Demo");
        fr.setBounds(10, 10, 300, 200);
        fr.setLayout(new BorderLayout());
        Panel p = new Panel(new GridLayout(1, 2));
        List list_C = new List();
        list_C.add("C:\\");
        File driver_C = new File("C:\\");
        String[] dirs_C = driver_C.list();
        for (int i = 0; i < dirs_C.length; i++) {
            File f = new File("C:\\" + dirs_C[i]);
            if (f.isDirectory())
                list_C.add("<DIR>" + dirs_C[i]);
            else
                list_C.add(" " + dirs_C[i]);
        }
        List list_D = new List();
        list_D.add("D:\\");
        File driver_D = new File("D:\\");
        String[] dirs_D = driver_D.list();
        for (int i = 0; i < dirs_D.length; i++) {
            File f = new File("D:\\" + dirs_D[i]);
            if (f.isDirectory())
                list_D.add("<DIR>" + dirs_D[i]);
            else
                list_D.add(" " + dirs_D[i]);
        }
        p.add(list_C);
        p.add(list_D);
        fr.add(p, BorderLayout.CENTER);
        fr.setVisible(true);
    }
    }

