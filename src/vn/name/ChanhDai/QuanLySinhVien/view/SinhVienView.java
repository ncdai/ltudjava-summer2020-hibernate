package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.utils.SimpleTableModel;
import vn.name.ChanhDai.QuanLySinhVien.dao.SinhVienDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

class GetSinhVienThread extends Thread {
    JTable table;
    JButton button;

    public GetSinhVienThread(JTable table, JButton button) {
        this.table = table;
        this.button = button;
    }

    public void run() {
        String prevButtonText = this.button.getText();
        this.button.setText("Getting ...");
        List<SinhVien> list = SinhVienDAO.getList();

        SimpleTableModel model = (SimpleTableModel) table.getModel();

        int totalRow = model.getRowCount();
        for (int i = 0; i < totalRow; ++i) {
            model.removeRow(0);
        }

        for (SinhVien sinhVien : list) {
            Vector<String> row = new Vector<>();
            row.add(sinhVien.getMaSinhVien());
            row.add(sinhVien.getHoTen());
            row.add(sinhVien.getGioiTinh());
            row.add(sinhVien.getCmnd());
            row.add(sinhVien.getMaLop());

            model.addRow(row);
        }

        model.fireTableDataChanged();

        this.button.setText(prevButtonText);
    }
}

public class SinhVienView {
    JFrame sinhVienListFrame;
    JTable sinhVienTable;

    JTextField mssvInput;
    JTextField hoTenInput;
    JComboBox<String> selectGioiTinh;
    JTextField cmndInput;
    JTextField maLopInput;

    public SinhVienView() {
        createAndShowUI();
    }

    JLabel createFormLabel(String text) {
        return new JLabel(text);
    }

    JButton createButton(String text) {
        return new JButton(text);
    }

    public void createAndShowUI() {
        this.sinhVienListFrame = new JFrame();
        this.sinhVienListFrame.setTitle("Danh Sách Sinh Viên");

        BorderLayout layout = new BorderLayout();
        this.sinhVienListFrame.setLayout(layout);

        JLabel title = new JLabel("Danh Sách Sinh Viên");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        headerPanel.add(title);

        String[] maLopList = {"17HCB", "18HCB"};
        JComboBox<String> selectMaLop = new JComboBox<>(maLopList);

        JButton buttonGet = new JButton("Xem");
        buttonGet.addActionListener(e -> {
            new GetSinhVienThread(sinhVienTable, buttonGet).start();
        });

        JButton importCSVButton = new JButton("Import CSV");

        JPanel topMenuPanel = new JPanel();
        topMenuPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        topMenuPanel.setBackground(Color.WHITE);
        BoxLayout topMenuPanelLayout = new BoxLayout(topMenuPanel, BoxLayout.X_AXIS);
        topMenuPanel.setLayout(topMenuPanelLayout);
        topMenuPanel.add(selectMaLop);
        topMenuPanel.add(buttonGet);
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

        sinhVienTable = new JTable(new SimpleTableModel(columnNames, null));
        sinhVienTable.setFillsViewportHeight(true);
        sinhVienTable.setAutoCreateColumnsFromModel(true);

        sinhVienTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sinhVienTable.setDefaultEditor(Object.class, null);

        ListSelectionModel rowSM = sinhVienTable.getSelectionModel();
        rowSM.addListSelectionListener(e -> {
            SimpleTableModel tableModel = (SimpleTableModel) sinhVienTable.getModel();

            int rowIndex = sinhVienTable.getSelectedRow();

            String maSinhVien = tableModel.getValueAt(rowIndex, 0).toString();
            String hoTen = tableModel.getValueAt(rowIndex, 1).toString();
            String gioiTinh = tableModel.getValueAt(rowIndex, 2).toString();
            String cmnd = tableModel.getValueAt(rowIndex, 3).toString();
            String maLop = tableModel.getValueAt(rowIndex, 4).toString();

            mssvInput.setText(maSinhVien);
            hoTenInput.setText(hoTen);
            selectGioiTinh.setSelectedItem(gioiTinh);
            cmndInput.setText(cmnd);
            maLopInput.setText(maLop);
        });

        JScrollPane scrollPane = new JScrollPane(sinhVienTable);
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
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 8;
        c.insets = new Insets(8, 8, 0, 0);
        c.gridwidth = 1;
        form.add(createFormLabel("MSSV"), c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(8, 0, 0, 8);
        mssvInput = new JTextField();
        form.add(mssvInput, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(0, 8, 0, 0);
        form.add(createFormLabel("Họ và tên"), c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        hoTenInput = new JTextField();
        form.add(hoTenInput, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.insets = new Insets(0, 8, 0, 0);
        form.add(createFormLabel("Giới tính"), c);

        String[] gioiTinhList = {"Nam", "Nữ", "Khác"};
        selectGioiTinh = new JComboBox<>(gioiTinhList);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        form.add(selectGioiTinh, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.insets = new Insets(0, 8, 0, 0);
        form.add(createFormLabel("CMND"), c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        cmndInput = new JTextField();
        form.add(cmndInput, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.insets = new Insets(0, 8, 0, 0);
        form.add(createFormLabel("Mã lớp"), c);

        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 8);
        maLopInput = new JTextField();
        form.add(maLopInput, c);

        JButton buttonDelete = createButton("Xóa");
        JButton buttonCreate = createButton("Thêm");
        JButton buttonUpdate = createButton("Cập nhật");

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        c.insets = new Insets(16, 8, 8, 0);
        form.add(buttonDelete, c);

        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.insets = new Insets(16, 0, 8, 0);
        form.add(buttonCreate, c);

        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        c.insets = new Insets(16, 0, 8, 8);
        form.add(buttonUpdate, c);

        sidebarPanel.add(formPanel);

        Container pane = this.sinhVienListFrame.getContentPane();
        pane.add(headerPanel, BorderLayout.PAGE_START);
        pane.add(centerPanel, BorderLayout.CENTER);
        pane.add(sidebarPanel, BorderLayout.LINE_END);

        this.sinhVienListFrame.pack();
        this.sinhVienListFrame.setLocationRelativeTo(null);
        this.sinhVienListFrame.setVisible(true);
    }
}
