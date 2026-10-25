package buoi3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

public class bai2 extends JFrame {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");
    private static final Color MAIN_COLOR = new Color(50, 150, 255);

    private final JTextField txtUsername = new JTextField(14);
    private final JTextField txtEmail = new JTextField(14);
    private final JPasswordField txtPassword = new JPasswordField(14);
    private final JPasswordField txtCpassword = new JPasswordField(14);
    private final JButton btnRegistration = new JButton("Đăng ký");

    private boolean showingEmailError = false; // tránh hiện thông báo lặp khi mất focus

    public bai2() {
        initUI();
        initEvents();
    }

    // ===================== GIAO DIỆN =====================
    private void initUI() {
        setTitle("Đăng ký tài khoản");
        setSize(420, 260);
        setLocationRelativeTo(null);
        // Không cho tự thoát, để windowClosing hỏi xác nhận
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;

        JLabel lblTitle = new JLabel("Đăng ký tài khoản");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblTitle.setForeground(MAIN_COLOR);
        c.gridx = 1; c.gridy = 0;
        add(lblTitle, c);

        addRow(1, "Tên đăng nhập", txtUsername, true);
        addRow(2, "Địa chỉ email", txtEmail, true);
        addRow(3, "Mật khẩu", txtPassword, true);
        addRow(4, "Xác nhận mật khẩu", txtCpassword, false);

        btnRegistration.setForeground(MAIN_COLOR);
        c.gridx = 1; c.gridy = 5;
        add(btnRegistration, c);
    }

    private void addRow(int row, String label, JComponent field, boolean required) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.gridy = row;

        c.gridx = 0;
        add(new JLabel(label), c);
        c.gridx = 1;
        add(field, c);
        if (required) {
            c.gridx = 2;
            add(new JLabel("(*)"), c);
        }
    }

    // ===================== SỰ KIỆN =====================
    private void initEvents() {
        // 1. Kiểm tra email khi rời khỏi textbox
        txtEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (e.isTemporary() || showingEmailError) return;
                String email = txtEmail.getText().trim();
                if (!email.isEmpty() && !isValidEmail(email)) {
                    showingEmailError = true;
                    JOptionPane.showMessageDialog(bai2.this,
                            "Địa chỉ email không đúng định dạng!\nVí dụ: ten@gmail.com",
                            "Lỗi email", JOptionPane.ERROR_MESSAGE);
                    showingEmailError = false;
                    txtEmail.requestFocusInWindow();
                }
            }
        });

        // 2. Nhấn nút Đăng ký hoặc Enter ở ô Xác nhận mật khẩu
        btnRegistration.addActionListener(e -> register());
        txtCpassword.addActionListener(e -> register());

        // 3. Hỏi xác nhận trước khi đóng form
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(bai2.this,
                        "Bạn có chắc chắn muốn thoát?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    // ===================== XỬ LÝ =====================
    private static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean showError(String message, JComponent focusTarget) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
        focusTarget.requestFocusInWindow();
        return false;
    }

    /** Kiểm tra dữ liệu bắt buộc (*) + định dạng email + mật khẩu khớp. */
    private boolean validateForm() {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String cpassword = new String(txtCpassword.getPassword());

        if (username.isEmpty())
            return showError("Vui lòng nhập Tên đăng nhập!", txtUsername);
        if (email.isEmpty())
            return showError("Vui lòng nhập Địa chỉ email!", txtEmail);
        if (!isValidEmail(email))
            return showError("Địa chỉ email không đúng định dạng!", txtEmail);
        if (password.isEmpty())
            return showError("Vui lòng nhập Mật khẩu!", txtPassword);
        if (!password.equals(cpassword))
            return showError("Xác nhận mật khẩu không khớp!", txtCpassword);
        return true;
    }

    private void register() {
        if (!validateForm()) return;

        String info = "Tên đăng nhập: " + txtUsername.getText().trim()
                + "\nEmail: " + txtEmail.getText().trim()
                + "\nMật khẩu: " + new String(txtPassword.getPassword())
                + "\nXác nhận mật khẩu: " + new String(txtCpassword.getPassword());
        JOptionPane.showMessageDialog(this, info, "Thông tin đăng ký",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new bai2().setVisible(true));
    }
}