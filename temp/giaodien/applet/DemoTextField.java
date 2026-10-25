import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
public class DemoTextField extends Applet implements ActionListener
{
private TextField txtEdit;
private TextField txtReadOnly;
private TextField txtPass;
private final String PASSWORD = "Java";
public void init()
{
txtEdit = new TextField("Your name here");
txtPass = newTextField(12);
txtPass.setEchoChar('*');
txtPass.addActionListener(this);
txtReadOnly = newTextField("This text is read only");
txtReadOnly.setEditable(false);
add(txtEdit);
add(txtPass);
add(txtReadOnly);
}
public void actionPerformed(ActionEvent event)
{

if(txtPass.getText().equals(PASSWORD))

txtReadOnly.setText("Password is valid");

else

txtReadOnly.setText("Invalid password !");

}
}