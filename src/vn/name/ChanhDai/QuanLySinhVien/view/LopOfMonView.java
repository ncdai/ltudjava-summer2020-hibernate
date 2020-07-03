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

        new GetLopOfMonThread(tableLopOfMon, "all", "all").start();
        new GetComboBoxMaLopThread(comboBoxMaLopFilter).start();
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
//                System.out.println("After : " + lopOfMon.toString()); // DEBUG

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
                String _maSinhVien = (String) tableTargetModel.getValueAt(index, 4);
                String _maMon = (String) tableTargetModel.getValueAt(index, 2);

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
        mainFrame.setTitle("Danh sách lớp");

        mainFrame.setLayout(new BorderLayout());

        // Frame -> Header
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        JLabel headerTitle = new JLabel("Danh sách lớp / Bảng điểm");
        headerTitle.setFont(new Font("", Font.BOLD, 24));
        panelHeader.add(headerTitle);

        // Frame -> Main -> Filter
        JPanel panelFilter = createPanelFilter();
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());
        panelMain.setBackground(Color.WHITE);
        panelMain.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        // Frame -> Main -> Table
        createTable();
        JScrollPane scrollPaneTable = new JScrollPane(tableLopOfMon);
        scrollPaneTable.setBackground(Color.WHITE);
        scrollPaneTable.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        scrollPaneTable.setPreferredSize(new Dimension(880, scrollPaneTable.getPreferredSize().height));

        panelMain.add(panelFilter, BorderLayout.PAGE_START);
        panelMain.add(scrollPaneTable, BorderLayout.CENTER);

        // Frame -> Sidebar
        JPanel panelSidebar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelSidebar.setBackground(Color.WHITE);
        panelSidebar.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        JPanel form = createPanelForm();
        panelSidebar.add(form);

        mainFrame.add(panelHeader, BorderLayout.PAGE_START);
        mainFrame.add(panelMain, BorderLayout.CENTER);
        mainFrame.add(panelSidebar, BorderLayout.LINE_END);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }

    public JComboBox<SimpleComboBoxItem> createComboBox(SimpleComboBoxItem placeholder) {
        Vector<SimpleComboBoxItem> defaultList = new Vector<>();
        defaultList.add(placeholder);

        JComboBox<SimpleComboBoxItem> comboBox = new JComboBox<>(new SimpleComboBoxModel(defaultList));
        comboBox.setPrototypeDisplayValue(placeholder);

        return comboBox;
    }

    public JComboBox<SimpleComboBoxItem> createComboBoxMaLop() {
        return createComboBox(new SimpleComboBoxItem("all", "Chọn Lớp"));
    }

    public JComboBox<SimpleComboBoxItem> createComboBoxMaMon() {
        return createComboBox(new SimpleComboBoxItem("all", "Chọn Môn"));
    }

    public JRadioButton createRadioButton(String label, int width, int height, int alignment) {
        JRadioButton radioButton = new JRadioButton(label);
        radioButton.setPreferredSize(new Dimension(width, height));
        radioButton.setBackground(Color.decode("#f5f5f5"));
        radioButton.setHorizontalAlignment(alignment);

        return radioButton;
    }

    public GridBagConstraints createFormConstraints(int x, int y, int width, int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.insets = new Insets(paddingTop, paddingLeft, paddingBottom, paddingRight);

        return c;
    }

    public GridBagConstraints createFormConstraints(int x, int y, int width) {
        return createFormConstraints(x, y, width, 0, 0, 0, 0);
    }

    public GridBagConstraints createFormConstraints(int x, int y, int width, int paddingHorizontal, int paddingVertical) {
        return createFormConstraints(x, y, width, paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
    }

    public String getComboBoxValue(JComboBox<SimpleComboBoxItem> comboBox) {
        SimpleComboBoxItem item = (SimpleComboBoxItem) comboBox.getSelectedItem();
        if (item != null) return item.getValue();
        return "";
    }

    public void setTableColumnWidth(JTable table, int columnIndex, int width) {
        table.getColumnModel().getColumn(columnIndex).setMinWidth(width);
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(width);
        table.getColumnModel().getColumn(columnIndex).setWidth(width);
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
        };
    }

    JPanel createPanelFilter() {
        JPanel panelFilter = new JPanel();
        panelFilter.setLayout(new BoxLayout(panelFilter, BoxLayout.X_AXIS));
        panelFilter.setBackground(Color.WHITE);
        panelFilter.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        comboBoxMaMonFilter = createComboBoxMaMon();
        comboBoxMaLopFilter = createComboBoxMaLop();

        comboBoxMaLopFilter.addActionListener(e -> {
            SimpleComboBoxItem maLopItem = (SimpleComboBoxItem) comboBoxMaLopFilter.getSelectedItem();
            if (maLopItem != null) {
                String maLop = maLopItem.getValue();
                System.out.println(maLop);
                new GetComboBoxMaMonThread(comboBoxMaMonFilter, maLop).start();
            }
        });

        JButton buttonGet = new JButton("Xem danh sách");
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
        buttonXemThongKe.addActionListener(e -> {
            String maLop = getComboBoxValue(comboBoxMaLopFilter);
            String maMon = getComboBoxValue(comboBoxMaMonFilter);

            if (!maLop.equals("all") && !maMon.equals("all")) {
                new LopOfMonThongKeView(maLop, maMon);
            } else {
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn Lớp và Môn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        panelFilter.add(comboBoxMaLopFilter);
        panelFilter.add(Box.createRigidArea(new Dimension(8, 0)));
        panelFilter.add(comboBoxMaMonFilter);
        panelFilter.add(Box.createRigidArea(new Dimension(8, 0)));
        panelFilter.add(buttonGet);
        panelFilter.add(Box.createRigidArea(new Dimension(8, 0)));
        panelFilter.add(buttonXemThongKe);
        panelFilter.add(Box.createHorizontalGlue());

        buttonImportCSV = new JButton("Nhập Bảng Điểm CSV");
        panelFilter.add(buttonImportCSV);

        return panelFilter;
    }

    void createTable() {
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

        tableLopOfMon = new JTable(new SimpleTableModel(columnNames, null));
        tableLopOfMon.setFillsViewportHeight(true);

        setTableColumnWidth(tableLopOfMon, 0, 64);
        setTableColumnWidth(tableLopOfMon, 1, 64);

        setTableColumnWidth(tableLopOfMon, 3, 64);
        setTableColumnWidth(tableLopOfMon, 4, 112);
        setTableColumnWidth(tableLopOfMon, 5, 64);

        for (int i = 6; i <= 9; ++i) {
            setTableColumnWidth(tableLopOfMon, i, 72);
        }

        setTableColumnWidth(tableLopOfMon, 10, 64);

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
        form.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        radioButtonUpdate = createRadioButton("Sửa Điểm", 96, 24, SwingConstants.CENTER);
        radioButtonUpdate.setSelected(true);
        radioButtonUpdate.addActionListener(e -> {
            System.out.println("radioButtonUpdate " + radioButtonUpdate.isSelected());
            setFormValuesBySeletedRow();
            setBasicInfoFormEnabled(false);
            setDiemInfoFormEnabled(true);
        });

        radioButtonDelete = createRadioButton("Xóa SV", 96, 24, SwingConstants.CENTER);
        radioButtonDelete.addActionListener(e -> {
            System.out.println("radioButtonDelete " + radioButtonDelete.isSelected());
            setFormValuesBySeletedRow();
            setFormEnabled(false);
        });

        radioButtonCreate = createRadioButton("Thêm SV", 96, 24, SwingConstants.CENTER);
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

        form.add(radioButtonUpdate, createFormConstraints(0, 0, 1));
        form.add(radioButtonDelete, createFormConstraints(1, 0, 1));
        form.add(radioButtonCreate, createFormConstraints(2, 0, 1));

        form.add(new JLabel("Lớp"), createFormConstraints(0, 1, 1, 8, 8, 8, 0));
        comboBoxMaLopForm.setEnabled(false);
        form.add(comboBoxMaLopForm, createFormConstraints(1, 1, 2, 8, 0));

        form.add(new JLabel("Môn"), createFormConstraints(0, 2, 1));
        comboBoxMaMonForm.setEnabled(false);
        form.add(comboBoxMaMonForm, createFormConstraints(1, 2, 2));

        form.add(new JLabel("MSSV"), createFormConstraints(0, 3, 1, 8, 0));
        textFieldMaSinhVien = new JTextField();
        textFieldMaSinhVien.setEnabled(false);
        form.add(textFieldMaSinhVien, createFormConstraints(1, 3, 2, 8, 0));

        form.add(new JLabel("Họ Tên"), createFormConstraints(0, 4, 1));
        textFieldHoTen = new JTextField();
        textFieldHoTen.setEnabled(false);
        form.add(textFieldHoTen, createFormConstraints(1, 4, 2));

        form.add(new JLabel("Điểm GK"), createFormConstraints(0, 5, 1, 8, 0));
        spinnerDiemGK = new JSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        form.add(spinnerDiemGK, createFormConstraints(1, 5, 2, 8, 0));

        form.add(new JLabel("Điểm CK"), createFormConstraints(0, 6, 1));
        spinnerDiemCK = new JSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        form.add(spinnerDiemCK, createFormConstraints(1, 6, 2));

        form.add(new JLabel("Điểm Khác"), createFormConstraints(0, 7, 1, 8, 0));
        spinnerDiemKhac = new JSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        form.add(spinnerDiemKhac, createFormConstraints(1, 7, 2, 8, 0));

        form.add(new JLabel("Điểm Tổng"), createFormConstraints(0, 8, 1, 0, 8, 0, 0));
        spinnerDiemTong = new JSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        form.add(spinnerDiemTong, createFormConstraints(1, 8, 2));

        buttonSubmit = new JButton("Thực Hiện");
        form.add(buttonSubmit, createFormConstraints(0, 9, 3, 8, 0, 0, 0));

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

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }
}
