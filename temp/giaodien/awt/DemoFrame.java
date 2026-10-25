import java.awt.*;
import java.awt.event.*;
public class DemoFrame
{
public static void main(String[] args)
{

Frame frame = new Frame("Example on Frame");
Label label = new Label("This is a label in Frame",Label.CENTER);
frame.add(label, BorderLayout.CENTER);
frame.setSize(500,500);
frame.setVisible(true);
frame.addWindowListener(new MyWindowListener());
}
}
class MyWindowListener extends WindowAdapter
{
public void windowClosing(WindowEvent event)
{

System.exit(0);

}
}