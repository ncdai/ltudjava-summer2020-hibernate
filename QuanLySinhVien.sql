create table Mon
(
    maMon  varchar(10) not null
        primary key,
    tenMon varchar(50) null
);

create table SinhVien
(
    maSinhVien varchar(10) not null
        primary key,
    hoTen      varchar(50) null,
    gioiTinh   varchar(10) null,
    cmnd       varchar(15) null,
    matKhau    varchar(50) null,
    maLop      varchar(10) null
);

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


