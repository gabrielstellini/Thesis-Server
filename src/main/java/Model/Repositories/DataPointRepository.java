package Model.Repositories;

import Model.DatabaseEntities.DataPoint;
import org.springframework.data.repository.CrudRepository;

public interface DataPointRepository extends CrudRepository<DataPoint, Long> {

}
