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

    public static List<LopOfMon> getList() {
        // language=HQL
        String hql = "select lom from LopOfMon lom";
        Map<String, String> params = new HashMap<>();
        return HibernateUtils.queryList(LopOfMon.class, hql, params);
    }

    public static boolean create(LopOfMon item) {
        if (SinhVienDAO.getSingle(item.getSinhVien().getMaSinhVien()) == null) {
            System.out.println("SinhVien[" + item.getSinhVien().getMaSinhVien() + "] khong ton tai!");
            return false;
        }

        if (LopOfMonDAO.getSingleByMaSinhVienAndMaMon(item.getSinhVien().getMaSinhVien(), item.getMon().getMaMon()) != null) {
            // Da ton tai SinhVien[maSinhVien] hoc Mon[maMon]
            System.out.println("Da ton tai SinhVien[" + item.getSinhVien().getMaSinhVien() + "] hoc Mon[" + item.getMon().getMaMon() + "]");
            return false;
        }

        if (ThoiKhoaBieuDAO.getByMaMonAndMaLop(item.getMon().getMaMon(), item.getMaLop()) == null) {
            System.out.println("Lop[" + item.getMaLop() + "] - Mon[" + item.getMon().getMaMon() + " - " + item.getMon().getTenMon() + "] khong ton tai!");
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

    public static List<LopOfMon> getListByMaMon(String maMon) {
        // language=HQL
        String hql = "select lom from LopOfMon lom where lom.mon.maMon = :maMon";

        Map<String, String> params = new HashMap<>();
        params.put("maMon", maMon);

        return HibernateUtils.queryList(LopOfMon.class, hql, params);
    }

    public static List<LopOfMon> getListByMaSinhVien(String maSinhVien) {
        // language=HQL
        String hql = "select lom from LopOfMon lom where lom.sinhVien.maSinhVien = :maSinhVien";

        Map<String, String> params = new HashMap<>();
        params.put("maSinhVien", maSinhVien);

        return HibernateUtils.queryList(LopOfMon.class, hql, params);
    }

    public static boolean update(LopOfMon lopOfMon) {
        LopOfMon check = LopOfMonDAO.getSingle(lopOfMon.getId());

        if (check == null) {
            // Khong ton tai LopOfMon
            return false;
        }

        lopOfMon.setId(check.getId());

        return HibernateUtils.updateRow(lopOfMon);
    }

    public static boolean updateByMaSinhVienAndMaMon(LopOfMon data) {
        LopOfMon lopOfMon = getSingleByMaSinhVienAndMaMon(data.getSinhVien().getMaSinhVien(), data.getMon().getMaMon());
        if (lopOfMon != null) {
            data.setId(lopOfMon.getId());
            return HibernateUtils.updateRow(data);
        }

        return false;
    }

//    public static boolean updateByMaSinhVienAndMaMon(LopOfMon data, boolean res) {
//        LopOfMon lopOfMon = getSingleByMaSinhVienAndMaMon(data.getSinhVien().getMaSinhVien(), data.getMon().getMaMon());
//        if (lopOfMon != null) {
//            data.setId(lopOfMon.getId());
//            return update(data);
//        }
//
//        return false;
//    }

    public static boolean delete(int id) {
        LopOfMon lopOfMon = LopOfMonDAO.getSingle(id);
        if (lopOfMon == null) {
            return false;
        }

        return HibernateUtils.deleteRow(lopOfMon);
    }

    public static boolean deleteByMaSinhVienAndMaMon(LopOfMon data) {
        LopOfMon lopOfMon = getSingleByMaSinhVienAndMaMon(data.getSinhVien().getMaSinhVien(), data.getMon().getMaMon());
        if (lopOfMon != null) {
            return HibernateUtils.deleteRow(lopOfMon);
        }

        return false;
    }

    public static Map<String, Integer> getThongKe(String maLop, String maMon) {
        // language=HQL
        String hql = "select count(*) as soLuongSV, sum(case when (lom.diemTong is not null) and (lom.diemTong >= 5) then 1 else 0 end) as soLuongSVQuaMon, sum(case when (lom.diemTong is not null) and (lom.diemTong < 5) then 1 else 0 end) as soLuongRotMon from LopOfMon lom where lom.maLop = :maLop and lom.mon.maMon = :maMon";

        Map<String, String> params = new HashMap<>();
        params.put("maLop", maLop);
        params.put("maMon", maMon);

        Object[] data = HibernateUtils.querySingle(Object[].class, hql, params);

        Map<String, Integer> res = new HashMap<>();

        if (data != null) {
            int soLuongSV = Integer.parseInt(data[0].toString());
            int soLuongSVQuaMon = Integer.parseInt(data[1].toString());
            int soLuongSVRotMon = Integer.parseInt(data[2].toString());
            int soLuongSVKhongDiem = soLuongSV - (soLuongSVQuaMon + soLuongSVRotMon);

            res.put("soLuongSV", soLuongSV);
            res.put("soLuongSVQuaMon", soLuongSVQuaMon);
            res.put("soLuongSVRotMon", soLuongSVRotMon);
            res.put("soLuongSVKhongDiem", soLuongSVKhongDiem);

            return res;
        }

        return null;
    }
}
