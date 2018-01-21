package Model.Repositories;

import Model.Food;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long> {
    
}
