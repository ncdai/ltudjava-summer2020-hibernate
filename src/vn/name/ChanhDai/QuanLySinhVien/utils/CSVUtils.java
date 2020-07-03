package vn.name.ChanhDai.QuanLySinhVien.utils;

import vn.name.ChanhDai.QuanLySinhVien.entity.LopOfMon;
import vn.name.ChanhDai.QuanLySinhVien.entity.Mon;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;
import vn.name.ChanhDai.QuanLySinhVien.entity.ThoiKhoaBieu;

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

    public static ThoiKhoaBieu parseThoiKhoaBieu(String[] item) {
        if (item.length < 4) return null;

        Mon mon = new Mon();
        mon.setMaMon(item[1]);
        mon.setTenMon(item[2]);

        ThoiKhoaBieu tkb = new ThoiKhoaBieu();
        tkb.setMaLop( item[0]);
        tkb.setMon(mon);
        tkb.setPhongHoc(item[3]);

        return tkb;
    }

    public static LopOfMon parseLopOfMon(String[] item) {
        if (item.length < 8) return null;

        String maLop = item[0];
        String maMon = item[1];
        String maSinhVien = item[2];
        String hoTen = item[3];
        Double diemGK = Double.parseDouble(item[4]);
        Double diemCK = Double.parseDouble(item[5]);
        Double diemKhac = Double.parseDouble(item[6]);
        Double diemTong = Double.parseDouble(item[7]);

        LopOfMon lopOfMon = new LopOfMon(
            maLop,
            maMon,
            maSinhVien,
            diemGK,
            diemCK,
            diemKhac,
            diemTong
        );

        lopOfMon.getSinhVien().setHoTen(hoTen);

        return lopOfMon;
    }
}
