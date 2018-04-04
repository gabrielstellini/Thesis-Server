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

        List<Double> normScoredDocs = new ArrayList<Double>(dataPoints.size());
        for (int i = 0; i < dataPoints.size(); i++) {
            Double aDouble = (dataPoints.get(i).getGSR() - mu) / sigma;
            normScoredDocs.add(aDouble);
        }

        double[] normalisedDataArr = normScoredDocs.stream().mapToDouble(d -> d).toArray();
        DoubleFFT_1D doubleFFT_1D = new DoubleFFT_1D(normalisedDataArr.length -1);
        doubleFFT_1D.realForward(normalisedDataArr);
        return normalisedDataArr;
    }

    public void showGSR(List<DataPoint> dataPoints, String title){
        showBarChart(title + "normalised", zScoreFFT(dataPoints), IntStream.range(0, dataPoints.size()).toArray());
//        showBarChart(title + "Raw GSR", dataPoints.stream().mapToDouble(DataPoint::getGSR).toArray(), IntStream.range(0, dataPoints.size()).toArray());
    }


    public boolean checkIfStressedGSR(){

        List<DataPoint> calmDataPoints = getBaselineData();
        List<DataPoint> unknownDataPoints = getUnprocessedData();

        if(calmDataPoints.size() >= 1 && unknownDataPoints.size() >= 1 ){
            double[] fftCalmDataPoints = zScoreFFT(calmDataPoints);
            double[] fftUnknownDataPoints = zScoreFFT(unknownDataPoints);

            boolean result = standardDeviationStressCalc(fftCalmDataPoints, fftUnknownDataPoints);

            System.out.println(result);
            return result;
        }else {
            return false;
        }
    }

    public boolean standardDeviationStressCalc(double[] calmStressData, double[] unknownStress){
        double calmStressMetric = standardDeviation(calmStressData, 2);
        double unknownStressMetric = standardDeviation(unknownStress, 2);
        System.out.print("Calm: " + calmStressMetric + " Stressed: " + unknownStressMetric + " ");
        return calmStressMetric > unknownStressMetric;
    }
}
