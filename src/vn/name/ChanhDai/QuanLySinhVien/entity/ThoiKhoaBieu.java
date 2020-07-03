package vn.name.ChanhDai.QuanLySinhVien.entity;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/25/20 - 3:04 PM
 * @description
 */
public class ThoiKhoaBieu {
    private int id;
    private String phongHoc;
    private String maLop;
    private Mon mon;

//    public ThoiKhoaBieu() {}
//
//    public ThoiKhoaBieu(String maMon, String tenMon) {
//        Mon mon = new Mon();
//        mon.setMaMon(maMon);
//        mon.setTenMon(tenMon);
//
//        this.setMon(mon);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public Mon getMon() {
        return mon;
    }

    public void setMon(Mon mon) {
        this.mon = mon;
    }
}
