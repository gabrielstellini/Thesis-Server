package API;

import Model.DTO.DataPointDTO;
import Model.DatabaseEntities.DataPoint;
import Model.DatabaseEntities.User;
import Service.DataPointService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@RestController
@RequestMapping("/datapoint")
public class DataPointController extends MainController {

    private final DataPointService dataPointService;
    public DataPointController(DataPointService dataPointService) {
        this.dataPointService = dataPointService;
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
    public void addDataPoint(@RequestBody @NotNull DataPointDTO[] dataPoints){
        User user = getCurrentUser();
        for (DataPointDTO dp: dataPoints) {
                DataPoint dataPoint = new DataPoint(dp.getHeartRate(),dp.getRrInterval(), dp.getGsr(), dp.getTimestamp(), user);
                dataPointService.save(dataPoint);
            }
//        }
    }
}
