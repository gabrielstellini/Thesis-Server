package Service;

import Model.DatabaseEntities.DataPointMetaData;
import Model.DatabaseEntities.User;
import Repositories.DataPointMetaRepository;
import org.springframework.stereotype.Service;

@Service
public class DataPointMetaService {
    private final DataPointMetaRepository dataPointMetaRepository;
    private final UserService userService;

    public DataPointMetaService(DataPointMetaRepository dataPointMetaRepository, UserService userService) {
        this.dataPointMetaRepository = dataPointMetaRepository;
        this.userService = userService;
    }


    public DataPointMetaData[] findByGoogleId(String googleId){
        User user = userService.findByGoogleId(googleId);
        DataPointMetaData[] dataPointMetaData = dataPointMetaRepository.findByUser(user);
        return dataPointMetaData;
    }

    public void save(DataPointMetaData dataPoint){
        dataPointMetaRepository.save(dataPoint);
    }

    public DataPointMetaData findById(Integer id){
        return dataPointMetaRepository.findById(id);
    }
}
