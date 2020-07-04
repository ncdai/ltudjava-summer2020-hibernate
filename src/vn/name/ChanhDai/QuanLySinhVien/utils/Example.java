package vn.name.ChanhDai.QuanLySinhVien.utils;

import vn.name.ChanhDai.QuanLySinhVien.dao.LopOfMonDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.MonDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.SinhVienDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.ThoiKhoaBieuDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.LopOfMon;
import vn.name.ChanhDai.QuanLySinhVien.entity.Mon;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;
import vn.name.ChanhDai.QuanLySinhVien.entity.ThoiKhoaBieu;

import java.util.List;

public class Example {
    public static void CSVReaderExample() {
        List<String[]> dsSinhVien = CSVUtils.reader("sinhVien_18HCB.csv");

        for (String[] item : dsSinhVien) {
            for (String value : item) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void importDanhSachLopExample() {
        List<String[]> danhSachLop = CSVUtils.reader("sinhVien_17HCB.csv");

        for (String[] item : danhSachLop) {
            SinhVien sv = CSVUtils.parseSinhVien(item);
            if (sv != null) {
                boolean res = SinhVienDAO.create(sv);
                if (res) {
                    System.out.println("Them SinhVien " + sv.getMaSinhVien() + " thanh cong!");
                } else {
                    System.out.println("Them SinhVien " + sv.getMaSinhVien() + " that bai!");
                }
            }
        }
    }

    public static void addSinhVienExample() {
        SinhVien sv = new SinhVien();

        sv.setMaSinhVien("18120113");
        sv.setMaLop("18CTT1");
        sv.setHoTen("Nguyễn Chánh Đại");
        sv.setGioiTinh("Nam");
        sv.setCmnd("092200002577");
        sv.setMatKhau("happy2code");

        boolean res = SinhVienDAO.create(sv);
        if (res) {
            System.out.println("addSinhVienExample : success");
        } else {
            System.out.println("addSinhVienExample : error");
        }
    }

    public static void importThoiKhoaBieuExample() {
        List<String[]> thoiKhoaBieu = CSVUtils.reader("data/thoiKhoaBieu_17HCB.csv");
        for (String[] item : thoiKhoaBieu) {
//            String maLop = item[0];
//            String maMon = item[1];
//            String tenMon = item[2];
//            String phongHoc = item[3];
//
//            Mon mon = new Mon();
//            mon.setMaMon(maMon);
//            mon.setTenMon(tenMon);
//
//            ThoiKhoaBieu tkb = new ThoiKhoaBieu();
//            tkb.setMaLop(maLop);
//            tkb.setMon(mon);
//            tkb.setPhongHoc(phongHoc);

            ThoiKhoaBieu tkb = CSVUtils.parseThoiKhoaBieu(item);

            boolean res = ThoiKhoaBieuDAO.create(tkb);
            if (res) {
                System.out.println("Them (" + tkb.getMaLop() + ", " + tkb.getMon().getMaMon() + ", " + tkb.getMon().getTenMon() + ", " + tkb.getPhongHoc() + ") thanh cong!");
            } else {
                System.out.println("Them (" + tkb.getMaLop() + ", " + tkb.getMon().getMaMon() + ", " + tkb.getMon().getTenMon() + ", " + tkb.getPhongHoc() + ") that bai!");
            }

//            // Mặc định tất cả SV của lớp đều học các môn trong TKB
//            List<SinhVien> danhSachLop = SinhVienDAO.getListByMaLop(tkb.getMaLop());
//            for (SinhVien sv : danhSachLop) {
//                LopOfMon lopOfMon = new LopOfMon();
//                lopOfMon.setMaLop(tkb.getMaLop());
//                lopOfMon.setMon(tkb.getMon());
//                lopOfMon.setSinhVien(sv);
//
//                res = LopOfMonDAO.create(lopOfMon);
//                if (res) {
//                    System.out.println("Them SinhVien(" + sv.getMaSinhVien() + ") vao Lop(" + tkb.getMaLop() + "-" + tkb.getMon().getMaMon() + ") thanh cong!");
//                } else {
//                    System.out.println("Them SinhVien(" + sv.getMaSinhVien() + ") vao Lop(" + tkb.getMaLop() + "-" + tkb.getMon().getMaMon() + ") that bai!");
//                }
//            }
        }
    }

    public static void deleteLopOfMonExample() {
        LopOfMon lopOfMon = LopOfMonDAO.getSingleByMaSinhVienAndMaMon("1842001", "CTT001");

        if (lopOfMon != null) {
            boolean success = LopOfMonDAO.delete(lopOfMon.getId());

            if (success) {
                System.out.println("deleteLopOfMonExample : success");
            } else {
                System.out.println("deleteLopOfMonExample : error");
            }
        } else {
            System.out.println("deleteLopOfMonExample : error");
        }
    }

    public static void addLopOfMonExample() {
        String maLop = "18HCB";

        Mon mon = new Mon();
        mon.setMaMon("CTT001");

        SinhVien sv = new SinhVien();
        sv.setMaSinhVien("1742005");

        LopOfMon lopOfMon = new LopOfMon();
        lopOfMon.setMaLop(maLop);
        lopOfMon.setMon(mon);
        lopOfMon.setSinhVien(sv);

        boolean res = LopOfMonDAO.create(lopOfMon);
        if (res) {
            System.out.println("Them SinhVien(" + sv.getMaSinhVien() + ") vao Lop(" + maLop + "-" + mon.getMaMon() + ") thanh cong!");
        } else {
            System.out.println("Them SinhVien(" + sv.getMaSinhVien() + ") vao Lop(" + maLop + "-" + mon.getMaMon() + ") that bai!");
        }
    }

    public static void getLopListExample() {
        String maLop = "17HCB";

        List<SinhVien> danhSachLop = SinhVienDAO.getListByMaLop(maLop);
        for (SinhVien sv : danhSachLop) {
            System.out.println("SinhVien(" + sv.getMaSinhVien() + ", " + sv.getHoTen() + ")");
        }
    }

    public static void getLopOfMonByLopAndMonExample() {
        String maLop = "18HCB";
        String maMon = "CTT001";
        List<LopOfMon> danhSachLop = LopOfMonDAO.getListByMaLopAndMaMon(maLop, maMon);

        for (LopOfMon lopOfMon : danhSachLop) {
            System.out.println("SinhVien(" + lopOfMon.getSinhVien().getMaSinhVien() + ", " + lopOfMon.getSinhVien().getHoTen() + ")");
            System.out.println(lopOfMon.getMaLop());
            System.out.println(lopOfMon.getMon().getMaMon());
            System.out.println(lopOfMon.getDiemGK());
            System.out.println(lopOfMon.getDiemCK());
            System.out.println(lopOfMon.getDiemKhac());
            System.out.println(lopOfMon.getDiemTong());
            System.out.println("---");
        }
    }

    public static void getTKBListByLopExample() {
        String maLop = "17HCB";
        List<ThoiKhoaBieu> tkbList = ThoiKhoaBieuDAO.getListByMaLop(maLop);
        for (ThoiKhoaBieu tkb : tkbList) {
            System.out.println(tkb.getMaLop() + " " + tkb.getMon().getTenMon() + " " + tkb.getPhongHoc());
        }
    }

    public static void importBangDiemExample() {
//        List<String[]> bangDiem = CSVUtils.reader("data/bangDiem.csv");
//        for (String[] item : bangDiem) {
//            String maLop = item[0];
//            String maMon = item[1];
//            String maSinhVien = item[2];
//            String hoTen = item[3];
//            Double diemGK = Double.parseDouble(item[4]);
//            Double diemCK = Double.parseDouble(item[5]);
//            Double diemKhac = Double.parseDouble(item[6]);
//            Double diemTong = Double.parseDouble(item[7]);
//
//            LopOfMon lopOfMon = LopOfMonDAO.getSingleByMaSinhVienAndMaMon(maSinhVien, maMon);
//
//            if (lopOfMon != null) {
//                lopOfMon.setDiemGK(diemGK);
//                lopOfMon.setDiemCK(diemCK);
//                lopOfMon.setDiemKhac(diemKhac);
//                lopOfMon.setDiemTong(diemTong);
//
//                boolean success = LopOfMonDAO.update(lopOfMon);
//                if (success) {
//                    System.out.println(lopOfMon.getMaLop());
//                    System.out.println(lopOfMon.getMon().getMaMon());
//                    System.out.println(lopOfMon.getSinhVien().getMaSinhVien());
//                    System.out.println(lopOfMon.getSinhVien().getHoTen());
//                    System.out.println(lopOfMon.getDiemGK());
//                    System.out.println(lopOfMon.getDiemCK());
//                    System.out.println(lopOfMon.getDiemKhac());
//                    System.out.println(lopOfMon.getDiemTong());
//                    if (lopOfMon.getDiemTong() > 5.0) {
//                        System.out.println("Dau!");
//                    } else {
//                        System.out.println("Rot!");
//                    }
//
//                    System.out.println("Them thanh cong!");
//                    System.out.println("---");
//                } else {
//                    System.out.println(maLop);
//                    System.out.println(maMon);
//                    System.out.println(maSinhVien);
//                    System.out.println(hoTen);
//                    System.out.println("Them that bai!");
//                    System.out.println("---");
//                }
//            } else {
//                System.out.println(maLop);
//                System.out.println(maMon);
//                System.out.println(maSinhVien);
//                System.out.println(hoTen);
//                System.out.println("Them that bai!");
//                System.out.println("---");
//            }
//        }
    }

    public static void updateLopOfMonExample() {
        int lopOfMonId = 11;
        LopOfMon lopOfMon = LopOfMonDAO.getSingle(lopOfMonId);

        if (lopOfMon != null) {
            System.out.println(lopOfMon.getMaLop());
            System.out.println(lopOfMon.getMon().getTenMon());
            System.out.println(lopOfMon.getSinhVien().getHoTen());

            lopOfMon.setDiemTong(10.0);
            boolean success = LopOfMonDAO.update(lopOfMon);
            if (success) {
                System.out.println("updateLopOfMonExample : success");
            } else {
                System.out.println("updateLopOfMonExample : error");
            }
        } else {
            System.out.println("updateLopOfMonExample : error");
        }
    }

    public static void getSinhVienExample() {
        String maSinhVien = "18120113";
        SinhVien sv = SinhVienDAO.getSingle(maSinhVien);
        if (sv == null) {
            System.out.println("getSinhVien : error");
            return;
        }

        System.out.println("getSinhVien : Success");
        System.out.println(sv.getMaSinhVien());
        System.out.println(sv.getHoTen());
        System.out.println();

        for (LopOfMon lopOfMon : sv.getDanhSachLop()) {
            System.out.println(lopOfMon.getMaLop() + " " + lopOfMon.getMon().getTenMon() + " " + lopOfMon.getDiemTong());
        }
    }

    public static void loginAsSinhVienExample() {
        String maSinhVien = "1742001";
        String matKhau = "1742001";

//        boolean success = SinhVienDAO.login(maSinhVien, matKhau);
//        if (success) {
//            System.out.println("loginAsSinhVienExample : success");
//        } else {
//            System.out.println("loginAsSinhVienExample : error");
//        }
    }

    public static void updateSinhVienExample() {
        String maSinhVien = "18120113";
        SinhVien sinhVien = SinhVienDAO.getSingle(maSinhVien);

        if (sinhVien != null) {
            sinhVien.setMatKhau("123456");
            boolean success = SinhVienDAO.update(sinhVien);
            if (success) {
                System.out.println("updateSinhVienExample : success");
            } else {
                System.out.println("updateSinhVienExample : error");
            }
        } else {
            System.out.println("updateSinhVienExample : error");
        }
    }

    public static void getSinhVienListExample() {
        List<SinhVien> list = SinhVienDAO.getList();

        for (SinhVien sv : list) {
            System.out.print(sv.getMaSinhVien());
            System.out.print(sv.getHoTen());
            System.out.println();

            for (LopOfMon lopOfMon : sv.getDanhSachLop()) {
                System.out.println(lopOfMon.getMaLop() + " " + lopOfMon.getMon().getTenMon() + " " + lopOfMon.getDiemTong());
            }

            System.out.println("---");
        }
    }

    public static void deleteSinhVienExample() {
        String maSinhVien = "18120113";
        boolean res = SinhVienDAO.delete(maSinhVien);
        if (res) {
            System.out.println("deleteSinhVienExample : success");
        } else {
            System.out.println("deleteSinhVienExample : error");
        }
    }

    public static void getMonListExample() {
        List<Mon> monList = MonDAO.getList();
        for (Mon mon : monList) {
            System.out.println(mon.getMaMon() + " " + mon.getTenMon());
        }
    }

    public static void main(String[] args) {
//        CSVReaderExample();

        // 1. Import CSV [Danh sách sinh viên]
//        importDanhSachLopExample();

        // 2. Thêm sinh viên vào 1 lớp
//        addSinhVienExample();

        // 3. Import CSV [Thời khóa biểu]
//        importThoiKhoaBieuExample();

        // 4.1 Xóa sinh viên khỏi lớp của 1 môn học
//        deleteLopOfMonExample();
        // 4.2 Thêm sinh viên vào lớp của 1 môn học
//        addLopOfMonExample();

        // 5.1 Xem danh sách lớp
//        getLopListExample();
        // 5.2 Xem danh sách lớp của môn
        // 8. Xem bảng điểm lớp của môn
//        getLopOfMonByLopAndMonExample();

        // 6. Xem thời khóa biểu của lớp
//        getTKBListByLopExample();

        // 7. Import CSV [Bảng điểm]
//        importBangDiemExample();

        // 8. Xem bảng điểm lớp của môn
        // Line 365

        // 9. Sửa điểm 1 sinh viên
//        updateLopOfMonExample();

        // 10. Chỉ xem được điểm của mình
//        getSinhVienExample();

        // 11. Login ...
//        loginAsSinhVienExample();

        // 12. Sửa thông tin sinh viên
//        updateSinhVienExample();

        // Draft
//        getSinhVienListExample();
//        deleteSinhVienExample();
//        getMonListExample();

//        List<String> list = SinhVienDAO.getLopList();
//        for (String item : list) {
//            System.out.println(item);
//        }

//        List<Mon> list = ThoiKhoaBieuDAO.getMonList();
//        for (Mon item : list) {
//            System.out.println(item.getMaMon() + " - " + item.getTenMon());
//        }
    }
}
