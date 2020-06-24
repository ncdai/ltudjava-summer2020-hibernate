package vn.edu.hcmus.fit.sv18120113.QuanLySinhVien;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @Date 6/24/20 - 2:56 PM
 * @Description
 */
public class CSVUtil {
    public static ArrayList<String[]> reader(String file) {
        ArrayList<String[]> data = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
