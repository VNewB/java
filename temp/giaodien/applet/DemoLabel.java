import java.applet.Applet;
import java.awt.*;
public class DemoLabel extends Applet
{
private Label label;
public void init()
{

Font font = new Font("Courier", Font.BOLD, 20);
label = new Label("Thu nghiem voi Label");
label.setFont(font);
add(label);
}
public void paint(Graphics g)
{
showStatus("Noi dung cua Label la: " + label.getText());
}
}