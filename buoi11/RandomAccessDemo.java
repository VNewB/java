package buoi11;

import java.io.*;
class RandomAccessDemo {
    public static void main(String args[]) throws IOException {
        double data[] = {19.4, 10.1, 123.54, 33.0, 87.9, 74.25};
        double d;
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile("./random.dat", "rw");
        } catch (FileNotFoundException exc) {
            System.out.println("Cannot open file.");
            return;
        }
        // Write values to the file.
        for (int i = 0; i < data.length; i++) {
            try {
                raf.writeDouble(data[i]);
            } catch (IOException exc) {
                System.out.println("Error writing to file.");
                return;
            }
        }
        try { // Now, read back specific values
            raf.seek(0); // seek to first double
            d = raf.readDouble();
            System.out.println("First value is " + d);
            raf.seek(8); // seek to second double
            d = raf.readDouble();
            System.out.println("Second value is " + d);
            raf.seek(8 * 3); // seek to fourth double
            d = raf.readDouble();
            System.out.println("Fourth value is " + d);
            System.out.println();
            // Now, read every other value.
            System.out.println("Here is every other value: ");
            for (int i = 0; i < data.length; i++) {
                raf.seek(8 * i); // seek to ith double
                d = raf.readDouble();
                System.out.print(d + " ");
            }
            System.out.println("\n");
        } catch (IOException exc) {
            System.out.println("Error seeking or reading.");
        }
        raf.close();
    }
}

