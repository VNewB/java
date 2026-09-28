package buoi3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class bai1 extends JFrame {

    JTextField txtA = new JTextField(5);
    JTextField txtB = new JTextField(5);
    JTextField result = new JTextField(10);
    JButton addBtn = new JButton("+");
    JButton minusBtn = new JButton("-");
    JButton dupBtn = new JButton("*");
    JButton divBtn = new JButton("/");

    JLabel error = new JLabel();

    JPanel mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints mainBag = new GridBagConstraints();



    public bai1() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int temp_result = JOptionPane.showConfirmDialog(null, "Exit?", "exit", JOptionPane.YES_NO_OPTION);
                if (temp_result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        result.setEditable(false);

        setTitle("Cong tru nhan chia");
        setSize(320,240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel ab = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ab.add(new JLabel("a = "));
        ab.add(txtA);
        ab.add(new JLabel("b = "));
        ab.add(txtB);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btns.add(addBtn);
        btns.add(minusBtn);
        btns.add(dupBtn);
        btns.add(divBtn);

        JPanel ress = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ress.add(new JLabel("Ket qua "));
        ress.add(result);

        mainBag.gridwidth = 4;
        mainBag.gridy = 1;
        mainPanel.add(ab, mainBag);
        mainBag.gridy = 2;
        mainBag.gridx = 1;
        mainPanel.add(ress, mainBag);
        mainBag.gridy = 3;
        mainBag.gridx = 0;
        mainPanel.add(btns, mainBag);


        add(mainPanel);
        // Muc 1
        addBtn1();
        minusBtn1();
        dupBtn1();
        divBtn1();
        // Muc 2
        txtACheckNumber();
        txtBCheckNumber();
    }

    void setError(String tooltip) {
        mainPanel.remove(error);
        mainBag.gridy = 0;
        mainBag.gridx = 0;
        mainPanel.add(error, mainBag);
        error.setToolTipText(tooltip);
        error.setVisible(true);
    }

    boolean XuLyBtn() {
        error.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
        error.setVisible(false);
        if (txtA.getText().trim().isEmpty()) {
            setError("Khong duoc de trong txtA");
            JOptionPane.showMessageDialog(null, "du lieu nhap vao khong phu hop!");
            return false;
        }
        else if (txtB.getText().trim().isEmpty()) {
            setError("Khong duoc de trong txtB");
            JOptionPane.showMessageDialog(null, "du lieu nhap vao khong phu hop!");
            return false;
        }
        return true;
    }

    void addBtn1() {
        error.setVisible(false);
        int temp_result;
        addBtn.addActionListener(e -> {
            if (XuLyBtn()) {
               try {
                   result.setText(String.valueOf(Integer.parseInt(txtA.getText().trim()) + Integer.parseInt(txtB.getText().trim())));
               }
               catch (NumberFormatException e1) {
                   setError("Loi format");
               }
            }
        });
    }

    void minusBtn1() {
        error.setVisible(false);
        int temp_result;
        minusBtn.addActionListener(e -> {
            if (XuLyBtn()) {
                try {
                    result.setText(String.valueOf(Integer.parseInt(txtA.getText().trim()) - Integer.parseInt(txtB.getText().trim())));
                }
                catch (NumberFormatException e1) {
                    setError("Loi format");
                }
            }
        });
    }

    void dupBtn1() {
        error.setVisible(false);
        int temp_result;
        dupBtn.addActionListener(e -> {
            if (XuLyBtn()) {
                try {
                    result.setText(String.valueOf(Integer.parseInt(txtA.getText().trim()) * Integer.parseInt(txtB.getText().trim())));
                }
                catch (NumberFormatException e1) {
                    setError("Loi format");
                }
            }
        });
    }
    void divBtn1() {
        error.setVisible(false);
        int temp_result;
        divBtn.addActionListener(e -> {
            if (XuLyBtn()) {
                try {
                    result.setText(String.valueOf(Integer.parseInt(txtA.getText().trim()) / Integer.parseInt(txtB.getText().trim())));
                }
                catch (NumberFormatException e1) {
                    setError("Loi format");
                }
            }
        });
    }

    void txtACheckNumber() {
        txtA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }
    void txtBCheckNumber() {
        txtB.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            bai1 frame = new bai1();
            frame.setVisible(true);
        });
    }
}
