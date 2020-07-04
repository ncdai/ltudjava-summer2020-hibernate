package vn.name.ChanhDai.QuanLySinhVien.dao;

import vn.name.ChanhDai.QuanLySinhVien.entity.Admin;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;
import vn.name.ChanhDai.QuanLySinhVien.utils.HibernateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * vn.name.ChanhDai.QuanLySinhVien.dao
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/3/20 - 11:01 PM
 * @description
 */
public class AdminDAO {
    public static Admin login(String tenDangNhap, String matKhau) {
        // language=HQL
        String hql = "select ad from Admin ad where ad.tenDangNhap = :tenDangNhap and ad.matKhau = :matKhau";

        Map<String, String> params = new HashMap<>();
        params.put("tenDangNhap", tenDangNhap);
        params.put("matKhau", matKhau);

        return HibernateUtils.querySingle(Admin.class, hql, params);
    }

    public static boolean updatePassword(String tenDangNhap, String matKhauHienTai, String matKhauMoi) {
        Admin user = AdminDAO.login(tenDangNhap, matKhauHienTai);
        if (user == null) {
            return false;
        }

        user.setMatKhau(matKhauMoi);
        return HibernateUtils.updateRow(user);
    }
}
