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

import java.util.Arrays;
import java.util.List;

public abstract class Engine {

    private DataPointService dataPointService;
    private DataPointMetaService dataPointMetaService;

    Engine(DataPointService dataPointService, DataPointMetaService dataPointMetaService){
        this.dataPointService = dataPointService;
        this.dataPointMetaService = dataPointMetaService;
    }

    /***
     * Shows scatter chart with requested data points
     * @param chartName chart title
     * @param dataPointList data
     */
    public void showRRScatterChart(String chartName, List<DataPoint> dataPointList){
        //shows chart using data in file
        cleanData(dataPointList);
        double[] RRsAsArr = dataPointList.stream().map(DataPoint::getRRInterval).mapToDouble(i->i).toArray();

        PoincarePlot scatterChart = new PoincarePlot(chartName, RRsAsArr);
        scatterChart.pack();
        RefineryUtilities.centerFrameOnScreen(scatterChart);
        scatterChart.setVisible(true);
    }

    /***
     * Removes datapoints which are not valid in a different array
     * @param data noisy data
     */
    private DataPoint[] cleanData(DataPoint[] data) {
        // Remove the current element from the iterator and the list.
        List<DataPoint> dataAsList = new java.util.ArrayList<>(Arrays.asList(data));
        dataAsList.removeIf(dataPoint -> !dataPoint.getQuality().equals("LOCKED") || !dataPoint.getContactStatus().equals("WORN"));
        return dataAsList.toArray(new DataPoint[dataAsList.size()]);
    }

    /***
     * Removes datapoints which are not valid in the same array
     * @param data noisy data
     */
    void cleanData(List<DataPoint> data) {
        // Remove the current element from the iterator and the list.
        data.removeIf(dataPoint -> !dataPoint.getQuality().equals("LOCKED") || !dataPoint.getContactStatus().equals("WORN"));
    }

    /**
     * Shows one bar chart
     * @param name name of chart
     * @param dataset y axis data
     * @param time x axis data
     */
    void showBarChart(String name, double[] dataset, int[] time){
        BarChart demo = new BarChart(name, dataset, time);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    /**
     * Shows bar chart with 2 datasets
     * @param name of chart
     * @param dataset1 data set to be shown on the left
     * @param dataset2 data set to be shown on the right
     */
    void showBarChart(String name, double[] dataset1, double[] dataset2){
        BarChart demo = new BarChart(name, dataset1, dataset2);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    List<DataPoint> getBaselineData(){
        return getData(DataPointType.BASELINE);
    }

    List<DataPoint> getUnprocessedData(){
        return getData(DataPointType.UNPROCESSED);
    }

    /**
     * Retrieves datapoints for a user
     * @param dataPointType type of datapoint requested (STRESSED, CALM, UNPROCESSED, BASELINE)
     * @return datapoints which fall under the datapointType category
     */
    private List<DataPoint> getData(DataPointType dataPointType){
        //Get current user
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


    /**
     * calculates standard deviation of the specified number of points
     * @param numbers standard deviation calculated
     * @param iterations number of datapoints to take into consideration
     * @return overall standard deviation
     */
    double standardDeviation(double[] numbers, int iterations){
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
