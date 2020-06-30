package vn.name.ChanhDai.QuanLySinhVien.entity;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/25/20 - 3:04 PM
 * @description
 */
public class LopOfMon {
    private int id;
    private String maLop;
    private Double diemGK;
    private Double diemCK;
    private Double diemKhac;
    private Double diemTong;
    private SinhVien sinhVien;
    private Mon mon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public Double getDiemGK() {
        return diemGK;
    }

    public void setDiemGK(Double diemGk) {
        this.diemGK = diemGk;
    }

    public Double getDiemCK() {
        return diemCK;
    }

    public void setDiemCK(Double diemCk) {
        this.diemCK = diemCk;
    }

    public Double getDiemKhac() {
        return diemKhac;
    }

    public void setDiemKhac(Double diemKhac) {
        this.diemKhac = diemKhac;
    }

    public Double getDiemTong() {
        return diemTong;
    }

    public void setDiemTong(Double diemTong) {
        this.diemTong = diemTong;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public Mon getMon() {
        return mon;
    }

    public void setMon(Mon mon) {
        this.mon = mon;
    }
}
