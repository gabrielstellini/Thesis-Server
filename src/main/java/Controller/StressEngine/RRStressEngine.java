package Controller.StressEngine;

import Model.DatabaseEntities.DataPoint;
import Service.DataPointMetaService;
import Service.DataPointService;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class RRStressEngine extends Engine{

    public RRStressEngine(DataPointService dataPointService, DataPointMetaService dataPointMetaService){
        super(dataPointService, dataPointMetaService);
    }

    /**
     * Compares calm data points to the unprocessed data points
     * @return stress detection
     */
    public boolean checkIfStressedRR(){
        List<DataPoint> calmDataPoints = getBaselineData();
        double[] fftCalmDataPoints = doRRFFT(calmDataPoints);

        List<DataPoint> unknownDataPoints = getUnprocessedData();
        double[] fftUnknownDataPoints = doRRFFT(unknownDataPoints);

        boolean result = isStressed(fftCalmDataPoints, fftUnknownDataPoints);

        System.out.println(result);
        return result;
    }

    /**
     * Runs FFT on GSR data points
     * @param dataPointList data
     * @return fft result
     */
    private double[] doRRFFT(List<DataPoint> dataPointList){
        //convert to the correct type
        LinkedList<Double> RRs = new LinkedList<>();

        for (DataPoint dp: dataPointList) {
            RRs.add(dp.getRRInterval());
        }
        double[] RRsAsArr = RRs.stream().mapToDouble(i->i).toArray();

        //run fft
        DoubleFFT_1D doubleFFT_1D = new DoubleFFT_1D(dataPointList.size() -1);
        doubleFFT_1D.realForward(RRsAsArr);
        return RRsAsArr;
    }

    /**
     * Calculates standard deviation for both datasets and checks if the standard deviation is smaller for calm
     * @param calmStressData calm data set
     * @param unknownStress unclassified data set
     * @return standard deviation is smaller for calm
     */
    private boolean standardDeviationStressCalc(double[] calmStressData, double[] unknownStress){
        double calmStressMetric = standardDeviation(calmStressData, 2);
        double unknownStressMetric = standardDeviation(unknownStress, 2);
        System.out.print("RR Calm: " + calmStressMetric + " Stressed: " + unknownStressMetric + " ");
        return calmStressMetric < unknownStressMetric;
    }


    /**
     * Returns whether the unclassified dataset is stressed
     * @param calmStressMetric
     * @param unknownStressMetric
     * @return
     */
    private boolean isStressed(double[] calmStressMetric, double[] unknownStressMetric){
        return standardDeviationStressCalc(calmStressMetric, unknownStressMetric);
//        return true;
//        return isStressed(calculateStressMetric(calmStressMetric), calculateStressMetric(unknownStressMetric));
    }


    /**
     * Shows FFT graph for a set of data points
     * @param chartName chart title
     * @param dataPointList data
     */
    public void showFFT(String chartName, List<DataPoint> dataPointList){
        cleanData(dataPointList);
        double[] fftResult = doRRFFT(dataPointList);
        int[] numList = IntStream.range(0, dataPointList.size()).toArray();
        showBarChart(chartName, fftResult, numList);
    }

    /**
     * Shows unprocessed data points and calm data points against each other
     * @param chartName chart title
     */
    public void showFFTs(String chartName){

        List<DataPoint> dataPointList1 = getBaselineData();
        List<DataPoint> dataPointList2 = getUnprocessedData();

        double[] fftResult1 = doRRFFT(dataPointList1);
        double[] fftResult2 = doRRFFT(dataPointList2);

        showBarChart(chartName, fftResult1, fftResult2);
    }
}
