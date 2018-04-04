package Controller.View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.*;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class BarChart extends ApplicationFrame {
    public BarChart(String title, double[] data, int[] time) {
        super(title);
        IntervalXYDataset dataset = createDataset(data, time);
        JFreeChart chart = createChart(dataset, title);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setRangeZoomable(true);
        chartPanel.setDomainZoomable(true);
        chartPanel.setMouseWheelEnabled(true);
//        chartPanel.setPreferredSize(new Dimension(500, 270));
        this.setContentPane(chartPanel);
    }


    public BarChart(String title, double[] data1, double[] data2) {
        super(title);
        IntervalXYDataset dataset1 = createDataset(data1, IntStream.range(0, data1.length).toArray());
        IntervalXYDataset dataset2 = createDataset(data2, IntStream.range(0, data2.length).toArray());
        JFreeChart chart1 = createChart(dataset1, title);
        JFreeChart chart2 = createChart(dataset2, title);
        ChartPanel chartPanel1 = new ChartPanel(chart1);
        ChartPanel chartPanel2 = new ChartPanel(chart2);
        chartPanel1.setDomainZoomable(true);
        chartPanel2.setDomainZoomable(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel1,BorderLayout.WEST);
        panel.add(chartPanel2,BorderLayout.EAST);

        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

//        chartPanel.setFillZoomRectangle(true);
//        chartPanel.setRangeZoomable(true);
//        chartPanel.setDomainZoomable(true);
//        chartPanel.setMouseWheelEnabled(true);
////        chartPanel.setPreferredSize(new Dimension(500, 270));
//        this.setContentPane(chartPanel);
    }

    private static IntervalXYDataset createDataset(double[] data, int[] time) {
        //legend or key goes here
        final XYSeries series = new XYSeries("");


        for (int i = 0; i < data.length; i++) {
            series.add(time[i], data[i]);
        }

        IntervalXYDataset dataset = new XYBarDataset(new XYSeriesCollection(series), 1.0);
        return dataset;

    }

    private static JFreeChart createChart(IntervalXYDataset dataset, String chartName) {
        JFreeChart chart = ChartFactory.createXYBarChart(chartName, "Frequency",false, "Amplitude", dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot)chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();

        ValueAxis domainValueAxis = plot.getDomainAxis();
        ValueAxis rangeValueAxis = plot.getRangeAxis();

//        domainValueAxis.setRange(0.0, 500);
        rangeValueAxis.setRange(-70, 70);

        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYBarRenderer renderer = (XYBarRenderer)plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        GradientPaint gp0 = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, 64));
        GradientPaint gp1 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, new Color(0, 64, 0));
        GradientPaint gp2 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(64, 0, 0));
        renderer.setSeriesPaint(0, gp0);

        return chart;
    }
}
