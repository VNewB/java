public class DemoRadio extends Applet implements ItemListener
{
private Checkbox plain, bold, italic;
private CheckboxGroup group;
private TextFieldtxtText;
public void init()
{

group = new CheckboxGroup();
plain = new Checkbox("Plain", group,true);
bold = new Checkbox("Bold", group, false);
italic = new Checkbox("Italic", group, false);
txtText = new TextField("Sample Text");
txtText.setFont(new Font("Courier", Font.PLAIN, 14));
plain.addItemListener(this);
bold.addItemListener(this);
italic.addItemListener(this);
add(txtText);
add(plain);
add(italic);
add(bold);
}
public void itemStateChanged(ItemEvent event)
{

int mode = 0;
if(event.getSource() == plain) mode = Font.PLAIN;
if(event.getSource() == italic) mode = Font.ITALIC;
if(event.getSource() == bold) mode = Font.BOLD;
txtText.setFont(newFont("Courier", mode, 14));
}
}