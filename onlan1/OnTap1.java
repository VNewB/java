package onlan1;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OnTap1 {
    public static void main(String[] args) {
        Frame frame = new Frame("");
        Label label = new Label("Máy tính lắp ráp");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        Font font = new Font("Times New Roman", Font.BOLD, 20);
        Font font2 = new Font("Times New Roman", Font.BOLD, 13);
        label.setFont(font);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        Panel NhapMay = new Panel(new GridLayout(4,2));
        NhapMay.add(new Label("Mã máy: ")).setFont(font2);
        NhapMay.add(new TextField(10));
        NhapMay.add(new Label("Tên máy: ")).setFont(font2);
        NhapMay.add(new TextField(10));
        NhapMay.add(new Label("Đơn giá: ")).setFont(font2);
        NhapMay.add(new TextField(10));
        NhapMay.add(new Label("Số lượng: ")).setFont(font2);
        NhapMay.add(new TextField(10));

        Panel LoaiMay = new Panel(new GridLayout(3,1));
        LoaiMay.add(new Label("Loại máy:"));
        LoaiMay.add(new Checkbox("Lắp ráp"));
        LoaiMay.add(new Checkbox("Nhập khẩu"));

        Panel PhuKien = new Panel(new GridLayout(4,1));
        PhuKien.add(new Label("Phụ kiện:"));
        PhuKien.add(new Checkbox("VGA 2G"));
        PhuKien.add(new Checkbox("DVD"));
        PhuKien.add(new Checkbox("LOA"));

        Panel NutNhapMay = new Panel(new FlowLayout());
        NutNhapMay.add(new Button("Nhập máy"));
        NutNhapMay.add(new Button("Nhập mới"));

        Panel GiaTri = new Panel(new FlowLayout());
        GiaTri.add(new Label("Giá trị: "));
        GiaTri.add(new TextField(10));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 10;
        frame.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 4;
        frame.add(NhapMay, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        frame.add(LoaiMay, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 4;
        frame.add(PhuKien, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        frame.add(NutNhapMay, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        frame.add(GiaTri, gbc);

        frame.setSize(640,480);
        frame.setVisible(true);
    }
}
