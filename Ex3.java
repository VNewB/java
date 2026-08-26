public class Ex3 {
    int x = 10;
    // variable
    public void show( ) { // method
        System.out.println(x);
    }
    public static void main(String args[ ]) {
        Ex3 t = new Ex3( );
        // tao doi tuong t
        t.show( );
        // method call
        Ex3 t1 = new Ex3( );
        // tao doi tuong t1
        t1.x = 20;
        t1.show( );
    }
}