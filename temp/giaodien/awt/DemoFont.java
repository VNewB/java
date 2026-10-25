import java.applet.Applet;
import java.awt.*;
public class DemoFont extends Applet{
public void paint(Graphics g) {

Font font = newFont("Arial", Font.BOLD, 30);
g.setFont(font);
g.drawString("I am font Arial, bold, size 30", 50, 50);

}
}