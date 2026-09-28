package guitest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongTinCaNhan {
    private JTextField yourNameTextField;
    private JTextField yearOfBirthTextField;
    private JButton showButton;
    private JButton clearButton;
    private JButton exitButton;
    private JLabel yearOfBirthLabel;
    private JLabel yourNameLabel;
    private JPanel ButtonChoice;
    private JPanel mainPanel;

    public ThongTinCaNhan() {
        yourNameTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String name = yourNameTextField.getText().trim();
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("ErrorProvider");
                }
            }
        });
        yearOfBirthTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int year = Integer.parseInt(yearOfBirthTextField.getText().trim());
                }
                catch (NumberFormatException e) {
                    System.out.println("ErrorProvider");
                }
            }
        });
    }

    public static void main(String[] args) {
    }
}
