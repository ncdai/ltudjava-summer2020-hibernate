package vn.name.ChanhDai.QuanLySinhVien.utils;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/30/20 - 9:32 PM
 * @description
 */
public class SimpleTableModel extends AbstractTableModel {
    private final Vector<String> columnNames;
    private final Vector<Vector<String>> data;

    public SimpleTableModel(Vector<String> columnNames, Vector<Vector<String>> data) {
        this.columnNames = columnNames != null ? columnNames : new Vector<>();
        this.data = data != null ? data : new Vector<>();
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        if (0 <= row && row <= data.size() - 1) {
            return data.get(row).get(col);
        }
        return "";
    }

    public void addRow(Vector<String> row) {
        data.add(row);
//        int index = data.size() - 1;
//        fireTableRowsInserted(index, index);
    }

    public void removeRow(int rowIndex) {
        data.remove(rowIndex);
    }

    public void updateRow(int rowIndex, Vector<String> row) {
        data.set(rowIndex, row);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
