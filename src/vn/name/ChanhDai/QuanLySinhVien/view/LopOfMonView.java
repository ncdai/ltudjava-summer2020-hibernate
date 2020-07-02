package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.dao.LopOfMonDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.SinhVienDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.ThoiKhoaBieuDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.LopOfMon;
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
 * @date 7/2/20 - 3:09 PM
 * @description
 */
public class LopOfMonView {
    JFrame mainFrame;
    JTable tableLopOfMon;

    JComboBox<SimpleComboBoxItem> comboBoxMaLop;
    JComboBox<SimpleComboBoxItem> comboBoxMaMon;

    public LopOfMonView() {
        createUI();

        new GetLopOfMonThread(tableLopOfMon, "all", "all").start();
    }

    static class GetLopOfMonThread extends Thread {
        JTable table;
        String maLop;
        String maMon;

        public GetLopOfMonThread(JTable table, String maLop, String maMon) {
            this.table = table;
            this.maLop = maLop;
            this.maMon = maMon;
        }

        @Override
        public void run() {
            List<LopOfMon> lopOfMonList;

            if (maLop.equals("all") && maMon.equals("all")) {
                lopOfMonList = LopOfMonDAO.getList();
                System.out.println("Get by ...");
            } else {
                lopOfMonList = LopOfMonDAO.getListByMaLopAndMaMon(maLop, maMon);
                System.out.println("Get by Lop&Mon(" + maLop + "-" + maMon + ")");
            }

            SimpleTableModel tableModel = (SimpleTableModel) table.getModel();
            tableModel.clearRows();

            for (LopOfMon lopOfMon : lopOfMonList) {
                Vector<String> row = TableUtils.toRow(lopOfMon);
                tableModel.addRow(row);
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
            List<String> lopList = SinhVienDAO.getLopList();
            SimpleComboBoxModel model = (SimpleComboBoxModel) comboBox.getModel();
            for (String maLop : lopList) {
                model.addElement(new SimpleComboBoxItem(maLop, maLop));
            }
        }
    }

    static class GetComboBoxMaMonThread extends Thread {
        JComboBox<SimpleComboBoxItem> comboBox;
        String maLop;

        GetComboBoxMaMonThread(JComboBox<SimpleComboBoxItem> comboBox, String maLop) {
            this.comboBox = comboBox;
            this.maLop = maLop;
        }

        @Override
        public void run() {
            List<ThoiKhoaBieu> thoiKhoaBieuList = ThoiKhoaBieuDAO.getListByMaLop(maLop);

            SimpleComboBoxModel model = (SimpleComboBoxModel) comboBox.getModel();
            model.removeAllElements();
            model.addElement(new SimpleComboBoxItem("all", "Chọn Môn"));

            for (ThoiKhoaBieu thoiKhoaBieu : thoiKhoaBieuList) {
                String value = thoiKhoaBieu.getMon().getMaMon();
                String label = value + " - " + thoiKhoaBieu.getMon().getTenMon();

                model.addElement(new SimpleComboBoxItem(value, label));
            }
        }
    }

    void createUI() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Danh sách lớp");

        mainFrame.setLayout(new BorderLayout());

        JPanel panelPageStart = new JPanel();
        panelPageStart.setLayout(new BoxLayout(panelPageStart, BoxLayout.X_AXIS));
        panelPageStart.setBackground(Color.WHITE);

        comboBoxMaLop = new JComboBox<>();
        Vector<SimpleComboBoxItem> lopList = new Vector<>();
        lopList.add(new SimpleComboBoxItem("all", "Chọn lớp"));
        comboBoxMaLop.setModel(new SimpleComboBoxModel(lopList));
        comboBoxMaLop.addActionListener(e -> {
            SimpleComboBoxItem maLopItem = (SimpleComboBoxItem) comboBoxMaLop.getSelectedItem();
            if (maLopItem != null) {
                String maLop = maLopItem.getValue();
                System.out.println(maLop);
                new GetComboBoxMaMonThread(comboBoxMaMon, maLop).start();
            }
        });

        comboBoxMaMon = new JComboBox<>();
        Vector<SimpleComboBoxItem> monList = new Vector<>();
        monList.add(new SimpleComboBoxItem("all", "Chọn môn"));
        comboBoxMaMon.setModel(new SimpleComboBoxModel(monList));

        JButton buttonGet = new JButton("Xem danh sách");
        buttonGet.addActionListener(e -> {
            SimpleComboBoxItem maLopItem = (SimpleComboBoxItem) comboBoxMaLop.getSelectedItem();
            SimpleComboBoxItem maMonItem = (SimpleComboBoxItem) comboBoxMaMon.getSelectedItem();

            if (maLopItem != null && maMonItem != null) {
                String maLopItemLabel = maLopItem.getLabel();
                String maLopItemValue = maLopItem.getValue();

                String maMonItemLabel = maMonItem.getLabel();
                String maMonItemValue = maMonItem.getValue();

                if (!maLopItemValue.equals("all") && maMonItemValue.equals("all")) {
                    System.out.println("Xem Danh Sach Lop");
                    return;
                }

                System.out.println("[label=" + maLopItemLabel + "][value=" + maLopItemValue + "]");
                System.out.println("[label=" + maMonItemLabel + "][value=" + maMonItemValue + "]");

                new GetLopOfMonThread(tableLopOfMon, maLopItemValue, maMonItemValue).start();
            } else {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn Lớp và Môn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        panelPageStart.add(comboBoxMaLop);
        panelPageStart.add(Box.createRigidArea(new Dimension(8, 0)));
        panelPageStart.add(comboBoxMaMon);
        panelPageStart.add(Box.createRigidArea(new Dimension(8, 0)));
        panelPageStart.add(buttonGet);

        panelPageStart.setBorder(BorderFactory.createLineBorder(Color.WHITE, 8));

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BorderLayout());
        panelCenter.setBackground(Color.YELLOW);

        JPanel panelCenterHeader = new JPanel();

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Mã Lớp");
        columnNames.add("Mã Môn");
        columnNames.add("Tên môn");
        columnNames.add("MSSV");
        columnNames.add("Họ và Tên");
        columnNames.add("Giới tính");
        tableLopOfMon = new JTable(new SimpleTableModel(columnNames, null));
        tableLopOfMon.setFillsViewportHeight(true);

        JScrollPane scrollPaneCenter = new JScrollPane(tableLopOfMon);
        scrollPaneCenter.setBorder(BorderFactory.createLineBorder(Color.WHITE, 8));

        panelCenter.add(panelCenterHeader, BorderLayout.PAGE_START);
        panelCenter.add(scrollPaneCenter, BorderLayout.CENTER);

        mainFrame.add(panelPageStart, BorderLayout.PAGE_START);
        mainFrame.add(panelCenter, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }
}
