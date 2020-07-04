package vn.name.ChanhDai.QuanLySinhVien.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * vn.name.ChanhDai.QuanLySinhVien.utils
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/4/20 - 9:21 PM
 * @description
 */
public class BcryptUtils {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(8));
    }

    public static boolean checkPassword(String hashed, String password) {
        return BCrypt.checkpw(password, hashed);
    }
}
