-- MySQL dump 10.13  Distrib 8.0.20, for macos10.15 (x86_64)
--
-- Host: localhost    Database: QuanLySinhVien
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Admin`
--

DROP TABLE IF EXISTS `Admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Admin` (
  `tenDangNhap` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `matKhau` varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hoTen` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`tenDangNhap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
INSERT INTO `Admin` VALUES ('giaovu','$2a$08$ry4GeFFeKv4ObI405v4VA.9r8isDqNmC/xzZLROodq15Y.YXX5pl6','Giáo Vụ HCMUS');
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LopOfMon`
--

DROP TABLE IF EXISTS `LopOfMon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LopOfMon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `maLop` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `maMon` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `maSinhVien` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `diemGK` double DEFAULT NULL,
  `diemCK` double DEFAULT NULL,
  `diemKhac` double DEFAULT NULL,
  `diemTong` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `LopOfMon_Mon_maMon_fk` (`maMon`),
  KEY `LopOfMon_SinhVien_maSinhVien_fk` (`maSinhVien`),
  CONSTRAINT `LopOfMon_Mon_maMon_fk` FOREIGN KEY (`maMon`) REFERENCES `Mon` (`maMon`),
  CONSTRAINT `LopOfMon_SinhVien_maSinhVien_fk` FOREIGN KEY (`maSinhVien`) REFERENCES `SinhVien` (`maSinhVien`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LopOfMon`
--

LOCK TABLES `LopOfMon` WRITE;
/*!40000 ALTER TABLE `LopOfMon` DISABLE KEYS */;
INSERT INTO `LopOfMon` VALUES (1,'17HCB','CTT011','1742001',NULL,NULL,NULL,NULL),(2,'17HCB','CTT011','1742002',NULL,NULL,NULL,NULL),(3,'17HCB','CTT011','1742003',NULL,NULL,NULL,NULL),(4,'17HCB','CTT011','1742004',NULL,NULL,NULL,NULL),(5,'17HCB','CTT011','1742005',NULL,NULL,NULL,NULL),(6,'17HCB','CTT011','1742006',NULL,NULL,NULL,NULL),(7,'17HCB','CTT012','1742001',NULL,NULL,NULL,NULL),(8,'17HCB','CTT012','1742002',NULL,NULL,NULL,NULL),(9,'17HCB','CTT012','1742003',NULL,NULL,NULL,NULL),(10,'17HCB','CTT012','1742004',NULL,NULL,NULL,NULL),(11,'17HCB','CTT012','1742005',NULL,NULL,NULL,NULL),(12,'17HCB','CTT012','1742006',NULL,NULL,NULL,NULL),(14,'18HCB','CTT001','1842002',4,5,6,5),(15,'18HCB','CTT001','1842003',7,8,9,8.5),(16,'18HCB','CTT001','1842004',2,4,6,4.5),(17,'18HCB','CTT001','1842005',8,10,2,9.5),(18,'18HCB','CTT002','1842001',NULL,NULL,NULL,NULL),(19,'18HCB','CTT002','1842002',NULL,NULL,NULL,NULL),(20,'18HCB','CTT002','1842003',NULL,NULL,NULL,NULL),(21,'18HCB','CTT002','1842004',NULL,NULL,NULL,NULL),(22,'18HCB','CTT002','1842005',NULL,NULL,NULL,NULL),(23,'18HCB','CTT001','1742005',9,9,9,9);
/*!40000 ALTER TABLE `LopOfMon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Mon`
--

DROP TABLE IF EXISTS `Mon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Mon` (
  `maMon` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `tenMon` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`maMon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mon`
--

LOCK TABLES `Mon` WRITE;
/*!40000 ALTER TABLE `Mon` DISABLE KEYS */;
INSERT INTO `Mon` VALUES ('CTT001','Lập trình ứng dụng Java'),('CTT002','Mạng máy tính'),('CTT011','Thiết kế giao diện'),('CTT012','Kiểm chứng phần mềm');
/*!40000 ALTER TABLE `Mon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SinhVien`
--

DROP TABLE IF EXISTS `SinhVien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SinhVien` (
  `maSinhVien` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `hoTen` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gioiTinh` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cmnd` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matKhau` varchar(70) COLLATE utf8_unicode_ci DEFAULT NULL,
  `maLop` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`maSinhVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SinhVien`
--

LOCK TABLES `SinhVien` WRITE;
/*!40000 ALTER TABLE `SinhVien` DISABLE KEYS */;
INSERT INTO `SinhVien` VALUES ('1742001','Nguyễn Văn A','Nam','123456789','$2a$08$xBsHucVj6ky/BLblwlp9sed1U0xwgqnbBRI0rO2Fn3Dd8bLSbZ0Pe','17HCB'),('1742002','Trần Văn B','Nam','234567891','$2a$08$35v50pcZEfGFyGDoJ0mHVen9WyrjNoycXbulhT7R7FdoNcCAU/ika','17HCB'),('1742003','Huỳnh Thị C','Nữ','345678912','$2a$08$rqtML61oEhfTcaTSx9myn.En7MnDh2nLBBZbpe60ajuqKU1RCdPum','17HCB'),('1742004','Mai Văn D','Nam','456789123','$2a$08$9p9TVwou8J5yWTnS/EK30uL4dyG0Fo.h/PRRL12z29Zi23FMxJa6O','17HCB'),('1742005','Hồ Thị E','Nữ','567891234','$2a$08$ZSeZ2neXkP1aU2XLe3RrQecDyep34F.TDqWtJCUR5w1TSjxV1zxw6','17HCB'),('1742006','Trần Kiều X','Nữ','987612345','$2a$08$CkfhEFkqhTlyTZl5Tk/7ieayxSOzfLDi9fLzl5e6/wq/UUE29xYXm','17HCB'),('1842001','Lý Văn F','Nam','678912345','$2a$08$1qk/7HaHP9fuPcN7olPp5u3OXjRDg1/7br28AlGy0iTrSBZgAsotO','18HCB'),('1842002','Chiêu Văn G','Nam','789123456','$2a$08$5qODkSOeLesfhBNL6jTx5ukKt0GK/AZASghGMU4rUFrA1Cdlqd1ha','18HCB'),('1842003','Trần Thị H','Nữ','891234567','$2a$08$e7IFSQd/PqgQJZMP2JW21O7k01xY1lzmgjVJ8V5dH3bcWpqN3joou','18HCB'),('1842004','Mạc Văn I','Nam','912345678','$2a$08$pTB1PSPRZEmZuYCK1V.bIuqAUmMyxQ3tYNVxptfD1GKx7OVy9j8za','18HCB'),('1842005','Văn Thị J','Nữ','987654321','$2a$08$t3.BtJDYpIScVMLCvaLrlufyGB.swpd9jsZuOH9u0ZAOZLQFKBv2m','18HCB');
/*!40000 ALTER TABLE `SinhVien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ThoiKhoaBieu`
--

DROP TABLE IF EXISTS `ThoiKhoaBieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ThoiKhoaBieu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `maMon` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phongHoc` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `maLop` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ThoiKhoaBieu_Mon_maMon_fk` (`maMon`),
  CONSTRAINT `ThoiKhoaBieu_Mon_maMon_fk` FOREIGN KEY (`maMon`) REFERENCES `Mon` (`maMon`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ThoiKhoaBieu`
--

LOCK TABLES `ThoiKhoaBieu` WRITE;
/*!40000 ALTER TABLE `ThoiKhoaBieu` DISABLE KEYS */;
INSERT INTO `ThoiKhoaBieu` VALUES (1,'CTT011','C32','17HCB'),(2,'CTT012','C32','17HCB'),(3,'CTT001','C31','18HCB'),(4,'CTT002','C31','18HCB');
/*!40000 ALTER TABLE `ThoiKhoaBieu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-04 22:17:13
