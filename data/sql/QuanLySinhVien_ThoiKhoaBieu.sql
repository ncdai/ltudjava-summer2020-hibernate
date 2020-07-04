create table ThoiKhoaBieu
(
    id       int auto_increment
        primary key,
    maMon    varchar(10) null,
    phongHoc varchar(10) null,
    maLop    varchar(10) null,
    constraint ThoiKhoaBieu_Mon_maMon_fk
        foreign key (maMon) references Mon (maMon)
);

INSERT INTO QuanLySinhVien.ThoiKhoaBieu (id, maMon, phongHoc, maLop) VALUES (1, 'CTT011', 'C32', '17HCB');
INSERT INTO QuanLySinhVien.ThoiKhoaBieu (id, maMon, phongHoc, maLop) VALUES (2, 'CTT012', 'C32', '17HCB');
INSERT INTO QuanLySinhVien.ThoiKhoaBieu (id, maMon, phongHoc, maLop) VALUES (3, 'CTT001', 'C31', '18HCB');
INSERT INTO QuanLySinhVien.ThoiKhoaBieu (id, maMon, phongHoc, maLop) VALUES (4, 'CTT002', 'C31', '18HCB');