package Repositories;

import Model.DatabaseEntities.DataPointMetaData;
import Model.DatabaseEntities.User;
import org.springframework.data.repository.CrudRepository;

public interface DataPointMetaRepository extends CrudRepository <DataPointMetaData, Long> {
    DataPointMetaData findById(Integer id);
    DataPointMetaData[] findByUser(User user);
}
