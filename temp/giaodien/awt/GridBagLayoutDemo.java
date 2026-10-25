import java.awt.*;
public class GridBagLayoutDemo
{

public static void main(String arg[])
{

Frame f = new Frame("GridBagLayout Demo");
// Thiet lap layout manager
// Tao doi tuong rang buoc cho cach trinh bay
// GridBagLayout.
GridBagLayout layout = new GridBagLayout();
GridBagConstraints constraints = new
GridBagConstraints();
f.setLayout(layout);
// Tao ra 9 nut nhan
String[] buttName = {"Mot", "Hai", "Ba", "Bon", "Nam", "Sau", "Bay",
"Tam", "Chin"};

Button[] buttons = new Button[9];
for(int i=0;i<9;i++)
{

buttons[i] = new Button (buttName[i]);

}
// Rang buoc cac nut nhan cach nhau 2 pixel
constraints.insets = new Insets(2,2,2,2);
// Qui dinh cac nut nhan se thay doi kich thuoc
// theo ca 2 chieu
constraints.fill = GridBagConstraints.BOTH;
// Rang buoc cho nut nhan thu 1
constraints.gridx = 1; constraints.gridy = 1;
constraints.gridheight = 2; constraints.gridwidth = 1;
layout.setConstraints(buttons[0], constraints);
// Rang buoc cho nut nhan thu 2
constraints.gridx = 2; constraints.gridy = 1;
constraints.gridheight = 1; constraints.gridwidth = 2;
layout.setConstraints(buttons[1], constraints);
// Rang buoc cho nut nhan thu 3
constraints.gridx = 2; constraints.gridy = 2;
constraints.gridheight = 1; constraints.gridwidth = 1;
layout.setConstraints(buttons[2], constraints);
// Rang buoc cho nut nhan thu 4
constraints.gridx = 1; constraints.gridy = 3;
constraints.gridheight = 1; constraints.gridwidth = 2;
layout.setConstraints(buttons[3], constraints);
// Rang buoc cho nut nhan thu 5
constraints.gridx = 3; constraints.gridy = 2;
constraints.gridheight = 2; constraints.gridwidth = 1;
layout.setConstraints(buttons[4], constraints);
// Rang buoc cho nut nhan thu 6
constraints.gridx = 4; constraints.gridy = 1;
constraints.gridheight = 3; constraints.gridwidth = 1;
layout.setConstraints(buttons[5], constraints);
// Tu nut thu 7 tro di khong can rang buoc
// thay vi doi kich thuoc
constraints.fill = GridBagConstraints.NONE;
// Rang buoc cho nut nhan thu 7
constraints.gridx = 1; constraints.gridy = 4;
constraints.gridheight = 1; constraints.gridwidth = 1;
constraints.weightx = 1.0;
layout.setConstraints(buttons[6], constraints);
// Rang buoc cho nut nhan thu 8
constraints.gridx = 2; constraints.gridy = 5;
constraints.gridheight = 1; constraints.gridwidth = 1;
constraints.weightx = 2.0;
layout.setConstraints(buttons[7], constraints);
// Rang buoc cho nut nhan thu 9
constraints.gridx = 3;
constraints.gridy = 6;
constraints.gridheight = 1;
constraints.gridwidth = 1;
constraints.weightx = 3.0;
layout.setConstraints(buttons[8], constraints);
// Dua cac nut nhan khung chua chuong trinh
for (int i=0;i<9;i++)

f.add(buttons[i]);

f.pack();
f.setVisible(true);
}
}