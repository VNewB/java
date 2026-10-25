import java.awt.*;
class NullLayoutDemo{

public static void main(String args[]) {

Frame fr = new Frame("NullLayout Demo");
fr.setLayout(null);
Button buttOk = new Button("OK");
buttOk.setBounds(100, 150, 50, 30);
Button buttCancel = new Button("Cancel");
buttCancel.setBounds(200, 150, 50, 30);
Checkbox checkBut = new Checkbox("Check box", true);
checkBut.setBounds(100, 50, 100, 20);
List li = new List();
for (int i=0; i<5; i++) {
li.add(Integer.toString(i));

}
li.setBounds(200, 50, 50, 50);
fr.add(buttOk);
fr.add(buttCancel);
fr.add(checkBut);
fr.add(li);
fr.setBounds(10, 10, 400, 200);
fr.setVisible(true);

}

}