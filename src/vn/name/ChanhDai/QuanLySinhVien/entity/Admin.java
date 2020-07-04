package vn.name.ChanhDai.QuanLySinhVien.entity;

import java.util.Objects;

/**
 * vn.name.ChanhDai.QuanLySinhVien.entity
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/3/20 - 11:00 PM
 * @description
 */
public class Admin {
    private String tenDangNhap;
    private String matKhau;
    private String hoTen;

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(tenDangNhap, admin.tenDangNhap) &&
            Objects.equals(matKhau, admin.matKhau) &&
            Objects.equals(hoTen, admin.hoTen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenDangNhap, matKhau, hoTen);
    }
}
