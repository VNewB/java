import java.applet.Applet;
import java.awt.*;
public class DemoColor extends Applet{
public void paint(Graphics g) {
Dimension size = getSize();
g.setColor(Color.orange);
g.fillRect(0, 0, size.width, size.height);
Color color = new Color(10, 150, 20);
g.setColor(color);
g.drawString("I am a colorful string",

size.width/2 -50, size.height/2);

}
}