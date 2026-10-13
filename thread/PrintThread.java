package thread;

public class PrintThread extends Thread {
    private int sleepTime;
    public PrintThread( String name ) {
        super(name);
        sleepTime = (int) (Math.random() * 5000);
        System.out.println(getName() +
                " have sleep time: " + sleepTime);
    }
    public void run(){
        try{
            System.out.println(getName()+ "starts to sleep");
                    Thread.sleep( sleepTime );
        }
//sleep() may throw an InterruptedException
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println( getName() + " done sleeping" );
    }
}
