package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.utils.ViewUtils;

import javax.swing.*;
import java.awt.*;

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
        frame.setTitle("Đổi mật khẩu");

        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(32, 64, 32, 64));

        JLabel labelTitle = new JLabel("Đổi mật khẩu");
        labelTitle.setFont(new Font("", Font.BOLD, 20));
        form.add(labelTitle, ViewUtils.createFormConstraints(0, 0, 2));

        passwordFieldMatKhauHienTai = new JPasswordField(10);
        form.add(new JLabel("Mật khẩu hiện tại"), ViewUtils.createFormConstraints(0, 1, 1, 8, 8, 8, 0));
        form.add(passwordFieldMatKhauHienTai, ViewUtils.createFormConstraints(1, 1, 1, 8, 0));

        passwordFieldMatKhauMoi = new JPasswordField();
        form.add(new JLabel("Mật khẩu mới"), ViewUtils.createFormConstraints(0, 2, 1));
        form.add(passwordFieldMatKhauMoi, ViewUtils.createFormConstraints(1, 2, 1));

        buttonSubmit = new JButton("Đổi mật khẩu");
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

        frame.getContentPane().add(form);

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public abstract void submit(String matKhauHienTai, String matKhauMoi);
}
