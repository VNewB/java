import java.applet.Applet;
import java.awt.Graphics;
public class DemoRect extends Applet{
public void init() {
System.out.println("Demonstration of some simple figures");
}
public void paint(Graphics g) {
g.drawLine(70, 300, 400, 250);
g.drawRect(100, 50, 130, 170);
g.fillRect(120, 70, 70, 70);
int[] x = { 280, 310, 330, 430, 370 };
int[] y = { 280, 140, 170, 70, 90 };
g.drawPolygon(x, y, x.length);
}
}