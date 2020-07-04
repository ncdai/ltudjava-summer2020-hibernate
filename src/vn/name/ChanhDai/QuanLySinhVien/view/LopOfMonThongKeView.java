package vn.name.ChanhDai.QuanLySinhVien.view;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import vn.name.ChanhDai.QuanLySinhVien.dao.LopOfMonDAO;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * vn.name.ChanhDai.QuanLySinhVien.view
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 7/3/20 - 7:28 PM
 * @description
 */
public class LopOfMonThongKeView {
    JFrame mainFrame;
    PieChart pieChart;
    JPanel panelPieChart;

    String maLop;
    String maMon;

    public LopOfMonThongKeView(String maLop, String maMon) {
        this.maLop = maLop;
        this.maMon = maMon;

        createAndShowUI();
        new GetDataThread(pieChart, panelPieChart, maLop, maMon).start();
    }

    static class GetDataThread extends Thread {
        PieChart chart;
        JPanel panelChart;
        String maLop;
        String maMon;

        GetDataThread(PieChart chart, JPanel panelChart, String maLop, String maMon) {
            this.chart = chart;
            this.panelChart = panelChart;
            this.maLop = maLop;
            this.maMon = maMon;
        }

        @Override
        public void run() {
            Map<String, Integer> statistic = LopOfMonDAO.getThongKe(maLop, maMon);
            if (statistic != null) {
                int soLuongSVQuaMon = statistic.get("soLuongSVQuaMon");
                int soLuongSVRotMon = statistic.get("soLuongSVRotMon");
                int soLuongKhongCoDiem = statistic.get("soLuongSVKhongDiem");

                chart.getSeriesMap().clear();

                chart.addSeries("Qua Môn (" + soLuongSVQuaMon + " Sinh Viên)", soLuongSVQuaMon);
                chart.addSeries("Rớt Môn (" + soLuongSVRotMon + " Sinh Viên)", soLuongSVRotMon);
                if (soLuongKhongCoDiem > 0) {
                    chart.addSeries("Không Có Điểm (" + soLuongKhongCoDiem + " Sinh Viên)", soLuongKhongCoDiem);
                }

                panelChart.repaint();
            }
        }
    }

    void createAndShowUI() {
        pieChart = new PieChartBuilder()
            .width(800)
            .height(600)
            .title("Lớp " + maLop + "-" + maMon)
            .theme(Styler.ChartTheme.XChart)
            .build();

        pieChart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);
        pieChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideS);
        pieChart.getStyler().setAnnotationDistance(1.15);
        pieChart.getStyler().setPlotContentSize(.7);
        pieChart.getStyler().setStartAngleInDegrees(90);

        mainFrame = new JFrame("Thống kê Số Lượng / Phần Trăm Qua / Rớt Môn");
        mainFrame.setLayout(new BorderLayout());

        panelPieChart = new XChartPanel<>(pieChart);
        mainFrame.add(panelPieChart, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
