package buoi3;

import org.w3c.dom.Text;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class baimau extends JFrame {
    JTextField yourNameIn = new JTextField(20);
    JTextField yearBirthIn = new JTextField(20);
    JButton show = new JButton("Show");
    JButton clear = new JButton("Clear");
    JButton exit = new JButton("Exit");
    JLabel lblError = new JLabel();
    JPanel Input = new JPanel(new GridBagLayout());
    GridBagConstraints inputBag = new GridBagConstraints();


    public baimau() {
        lblError.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
        lblError.setVisible(false);

        setTitle("");
        setSize(360, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Thêm component ở đây

        inputBag.anchor = GridBagConstraints.CENTER;

        JPanel Name = new JPanel(new FlowLayout());
        Name.add(new JLabel("Your name: "));
        Name.add(yourNameIn);

        JPanel Birth = new JPanel(new FlowLayout());
        Birth.add(new JLabel("Year of birth: "));
        Birth.add(yearBirthIn);

        JPanel Buttons = new JPanel(new FlowLayout());
        Buttons.add(show);
        Buttons.add(clear);
        Buttons.add(exit);

        inputBag.gridy = 0;
        Input.add(Name, inputBag);
        inputBag.gridy = 1;
        Input.add(Birth, inputBag);
        inputBag.gridy = 2;
        Input.add(Buttons, inputBag);

        add(Input);
        output();
    }

    void output() {
        show.addActionListener(showBtn -> {
            if (yourNameIn.getText().trim().isEmpty()) {
                lblError.setVisible(false);
                Input.remove(lblError);
                inputBag.gridy = 0;
                Input.add(lblError, inputBag);
                lblError.setVisible(true);
                lblError.setToolTipText("khong duoc de trong ten!");
            }
            else if (yearBirthIn.getText().isEmpty()) {
                lblError.setVisible(false);
                Input.remove(lblError);
                inputBag.gridy = 1;
                Input.add(lblError, inputBag);
                lblError.setVisible(true);
                lblError.setToolTipText("khong duoc de trong nam sinh!");
            }
            else {
                String name = yourNameIn.getText().trim();
                lblError.setVisible(false);
                Input.remove(lblError);
                try {
                    int age = Integer.parseInt(yearBirthIn.getText().trim());
                    JOptionPane.showMessageDialog(null, "My name is: " + name + "\n" + "Age: " + (LocalDate.now().getYear() - age));
                }
                catch (NumberFormatException yearOfBirthError) {
                    inputBag.gridy = 1;
                    Input.add(lblError, inputBag);
                    lblError.setVisible(true);
                    lblError.setToolTipText("vui long nhap so nguyen hop le!");
                }
            }
        });
        clear.addActionListener(cleanBtn -> {
            yourNameIn.setText("");
            yearBirthIn.setText("");
            yourNameIn.requestFocusInWindow();
        });
        exit.addActionListener(exitBtn -> {
            int result = JOptionPane.showConfirmDialog(null, "ban chac chan muon thoat khong?","Exit",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            baimau frame = new baimau();
            frame.setVisible(true);
        });
    }
}