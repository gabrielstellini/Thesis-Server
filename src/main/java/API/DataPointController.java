package API;

import Controller.ScoreEngine;
import Controller.StressEngine.GSRStressEngine;
import Model.DTO.DataPointDTO;
import Model.DatabaseEntities.DataPoint;
import Model.DatabaseEntities.DataPointMetaData;
import Model.DatabaseEntities.User;
import Service.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/datapoint")
public class DataPointController extends MainController {

    private final DataPointService dataPointService;
    private final DataPointMetaService dataPointMetaService;
    private ScoreEngine scoreEngine;

    public DataPointController(  DataPointService dataPointService,
                                 DataPointMetaService dataPointMetaService,
                                 ScoreService scoreService,
                                 FoodService foodService,
                                 UserService userService,
                                 UserPreferenceService userPreferenceService) {
        this.dataPointService = dataPointService;
        this.dataPointMetaService = dataPointMetaService;
        scoreEngine = new ScoreEngine(dataPointService, dataPointMetaService,  scoreService, foodService, userService, userPreferenceService);
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
        metaData.setTimestamp(System.currentTimeMillis());

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
        scoreEngine.calculateScore();
    }


    @PostMapping("/calm")
    public void addCalmDataPoints(@RequestBody @NotNull DataPointDTO[] dataPointDtos){
        DataPointMetaData metaData= new DataPointMetaData();
        metaData.setBaseline(true);
        metaData.setStressStatus(0);
        metaData.setUser(getCurrentUser());
        metaData.setTimestamp(System.currentTimeMillis());

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
