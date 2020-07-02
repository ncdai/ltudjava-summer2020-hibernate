package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.dao.ThoiKhoaBieuDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.ThoiKhoaBieu;
import vn.name.ChanhDai.QuanLySinhVien.utils.SimpleComboBoxItem;
import vn.name.ChanhDai.QuanLySinhVien.utils.SimpleComboBoxModel;
import vn.name.ChanhDai.QuanLySinhVien.utils.SimpleTableModel;
import vn.name.ChanhDai.QuanLySinhVien.utils.TableUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * vn.name.ChanhDai.QuanLySinhVien.view
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/2/20 - 10:11 AM
 * @description
 */

public class ThoiKhoaBieuView {
    JFrame mainFrame;
    JTable tableThoiKhoaBieu;
    JComboBox<SimpleComboBoxItem> comboBoxMaLop;

    public ThoiKhoaBieuView() {
        createAndShowUI();
        new GetThoiKhoaBieuThread(tableThoiKhoaBieu, "all").start();
        new GetComboBoxMaLopThread(comboBoxMaLop).start();
    }

    static class GetThoiKhoaBieuThread extends Thread {
        JTable table;
        String maLop;

        public GetThoiKhoaBieuThread(JTable table, String maLop) {
            this.table = table;
            this.maLop = maLop;
        }

        @Override
        public void run() {
            List<ThoiKhoaBieu> thoiKhoaBieuList;

            if (maLop == null || maLop.equals("all")) {
                thoiKhoaBieuList = ThoiKhoaBieuDAO.getList();
            } else {
                thoiKhoaBieuList = ThoiKhoaBieuDAO.getListByMaLop(maLop);
            }

            SimpleTableModel tableModel = (SimpleTableModel)table.getModel();
            tableModel.clearRows();

            for (ThoiKhoaBieu thoiKhoaBieu : thoiKhoaBieuList) {
                tableModel.addRow(TableUtils.toRow(thoiKhoaBieu));
            }

            tableModel.fireTableDataChanged();
        }
    }

    static class GetComboBoxMaLopThread extends Thread {
        JComboBox<SimpleComboBoxItem> comboBox;

        GetComboBoxMaLopThread(JComboBox<SimpleComboBoxItem> comboBox) {
            this.comboBox = comboBox;
        }

        @Override
        public void run() {
            List<String> list = ThoiKhoaBieuDAO.getLopList();
            SimpleComboBoxModel model = (SimpleComboBoxModel)comboBox.getModel();
            for (String item : list) {
                model.addElement(new SimpleComboBoxItem(item, item));
            }
        }
    }

    private void createAndShowUI() {
        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout());

        JPanel panelPageStart = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 16));
        JLabel labelTitle = new JLabel("Thời khóa biểu");
        labelTitle.setFont(new Font("", Font.BOLD, 24));
        panelPageStart.add(labelTitle);

        Vector<SimpleComboBoxItem> maLopList = new Vector<>();
        maLopList.add(new SimpleComboBoxItem("all", "Tất cả"));
        SimpleComboBoxModel maLopModel = new SimpleComboBoxModel(maLopList);
        comboBoxMaLop = new JComboBox<>(maLopModel);
        comboBoxMaLop.addActionListener(e -> {
            SimpleComboBoxItem item = (SimpleComboBoxItem) comboBoxMaLop.getSelectedItem();
            if (item != null) {
                String label = item.getLabel();
                String maLop = item.getValue();

                System.out.println("[label=" + label + "][value=" + maLop + "]");
                new GetThoiKhoaBieuThread(tableThoiKhoaBieu, maLop).start();
            }
        });

        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelCenterHeader = new JPanel();

        panelCenterHeader.setLayout(new BoxLayout(panelCenterHeader, BoxLayout.X_AXIS));
        panelCenterHeader.setBackground(Color.WHITE);
        panelCenterHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE, 8));
        panelCenterHeader.add(comboBoxMaLop);
        panelCenterHeader.add(Box.createHorizontalGlue());
        panelCenterHeader.add(new JButton("Nhập File CSV"));

        Vector<String> columnNames = new Vector<>();
        columnNames.add("#");
        columnNames.add("Mã lớp");
        columnNames.add("Mã môn");
        columnNames.add("Tên môn");
        columnNames.add("Phòng học");

        tableThoiKhoaBieu = new JTable(new SimpleTableModel(columnNames, null));
        tableThoiKhoaBieu.setFillsViewportHeight(true);
        tableThoiKhoaBieu.getColumnModel().getColumn(0).setMinWidth(0);
        tableThoiKhoaBieu.getColumnModel().getColumn(0).setMaxWidth(0);
        tableThoiKhoaBieu.getColumnModel().getColumn(0).setWidth(0);

//        ListSelectionModel listSelectionModel = tableThoiKhoaBieu.getSelectionModel();
//        listSelectionModel.addListSelectionListener(e -> {
//            int rowIndex = tableThoiKhoaBieu.getSelectedRow();
//            SimpleTableModel tableModel = (SimpleTableModel)tableThoiKhoaBieu.getModel();
//            System.out.println(tableModel.getRow(rowIndex).toString());
//        });

        JScrollPane scrollPaneCenter = new JScrollPane(tableThoiKhoaBieu);

        panelCenter.add(panelCenterHeader, BorderLayout.PAGE_START);
        panelCenter.add(scrollPaneCenter, BorderLayout.CENTER);

        mainFrame.add(panelPageStart, BorderLayout.PAGE_START);
        mainFrame.add(panelCenter, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
