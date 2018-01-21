package Controller;

import Model.DataPoint;
import Model.User;


public class StressManager {
    public boolean calculate(DataPoint[] dataPoints){

        calculateHR(dataPoints);
        calculateGSR(dataPoints);
        calculateUser(dataPoints[0].getUser());

        throw new UnsupportedOperationException();
    }

    private boolean calculateHR(DataPoint[] dataPoints){
        throw new UnsupportedOperationException();
    }

    private boolean calculateGSR(DataPoint[] dataPoints){
        throw new UnsupportedOperationException();
    }

    private boolean calculateUser(User user){
        throw new UnsupportedOperationException();
    }
}
