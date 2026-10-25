import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
public class DemoCheckbox extends Applet implements ItemListener
{
private Checkbox checkBold;
private Checkbox checkItalic;
privateTextFieldtxtText;
public void init()
{
checkBold = new Checkbox("Bold");
checkItalic = new Checkbox("Italic");
checkBold.addItemListener(this);
checkItalic.addItemListener(this);
txtText = new TextField("Sample Text", 16);
Font font = new Font("Courier", Font.PLAIN, 14);
txtText.setFont(font);
add(txtText);
add(checkBold);
add(checkItalic);
}
public void itemStateChanged(ItemEvent event)
{

int valBold = Font.PLAIN;
int valItalic = Font.PLAIN;
if(checkBold.getState()) valBold = Font.BOLD;
if(checkItalic.getState()) valItalic = Font.ITALIC;
Font font = new Font("Courier", valBold + valItalic, 14);
txtText.setFont(font);
}
}