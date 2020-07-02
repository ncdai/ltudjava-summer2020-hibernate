package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.dao.LopOfMonDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.SinhVienDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.ThoiKhoaBieuDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.LopOfMon;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;
import vn.name.ChanhDai.QuanLySinhVien.entity.ThoiKhoaBieu;
import vn.name.ChanhDai.QuanLySinhVien.utils.*;

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
    FileChooserView fileChooserView;

    JButton buttonImportCSV;

    public ThoiKhoaBieuView() {
        createAndShowUI();
        createImportCSVUI();

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
                boolean check = true;
                for (int i = 0; i < model.getSize(); ++i) {
                    if (model.getElementAt(i).getValue().equals(item)) {
                        check = false;
                        break;
                    }
                }

                if (check) {
                    model.addElement(new SimpleComboBoxItem(item, item));
                }
            }
        }
    }

    static class ImportCSVThread extends Thread {
        JTable tableDraft;
        JTable tableTarget;
        JComboBox<SimpleComboBoxItem> comboBoxMaLop;

        ImportCSVThread(JTable tableDraft, JTable tableTarget, JComboBox<SimpleComboBoxItem> comboBoxMaLop) {
            this.tableDraft = tableDraft;
            this.tableTarget = tableTarget;
            this.comboBoxMaLop = comboBoxMaLop;
        }

        @Override
        public void run() {
            SimpleTableModel tableDraftModel = (SimpleTableModel)tableDraft.getModel();
            SimpleTableModel tableTargetModel = (SimpleTableModel)tableTarget.getModel();

            int desiredImportQuantity = tableDraftModel.getRowCount();
            int actualImportQuantity = 0;

            for (int i = 0; i < desiredImportQuantity; ++i) {
                ThoiKhoaBieu thoiKhoaBieu = TableUtils.parseThoiKhoaBieu(tableDraftModel.getRow(i));

                boolean success = ThoiKhoaBieuDAO.create(thoiKhoaBieu);

                if (success) {
                    // Mặc định tất cả SV của lớp đều học các môn trong TKB
                    List<SinhVien> danhSachLop = SinhVienDAO.getListByMaLop(thoiKhoaBieu.getMaLop());
                    for (SinhVien sv : danhSachLop) {
                        LopOfMon lopOfMon = new LopOfMon();
                        lopOfMon.setMaLop(thoiKhoaBieu.getMaLop());
                        lopOfMon.setMon(thoiKhoaBieu.getMon());
                        lopOfMon.setSinhVien(sv);

                        success = LopOfMonDAO.create(lopOfMon);
                        if (success) {
                            System.out.println("Them SinhVien(" + sv.getMaSinhVien() + ") vao Lop(" + thoiKhoaBieu.getMaLop() + "-" + thoiKhoaBieu.getMon().getMaMon() + ") thanh cong!");
                        } else {
                            System.out.println("Them SinhVien(" + sv.getMaSinhVien() + ") vao Lop(" + thoiKhoaBieu.getMaLop() + "-" + thoiKhoaBieu.getMon().getMaMon() + ") that bai!");
                        }
                    }

                    tableDraftModel.setValueAt( i, 4, "[SUCCESS]");
                    tableTargetModel.addRow(TableUtils.toRow(thoiKhoaBieu));
                    ++actualImportQuantity;

                } else {
                    tableDraftModel.setValueAt( i, 4, "[FAILED]");
                }

                tableDraftModel.fireTableDataChanged();
                tableTargetModel.fireTableDataChanged();
            }

            JOptionPane.showMessageDialog(null, "Đã nhập dữ liệu thành công (" + actualImportQuantity + "/" + desiredImportQuantity + ")", "Kết quả", JOptionPane.INFORMATION_MESSAGE);

            // Cap nhat lai du lieu comboBoxMaLop
            new GetComboBoxMaLopThread(comboBoxMaLop).start();
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

        JPanel panelCenter = new JPanel(new BorderLayout(8, 8));
        JPanel panelCenterHeader = new JPanel();

        panelCenterHeader.setLayout(new BoxLayout(panelCenterHeader, BoxLayout.X_AXIS));
        panelCenterHeader.setBackground(Color.WHITE);
        panelCenterHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE, 8));
        panelCenterHeader.add(new JLabel("Xem theo lớp"));
        panelCenterHeader.add(Box.createRigidArea(new Dimension(8, 0)));
        panelCenterHeader.add(comboBoxMaLop);
        panelCenterHeader.add(Box.createHorizontalGlue());
        buttonImportCSV = new JButton("Nhập File CSV");
        panelCenterHeader.add(buttonImportCSV);

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
        scrollPaneCenter.setBorder(BorderFactory.createLineBorder(Color.WHITE, 8));

        panelCenter.add(panelCenterHeader, BorderLayout.PAGE_START);
        panelCenter.add(scrollPaneCenter, BorderLayout.CENTER);

        mainFrame.add(panelPageStart, BorderLayout.PAGE_START);
        mainFrame.add(panelCenter, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }

    private void createImportCSVUI() {
        fileChooserView = new FileChooserView(buttonImportCSV) {
            @Override
            public Vector<String> getColumnNames() {
                Vector<String> columnNames = new Vector<>();
                columnNames.add("Mã lớp");
                columnNames.add("Mã môn");
                columnNames.add("Tên môn");
                columnNames.add("Phòng học");
                columnNames.add("Trạng thái");

                return columnNames;
            }

            @Override
            public Vector<String> parseTableRow(String[] str) {
                ThoiKhoaBieu thoiKhoaBieu = CSVUtils.parseThoiKhoaBieu(str);
                if (thoiKhoaBieu != null) {
                    Vector<String> row = TableUtils.toRow(thoiKhoaBieu);
                    row.remove(0); // remove idCol
                    row.add("[PENDING]");
                    return row;
                }

                return null;
            }

            @Override
            public void startImport(JTable tablePreview) {
                System.out.println("Start Import");
                new ImportCSVThread(tablePreview, tableThoiKhoaBieu, comboBoxMaLop).start();
            }
        };
    }

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }
}
