package vn.name.ChanhDai.QuanLySinhVien.view;

import javax.swing.*;
import java.awt.*;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien.view
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/30/20 - 9:50 PM
 * @description
 */
public class HomeView {
    SinhVienView sinhVienView;
    ThoiKhoaBieuView thoiKhoaBieuView;

    public HomeView() {
        sinhVienView = new SinhVienView();
        thoiKhoaBieuView = new ThoiKhoaBieuView();

        createAndShowUI();
    }

    private void createAndShowUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Quản Lý Sinh Viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menuPanel = new JPanel();
        BoxLayout menuPanelLayout = new BoxLayout(menuPanel, BoxLayout.Y_AXIS);

        JButton sinhVienListButton = new JButton("Danh sách sinh viên");
        sinhVienListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sinhVienListButton.addActionListener(e -> {
            sinhVienView.setVisible(true);
        });

        JButton thoiKhoaBieuButton = new JButton("Thời khóa biểu");
        thoiKhoaBieuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        thoiKhoaBieuButton.addActionListener(e -> {
            thoiKhoaBieuView.setVisible(true);
        });

        JButton lopOfMonButton = new JButton("Danh sách lớp");
        lopOfMonButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton diemButton = new JButton("Bảng điểm");
        diemButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.setLayout(menuPanelLayout);
        menuPanel.add(sinhVienListButton);
        menuPanel.add(thoiKhoaBieuButton);
        menuPanel.add(lopOfMonButton);
        menuPanel.add(diemButton);

        frame.getContentPane().add(menuPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
