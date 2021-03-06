package Controller.StressEngine;


import Model.DatabaseEntities.DataPoint;
import Service.DataPointMetaService;
import Service.DataPointService;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GSRStressEngine extends Engine{

    public GSRStressEngine(DataPointService dataPointService, DataPointMetaService dataPointMetaService){
        super(dataPointService, dataPointMetaService);
    }

    /**
     * Used to return points passed through zScore and FFT
     * Deprecated as FFT on it's own was more effective
     * @param dataPoints input data from band
     * @return result
     */
    private double[] zScoreFFT(List<DataPoint> dataPoints){
        double mu = 0;
        double temp = 0;

        for (int i = 0; i < dataPoints.size(); i++) {
            double x = dataPoints.get(i).getGSR();

            double delta = x - mu;
            mu += delta / (i + 1);

            if (i > 0) {
                temp += delta * (x - mu);
            }
        }

        double sigma = Math.sqrt(temp / (dataPoints.size() - 1));
        if (sigma == 0) {
            sigma = 1;
        }

        List<Double> normScoredDocs = new ArrayList<>(dataPoints.size());
        for (DataPoint dataPoint : dataPoints) {
            Double aDouble = (dataPoint.getGSR() - mu) / sigma;
            normScoredDocs.add(aDouble);
        }

        double[] normalisedDataArr = normScoredDocs.stream().mapToDouble(d -> d).toArray();
        DoubleFFT_1D doubleFFT_1D = new DoubleFFT_1D(normalisedDataArr.length -1);
        doubleFFT_1D.realForward(normalisedDataArr);
        return normalisedDataArr;
    }

    /**
     * Passes data points through FFT
     * @param dataPoints input data from band
     * @return datapoint values
     */
    private double[] scoreFFT(List<DataPoint> dataPoints){
        List<Double> normScoredDocs = new ArrayList<>(dataPoints.size());
        for (DataPoint dataPoint : dataPoints) {
            double aDouble = dataPoint.getGSR();
            normScoredDocs.add(aDouble);
        }

        double[] normalisedDataArr = normScoredDocs.stream().mapToDouble(d -> d).toArray();
        DoubleFFT_1D doubleFFT_1D = new DoubleFFT_1D(normalisedDataArr.length -1);
        doubleFFT_1D.realForward(normalisedDataArr);
        return normalisedDataArr;
    }

    public void showGSR(List<DataPoint> dataPoints, String title){
        showBarChart(title, scoreFFT(dataPoints), IntStream.range(0, dataPoints.size()).toArray());
    }

    /**
     * Compares 2 sets of data points for a user - one which was used for calibration and the unclassified set
     * @return true when unknown is larger than calm data points
     */
    public boolean checkIfStressedGSR(){
        List<DataPoint> calmDataPoints = getBaselineData();
        List<DataPoint> unknownDataPoints = getUnprocessedData();

        if(calmDataPoints.size() >= 1 && unknownDataPoints.size() >= 1 ){
            double[] fftCalmDataPoints = scoreFFT(calmDataPoints);
            double[] fftUnknownDataPoints = scoreFFT(unknownDataPoints);
            boolean result = standardDeviationStressCalc(fftCalmDataPoints, fftUnknownDataPoints);
            System.out.println(result);
            return result;
        }else {
            return false;
        }
    }

    /**
     *
     * @param calmStressData
     * @param unknownStress
     * @return
     */
    private boolean standardDeviationStressCalc(double[] calmStressData, double[] unknownStress){
        double calmStressMetric = standardDeviation(calmStressData, 2);
        double unknownStressMetric = standardDeviation(unknownStress, 2);
        System.out.print("GSR Calm: " + calmStressMetric + " Stressed: " + unknownStressMetric + " ");
        return calmStressMetric > unknownStressMetric;
    }
}
