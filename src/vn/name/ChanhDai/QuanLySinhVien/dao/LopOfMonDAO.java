package vn.name.ChanhDai.QuanLySinhVien.dao;

import vn.name.ChanhDai.QuanLySinhVien.entity.LopOfMon;
import vn.name.ChanhDai.QuanLySinhVien.utils.HibernateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/25/20 - 4:35 PM
 * @description
 */
public class LopOfMonDAO {
    public static LopOfMon getSingle(int id) {
        return HibernateUtils.getRow(LopOfMon.class, id);
    }

    public static LopOfMon getSingleByMaSinhVienAndMaMon(String maSinhVien, String maMon) {
        // language=HQL
        String hql = "select lom from LopOfMon lom where lom.sinhVien.maSinhVien = :maSinhVien and lom.mon.maMon = :maMon";

        Map<String, String> params = new HashMap<>();
        params.put("maSinhVien", maSinhVien);
        params.put("maMon", maMon);

        return HibernateUtils.querySingle(LopOfMon.class, hql, params);
    }

    public static boolean create(LopOfMon item) {
        if (LopOfMonDAO.getSingleByMaSinhVienAndMaMon(item.getSinhVien().getMaSinhVien(), item.getMon().getMaMon()) != null) {
            // Da ton tai SinhVien[maSinhVien] hoc Mon[maMon]
            return false;
        }

        return HibernateUtils.insertRow(item);
    }

    public static List<LopOfMon> getListByMaLopAndMaMon(String maLop, String maMon) {
        // language=HQL
        String hql = "select lom from LopOfMon lom where lom.maLop = :maLop and lom.mon.maMon = :maMon";

        Map<String, String> params = new HashMap<>();
        params.put("maLop", maLop);
        params.put("maMon", maMon);

        return HibernateUtils.queryList(LopOfMon.class, hql, params);
    }

    public static boolean update(LopOfMon lopOfMon) {
        if (LopOfMonDAO.getSingle(lopOfMon.getId()) == null) {
            // Khong ton tai LopOfMon
            return false;
        }

        return HibernateUtils.updateRow(lopOfMon);
    }

    public static boolean delete(int id) {
        LopOfMon lopOfMon = LopOfMonDAO.getSingle(id);
        if (lopOfMon == null) {
            return false;
        }

        return HibernateUtils.deleteRow(lopOfMon);
    }
}