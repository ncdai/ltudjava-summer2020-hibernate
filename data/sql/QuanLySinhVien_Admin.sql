create table Admin
(
    tenDangNhap varchar(50) not null
        primary key,
    matKhau     varchar(70) null,
    hoTen       varchar(50) null
);

INSERT INTO QuanLySinhVien.Admin (tenDangNhap, matKhau, hoTen) VALUES ('giaovu', '$2a$08$ry4GeFFeKv4ObI405v4VA.9r8isDqNmC/xzZLROodq15Y.YXX5pl6', 'Giáo Vụ HCMUS');