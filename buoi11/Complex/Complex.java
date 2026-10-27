//package buoi11.Complex;


import java.util.Scanner;

class Complex {
    private double realPart;
    private double imaginaryPart;

    public Complex() {
        this(0, 0);
    }

    public Complex(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public Complex cong(Complex c) {
        return new Complex(
                this.realPart + c.realPart,
                this.imaginaryPart + c.imaginaryPart
        );
    }

    public Complex tru(Complex c) {
        return new Complex(
                this.realPart - c.realPart,
                this.imaginaryPart - c.imaginaryPart
        );
    }

    public void nhap(Scanner sc) {
        System.out.print("Nhap phan thuc: ");
        realPart = sc.nextDouble();

        System.out.print("Nhap phan ao: ");
        imaginaryPart = sc.nextDouble();
    }

    public void xuat() {
        System.out.println("(" + realPart + ", " + imaginaryPart + ")");
    }
}
