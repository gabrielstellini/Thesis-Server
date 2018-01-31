package Model.Repositories;

import Model.DatabaseEntities.Food;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long> {
    
}
