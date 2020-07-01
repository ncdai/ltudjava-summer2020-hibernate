package vn.name.ChanhDai.QuanLySinhVien.utils;

import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/24/20 - 2:56 PM
 * @description
 */
public class CSVUtils {
    public static List<String[]> reader(String file) {
        List<String[]> data = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static SinhVien parseSinhVien(String[] item) {
        if (item.length < 5) return null;

        String maLop = item[0];
        String maSinhVien = item[1];
        String hoTen = item[2];
        String gioiTinh = item[3];
        String cmnd = item[4];

        SinhVien sv = new SinhVien();
        sv.setMaLop(maLop);
        sv.setMaSinhVien(maSinhVien);
        sv.setHoTen(hoTen);
        sv.setGioiTinh(gioiTinh);
        sv.setCmnd(cmnd);
        sv.setMatKhau(maSinhVien);

        return sv;
    }
}
