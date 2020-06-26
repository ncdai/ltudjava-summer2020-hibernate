package vn.edu.hcmus.fit.sv18120113.QuanLySinhVien;

import org.hibernate.HibernateException;
import org.hibernate.Session;

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

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            sv = session.get(SinhVien.class, maSinhVien);
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return sv;
    }

    public static boolean create(SinhVien sv) {
        return HibernateUtil.insertRow(sv);
    }

    public static boolean update(SinhVien sinhVien) {
        if (HibernateUtil.getRow(SinhVien.class, sinhVien.getMaSinhVien()) == null) {
            return false;
        }

        return HibernateUtil.updateRow(sinhVien);
    }

    public static boolean delete(String maSinhVien) {
        SinhVien sinhVien = HibernateUtil.getRow(SinhVien.class, maSinhVien);
        if (sinhVien == null) {
            return false;
        }

        return HibernateUtil.deleteRow(sinhVien);
    }

    public static List<SinhVien> getList() {
        // language=HQL
        String hql = "select sv from SinhVien sv";
        Map<String, String> params = new HashMap<>();

        return HibernateUtil.queryList(SinhVien.class, hql, params);
    }

    public static List<SinhVien> getListByMaLop(String maLop) {
        // language=HQL
        String hql = "select sv from SinhVien sv where sv.maLop = :maLop";

        Map<String, String> params = new HashMap<>();
        params.put("maLop", maLop);

        return HibernateUtil.queryList(SinhVien.class, hql, params);
    }

    public static boolean login(String maSinhVien, String matKhau) {
        // language=HQL
        String hql = "select sv from SinhVien sv where sv.maSinhVien = :maSinhVien and sv.matKhau = :matKhau";

        Map<String, String> params = new HashMap<>();
        params.put("maSinhVien", maSinhVien);
        params.put("matKhau", matKhau);

        return HibernateUtil.querySingle(SinhVien.class, hql, params) != null;
    }
}
