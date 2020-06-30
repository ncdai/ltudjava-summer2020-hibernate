package vn.name.ChanhDai.QuanLySinhVien.dao;

import vn.name.ChanhDai.QuanLySinhVien.entity.Mon;
import vn.name.ChanhDai.QuanLySinhVien.utils.HibernateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/26/20 - 1:04 PM
 * @description
 */
public class MonDAO {
    public static List<Mon> getList() {
        // language=HQL
        String hql = "select mon from Mon mon";
        Map<String, String> params = new HashMap<>();

        return HibernateUtils.queryList(Mon.class, hql, params);
    }
}
