package vn.name.ChanhDai.QuanLySinhVien.utils;

/**
 * vn.name.ChanhDai.QuanLySinhVien.utils
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/1/20 - 9:51 AM
 * @description
 */
public class SimpleComboBoxItem {
    private final String value;
    private final String label;

    public SimpleComboBoxItem(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

//    public void setValue(String value) {
//        this.value = value;
//    }

    public String getLabel() {
        return label;
    }

//    public void setLabel(String label) {
//        this.label = label;
//    }

    @Override
    public String toString() {
        return this.getLabel();
    }
}
