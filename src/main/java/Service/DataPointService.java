package Service;

import Model.DatabaseEntities.DataPoint;
import Model.DatabaseEntities.User;
import Repositories.DataPointRepository;
import org.springframework.stereotype.Service;

@Service
public class DataPointService {
    private final DataPointRepository dataPointRepository;
    private final UserService userService;

    public DataPointService(DataPointRepository dataPointRepository, UserService userService) {
        this.dataPointRepository = dataPointRepository;
        this.userService = userService;
    }


    public DataPoint[] findByGoogleId(String googleId){
        User user = userService.findByGoogleId(googleId);
        DataPoint[] dataPoints = dataPointRepository.findByUser(user);
        return dataPoints;
    }

    public void save(DataPoint dataPoint){
        dataPointRepository.save(dataPoint);
    }

    public DataPoint findById(Integer id){
        return dataPointRepository.findById(id);
    }

    public DataPoint[] findByDataPointMetaDataId(Integer id){return  dataPointRepository.findByDataPointMetaDataId(id);}
}
