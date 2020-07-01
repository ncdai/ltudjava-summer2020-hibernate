package vn.name.ChanhDai.QuanLySinhVien.utils;

import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;

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

//    public static SinhVien parseSinhVien(SimpleTableModel tableModel, int rowIndex) {
//        SinhVien sinhVien = new SinhVien();
//        sinhVien.setMaSinhVien(tableModel.getValueAt(rowIndex, 0).toString());
//        sinhVien.setHoTen(tableModel.getValueAt(rowIndex, 1).toString());
//        sinhVien.setGioiTinh(tableModel.getValueAt(rowIndex, 2).toString());
//        sinhVien.setCmnd(tableModel.getValueAt(rowIndex, 3).toString());
//        sinhVien.setMaLop(tableModel.getValueAt(rowIndex, 4).toString());
//
//        return sinhVien;
//    }

    public static Vector<String> toRow(SinhVien sinhVien) {
        Vector<String> newRow = new Vector<>();
        newRow.add(sinhVien.getMaSinhVien());
        newRow.add(sinhVien.getHoTen());
        newRow.add(sinhVien.getGioiTinh());
        newRow.add(sinhVien.getCmnd());
        newRow.add(sinhVien.getMaLop());
        return newRow;
    }
}
