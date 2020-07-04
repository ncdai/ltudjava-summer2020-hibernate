package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.dao.LopOfMonDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.SinhVienDAO;
import vn.name.ChanhDai.QuanLySinhVien.dao.ThoiKhoaBieuDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.LopOfMon;
import vn.name.ChanhDai.QuanLySinhVien.entity.Mon;
import vn.name.ChanhDai.QuanLySinhVien.utils.*;

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

    FileChooserView fileChooserView;

    JComboBox<SimpleComboBoxItem> comboBoxMaLopFilter;
    JComboBox<SimpleComboBoxItem> comboBoxMaMonFilter;

    JComboBox<SimpleComboBoxItem> comboBoxMaLopForm;
    JComboBox<SimpleComboBoxItem> comboBoxMaMonForm;
    JTextField textFieldMaSinhVien;
    JTextField textFieldHoTen;

    JSpinner spinnerDiemGK;
    JSpinner spinnerDiemCK;
    JSpinner spinnerDiemKhac;
    JSpinner spinnerDiemTong;

    JRadioButton radioButtonUpdate;
    JRadioButton radioButtonDelete;
    JRadioButton radioButtonCreate;

    JButton buttonImportCSV;
    JButton buttonSubmit;

    public LopOfMonView() {
        createUI();
        createImportCSVUI();

        initData();
    }

    void initData() {
        new GetLopOfMonThread(tableLopOfMon, "all", "all").start();

        new GetComboBoxMaLopThread(comboBoxMaLopFilter).start();
        new GetComboBoxMaMonThread(comboBoxMaMonFilter, "all").start();

        new GetComboBoxMaLopThread(comboBoxMaLopForm).start();
        new GetComboBoxMaMonThread(comboBoxMaMonForm, "all").start();
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
                System.out.println("Get by All");
            } else if (maLop.equals("all")) {
                lopOfMonList = LopOfMonDAO.getListByMaMon(maMon);
                System.out.println("Get by Mon(" + maMon + ")");
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
            model.removeAllElements();
            model.addElement(new SimpleComboBoxItem("all", "Chọn Lớp"));

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
            List<Mon> monList;
            if (maLop.equals("all")) {
                monList = ThoiKhoaBieuDAO.getMonList();
            } else {
                monList = ThoiKhoaBieuDAO.getMonListByMaLop(maLop);
            }

            SimpleComboBoxModel model = (SimpleComboBoxModel) comboBox.getModel();
            model.removeAllElements();
            model.addElement(new SimpleComboBoxItem("all", "Chọn Môn"));

            for (Mon mon : monList) {
                String value = mon.getMaMon();
                String label = value + " - " + mon.getTenMon();

                model.addElement(new SimpleComboBoxItem(value, label));
            }
        }
    }

    static class UpdateThread extends Thread {
        JTable tableTarget;
        LopOfMon lopOfMon;
        int rowIndex;

        public UpdateThread(JTable tableTarget, LopOfMon lopOfMon, int rowIndex) {
            this.tableTarget = tableTarget;
            this.lopOfMon = lopOfMon;
            this.rowIndex = rowIndex;
        }

        @Override
        public void run() {
            boolean success = LopOfMonDAO.updateByMaSinhVienAndMaMon(lopOfMon);
            if (success) {
                SimpleTableModel tableModel = (SimpleTableModel) tableTarget.getModel();
                tableModel.updateRow(rowIndex, TableUtils.toRow(lopOfMon));
                tableModel.fireTableDataChanged();

                JOptionPane.showMessageDialog(null, "Cập nhật điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Cập nhật điểm thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class DeleteThread extends Thread {
        JTable tableTarget;
        LopOfMon lopOfMon;
        int rowIndex;

        public DeleteThread(JTable tableTarget, LopOfMon lopOfMon, int rowIndex) {
            this.tableTarget = tableTarget;
            this.lopOfMon = lopOfMon;
            this.rowIndex = rowIndex;
        }

        @Override
        public void run() {
            boolean success = LopOfMonDAO.deleteByMaSinhVienAndMaMon(lopOfMon);
            if (success) {
                SimpleTableModel tableModel = (SimpleTableModel) tableTarget.getModel();
                tableModel.removeRow(rowIndex);
                tableModel.fireTableDataChanged();

                JOptionPane.showMessageDialog(null, "Đã xóa sinh viên " + lopOfMon.getSinhVien().getMaSinhVien() + " - " + lopOfMon.getSinhVien().getHoTen() + " khỏi lớp " + lopOfMon.getMaLop() + "-" + lopOfMon.getMon().getMaMon() + "!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Xóa sinh viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class CreateThread extends Thread {
        JTable tableTarget;
        LopOfMon lopOfMon;

        public CreateThread(JTable tableTarget, LopOfMon lopOfMon) {
            this.tableTarget = tableTarget;
            this.lopOfMon = lopOfMon;
        }

        @Override
        public void run() {
            boolean success = LopOfMonDAO.create(lopOfMon);
            if (success) {
                SimpleTableModel tableModel = (SimpleTableModel) tableTarget.getModel();
                tableModel.addRow(TableUtils.toRow(lopOfMon));
                tableModel.fireTableDataChanged();

                JOptionPane.showMessageDialog(null, "Đã thêm sinh viên " + lopOfMon.getSinhVien().getMaSinhVien() + " - " + lopOfMon.getSinhVien().getHoTen() + " vào lớp " + lopOfMon.getMaLop() + "-" + lopOfMon.getMon().getMaMon() + "!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Thêm sinh viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class ImportCSVThread extends Thread {
        JTable tableDraft;
        JTable tableTarget;

        ImportCSVThread(JTable tableDraft, JTable tableTarget) {
            this.tableDraft = tableDraft;
            this.tableTarget = tableTarget;
        }

        public int findIndex(String maSinhVien, String maMon) {
            SimpleTableModel tableTargetModel = (SimpleTableModel) tableTarget.getModel();
            for (int index = 0; index < tableTargetModel.getRowCount(); ++index) {
                String _maSinhVien = (String) tableTargetModel.getValueAt(index, 3);
                String _maMon = (String) tableTargetModel.getValueAt(index, 1);

                if (_maSinhVien.equals(maSinhVien) && _maMon.equals(maMon)) {
                    return index;
                }
            }
            return -1;
        }

        @Override
        public void run() {
            SimpleTableModel tableDraftModel = (SimpleTableModel) tableDraft.getModel();
            SimpleTableModel tableTargetModel = (SimpleTableModel) tableTarget.getModel();

            int desiredImportQuantity = tableDraftModel.getRowCount();
            int actualImportQuantity = 0;

            for (int i = 0; i < desiredImportQuantity; ++i) {
                // maLop, maMon, maSinhVien, hoTen, diemGK, diemCK, diemKhac, diemTong
                Vector<String> row = tableDraftModel.getRow(i);

                String maLop = row.get(0);
                String maMon = row.get(1);
                String maSinhVien = row.get(2);

                Double diemGK = Double.parseDouble(row.get(4));
                Double diemCK = Double.parseDouble(row.get(5));
                Double diemKhac = Double.parseDouble(row.get(6));
                Double diemTong = Double.parseDouble(row.get(7));

                LopOfMon lopOfMon = new LopOfMon(
                    maLop,
                    maMon,
                    maSinhVien,
                    diemGK,
                    diemCK,
                    diemKhac,
                    diemTong
                );

                boolean success = LopOfMonDAO.updateByMaSinhVienAndMaMon(lopOfMon);
                if (success) {
                    ++actualImportQuantity;
                    tableDraftModel.setValueAt(i, 8, "[SUCCESS]");

                    int rowIndex = findIndex(maSinhVien, maMon);
                    if (rowIndex != -1) {
                        tableTargetModel.updateRow(rowIndex, TableUtils.toRow(lopOfMon));
                    }

                } else {
                    tableDraftModel.setValueAt(i, 8, "[FAILED]");
                }

                tableDraftModel.fireTableDataChanged();
                tableTargetModel.fireTableDataChanged();
            }

            JOptionPane.showMessageDialog(null, "Đã nhập dữ liệu thành công (" + actualImportQuantity + "/" + desiredImportQuantity + ")", "Kết quả", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public LopOfMon getSeletedRow() {
        int rowIndex = tableLopOfMon.getSelectedRow();
        if (rowIndex == -1) {
            return null;
        }

        SimpleTableModel tableModel = (SimpleTableModel) tableLopOfMon.getModel();
        return TableUtils.parseLopOfMon(tableModel.getRow(rowIndex));
    }

    public void setFormValues(
        String maLop,
        String maMon,
        String maSinhVien,
        String hoTen,
        Double diemGK,
        Double diemCK,
        Double diemKhac,
        Double diemTong
    ) {
        SimpleComboBoxModel maLopFormModel = (SimpleComboBoxModel) comboBoxMaLopForm.getModel();
        maLopFormModel.setSelectedItem(maLop);

        SimpleComboBoxModel maMonFormModel = (SimpleComboBoxModel) comboBoxMaMonForm.getModel();
        maMonFormModel.setSelectedItem(maMon);

        textFieldMaSinhVien.setText(maSinhVien);
        textFieldHoTen.setText(hoTen);
        spinnerDiemGK.setValue(diemGK != null ? diemGK : 0);
        spinnerDiemCK.setValue(diemCK != null ? diemCK : 0);
        spinnerDiemKhac.setValue(diemKhac != null ? diemKhac : 0);
        spinnerDiemTong.setValue(diemTong != null ? diemTong : 0);
    }

    void setFormValuesBySeletedRow() {
        LopOfMon lopOfMon = getSeletedRow();
        if (lopOfMon == null) return;

        this.setFormValues(
            lopOfMon.getMaLop(),
            lopOfMon.getMon().getMaMon(),
            lopOfMon.getSinhVien().getMaSinhVien(),
            lopOfMon.getSinhVien().getHoTen(),
            lopOfMon.getDiemGK(),
            lopOfMon.getDiemCK(),
            lopOfMon.getDiemKhac(),
            lopOfMon.getDiemTong()
        );
    }

    void resetForm() {
        this.setFormValues("", "", "", "", null, null, null, null);
    }

    void setBasicInfoFormEnabled(boolean enabled) {
        comboBoxMaLopForm.setEnabled(enabled);
        comboBoxMaMonForm.setEnabled(enabled);
        textFieldMaSinhVien.setEnabled(enabled);
    }

    void setDiemInfoFormEnabled(boolean enabled) {
        spinnerDiemGK.setEnabled(enabled);
        spinnerDiemCK.setEnabled(enabled);
        spinnerDiemKhac.setEnabled(enabled);
        spinnerDiemTong.setEnabled(enabled);
    }

    void setFormEnabled(boolean enabled) {
        setBasicInfoFormEnabled(enabled);
        setDiemInfoFormEnabled(enabled);
    }

    void createUI() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Danh Sách Lớp / Bảng Điểm");

        mainFrame.setLayout(new BorderLayout());

        // Frame -> Header
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        JLabel headerTitle = new JLabel("Danh Sách Lớp / Bảng Điểm");
        headerTitle.setFont(new Font("", Font.BOLD, 24));
        panelHeader.add(headerTitle);

        // Frame -> Main -> Filter
        JPanel panelFilter = createPanelFilter();
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));

        // Frame -> Main -> Table
        createTable();
        JScrollPane scrollPaneTable = new JScrollPane(tableLopOfMon);
        scrollPaneTable.setBackground(Color.WHITE);
        scrollPaneTable.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        scrollPaneTable.setPreferredSize(new Dimension(880, scrollPaneTable.getPreferredSize().height));

        panelMain.add(panelFilter, BorderLayout.PAGE_START);
        panelMain.add(scrollPaneTable, BorderLayout.CENTER);

        // Frame -> Sidebar
        JPanel panelSidebar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelSidebar.setBackground(Color.WHITE);
        panelSidebar.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel form = createPanelForm();
        panelSidebar.add(form);

        mainFrame.add(panelHeader, BorderLayout.PAGE_START);
        mainFrame.add(panelMain, BorderLayout.CENTER);
        mainFrame.add(panelSidebar, BorderLayout.LINE_END);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }

    public JComboBox<SimpleComboBoxItem> createComboBoxMaLop() {
        return ViewUtils.createComboBox(new SimpleComboBoxItem("all", "Chọn Lớp"));
    }

    public JComboBox<SimpleComboBoxItem> createComboBoxMaMon() {
        return ViewUtils.createComboBox(new SimpleComboBoxItem("all", "Chọn Môn"));
    }

    public String getComboBoxValue(JComboBox<SimpleComboBoxItem> comboBox) {
        SimpleComboBoxItem item = (SimpleComboBoxItem) comboBox.getSelectedItem();
        if (item != null) return item.getValue();
        return "";
    }

    private void createImportCSVUI() {
        fileChooserView = new FileChooserView(buttonImportCSV) {
            @Override
            public Vector<String> getColumnNames() {
                Vector<String> columnNames = new Vector<>();
                columnNames.add("Mã lớp");
                columnNames.add("Mã môn");
                columnNames.add("MSSV");
                columnNames.add("Họ tên");
                columnNames.add("Điểm GK");
                columnNames.add("Điểm CK");
                columnNames.add("Điểm Khác");
                columnNames.add("Điểm Tổng");
                columnNames.add("Trạng thái");

                return columnNames;
            }

            @Override
            public Vector<String> parseTableRow(String[] str) {
                LopOfMon lopOfMon = CSVUtils.parseLopOfMon(str);
                if (lopOfMon != null) {
                    Vector<String> row = new Vector<>();
                    row.add(lopOfMon.getMaLop());
                    row.add(lopOfMon.getMon().getMaMon());
                    row.add(lopOfMon.getSinhVien().getMaSinhVien());
                    row.add(lopOfMon.getSinhVien().getHoTen());
                    row.add(TableUtils.toString(lopOfMon.getDiemGK()));
                    row.add(TableUtils.toString(lopOfMon.getDiemCK()));
                    row.add(TableUtils.toString(lopOfMon.getDiemKhac()));
                    row.add(TableUtils.toString(lopOfMon.getDiemTong()));
                    row.add("[PENDING]");
                    return row;
                }

                return null;
            }

            @Override
            public void startImport(JTable tablePreview) {
                System.out.println("Start Import");
                new ImportCSVThread(tablePreview, tableLopOfMon).start();
            }

            @Override
            public void customTable(JTable table) {

            }
        };
    }

    JPanel createPanelFilter() {
        JPanel panelFilter = new JPanel();
        panelFilter.setLayout(new BoxLayout(panelFilter, BoxLayout.X_AXIS));
        panelFilter.setBackground(Color.WHITE);
        panelFilter.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        comboBoxMaMonFilter = createComboBoxMaMon();
        comboBoxMaMonFilter.setPreferredSize(new Dimension(160, 24));
        comboBoxMaLopFilter = createComboBoxMaLop();
        comboBoxMaLopFilter.setPreferredSize(new Dimension(160, 24));

        comboBoxMaLopFilter.addActionListener(e -> {
            SimpleComboBoxItem maLopItem = (SimpleComboBoxItem) comboBoxMaLopFilter.getSelectedItem();
            if (maLopItem != null) {
                String maLop = maLopItem.getValue();
                System.out.println(maLop);
                new GetComboBoxMaMonThread(comboBoxMaMonFilter, maLop).start();
            }
        });

        JButton buttonGet = new JButton("Xem Danh Sách");
        buttonGet.setPreferredSize(new Dimension(160, 24));
        buttonGet.addActionListener(e -> {
            SimpleComboBoxItem maLopItem = (SimpleComboBoxItem) comboBoxMaLopFilter.getSelectedItem();
            SimpleComboBoxItem maMonItem = (SimpleComboBoxItem) comboBoxMaMonFilter.getSelectedItem();

            if (maLopItem != null && maMonItem != null) {
                String maLopItemLabel = maLopItem.getLabel();
                String maLopItemValue = maLopItem.getValue();

                String maMonItemLabel = maMonItem.getLabel();
                String maMonItemValue = maMonItem.getValue();

                if (!maLopItemValue.equals("all") && maMonItemValue.equals("all")) {
                    System.out.println("Xem Danh Sach Lop");
                    new SinhVienView(maLopItemValue).setVisible(true);
                    return;
                }

                System.out.println("[label=" + maLopItemLabel + "][value=" + maLopItemValue + "]");
                System.out.println("[label=" + maMonItemLabel + "][value=" + maMonItemValue + "]");

                new GetLopOfMonThread(tableLopOfMon, maLopItemValue, maMonItemValue).start();
            } else {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn Lớp và Môn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton buttonXemThongKe = new JButton("Xem Thống Kê");
        buttonXemThongKe.setPreferredSize(new Dimension(160, 24));
        buttonXemThongKe.addActionListener(e -> {
            String maLop = getComboBoxValue(comboBoxMaLopFilter);
            String maMon = getComboBoxValue(comboBoxMaMonFilter);

            if (!maLop.equals("all") && !maMon.equals("all")) {
                new LopOfMonThongKeView(maLop, maMon);
            } else {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn Lớp và Môn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        temp.setBackground(Color.WHITE);

        JPanel temp2 = new JPanel(new GridBagLayout());
        temp2.setBackground(Color.WHITE);

        temp2.add(comboBoxMaLopFilter, ViewUtils.createFormConstraints(0, 0, 1, 0, 8, 8, 0));
        temp2.add(comboBoxMaMonFilter, ViewUtils.createFormConstraints(1, 0, 1, 0, 8, 8, 0));

        temp2.add(buttonGet, ViewUtils.createFormConstraints(2, 0, 1, 0, 0, 8, 0));
        temp2.add(buttonXemThongKe, ViewUtils.createFormConstraints(2, 1, 1));

        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#eeeeee"));
        panel.setPreferredSize(new Dimension(100, 24));

        JLabel label = new JLabel("Thống kê Số Lượng / Phần Trăm Qua / Rớt Môn");
        panel.add(label);
        temp2.add(panel, ViewUtils.createFormConstraints(0, 1, 2, 0, 8, 0, 0));

        temp.add(temp2);

        panelFilter.add(temp);
        panelFilter.add(Box.createRigidArea(new Dimension(8, 0)));
        panelFilter.add(Box.createHorizontalGlue());

        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setBackground(Color.WHITE);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panel2.setBackground(Color.WHITE);
        panel2.add(panel1);

        buttonImportCSV = new JButton("Nhập Bảng Điểm CSV");
        JButton buttonSaveCSVTemplateFile = new JButton("Lưu File CSV Mẫu");
        buttonSaveCSVTemplateFile.setBackground(Color.decode("#eeeeee"));
        buttonSaveCSVTemplateFile.addActionListener(e -> {
            String header = "maLop,maMon,maSinhVien,hoTen,diemGK,diemCK,diemKhac,diemTong";
            String content = "18HCB,CTT001,1842001,Nguyen Van A,10,10,10,10";
            CSVUtils.saveCSVTemplateFile(header, content);
        });

        panel1.add(buttonImportCSV, ViewUtils.createFormConstraints(0, 0, 1, 0, 0, 8, 0));
        panel1.add(buttonSaveCSVTemplateFile, ViewUtils.createFormConstraints(0, 1, 1));

        panelFilter.add(panel2);

        return panelFilter;
    }

    void createTable() {
        Vector<String> columnNames = LopOfMonView.getTableColumnNames();

        tableLopOfMon = ViewUtils.createSimpleTable(new SimpleTableModel(columnNames, null));

        ViewUtils.setTableColumnWidth(tableLopOfMon, 0, 64);
        ViewUtils.setTableColumnWidth(tableLopOfMon, 1, 64);

        ViewUtils.setTableColumnWidth(tableLopOfMon, 3, 64);
        ViewUtils.setTableColumnWidth(tableLopOfMon, 4, 112);
        ViewUtils.setTableColumnWidth(tableLopOfMon, 5, 64);

        for (int i = 6; i <= 10; ++i) {
            ViewUtils.setTableColumnWidth(tableLopOfMon, i, 80);
        }

        ListSelectionModel selectionModel = tableLopOfMon.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (radioButtonCreate.isSelected()) {
                resetForm();
            } else {
                setFormValuesBySeletedRow();
            }
        });
    }

    JPanel createPanelForm() {
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        radioButtonUpdate = ViewUtils.createRadioButton("Sửa Điểm", 96, 24, SwingConstants.CENTER);
        radioButtonUpdate.setSelected(true);
        radioButtonUpdate.addActionListener(e -> {
            System.out.println("radioButtonUpdate " + radioButtonUpdate.isSelected());
            setFormValuesBySeletedRow();
            setBasicInfoFormEnabled(false);
            setDiemInfoFormEnabled(true);
        });

        radioButtonDelete = ViewUtils.createRadioButton("Xóa SV", 96, 24, SwingConstants.CENTER);
        radioButtonDelete.addActionListener(e -> {
            System.out.println("radioButtonDelete " + radioButtonDelete.isSelected());
            setFormValuesBySeletedRow();
            setFormEnabled(false);
        });

        radioButtonCreate = ViewUtils.createRadioButton("Thêm SV", 96, 24, SwingConstants.CENTER);
        radioButtonCreate.addActionListener(e -> {
            System.out.println("radioButtonCreate " + radioButtonCreate.isSelected());
            resetForm();
            setFormEnabled(true);
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonUpdate);
        buttonGroup.add(radioButtonDelete);
        buttonGroup.add(radioButtonCreate);

        comboBoxMaLopForm = createComboBoxMaLop();
        comboBoxMaMonForm = createComboBoxMaMon();

        form.add(radioButtonUpdate, ViewUtils.createFormConstraints(0, 0, 1));
        form.add(radioButtonDelete, ViewUtils.createFormConstraints(1, 0, 1));
        form.add(radioButtonCreate, ViewUtils.createFormConstraints(2, 0, 1));

        form.add(new JLabel("Lớp"), ViewUtils.createFormConstraints(0, 1, 1, 8, 8, 8, 0));
        comboBoxMaLopForm.setEnabled(false);
        form.add(comboBoxMaLopForm, ViewUtils.createFormConstraints(1, 1, 2, 8, 0));

        form.add(new JLabel("Môn"), ViewUtils.createFormConstraints(0, 2, 1));
        comboBoxMaMonForm.setEnabled(false);
        form.add(comboBoxMaMonForm, ViewUtils.createFormConstraints(1, 2, 2));

        form.add(new JLabel("MSSV"), ViewUtils.createFormConstraints(0, 3, 1, 8, 0));
        textFieldMaSinhVien = new JTextField();
        textFieldMaSinhVien.setEnabled(false);
        form.add(textFieldMaSinhVien, ViewUtils.createFormConstraints(1, 3, 2, 8, 0));

        form.add(new JLabel("Họ Tên"), ViewUtils.createFormConstraints(0, 4, 1));
        textFieldHoTen = new JTextField();
        textFieldHoTen.setEnabled(false);
        form.add(textFieldHoTen, ViewUtils.createFormConstraints(1, 4, 2));

        form.add(new JLabel("Điểm GK"), ViewUtils.createFormConstraints(0, 5, 1, 8, 0));
        spinnerDiemGK = new JSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        form.add(spinnerDiemGK, ViewUtils.createFormConstraints(1, 5, 2, 8, 0));

        form.add(new JLabel("Điểm CK"), ViewUtils.createFormConstraints(0, 6, 1));
        spinnerDiemCK = new JSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        form.add(spinnerDiemCK, ViewUtils.createFormConstraints(1, 6, 2));

        form.add(new JLabel("Điểm Khác"), ViewUtils.createFormConstraints(0, 7, 1, 8, 0));
        spinnerDiemKhac = new JSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        form.add(spinnerDiemKhac, ViewUtils.createFormConstraints(1, 7, 2, 8, 0));

        form.add(new JLabel("Điểm Tổng"), ViewUtils.createFormConstraints(0, 8, 1, 0, 8, 0, 0));
        spinnerDiemTong = new JSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        form.add(spinnerDiemTong, ViewUtils.createFormConstraints(1, 8, 2));

        buttonSubmit = new JButton("Thực Hiện");
        form.add(buttonSubmit, ViewUtils.createFormConstraints(0, 9, 3, 8, 0, 0, 0));

        buttonSubmit.addActionListener(e -> {
            String maLop = getComboBoxValue(comboBoxMaLopForm);
            String maMon = getComboBoxValue(comboBoxMaMonForm);
            String maSinhVien = textFieldMaSinhVien.getText();
            String hoTen = textFieldHoTen.getText();

            Double diemGK = (Double) spinnerDiemGK.getValue();
            Double diemCK = (Double) spinnerDiemCK.getValue();
            Double diemKhac = (Double) spinnerDiemKhac.getValue();
            Double diemTong = (Double) spinnerDiemTong.getValue();

            if (maLop.equals("") || maMon.equals("") || maSinhVien.equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LopOfMon lopOfMon = new LopOfMon(
                maLop,
                maMon,
                maSinhVien,
                diemGK,
                diemCK,
                diemKhac,
                diemTong
            );
            lopOfMon.getSinhVien().setHoTen(hoTen);

            int rowSelectedIndex = tableLopOfMon.getSelectedRow();

            if (radioButtonUpdate.isSelected()) {

                System.out.println("Update");
                new UpdateThread(tableLopOfMon, lopOfMon, rowSelectedIndex).start();

            } else if (radioButtonDelete.isSelected()) {

                System.out.println("Delete");

                int confirm = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "Bạn chắn chắn muốn xóa Sinh Viên " + lopOfMon.getSinhVien().getMaSinhVien() + " - " + lopOfMon.getSinhVien().getHoTen() + " khỏi Lớp " + lopOfMon.getMaLop() + "-" + lopOfMon.getMon().getMaMon() + "?",
                    "Xác nhận",
                    JOptionPane.OK_CANCEL_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    new DeleteThread(tableLopOfMon, lopOfMon, rowSelectedIndex).start();
                }

            } else {

                System.out.println("Create");
                new CreateThread(tableLopOfMon, lopOfMon).start();

            }
        });

        return form;
    }

    public static Vector<String> getTableColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Mã Lớp");
        columnNames.add("Mã Môn");
        columnNames.add("Tên Môn");

        columnNames.add("MSSV");
        columnNames.add("Họ và Tên");
        columnNames.add("Giới Tính");

        columnNames.add("Điểm GK");
        columnNames.add("Điểm CK");
        columnNames.add("Điểm Khác");

        columnNames.add("Điểm Tổng");
        columnNames.add("Đậu / Rớt");

        return columnNames;
    }

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
        initData();
    }
}
