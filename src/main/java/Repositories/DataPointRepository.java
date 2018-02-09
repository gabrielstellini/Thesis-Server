package Repositories;

import Model.DatabaseEntities.DataPoint;
import Model.DatabaseEntities.User;
import org.springframework.data.repository.CrudRepository;

public interface DataPointRepository extends CrudRepository<DataPoint, Long> {
    DataPoint[] findByUser(User user);
    DataPoint findById(Integer id);
}
