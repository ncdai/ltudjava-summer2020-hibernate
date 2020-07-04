create table LopOfMon
(
    id         int auto_increment
        primary key,
    maLop      varchar(10) null,
    maMon      varchar(10) null,
    maSinhVien varchar(10) null,
    diemGK     double      null,
    diemCK     double      null,
    diemKhac   double      null,
    diemTong   double      null,
    constraint LopOfMon_Mon_maMon_fk
        foreign key (maMon) references Mon (maMon),
    constraint LopOfMon_SinhVien_maSinhVien_fk
        foreign key (maSinhVien) references SinhVien (maSinhVien)
);

INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (1, '17HCB', 'CTT011', '1742001', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (2, '17HCB', 'CTT011', '1742002', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (3, '17HCB', 'CTT011', '1742003', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (4, '17HCB', 'CTT011', '1742004', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (5, '17HCB', 'CTT011', '1742005', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (6, '17HCB', 'CTT011', '1742006', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (7, '17HCB', 'CTT012', '1742001', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (8, '17HCB', 'CTT012', '1742002', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (9, '17HCB', 'CTT012', '1742003', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (10, '17HCB', 'CTT012', '1742004', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (11, '17HCB', 'CTT012', '1742005', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (12, '17HCB', 'CTT012', '1742006', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (14, '18HCB', 'CTT001', '1842002', 4, 5, 6, 5);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (15, '18HCB', 'CTT001', '1842003', 7, 8, 9, 8.5);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (16, '18HCB', 'CTT001', '1842004', 2, 4, 6, 4.5);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (17, '18HCB', 'CTT001', '1842005', 8, 10, 2, 9.5);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (18, '18HCB', 'CTT002', '1842001', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (19, '18HCB', 'CTT002', '1842002', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (20, '18HCB', 'CTT002', '1842003', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (21, '18HCB', 'CTT002', '1842004', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (22, '18HCB', 'CTT002', '1842005', null, null, null, null);
INSERT INTO QuanLySinhVien.LopOfMon (id, maLop, maMon, maSinhVien, diemGK, diemCK, diemKhac, diemTong) VALUES (23, '18HCB', 'CTT001', '1742005', 9, 9, 9, 9);