package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.utils.SimpleComboBoxItem;
import vn.name.ChanhDai.QuanLySinhVien.utils.SimpleComboBoxModel;
import vn.name.ChanhDai.QuanLySinhVien.utils.SimpleTableModel;
import vn.name.ChanhDai.QuanLySinhVien.dao.SinhVienDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

class GetSinhVienThread extends Thread {
    JTable table;
    String maLop;
    JButton buttonTarget;

    public GetSinhVienThread(JTable table, String maLop, JButton buttonTarget) {
        this.table = table;
        this.maLop = maLop;
        this.buttonTarget = buttonTarget;
    }

    public void run() {
        String prevButtonText = null;

        if (this.buttonTarget != null) {
            prevButtonText = this.buttonTarget.getText();
            this.buttonTarget.setText("Đang tải ...");
        }

        List<SinhVien> list;
        if (maLop.equals("all") || maLop.equals("")) {
            list = SinhVienDAO.getList();
        } else {
            list = SinhVienDAO.getListByMaLop(maLop);
        }

        SimpleTableModel model = (SimpleTableModel) table.getModel();

        // Reset Table
        int totalRow = model.getRowCount();
        for (int i = 0; i < totalRow; ++i) {
            model.removeRow(0);
        }

        for (SinhVien sinhVien : list) {
            model.addRow(sinhVien.toVector());
        }

        model.fireTableDataChanged();

        if (this.buttonTarget != null) {
            this.buttonTarget.setText(prevButtonText);
        }
    }
}

public class SinhVienView {
    JFrame frame;
    JTable tableSinhVien;
    JButton buttonGetSinhVienList;

    JTextField textFieldMaSinhVien;
    JTextField textFieldHoTen;
    JComboBox<String> comboBoxGioiTinh;
    JTextField textFieldCMND;
    JTextField textFieldMaLop;

    JRadioButton radioButtonUpdate;
    JRadioButton radioButtonCreate;
    JRadioButton radioButtonDelete;

    public SinhVienView() {
        createAndShowUI();
        new GetSinhVienThread(tableSinhVien, "all", buttonGetSinhVienList).start();
    }

    public void createSinhVien(SinhVien sinhVien) {
        boolean success = SinhVienDAO.create(sinhVien);
        if (success) {
            SimpleTableModel tableModel = (SimpleTableModel) tableSinhVien.getModel();
            tableModel.addRow(sinhVien.toVector());
            tableModel.fireTableDataChanged();

            JOptionPane.showMessageDialog(frame, "Thêm sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "Thêm sinh viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }

    public void updateSinhVien(SinhVien sinhVien, int row) {
        boolean success = SinhVienDAO.update(sinhVien);
        if (success) {
            SimpleTableModel tableModel = (SimpleTableModel) tableSinhVien.getModel();
            tableModel.updateRow(row, sinhVien.toVector());
            tableModel.fireTableDataChanged();

            JOptionPane.showMessageDialog(frame, "Cập nhật thông tin sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "Cập nhật thông tin sinh viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }

    public void deleteSinhVien(SinhVien sinhVien, int row) {
        int confirm = JOptionPane.showConfirmDialog(
            frame,
            "Bạn chắn chắn muốn xóa sinh viên " + sinhVien.getMaSinhVien() + "?",
            "Warning",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean success = SinhVienDAO.delete(sinhVien.getMaSinhVien());
        if (success) {
            SimpleTableModel tableModel = (SimpleTableModel) tableSinhVien.getModel();
            tableModel.removeRow(row);
            tableModel.fireTableDataChanged();
            tableSinhVien.setRowSelectionInterval(0, 0);

            JOptionPane.showMessageDialog(frame, "Xóa sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "Xóa sinh viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }

    public SinhVien getSeletedRow() {
        SimpleTableModel tableModel = (SimpleTableModel) tableSinhVien.getModel();

        int rowIndex = tableSinhVien.getSelectedRow();

        if (rowIndex == -1) {
            return null;
        }

        String maSinhVien = tableModel.getValueAt(rowIndex, 0).toString();
        String hoTen = tableModel.getValueAt(rowIndex, 1).toString();
        String gioiTinh = tableModel.getValueAt(rowIndex, 2).toString();
        String cmnd = tableModel.getValueAt(rowIndex, 3).toString();
        String maLop = tableModel.getValueAt(rowIndex, 4).toString();

        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSinhVien(maSinhVien);
        sinhVien.setHoTen(hoTen);
        sinhVien.setGioiTinh(gioiTinh);
        sinhVien.setCmnd(cmnd);
        sinhVien.setMaLop(maLop);

        return sinhVien;
    }

    public void setFormValuesBySeletedRow() {
        SinhVien sinhVien = getSeletedRow();
        if (sinhVien == null) return;

        this.setFormValues(
            sinhVien.getMaSinhVien(),
            sinhVien.getHoTen(),
            sinhVien.getGioiTinh(),
            sinhVien.getCmnd(),
            sinhVien.getMaLop()
        );
    }

    public void setFormEnabled(boolean enabled) {
        textFieldMaSinhVien.setEnabled(enabled);
        textFieldHoTen.setEnabled(enabled);
        comboBoxGioiTinh.setEnabled(enabled);
        textFieldCMND.setEnabled(enabled);
        textFieldMaLop.setEnabled(enabled);
    }

    public void setFormValues(String maSinhVien, String hoTen, String gioiTinh, String cmnd, String maLop) {
        textFieldMaSinhVien.setText(maSinhVien);
        textFieldHoTen.setText(hoTen);
        comboBoxGioiTinh.setSelectedItem(gioiTinh);
        textFieldCMND.setText(cmnd);
        textFieldMaLop.setText(maLop);
    }

    public void resetForm() {
        this.setFormValues("", "", "", "", "");
    }

    public void createAndShowUI() {
        frame = new JFrame();
        frame.setTitle("Danh Sách Sinh Viên");

        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);

        JLabel title = new JLabel("Danh Sách Sinh Viên");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        headerPanel.add(title);

        JLabel labelFilter = new JLabel("Lọc theo lớp");

        SimpleComboBoxItem[] maLopList = new SimpleComboBoxItem[]{
            new SimpleComboBoxItem("all", "Tất cả"),
            new SimpleComboBoxItem("17HCB", "17HCB"),
            new SimpleComboBoxItem("18HCB", "18HCB")
        };

        SimpleComboBoxModel maLopModel = new SimpleComboBoxModel(maLopList);

        JComboBox<SimpleComboBoxItem> comboBoxMaLop = new JComboBox<>(maLopModel);
        comboBoxMaLop.addActionListener(e -> {
            int index = comboBoxMaLop.getSelectedIndex();
            if (index != -1) {
                System.out.println(comboBoxMaLop.getItemAt(index).getValue());
            }
        });

        buttonGetSinhVienList = new JButton("Xem");
        buttonGetSinhVienList.addActionListener(e -> {
            SimpleComboBoxItem item = (SimpleComboBoxItem) comboBoxMaLop.getSelectedItem();
            String maLop = item != null ? item.getValue() : "all";
            new GetSinhVienThread(tableSinhVien, maLop, buttonGetSinhVienList).start();
        });

        JButton importCSVButton = new JButton("Import CSV");

        JPanel topMenuPanel = new JPanel();
        topMenuPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        topMenuPanel.setBackground(Color.WHITE);
        BoxLayout topMenuPanelLayout = new BoxLayout(topMenuPanel, BoxLayout.X_AXIS);
        topMenuPanel.setLayout(topMenuPanelLayout);
        topMenuPanel.add(labelFilter);
        topMenuPanel.add(comboBoxMaLop);
        topMenuPanel.add(buttonGetSinhVienList);
        topMenuPanel.add(Box.createHorizontalGlue());
        topMenuPanel.add(importCSVButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        centerPanel.setLayout(new BorderLayout(0, 8));
        centerPanel.add(topMenuPanel, BorderLayout.PAGE_START);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("MSSV");
        columnNames.add("Họ tên");
        columnNames.add("Giới tính");
        columnNames.add("CMND");
        columnNames.add("Lớp");

        tableSinhVien = new JTable(new SimpleTableModel(columnNames, null));
        tableSinhVien.setFillsViewportHeight(true);

        tableSinhVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSinhVien.setDefaultEditor(Object.class, null);

        ListSelectionModel selectionModel = tableSinhVien.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (radioButtonCreate.isSelected()) {
                resetForm();
            } else {
                setFormValuesBySeletedRow();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableSinhVien);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        formPanel.add(form);

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;

        radioButtonUpdate = new JRadioButton("Cập nhật");
        radioButtonUpdate.setMnemonic(KeyEvent.VK_B);
        radioButtonUpdate.setActionCommand("update");
        radioButtonUpdate.setSelected(true);
        radioButtonUpdate.addActionListener(e -> {
            System.out.println("radioButtonUpdate " + radioButtonUpdate.isSelected());

            resetForm();
            setFormValuesBySeletedRow();

            setFormEnabled(true);
            textFieldMaSinhVien.setEnabled(false);
        });

        radioButtonCreate = new JRadioButton("Thêm");
        radioButtonCreate.setMnemonic(KeyEvent.VK_C);
        radioButtonCreate.setActionCommand("create");
        radioButtonCreate.addActionListener(e -> {
            System.out.println("radioButtonCreate " + radioButtonCreate.isSelected());

            resetForm();
            setFormEnabled(true);
        });

        radioButtonDelete = new JRadioButton("Xóa");
        radioButtonDelete.setMnemonic(KeyEvent.VK_F);
        radioButtonDelete.setActionCommand("delete");
        radioButtonDelete.addActionListener(e -> {
            System.out.println("radioButtonDelete " + radioButtonDelete.isSelected());

            resetForm();
            setFormEnabled(false);
            setFormValuesBySeletedRow();
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonUpdate);
        buttonGroup.add(radioButtonCreate);
        buttonGroup.add(radioButtonDelete);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(8, 8, 0, 0);
        form.add(radioButtonUpdate, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(8, 0, 0, 0);
        form.add(radioButtonCreate, c);

        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(8, 0, 0, 8);
        form.add(radioButtonDelete, c);

        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 0;
        c.ipady = 8;
        c.insets = new Insets(0, 8, 0, 0);
        c.gridwidth = 1;
        form.add(new JLabel("MSSV"), c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        textFieldMaSinhVien = new JTextField();
        textFieldMaSinhVien.setEnabled(false);
        form.add(textFieldMaSinhVien, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.insets = new Insets(0, 8, 0, 0);
        form.add(new JLabel("Họ và tên"), c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        textFieldHoTen = new JTextField();
        form.add(textFieldHoTen, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.insets = new Insets(0, 8, 0, 0);
        form.add(new JLabel("Giới tính"), c);

        String[] gioiTinhList = {"Nam", "Nữ", "Khác"};
        comboBoxGioiTinh = new JComboBox<>(gioiTinhList);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        form.add(comboBoxGioiTinh, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.insets = new Insets(0, 8, 0, 0);
        form.add(new JLabel("CMND"), c);

        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        textFieldCMND = new JTextField();
        form.add(textFieldCMND, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        c.insets = new Insets(0, 8, 0, 0);
        form.add(new JLabel("Mã lớp"), c);

        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        textFieldMaLop = new JTextField();
        form.add(textFieldMaLop, c);

        JButton buttonSubmit = new JButton("Thực hiện");
        buttonSubmit.addActionListener(e -> {
            String maSinhVien = textFieldMaSinhVien.getText();
            String hoTen = textFieldHoTen.getText();
            String gioiTinh = (String) comboBoxGioiTinh.getSelectedItem();
            String cmnd = textFieldCMND.getText();
            String maLop = textFieldMaLop.getText();

            if (maSinhVien.equals("") || hoTen.equals("") || gioiTinh == null || gioiTinh.equals("") || cmnd.equals("") || maLop.equals("")) {
                JOptionPane.showMessageDialog(frame, "Bạn chưa nhập đủ thông tin!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SinhVien sinhVien = new SinhVien();
            sinhVien.setMaSinhVien(maSinhVien);
            sinhVien.setHoTen(hoTen);
            sinhVien.setGioiTinh(gioiTinh);
            sinhVien.setCmnd(cmnd);
            sinhVien.setMaLop(maLop);
            sinhVien.setMatKhau(maSinhVien);

            int rowSelectedIndex = tableSinhVien.getSelectedRow();

            if (radioButtonCreate.isSelected()) {

                // Create
                System.out.println("buttonSubmit -> Create");
                createSinhVien(sinhVien);

            } else if (radioButtonUpdate.isSelected()) {

                // Update
                System.out.println("buttonSubmit -> Update");
                updateSinhVien(sinhVien, rowSelectedIndex);

            } else if (radioButtonDelete.isSelected()) {

                // Delete
                System.out.println("buttonSubmit -> Delete");
                deleteSinhVien(sinhVien, rowSelectedIndex);
            }

        });

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 3;
        c.insets = new Insets(8, 8, 8, 8);
        form.add(buttonSubmit, c);

        sidebarPanel.add(formPanel);

        Container pane = frame.getContentPane();
        pane.add(headerPanel, BorderLayout.PAGE_START);
        pane.add(centerPanel, BorderLayout.CENTER);
        pane.add(sidebarPanel, BorderLayout.LINE_END);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
