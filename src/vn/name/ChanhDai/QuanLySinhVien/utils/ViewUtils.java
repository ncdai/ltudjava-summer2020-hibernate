package vn.name.ChanhDai.QuanLySinhVien.utils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Vector;

/**
 * vn.name.ChanhDai.QuanLySinhVien.utils
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/3/20 - 9:58 PM
 * @description
 */
public class ViewUtils {
    public static GridBagConstraints createFormConstraints(int x, int y, int width, int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.insets = new Insets(paddingTop, paddingLeft, paddingBottom, paddingRight);

        return c;
    }

    public static GridBagConstraints createFormConstraints(int x, int y, int width) {
        return createFormConstraints(x, y, width, 0, 0, 0, 0);
    }

    public static GridBagConstraints createFormConstraints(int x, int y, int width, int paddingHorizontal, int paddingVertical) {
        return createFormConstraints(x, y, width, paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
    }

    public static JRadioButton createRadioButton(String label, int width, int height, int alignment) {
        JRadioButton radioButton = new JRadioButton(label);
        radioButton.setPreferredSize(new Dimension(width, height));
        radioButton.setBackground(Color.decode("#f5f5f5"));
        radioButton.setHorizontalAlignment(alignment);

        return radioButton;
    }

    public static ImageIcon createImageIcon(String path) {
        return new ImageIcon(path);
    }

    public static void setTableColumnWidth(JTable table, int columnIndex, int width) {
        table.getColumnModel().getColumn(columnIndex).setMinWidth(width);
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(width);
        table.getColumnModel().getColumn(columnIndex).setWidth(width);
    }

    static class SimpleHeaderRenderer extends JLabel implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {

            Color color = UIManager.getColor("Table.gridColor");

            setText(value.toString());
            setToolTipText((String) value);
            setHorizontalAlignment(CENTER);
            setFont(new Font(getFont().getName(), Font.PLAIN, getFont().getSize()));
            setBorder(BorderFactory.createMatteBorder( 1, 0, 1, 1, color));

            return this;
        }
    }

    public static JTable createSimpleTable(SimpleTableModel model) {
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);

        Color defaultGridColor = UIManager.getColor("Table.gridColor");
        MatteBorder border = new MatteBorder(0, 1, 1, 1, defaultGridColor);
        table.setBorder(border);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(new SimpleHeaderRenderer());
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, defaultGridColor));

        return table;
    }

    public static JComboBox<SimpleComboBoxItem> createComboBox(SimpleComboBoxItem placeholder) {
        Vector<SimpleComboBoxItem> defaultList = new Vector<>();
        defaultList.add(placeholder);

        JComboBox<SimpleComboBoxItem> comboBox = new JComboBox<>(new SimpleComboBoxModel(defaultList));
        comboBox.setPrototypeDisplayValue(placeholder);
        comboBox.setPreferredSize(new Dimension(128, 24));

        return comboBox;
    }

    public static JButton createButtonBack(JFrame frame, String text, Color background) {
        JButton buttonBack = new JButton(text, ViewUtils.createImageIcon("assets/images/back.png"));
        buttonBack.setBorderPainted(false);
        buttonBack.setBorder(null);
        buttonBack.setBackground(background);
        buttonBack.setFont(new Font("", Font.BOLD, 14));
        buttonBack.setForeground(Color.GRAY);
        buttonBack.setFocusPainted(false);
        buttonBack.setIconTextGap(8);
        buttonBack.addActionListener(e -> {
            frame.setVisible(false);
        });

        return buttonBack;
    }

    public static JButton createButtonBack(JFrame frame, String text) {
        return createButtonBack(frame, text, Color.decode("#eeeeee"));
    }
}
