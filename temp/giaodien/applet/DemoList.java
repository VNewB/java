public class DemoList extends Applet implements ItemListener, ActionListener
{
private List colorList;
public void init()
{
colorList = newList(3, false);
colorList.add("White");
colorList.add("Black");
colorList.add("Yellow");
colorList.add("Green");
colorList.addItemListener(this);
colorList.addActionListener(this);
add(colorList);
}
public void itemStateChanged(ItemEvent event)
{

List list = (List) event.getSource();
showStatus("Item " + list.getSelectedIndex() + " selected");
}
public void actionPerformed(ActionEvent event)
{

List list = (List) event.getSource();
Strings = list.getSelectedItem();
if(s.equals("White")) setBackground(Color.WHITE);
if(s.equals("Black")) setBackground(Color.BLACK);
if(s.equals("Yellow")) setBackground(Color.YELLOW);
if(s.equals("Green")) setBackground(Color.GREEN);
repaint();

}
}