//package buoi11.DaThuc;

import java.util.Scanner;

class DaThuc {
    private int n;
    private int[] tu;
    private int[] mau;

    public DaThuc(int n) {
        this.n = n;
        tu = new int[n + 1];
        mau = new int[n + 1];
    }

    public void nhap(Scanner sc) {
        for (int i = 0; i <= n; i++) {
            System.out.print("He so x^" + i + " - tu so: ");
            tu[i] = sc.nextInt();

            do {
                System.out.print("He so x^" + i + " - mau so: ");
                mau[i] = sc.nextInt();

                if (mau[i] == 0)
                    System.out.println("Mau so phai khac 0!");
            } while (mau[i] == 0);
        }
    }

    public void xuat() {
        for (int i = n; i >= 0; i--) {
            if (tu[i] == 0) continue;

            System.out.print(" + (" + tu[i] + "/" + mau[i] + ")");

            if (i > 0)
                System.out.print("x^" + i);
        }
        System.out.println();
    }

    public double tinhGiaTri(double x) {
        double ketQua = 0;

        for (int i = 0; i <= n; i++)
            ketQua += (double) tu[i] / mau[i] * Math.pow(x, i);

        return ketQua;
    }

    public DaThuc cong(DaThuc b) {
        int bac = Math.max(n, b.n);
        DaThuc kq = new DaThuc(bac);

        for (int i = 0; i <= bac; i++) {
            int t1 = i <= n ? tu[i] : 0;
            int m1 = i <= n ? mau[i] : 1;
            int t2 = i <= b.n ? b.tu[i] : 0;
            int m2 = i <= b.n ? b.mau[i] : 1;

            kq.tu[i] = t1 * m2 + t2 * m1;
            kq.mau[i] = m1 * m2;
        }

        return kq;
    }

    public DaThuc nhan(DaThuc b) {
        DaThuc kq = new DaThuc(n + b.n);

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= b.n; j++) {
                int t1 = tu[i] * b.tu[j];
                int m1 = mau[i] * b.mau[j];

                int t2 = kq.tu[i + j];
                int m2 = kq.mau[i + j];

                if (m2 == 0) m2 = 1;

                kq.tu[i + j] = t1 * m2 + t2 * m1;
                kq.mau[i + j] = m1 * m2;
            }
        }

        return kq;
    }
}
