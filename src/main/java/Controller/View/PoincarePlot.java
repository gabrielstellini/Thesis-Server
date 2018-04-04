package Controller.View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.*;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;

public class PoincarePlot extends ApplicationFrame {
    public PoincarePlot(String title, double[] rrIntervalData) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(rrIntervalData);

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                title,
                "RR", "RR+1", dataset, PlotOrientation.VERTICAL, false, true, false);


        //Changes background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,228,196));

        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();

        ValueAxis domainValueAxis = plot.getDomainAxis();
        ValueAxis rangeValueAxis = plot.getRangeAxis();

//        domainValueAxis.setRange(0.0, 500);
        domainValueAxis.setRange(0, 1.6);
        rangeValueAxis.setRange(0, 1.6);


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(double[] rrInterval) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        //Boys (Age,weight) series
        XYSeries series1 = new XYSeries("Boys");

        for (int i = 0; i < rrInterval.length-1; i++) {
            series1.add(rrInterval[i], rrInterval[i+1]);
        }

        dataset.addSeries(series1);

//        dataset.addSeries(series2);

        return dataset;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            ScatterPlotExample example = new ScatterPlotExample("Scatter Chart Example | BORAJI.COM");
//            example.setSize(800, 400);
//            example.setLocationRelativeTo(null);
//            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            example.setVisible(true);
//        });
//    }
}