import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
public class DemoButton extends Applet implements ActionListener
{
private Button blueButton;
private Button whiteButton;
private Button helloButton;
public void init()
{

blueButton = new Button("Blue");
whiteButton = new Button("White");
helloButton = new Button("Hello");
blueButton.addActionListener(this);
whiteButton.addActionListener(this);
helloButton.addActionListener(this);
add(blueButton);
add(whiteButton);
add(helloButton);
}
public void actionPerformed(ActionEvent event)
{

if(event.getSource() == helloButton)

javax.swing.JOptionPane.showMessageDialog(this, "Hello !");

else{

if (event.getSource() == blueButton)

this.setBackground(Color.BLUE);
else if (event.getSource() == whiteButton)
this.setBackground(Color.WHITE);

repaint();

}
}
}