package vn.edu.hcmus.fit.sv18120113.QuanLySinhVien;

import java.util.Set;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/25/20 - 9:52 AM
 * @description
 */
public class SinhVien {
    private String maSinhVien;
    private String hoTen;
    private String gioiTinh;
    private String cmnd;
    private String matKhau;
    private String maLop;
    private Set<LopOfMon> danhSachLop;

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public Set<LopOfMon> getDanhSachLop() {
        return danhSachLop;
    }

    public void setDanhSachLop(Set<LopOfMon> danhSachLop) {
        this.danhSachLop = danhSachLop;
    }
}
