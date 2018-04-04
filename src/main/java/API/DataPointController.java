package API;

import Controller.StressEngine.GSRStressEngine;
import Model.DTO.DataPointDTO;
import Model.DatabaseEntities.DataPoint;
import Model.DatabaseEntities.DataPointMetaData;
import Model.DatabaseEntities.User;
import Service.DataPointMetaService;
import Service.DataPointService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/datapoint")
public class DataPointController extends MainController {

    private final DataPointService dataPointService;
    private final DataPointMetaService dataPointMetaService;

    public DataPointController(DataPointService dataPointService, DataPointMetaService dataPointMetaService) {
        this.dataPointService = dataPointService;
        this.dataPointMetaService = dataPointMetaService;
    }

    @GetMapping("")
    public DataPoint[] getDataPoint(){
        String googleId = getCurrentUser().getGoogleId();

        DataPoint[] dataPoints = dataPointService.findByGoogleId(googleId);
        DataPointDTO[] dataPointDTOS = new DataPointDTO[dataPoints.length];

        for (int i = 0; i < dataPoints.length; i++) {
            dataPointDTOS[i] = new DataPointDTO();
            dataPointDTOS[i].toDto(dataPoints[i], dataPointDTOS[i]);
        }
        return dataPoints;
    }

    @PostMapping("")
    public void addDataPoints(@RequestBody DataPointDTO[] dataPoints){
        DataPointMetaData metaData= new DataPointMetaData();
        metaData.setBaseline(false);
        metaData.setStressStatus(2);
        metaData.setUser(getCurrentUser());

        dataPointMetaService.save(metaData);


        User user = getCurrentUser();
        for (DataPointDTO dp: dataPoints) {
            try{
                DataPoint dataPoint = new DataPoint(dp.getHeartRate(), dp.getRrInterval(), dp.getGsr(), dp.getTimestamp(), user, dp.getQuality(), dp.getContactStatus(), metaData);
                dataPointService.save(dataPoint);
            }catch (NullPointerException e){
                System.out.println("HERE");
            }
        }

        GSRStressEngine gsrStressEngine = new GSRStressEngine(dataPointService, dataPointMetaService);
        gsrStressEngine.checkIfStressedGSR();

    }


    @PostMapping("/calm")
    public void addCalmDataPoints(@RequestBody @NotNull DataPointDTO[] dataPointDtos){
        DataPointMetaData metaData= new DataPointMetaData();
        metaData.setBaseline(true);
        metaData.setStressStatus(0);
        metaData.setUser(getCurrentUser());

        dataPointMetaService.save(metaData);

        User user = getCurrentUser();

        DataPoint[] dataPoints = new DataPoint[dataPointDtos.length];


        for (int i = 0; i < dataPointDtos.length; i++) {
            try{
                DataPointDTO dp = dataPointDtos[i];
                dataPoints[i] = new DataPoint(dp.getHeartRate(), dp.getRrInterval(), dp.getGsr(), dp.getTimestamp(), user, dp.getQuality(), dp.getContactStatus(), metaData);
                dataPointService.save(dataPoints[i]);
            }catch (NullPointerException e){
                System.out.println("HERE");
            }
        }
    }
}
