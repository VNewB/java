import java.applet.Applet;
import java.awt.*;
public class DemoMove1 extends Applet{
private int x = 50;
private int y = 50;
public void paint(Graphics g) {

if(x > 300) x = 50;
g.fillOval(x, y, 100, 100);
delay(100);
move();

}
public void delay(int milisecond) {
try{

Thread.sleep(milisecond);

} catch(Exception e) { }
}
public void move() {
x += 5;
repaint();
// ve lai cua so
}
}