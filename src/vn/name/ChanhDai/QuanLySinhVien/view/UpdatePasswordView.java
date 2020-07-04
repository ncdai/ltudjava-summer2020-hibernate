package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.utils.ViewUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * vn.name.ChanhDai.QuanLySinhVien.view
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/4/20 - 7:24 AM
 * @description
 */
public abstract class UpdatePasswordView {
    JFrame frame;
    JPasswordField passwordFieldMatKhauHienTai;
    JPasswordField passwordFieldMatKhauMoi;
    JButton buttonSubmit;

    public UpdatePasswordView() {
        createUI();
    }

    void createUI() {
        frame = new JFrame();
        frame.setTitle("Đổi Mật Khẩu");
        frame.setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(32, 64, 32, 64));
        form.setBackground(Color.WHITE);

        JLabel labelTitle = new JLabel("Đổi Mật Khẩu");
        labelTitle.setFont(new Font("", Font.BOLD, 20));
        form.add(labelTitle, ViewUtils.createFormConstraints(0, 0, 2));

        passwordFieldMatKhauHienTai = new JPasswordField(10);
        passwordFieldMatKhauHienTai.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Pressed Enter -> Focus passwordFieldMatKhauMoi
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    passwordFieldMatKhauMoi.requestFocus();
                }
            }
        });
        form.add(new JLabel("Mật khẩu hiện tại"), ViewUtils.createFormConstraints(0, 1, 1, 8, 8, 8, 0));
        form.add(passwordFieldMatKhauHienTai, ViewUtils.createFormConstraints(1, 1, 1, 8, 0));

        passwordFieldMatKhauMoi = new JPasswordField();
        passwordFieldMatKhauMoi.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Pressed Enter -> Submit
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    buttonSubmit.doClick();
                }
            }
        });

        form.add(new JLabel("Mật khẩu mới"), ViewUtils.createFormConstraints(0, 2, 1));
        form.add(passwordFieldMatKhauMoi, ViewUtils.createFormConstraints(1, 2, 1));

        buttonSubmit = new JButton("Đổi Mật Khẩu");
        buttonSubmit.addActionListener(e -> {
            String matKhauHienTai = String.valueOf(passwordFieldMatKhauHienTai.getPassword());
            String matKhauMoi = String.valueOf(passwordFieldMatKhauMoi.getPassword());

            if (matKhauHienTai.equals("") || matKhauMoi.equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            submit(matKhauHienTai, matKhauMoi);
        });
        form.add(buttonSubmit, ViewUtils.createFormConstraints(0, 3, 2, 8, 0, 0, 0));

        JPanel temp = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        temp.setBorder(BorderFactory.createEmptyBorder(64, 64, 64, 64));
        temp.add(form);

        frame.add(temp, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public abstract void submit(String matKhauHienTai, String matKhauMoi);
}
