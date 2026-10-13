package thread;

public class ThreadTest{
    public static void main( String [ ] args ){
        PrintThread thread1 = new PrintThread( "thread1" );
        PrintThread thread2 = new PrintThread( "thread2" );
        PrintThread thread3 = new PrintThread( "thread3" );
        System.out.println( "Starting threads" );
        thread1.start(); //start and ready to run
        thread2.start(); //start and ready to run
        thread3.start(); //start and ready to run
        System.out.println( "Threads started, main ends\n" );
    }
}