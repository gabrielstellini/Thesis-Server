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

    public boolean checkIfStressedRR(){
        List<DataPoint> calmDataPoints = getBaselineData();
        double[] fftCalmDataPoints = doRRFFT(calmDataPoints);

        List<DataPoint> unknownDataPoints = getUnprocessedData();
        double[] fftUnknownDataPoints = doRRFFT(unknownDataPoints);

        boolean result = isStressed(fftCalmDataPoints, fftUnknownDataPoints);

        System.out.println(result);
        return result;
    }

    private double[] doRRFFT(List<DataPoint> dataPointList){
        //convert to the correct type

        LinkedList<Double> RRs = new LinkedList<>();

        for (DataPoint dp: dataPointList) {
            RRs.add(dp.getRRInterval());
        }


        double[] RRsAsArr = RRs.stream().mapToDouble(i->i).toArray();


//        System.out.println("Starting FFT");
        //run fft
        DoubleFFT_1D doubleFFT_1D = new DoubleFFT_1D(dataPointList.size() -1);
        doubleFFT_1D.realForward(RRsAsArr);
        return RRsAsArr;
    }

    private boolean standardDeviationStressCalc(double[] calmStressData, double[] unknownStress){
        double calmStressMetric = standardDeviation(calmStressData, 2);
        double unknownStressMetric = standardDeviation(unknownStress, 2);
        System.out.print("RR Calm: " + calmStressMetric + " Stressed: " + unknownStressMetric + " ");
        return calmStressMetric < unknownStressMetric;
    }



    private boolean isStressed(double[] calmStressMetric, double[] unknownStressMetric){
        return standardDeviationStressCalc(calmStressMetric, unknownStressMetric);
//        return true;
//        return isStressed(calculateStressMetric(calmStressMetric), calculateStressMetric(unknownStressMetric));
    }


    public void showFFT(String chartName, List<DataPoint> dataPointList){
        cleanData(dataPointList);
        double[] fftResult = doRRFFT(dataPointList);
        int[] numList = IntStream.range(0, dataPointList.size()).toArray();
        showBarChart(chartName, fftResult, numList);
    }

    public void showFFTs(String chartName, List<DataPoint> dataPointList){

        List<DataPoint> dataPointList1 = getBaselineData();
        List<DataPoint> dataPointList2 = getUnprocessedData();

        double[] fftResult1 = doRRFFT(dataPointList1);
        double[] fftResult2 = doRRFFT(dataPointList2);

        showBarChart(chartName, fftResult1, fftResult2);
    }
}
