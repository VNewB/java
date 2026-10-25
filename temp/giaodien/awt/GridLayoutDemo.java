import java.awt.*;
public class GridLayoutDemo{

public static void main(String arg[]) {

Frame f = new Frame("GridLayout Demo");
f.setLayout(new GridLayout(3,2));
f.add(new Button("Red"));
f.add(new Button("Green"));
f.add(new Button("Blue"));
f.add(new Checkbox("Pick me", true));
f.add(new Label("Enter name here:"));
f.add(new TextField());
f.pack();
f.setVisible(true);

}

}