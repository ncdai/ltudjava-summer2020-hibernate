package vn.name.ChanhDai.QuanLySinhVien.utils;

import vn.name.ChanhDai.QuanLySinhVien.entity.LopOfMon;
import vn.name.ChanhDai.QuanLySinhVien.entity.Mon;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;
import vn.name.ChanhDai.QuanLySinhVien.entity.ThoiKhoaBieu;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Vector;

/**
 * vn.name.ChanhDai.QuanLySinhVien.utils
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/1/20 - 9:01 PM
 * @description
 */
public class TableUtils {
    public static SinhVien parseSinhVien(Vector<String> row) {
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSinhVien(row.get(0));
        sinhVien.setHoTen(row.get(1));
        sinhVien.setGioiTinh(row.get(2));
        sinhVien.setCmnd(row.get(3));
        sinhVien.setMaLop(row.get(4));

        return sinhVien;
    }

    public static ThoiKhoaBieu parseThoiKhoaBieu(Vector<String> row) {
        // maLop, maMon, tenMon, phongHoc

        Mon mon = new Mon();
        mon.setMaMon(row.get(1));
        mon.setTenMon(row.get(2));

        ThoiKhoaBieu tkb = new ThoiKhoaBieu();
        tkb.setMaLop(row.get(0));
        tkb.setMon(mon);
        tkb.setPhongHoc(row.get(3));

        return tkb;
    }

    public static Double parseDouble(String string) {
        return !string.equals("") ? Double.parseDouble(string) : null;
    }

    public static String toString(Double number) {
        return number != null ? number.toString() : "";
    }

    public static LopOfMon parseLopOfMon(Vector<String> row) {
        // maLop, maMon, tenMon, maSinhVien, hoTen, gioiTinh

        String maLop = row.get(0);
        String maMon = row.get(1);
        String maSinhVien = row.get(3);
        String hoTen = row.get(4);

        Double diemGK = parseDouble(row.get(6));
        Double diemCK = parseDouble(row.get(7));
        Double diemKhac = parseDouble(row.get(8));
        Double diemTong = parseDouble(row.get(9));

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

    public static Vector<String> toRow(SinhVien sinhVien) {
        Vector<String> newRow = new Vector<>();
        newRow.add(sinhVien.getMaSinhVien());
        newRow.add(sinhVien.getHoTen());
        newRow.add(sinhVien.getGioiTinh());
        newRow.add(sinhVien.getCmnd());
        newRow.add(sinhVien.getMaLop());
        return newRow;
    }

    public static Vector<String> toRow(ThoiKhoaBieu thoiKhoaBieu) {
        Vector<String> newRow = new Vector<>();
        newRow.add(thoiKhoaBieu.getMaLop());
        newRow.add(thoiKhoaBieu.getMon().getMaMon());
        newRow.add(thoiKhoaBieu.getMon().getTenMon());
        newRow.add(thoiKhoaBieu.getPhongHoc());

        return newRow;
    }

    public static Vector<String> toRow(LopOfMon lopOfMon) {
        Vector<String> row = new Vector<>();

        row.add(lopOfMon.getMaLop());
        row.add(lopOfMon.getMon().getMaMon());
        row.add(lopOfMon.getMon().getTenMon());
        row.add(lopOfMon.getSinhVien().getMaSinhVien());
        row.add(lopOfMon.getSinhVien().getHoTen());
        row.add(lopOfMon.getSinhVien().getGioiTinh());
        row.add(lopOfMon.getDiemGK() != null ? lopOfMon.getDiemGK().toString() : "");
        row.add(lopOfMon.getDiemCK() != null ? lopOfMon.getDiemCK().toString() : "");
        row.add(lopOfMon.getDiemKhac() != null ? lopOfMon.getDiemKhac().toString() : "");
        row.add(lopOfMon.getDiemTong() != null ? lopOfMon.getDiemTong().toString() : "");
        row.add(lopOfMon.getDiemTong() != null ? (lopOfMon.getDiemTong() >= 5 ? "Đậu" : "Rớt") : "");

        return row;
    }
}
