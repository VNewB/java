import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
public class DemoImage extends Applet{
public void init() {
System.out.println("Demonstration of imaging");

}
public void paint(Graphics g) {

Image image = getToolkit().getImage("screenshot.jpg");
g.drawImage(image, 0, 0, this);

}
}