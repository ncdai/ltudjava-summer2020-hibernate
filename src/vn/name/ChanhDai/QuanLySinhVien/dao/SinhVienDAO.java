package vn.name.ChanhDai.QuanLySinhVien.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;
import vn.name.ChanhDai.QuanLySinhVien.utils.HibernateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/25/20 - 1:05 PM
 * @description
 */
public class SinhVienDAO {
    public static SinhVien getSingle(String maSinhVien) {
        SinhVien sv = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            sv = session.get(SinhVien.class, maSinhVien);
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return sv;
    }

    public static boolean create(SinhVien sv) {
        return HibernateUtils.insertRow(sv);
    }

    public static boolean update(SinhVien sinhVien) {
        if (HibernateUtils.getRow(SinhVien.class, sinhVien.getMaSinhVien()) == null) {
            return false;
        }

        return HibernateUtils.updateRow(sinhVien);
    }

    public static boolean delete(String maSinhVien) {
        SinhVien sinhVien = HibernateUtils.getRow(SinhVien.class, maSinhVien);
        if (sinhVien == null) {
            return false;
        }

        return HibernateUtils.deleteRow(sinhVien);
    }

    public static List<SinhVien> getList() {
        // language=HQL
        String hql = "select sv from SinhVien sv";
        Map<String, String> params = new HashMap<>();

        return HibernateUtils.queryList(SinhVien.class, hql, params);
    }

    public static List<SinhVien> getListByMaLop(String maLop) {
        // language=HQL
        String hql = "select sv from SinhVien sv where sv.maLop = :maLop";

        Map<String, String> params = new HashMap<>();
        params.put("maLop", maLop);

        return HibernateUtils.queryList(SinhVien.class, hql, params);
    }

    public static boolean login(String maSinhVien, String matKhau) {
        // language=HQL
        String hql = "select sv from SinhVien sv where sv.maSinhVien = :maSinhVien and sv.matKhau = :matKhau";

        Map<String, String> params = new HashMap<>();
        params.put("maSinhVien", maSinhVien);
        params.put("matKhau", matKhau);

        return HibernateUtils.querySingle(SinhVien.class, hql, params) != null;
    }
}
