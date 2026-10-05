package buoi3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class bai2 {
    static JFrame RegistrationFrame = new JFrame();

    static JLabel lblTitle = new JLabel("Đăng ký tài khoản");
    static JLabel lblUsername = new JLabel("Tên đăng nhập");
    static JLabel lblEmail = new JLabel("Địa chỉ email");
    static JLabel lblPassword = new JLabel("Mật khẩu");
    static JLabel lblCpassword = new JLabel("Xác nhận mật khẩu");

    static JTextField txtUsername = new JTextField(14);
    static JTextField txtEmail = new JTextField(14);
    static JTextField txtPassword = new JTextField(14);
    static JTextField txtCpassword = new JTextField(14);

    static JButton btnRegistration = new JButton("Đăng ký");

    static Font TitleFont = new Font("Times New Roman", Font.BOLD, 20);
    static Font RegularFont = new Font("Arial", Font.PLAIN, 13);


    static void GiaoDien() {

        RegistrationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int temp_result = JOptionPane.showConfirmDialog(null, "Exit?", "exit", JOptionPane.YES_NO_OPTION);
                if (temp_result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        RegistrationFrame.setTitle("Đăng ký tài khoản");
        RegistrationFrame.setSize(420,260);
        RegistrationFrame.setLocationRelativeTo(null);
        RegistrationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        RegistrationFrame.setLayout(new GridBagLayout());
        GridBagConstraints RFBag = new GridBagConstraints();
        RFBag.insets = new Insets(0,5,5,5);
        RFBag.anchor = GridBagConstraints.WEST;

        // Col 1
//        lblUsername.setFont(RegularFont);
//        lblEmail.setFont(RegularFont);
//        lblPassword.setFont(RegularFont);
//        lblCpassword.setFont(RegularFont);

        RFBag.gridx = 0;
        RFBag.gridy = 1;
        RegistrationFrame.add(lblUsername, RFBag);
        RFBag.gridy = 2;
        RegistrationFrame.add(lblEmail, RFBag);
        RFBag.gridy = 3;
        RegistrationFrame.add(lblPassword, RFBag);
        RFBag.gridy = 4;
        RegistrationFrame.add(lblCpassword, RFBag);

        // Col 2
        btnRegistration.setForeground(new Color(50,150,255));

        RFBag.gridx = 1;
        RFBag.gridy = 0;
        lblTitle.setFont(TitleFont);
        lblTitle.setForeground(new Color(50,150,255));
        RegistrationFrame.add(lblTitle, RFBag);
        RFBag.gridy = 1;
        RegistrationFrame.add(txtUsername, RFBag);
        RFBag.gridy = 2;
        RegistrationFrame.add(txtEmail, RFBag);
        RFBag.gridy = 3;
        RegistrationFrame.add(txtPassword, RFBag);
        RFBag.gridy = 4;
        RegistrationFrame.add(txtCpassword, RFBag);
        RFBag.gridy = 5;
        RegistrationFrame.add(btnRegistration, RFBag);

        // Col 3
        RFBag.gridx = 2;
        RFBag.gridy = 1;
        RegistrationFrame.add(new JLabel("(*)"), RFBag);
        RFBag.gridy = 2;
        RegistrationFrame.add(new JLabel("(*)"), RFBag);
        RFBag.gridy = 3;
        RegistrationFrame.add(new JLabel("(*)"), RFBag);

    }

    static void hienThiThongTin() {
        btnRegistration.addActionListener(ActionEvent -> {
            JOptionPane.showMessageDialog(null, txtUsername + "\n" + txtEmail + "\n" + txtPassword + "\n" + txtCpassword);
        });
    }
    public static void RegistrationForm() {
        GiaoDien();
        hienThiThongTin();
        SwingUtilities.invokeLater(() -> {
            RegistrationFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        RegistrationForm();
    }
}