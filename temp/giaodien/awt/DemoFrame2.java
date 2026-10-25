import java.awt.*;
import java.awt.event.*;
public class DemoFrame2
{ public static void main(String[] args)
{ MyFrame myFrame = new MyFrame("Example on my frame");

myFrame.setSize(250, 300);
myFrame.setVisible(true);
myFrame.addWindowListener(new WindowAdapter()
{// Lop nghe noi khong ten (anonymous inner class listener)
public void windowClosing(WindowEvent e)
{

System.exit(0);

}

});
}
}
class MyFrame extends Frame
{
public MyFrame(String title)
{

super(title);

}
public void paint(Graphics g)
{

g.setColor(Color.BLUE);
g.fillOval(40, 40, 80, 80);

}
}