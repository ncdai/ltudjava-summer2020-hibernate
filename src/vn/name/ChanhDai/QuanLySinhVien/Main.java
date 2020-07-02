package vn.name.ChanhDai.QuanLySinhVien;

import vn.name.ChanhDai.QuanLySinhVien.view.HomeView;
import vn.name.ChanhDai.QuanLySinhVien.view.SinhVienView;
import vn.name.ChanhDai.QuanLySinhVien.view.ThoiKhoaBieuView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }

        EventQueue.invokeLater(() -> {
//                new HomeView();
//            new SinhVienView();
            new ThoiKhoaBieuView().setVisible(true);
        });
    }
}
