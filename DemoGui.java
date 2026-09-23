import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DemoGui extends Applet {
    public static void main(String[] args) {
        Frame f = new Frame("Demo AWT");
        Label label = new Label("Hello Java");
        Button button = new Button("Click");
        Panel row1 = new Panel(new FlowLayout());
        Panel row2 = new Panel(new FlowLayout());

        row1.add(new Button("A"));
        row1.add(new Button("B"));

        row2.add(new Label("Dong 2"));
        row2.add(new Button("D"));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setLabel("Da Click!");
            }
        });
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        f.setLayout(new GridLayout(4,1));
        f.add(label);
        f.add(button);
        f.add(row1);
        f.add(row2);
        f.setSize(400, 300);
        f.setVisible(true);
    }
}
