package Controller.StressEngine;


import Controller.View.BarChart;
import Controller.View.PoincarePlot;
import Model.DataPointType;
import Model.DatabaseEntities.DataPoint;
import Model.DatabaseEntities.DataPointMetaData;
import Service.DataPointMetaService;
import Service.DataPointService;
import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import org.jfree.ui.RefineryUtilities;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Engine {

    DataPointService dataPointService;
    DataPointMetaService dataPointMetaService;

    public Engine(DataPointService dataPointService, DataPointMetaService dataPointMetaService){
        this.dataPointService = dataPointService;
        this.dataPointMetaService = dataPointMetaService;
    }

    public void showRRScatterChart(String chartName, String fileLocation, List<DataPoint> dataPointList){
        //shows chart using data in file
        cleanData(dataPointList);
        double[] RRsAsArr = dataPointList.stream().map(DataPoint::getRRInterval).mapToDouble(i->i).toArray();

        PoincarePlot scatterChart = new PoincarePlot(chartName, RRsAsArr);
        scatterChart.pack();
        RefineryUtilities.centerFrameOnScreen(scatterChart);
        scatterChart.setVisible(true);
    }

    protected DataPoint[] cleanData(DataPoint[] data) {
        // Remove the current element from the iterator and the list.
        List<DataPoint> dataAsList = new java.util.ArrayList<>(Arrays.asList(data));
        dataAsList.removeIf(dataPoint -> !dataPoint.getQuality().equals("LOCKED") || !dataPoint.getContactStatus().equals("WORN"));
        return dataAsList.toArray(new DataPoint[dataAsList.size()]);
    }

    protected void cleanData(List<DataPoint> data) {
        // Remove the current element from the iterator and the list.
        data.removeIf(dataPoint -> dataPoint.getQuality().equals("LOCKED") || !dataPoint.getContactStatus().equals("WORN"));
    }

    protected void showBarChart(String name, double[] dataset, int[] time ){
        BarChart demo = new BarChart(name, dataset, time);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    protected void showBarChart(String name, double[] dataset1, double[] dataset2){
        BarChart demo = new BarChart(name, dataset1, dataset2);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    protected List<DataPoint> getBaselineData(){
        return getData(DataPointType.BASELINE);
    }

    protected List<DataPoint> getUnprocessedData(){
        return getData(DataPointType.UNPROCESSED);
    }

    private List<DataPoint> getData(DataPointType dataPointType){
        //Current user
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationJsonWebToken authenticationJsonWebToken = (AuthenticationJsonWebToken) authentication;

        String oauthId = authenticationJsonWebToken.getName();

        DataPointMetaData[] dataPointMetaDatas = dataPointMetaService.findByGoogleId(oauthId);

        int baselineId = -1;

        for (DataPointMetaData dataPointMetaData: dataPointMetaDatas){
            if(dataPointMetaData.isBaseline() && dataPointType == DataPointType.BASELINE){
                baselineId = dataPointMetaData.id;
            }

            if(dataPointMetaData.getStressStatus() == 2 && dataPointType == DataPointType.UNPROCESSED){
                baselineId = dataPointMetaData.id;
            }
        }

        DataPoint[] dataPoints = dataPointService.findByDataPointMetaDataId(baselineId);
        dataPoints = cleanData(dataPoints);
        return Arrays.asList(dataPoints);
    }

    //calculates standard deviation of first point after the
    protected double standardDeviation(double[] numbers, int iterations){

        double mean = 0.0;
        double stdDeviation = 0;

        for (int i = 1; i < iterations; i++) {
            mean += numbers[i];
        }

        mean /= iterations;

        for (int i = 1; i < iterations; i++) {
            stdDeviation += Math.pow(numbers[i]-mean, 2);
        }

        stdDeviation /= iterations;
        return Math.sqrt(stdDeviation);
    }

}
