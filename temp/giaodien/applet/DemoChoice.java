import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
public class DemoChoice extends Applet implements ItemListener
{
private Choice choice;
private TextField txtText;
private Font font;
public void init()
{
choice = newChoice();
choice.addItem("TimesRoman");
choice.addItem("Courier");
choice.addItem("Helvetica");
choice.addItemListener(this);
txtText = new TextField("Sample Text", 16);
txtText.setEditable(false);
font = newFont(choice.getItem(0),Font.PLAIN, 12);
txtText.setFont(font);
add(choice);
add(txtText);

}
public void itemStateChanged(ItemEvent event)
{
font = newFont(choice.getSelectedItem(), Font.PLAIN, 12);
txtText.setFont(font);

}
}