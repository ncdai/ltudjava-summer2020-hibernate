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
    private final Vector<Vector<String>> rowData;

    public SimpleTableModel(Vector<String> columnNames, Vector<Vector<String>> rowData) {
        this.columnNames = columnNames != null ? columnNames : new Vector<>();
        this.rowData = rowData != null ? rowData : new Vector<>();
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return rowData.size();
    }

    public String getColumnName(int colIndex) {
        if (columnNames.size() - 1 < colIndex) return null;
        return columnNames.get(colIndex);
    }

    public Object getValueAt(int row, int col) {
        if (0 <= row && row <= rowData.size() - 1) {
            return rowData.get(row).get(col);
        }
        return "";
    }

    public void setValueAt(int rowIndex, int columnIndex, String value) {
        if (rowData.size() - 1 < rowIndex) return;
        Vector<String> row = rowData.get(rowIndex);

        if (row.size() - 1 < columnIndex) return;
        row.set(columnIndex, value);
    }

    public Vector<String> getRow(int rowIndex) {
        if (rowData.size() - 1 < rowIndex) return null;
        return rowData.get(rowIndex);
    }

    public void addRow(Vector<String> row) {
        rowData.add(row);
    }

    public void clearRows() {
        rowData.clear();
    }

    public void removeRow(int rowIndex) {
        if (rowData.size() - 1 < rowIndex) return;
        rowData.remove(rowIndex);
    }

    public void updateRow(int rowIndex, Vector<String> row) {
        if (rowData.size() - 1 < rowIndex) return;
        rowData.set(rowIndex, row);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
