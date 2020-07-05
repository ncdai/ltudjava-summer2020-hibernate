package vn.name.ChanhDai.QuanLySinhVien.view;

import vn.name.ChanhDai.QuanLySinhVien.dao.SinhVienDAO;
import vn.name.ChanhDai.QuanLySinhVien.entity.LopOfMon;
import vn.name.ChanhDai.QuanLySinhVien.entity.SinhVien;
import vn.name.ChanhDai.QuanLySinhVien.utils.SimpleTableModel;
import vn.name.ChanhDai.QuanLySinhVien.utils.TableUtils;
import vn.name.ChanhDai.QuanLySinhVien.utils.ViewUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * vn.name.ChanhDai.QuanLySinhVien.view
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/3/20 - 10:50 PM
 * @description
 */
public class SinhVienHomeView {
    JFrame frame;
    JLabel labelUser;

    LoginView loginView;
    SinhVien sinhVien;
    UpdatePasswordView updatePasswordView;

    JTable tableDiem;

    static class UpdatePasswordThread extends Thread {
        UpdatePasswordView updatePasswordView;
        JButton buttonSubmit;

        String tenDangNhap;
        String matKhauHienTai;
        String matKhauMoi;

        public UpdatePasswordThread(UpdatePasswordView updatePasswordView, JButton buttonSubmit, String tenDangNhap, String matKhauHienTai, String matKhauMoi) {
            this.updatePasswordView = updatePasswordView;
            this.buttonSubmit = buttonSubmit;
            this.tenDangNhap = tenDangNhap;
            this.matKhauHienTai = matKhauHienTai;
            this.matKhauMoi = matKhauMoi;
        }

        @Override
        public void run() {
            buttonSubmit.setEnabled(false);

            boolean success = SinhVienDAO.updatePassword(tenDangNhap, matKhauHienTai, matKhauMoi);
            buttonSubmit.setEnabled(true);

            if (success) {
                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                updatePasswordView.setVisible(false);
                return;
            }

            JOptionPane.showMessageDialog(null, "Mật khẩu hiện tại không chính xác!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public SinhVienHomeView(LoginView loginView) {
        this.loginView = loginView;
        this.updatePasswordView = new UpdatePasswordView() {
            @Override
            public void submit(String matKhauHienTai, String matKhauMoi) {
                new UpdatePasswordThread(this, buttonSubmit, sinhVien.getMaSinhVien(), matKhauHienTai, matKhauMoi).start();
            }
        };

        createUI();
    }

    private void createUI() {
        frame = new JFrame();
        frame.setTitle("Kết Quả Học Tập");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        labelUser = new JLabel();
        JPanel panelHeader = GiaoVuHomeView.createHeader("Kết Quả Học Tập", labelUser, frame, updatePasswordView, loginView);

        tableDiem = ViewUtils.createSimpleTable(new SimpleTableModel(LopOfMonView.getTableColumnNames(), null));

        ViewUtils.setTableColumnWidth(tableDiem, 0, 64);
        ViewUtils.setTableColumnWidth(tableDiem, 1, 64);

        ViewUtils.setTableColumnWidth(tableDiem, 3, 0);
        ViewUtils.setTableColumnWidth(tableDiem, 4, 0);
        ViewUtils.setTableColumnWidth(tableDiem, 5, 0);

        for (int i = 6; i <= 10; ++i) {
            ViewUtils.setTableColumnWidth(tableDiem, i, 80);
        }

        JScrollPane scrollPane = new JScrollPane(tableDiem);
        scrollPane.setPreferredSize(new Dimension(720, scrollPane.getPreferredSize().height));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        frame.add(panelHeader, BorderLayout.PAGE_START);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    void updateTableData(Set<LopOfMon> lopOfMonList) {
        SimpleTableModel tableModel = (SimpleTableModel) tableDiem.getModel();
        tableModel.clearRows();

        for (LopOfMon lopOfMon : lopOfMonList) {
            tableModel.addRow(TableUtils.toRow(lopOfMon));
        }

        tableModel.fireTableDataChanged();
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
        labelUser.setText("Hi, " + sinhVien.getHoTen() + " (" + sinhVien.getMaSinhVien() + ") !");

        Set<LopOfMon> list = sinhVien.getDanhSachLop();
        updateTableData(list);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}

//DRAFT
// new GetBangDiemThread(tableDiem, sinhVien.getMaSinhVien()).start();
//static class GetBangDiemThread extends Thread {
//    JTable table;
//    String maSinhVien;
//
//    public GetBangDiemThread(JTable table, String maSinhVien) {
//        this.table = table;
//        this.maSinhVien = maSinhVien;
//    }
//
//    @Override
//    public void run() {
//        List<LopOfMon> lopOfMonList = LopOfMonDAO.getListByMaSinhVien(maSinhVien);
//
//        SimpleTableModel tableModel = (SimpleTableModel) table.getModel();
//        tableModel.clearRows();
//
//        for (LopOfMon lopOfMon : lopOfMonList) {
//            tableModel.addRow(TableUtils.toRow(lopOfMon));
//        }
//
//        tableModel.fireTableDataChanged();
//    }
//}
