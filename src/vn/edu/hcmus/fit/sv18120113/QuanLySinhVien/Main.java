package vn.edu.hcmus.fit.sv18120113.QuanLySinhVien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void JDBCExample() {
        try {
            String URL = "jdbc:mysql://localhost:3306/QuanLySinhVien";
            String USER = "root";
            String PASS = "mysql";

            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.print("Connected!");

        } catch (SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
            System.out.println("SQLState : " + e.getSQLState());
            System.out.println("VendorError : " + e.getErrorCode());
        }
    }

    public static void CSVReaderExample() {
        ArrayList<String[]> dsSinhVien = CSVUtil.reader("sinhVien_18HCB.csv");

        for (String[] item : dsSinhVien) {
            for (String value : item) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CSVReaderExample();
    }
}
