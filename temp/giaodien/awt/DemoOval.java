import java.applet.Applet;
import java.awt.Graphics;
public class DemoOval extends Applet{
public void init() {
System.out.println("Demonstration of some simple figures");

}
public void paint(Graphics g) {
int xstart = 70, ystart = 40, size = 100;
g.drawOval(xstart, ystart, size, size);
g.drawOval(xstart + (size*3)/4, ystart, size, size);
g.drawOval(xstart + size/2, ystart + size/2, size, size);
g.drawArc(xstart, ystart, 300, 200, 0, -90);
g.drawString("good morning !", xstart + 265, ystart + 90);

}
}