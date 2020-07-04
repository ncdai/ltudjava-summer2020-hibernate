package vn.name.ChanhDai.QuanLySinhVien.dao;

import vn.name.ChanhDai.QuanLySinhVien.entity.Admin;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;
import vn.name.ChanhDai.QuanLySinhVien.utils.BcryptUtils;
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
        String hql = "select ad from Admin ad where ad.tenDangNhap = :tenDangNhap";

        Map<String, String> params = new HashMap<>();
        params.put("tenDangNhap", tenDangNhap);

        Admin admin = HibernateUtils.querySingle(Admin.class, hql, params);

        if (admin == null) return null;

        if (BcryptUtils.checkPassword(admin.getMatKhau(), matKhau)) return admin;

        return null;
    }

    public static boolean updatePassword(String tenDangNhap, String matKhauHienTai, String matKhauMoi) {
        Admin user = AdminDAO.login(tenDangNhap, matKhauHienTai);
        if (user == null) return false;

        user.setMatKhau(BcryptUtils.hashPassword(matKhauMoi));
        return HibernateUtils.updateRow(user);
    }
}
