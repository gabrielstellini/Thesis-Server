package Service;

import Model.DatabaseEntities.DataPoint;
import Model.DatabaseEntities.User;
import Model.Repositories.DataPointRepository;
import Model.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DataPointService {
    private final DataPointRepository dataPointRepository;
    private final UserRepository userRepository;

    public DataPointService(DataPointRepository dataPointRepository, UserRepository userRepository) {
        this.dataPointRepository = dataPointRepository;
        this.userRepository = userRepository;
    }


    public DataPoint[] findByGoogleId(String googleId){
        User user = userRepository.findByGoogleId(googleId);
        DataPoint[] dataPoints = dataPointRepository.findByUser(user);
        return dataPoints;
    }

    public void save(DataPoint dataPoint){
        dataPointRepository.save(dataPoint);
    }

    public DataPoint findById(Integer id){
        return dataPointRepository.findById(id);
    }
}
