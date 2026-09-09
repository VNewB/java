import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DemoFrame {
    public static void main (String[] args) {
        Frame frame = new Frame("Example on frame");
        Label label = new Label("This is label",Label.CENTER);
        frame.add(label,BorderLayout.CENTER);
        frame.setSize(500,500);
        frame.setVisible(true);
        System.exit(0);
    }
}
