package vn.name.ChanhDai.QuanLySinhVien;

import vn.name.ChanhDai.QuanLySinhVien.view.SinhVienView;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SinhVienView();
            }
        });
    }
}
