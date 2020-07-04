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

        initData();
    }

    void initData() {
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
                System.out.println("ThoiKhoaBieuView -> GetThoiKhoaBieuThread -> Get All");
            } else {
                thoiKhoaBieuList = ThoiKhoaBieuDAO.getListByMaLop(maLop);
                System.out.println("ThoiKhoaBieuView -> GetThoiKhoaBieuThread -> Get by Lop(" + maLop+ ")");
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

            model.removeAllElements();
            model.addElement(new SimpleComboBoxItem("all", "Tất Cả"));

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

                    tableDraftModel.setValueAt( i, 4, "[ Thành Công ]");
                    tableTargetModel.addRow(TableUtils.toRow(thoiKhoaBieu));
                    ++actualImportQuantity;

                } else {
                    tableDraftModel.setValueAt( i, 4, "[ Lỗi ]");
                }

                tableDraftModel.fireTableDataChanged();
                tableTargetModel.fireTableDataChanged();
            }

            JOptionPane.showMessageDialog(null, "Đã nhập Thời Khóa Biểu thành công (" + actualImportQuantity + "/" + desiredImportQuantity + ")", "Kết quả", JOptionPane.INFORMATION_MESSAGE);

            // Cap nhat lai du lieu comboBoxMaLop
            new GetComboBoxMaLopThread(comboBoxMaLop).start();
        }
    }

    private void createAndShowUI() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Thời Khóa Biểu");
        mainFrame.setLayout(new BorderLayout());

        JPanel panelPageStart = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        JLabel labelTitle = new JLabel("Thời Khóa Biểu");
        labelTitle.setFont(new Font("", Font.BOLD, 24));

        panelPageStart.add(ViewUtils.createButtonBack(mainFrame, "Trở về"));
        panelPageStart.add(labelTitle);

        comboBoxMaLop = ViewUtils.createComboBox(new SimpleComboBoxItem("all", "Tất Cả"));
        comboBoxMaLop.setPreferredSize(new Dimension(144, 24));
        comboBoxMaLop.addActionListener(e -> {
            SimpleComboBoxItem item = (SimpleComboBoxItem) comboBoxMaLop.getSelectedItem();
            if (item != null) {
                String maLop = item.getValue();
                System.out.println("ThoiKhoaBieuView -> comboBoxMaLop -> " + maLop);
                new GetThoiKhoaBieuThread(tableThoiKhoaBieu, maLop).start();
            }
        });

        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelCenterHeader = new JPanel();

        panelCenterHeader.setLayout(new BoxLayout(panelCenterHeader, BoxLayout.X_AXIS));
        panelCenterHeader.setBackground(Color.WHITE);
        panelCenterHeader.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panelCenterHeader.add(new JLabel("Xem TKB Lớp"));
        panelCenterHeader.add(Box.createRigidArea(new Dimension(8, 0)));

        JPanel comboBoxMaLopWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        comboBoxMaLopWrapper.setBackground(Color.WHITE);
        comboBoxMaLopWrapper.add(comboBoxMaLop);
        panelCenterHeader.add(comboBoxMaLopWrapper);

        panelCenterHeader.add(Box.createHorizontalGlue());

        JButton buttonSaveCSVTemplateFile = new JButton("Lưu File CSV Mẫu");
        buttonSaveCSVTemplateFile.setBackground(Color.decode("#eeeeee"));
        buttonSaveCSVTemplateFile.setPreferredSize(new Dimension(144, 24));
        buttonSaveCSVTemplateFile.addActionListener(e -> {
            String header = "maLop,maMon,tenMon,phongHoc";
            String content = "18HCB,CTT001,Lập trình ứng dụng Java,C31";
            CSVUtils.saveCSVTemplateFile(header, content);
        });

        panelCenterHeader.add(buttonSaveCSVTemplateFile);
        panelCenterHeader.add(Box.createRigidArea(new Dimension(8, 0)));

        buttonImportCSV = new JButton("Nhập File CSV");
        buttonImportCSV.setPreferredSize(new Dimension(144, 24));
        panelCenterHeader.add(buttonImportCSV);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Mã Lớp");
        columnNames.add("Mã Môn");
        columnNames.add("Tên Môn");
        columnNames.add("Phòng Học");

        tableThoiKhoaBieu = ViewUtils.createSimpleTable(new SimpleTableModel(columnNames, null));

        JScrollPane scrollPaneCenter = new JScrollPane(tableThoiKhoaBieu);
        scrollPaneCenter.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        scrollPaneCenter.setPreferredSize(new Dimension(720, scrollPaneCenter.getPreferredSize().height));
        scrollPaneCenter.setBackground(Color.WHITE);

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
                columnNames.add("Mã Lớp");
                columnNames.add("Mã Môn");
                columnNames.add("Tên Môn");
                columnNames.add("Phòng Học");
                columnNames.add("Trạng Thái");

                return columnNames;
            }

            @Override
            public Vector<String> parseTableRow(String[] str) {
                ThoiKhoaBieu thoiKhoaBieu = CSVUtils.parseThoiKhoaBieu(str);
                if (thoiKhoaBieu != null) {
                    Vector<String> row = TableUtils.toRow(thoiKhoaBieu);
                    row.add("[ Đang Chờ ]");
                    return row;
                }

                return null;
            }

            @Override
            public void startImport(JTable tablePreview) {
                new ImportCSVThread(tablePreview, tableThoiKhoaBieu, comboBoxMaLop).start();
            }

            @Override
            public void customTable(JTable table) {

            }
        };
    }

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
        initData();
    }
}
