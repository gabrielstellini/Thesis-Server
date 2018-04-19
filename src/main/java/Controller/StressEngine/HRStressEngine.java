package Controller.StressEngine;

import Model.DatabaseEntities.DataPoint;
import Service.DataPointMetaService;
import Service.DataPointService;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

import java.util.LinkedList;
import java.util.List;

public class HRStressEngine extends Engine{

    public HRStressEngine(DataPointService dataPointService, DataPointMetaService dataPointMetaService){
        super(dataPointService, dataPointMetaService);
    }

    public double calculateStressMetric(double[] fftData){
        double currMax = fftData[1];
        return currMax;
    }

    /**
     * Deprecated simple stress calculator which compares +10% of calm to the unknown data set
     * @param calmStressMetric
     * @param unknownStressMetric
     * @return
     */
    public boolean simpleStressCalc(double calmStressMetric, double unknownStressMetric){
        double upperboundStressMetric = calmStressMetric * 110 /100;

        if(unknownStressMetric < upperboundStressMetric){
            return false;
        }
        return true;
    }

    /**
     * Deprecated as RR is more accurate (accuracy of 66%) and both are RR and BPM based on the heart
     * Performs FFT on heart rate
     * @param dataPointList
     * @return
     */
    private double[] doHRFFT(List<DataPoint> dataPointList){
        //convert to the correct type

        LinkedList<Double> HRs = new LinkedList<>();

        for (DataPoint dp: dataPointList) {
            HRs.add((double)dp.getHeartRate());
        }

        double[] HRsAsArr = HRs.stream().mapToDouble(i->i).toArray();

        //run fft
        DoubleFFT_1D doubleFFT_1D = new DoubleFFT_1D(dataPointList.size() -1);
        doubleFFT_1D.realForward(HRsAsArr);
        return HRsAsArr;
    }

}
