package Controller;

import Model.DTO.DataPointDTO;
import Model.DatabaseEntities.DataPoint;
import Model.DatabaseEntities.User;

public class DemoReflection {
    public static void main(String args[]){
        DataPoint dataPoint = new DataPoint();
        dataPoint.setGSR(12.4);
        dataPoint.setHeartRate(123);
        dataPoint.setUser(new User());

        DataPointDTO dataPointDTO = new DataPointDTO();
        dataPointDTO.toDto(dataPoint, dataPointDTO);
        System.out.println("Here");
    }
}
