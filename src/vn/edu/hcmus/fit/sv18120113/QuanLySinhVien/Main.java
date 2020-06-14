package vn.edu.hcmus.fit.sv18120113.QuanLySinhVien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            String URL = "jdbc:mysql://localhost:3306/QuanLySinhVien";
            String USER = "root";
            String PASS = "mysql";

            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.printf("Connected!");

        } catch (SQLException e) {
            System.out.println("SQLException : " + e.getMessage());
            System.out.println("SQLState : " + e.getSQLState());
            System.out.println("VendorError : " + e.getErrorCode());
        }
    }
}
