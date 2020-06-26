package vn.edu.hcmus.fit.sv18120113.QuanLySinhVien;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/25/20 - 4:28 PM
 * @description
 */
public class ThoiKhoaBieuDAO {
    public static ThoiKhoaBieu getByMaMonAndMaLop(String maMon, String maLop) {
        // language=HQL
        String hql = "select tkb from ThoiKhoaBieu tkb where tkb.mon.maMon = :maMon and tkb.maLop = :maLop";

        Map<String, String> params = new HashMap<>();
        params.put("maMon", maMon);
        params.put("maLop", maLop);

        return HibernateUtil.querySingle(ThoiKhoaBieu.class, hql, params);
    }

    public static List<ThoiKhoaBieu> getListByMaLop(String maLop) {
        // language=HQL
        String hql = "select tkb from ThoiKhoaBieu tkb where tkb.maLop = :maLop";

        Map<String, String> params = new HashMap<>();
        params.put("maLop", maLop);

        return HibernateUtil.queryList(ThoiKhoaBieu.class, hql, params);
    }

    public static boolean create(ThoiKhoaBieu tkb) {
        if (getByMaMonAndMaLop(tkb.getMon().getMaMon(), tkb.getMaLop()) != null) {
            // Da ton tai
            return false;
        }

        return HibernateUtil.insertRow(tkb);
    }
}
