package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.dao.AdminDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.Admin;
import vn.name.ChanhDai.QuanLySinhVien.utils.ViewUtils;

import javax.swing.*;
import java.awt.*;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien.view
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/30/20 - 9:50 PM
 * @description
 */
public class GiaoVuHomeView {
    JFrame frame;

    Admin admin;
    JLabel labelUser;

    SinhVienView sinhVienView;
    ThoiKhoaBieuView thoiKhoaBieuView;
    LopOfMonView lopOfMonView;
    LoginView loginView;
    UpdatePasswordView updatePasswordView;

    public GiaoVuHomeView(LoginView loginView) {
        sinhVienView = new SinhVienView();
        thoiKhoaBieuView = new ThoiKhoaBieuView();
        lopOfMonView = new LopOfMonView();

        this.loginView = loginView;
        this.updatePasswordView = new UpdatePasswordView() {
            @Override
            public void submit(String matKhauHienTai, String matKhauMoi) {
                boolean success = AdminDAO.updatePassword(admin.getTenDangNhap(), matKhauHienTai, matKhauMoi);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                    return;
                }

                JOptionPane.showMessageDialog(null, "Mật khẩu hiện tại không chính xác!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        createAndShowUI();
    }

    private void createAndShowUI() {
        frame = new JFrame();
        frame.setTitle("Quản Lý Sinh Viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        GridBagLayout menuPanelLayout = new GridBagLayout();
        menuPanel.setLayout(menuPanelLayout);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JButton sinhVienListButton = createButtonFeature("Sinh Viên", "assets/images/user-graduate-solid.png", Color.decode("#ff4757"));
        sinhVienListButton.addActionListener(e -> {
            sinhVienView.setVisible(true);
        });

        JButton thoiKhoaBieuButton = createButtonFeature("Thời Khóa Biểu", "assets/images/calendar-alt-solid.png", Color.decode("#2ed573"));
        thoiKhoaBieuButton.addActionListener(e -> {
            thoiKhoaBieuView.setVisible(true);
        });

        JButton lopOfMonButton = createButtonFeature("Danh Sách Lớp / Bảng Điểm", "assets/images/users-class.png", Color.decode("#0984e3"));
        lopOfMonButton.addActionListener(e -> {
            lopOfMonView.setVisible(true);
        });

        menuPanel.add(sinhVienListButton, ViewUtils.createFormConstraints(0, 0, 1));
        menuPanel.add(thoiKhoaBieuButton, ViewUtils.createFormConstraints(1, 0, 1, 0, 16, 0, 16));
        menuPanel.add(lopOfMonButton, ViewUtils.createFormConstraints(2, 0, 1));

        labelUser = new JLabel();
        JPanel panelHeader = createHeader("Quản Lý Sinh Viên", labelUser, frame, updatePasswordView, loginView);

        Container container = frame.getContentPane();
        container.add(panelHeader, BorderLayout.PAGE_START);
        container.add(menuPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public JButton createButtonFeature(String label, String icon, Color background) {
        JButton button = new JButton(label, ViewUtils.createImageIcon(icon));
        button.setPreferredSize(new Dimension(256, 256));
        button.setIconTextGap(16);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(null);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("", Font.BOLD, 14));
        button.setFocusPainted(false);

        return button;
    }

    public static JPanel createHeader(String headerTitle, JLabel labelUser, JFrame frame, UpdatePasswordView updatePasswordView, LoginView loginView) {
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panelHeader.setBackground(Color.WHITE);

        JLabel labelHeaderTitle = new JLabel(headerTitle);
        labelHeaderTitle.setFont(new Font("", Font.BOLD, 24));
        panelHeader.add(labelHeaderTitle);
        panelHeader.add(Box.createHorizontalGlue());

        labelUser.setText("Tài khoản ...");
        panelHeader.add(labelUser);
        panelHeader.add(Box.createRigidArea(new Dimension(8, 0)));

        JButton buttonUpdatePassword = new JButton("Đổi Mật Khẩu");
        buttonUpdatePassword.setBackground(Color.decode("#eeeeee"));
        buttonUpdatePassword.addActionListener(e -> {
            updatePasswordView.setVisible(true);
        });
        panelHeader.add(buttonUpdatePassword);
        panelHeader.add(Box.createRigidArea(new Dimension(8, 0)));

        JButton buttonLogout = new JButton("Đăng Xuất");
        buttonLogout.setBackground(Color.decode("#eeeeee"));
        buttonLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn chắn chắn muốn Đăng Xuất ?",
                "Đăng Xuất",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                frame.setVisible(false);
                loginView.setVisible(true);
            }
        });

        panelHeader.add(buttonLogout);

        return panelHeader;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;

        labelUser.setText("Hi, " + admin.getHoTen() + " (" + admin.getTenDangNhap() + ") !");
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
