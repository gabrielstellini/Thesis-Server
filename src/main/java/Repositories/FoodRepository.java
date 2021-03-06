package Repositories;

import Model.DatabaseEntities.Food;
import Model.DatabaseEntities.User;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long> {
    Food[] findByUser(User user);
    Food findById(Integer id);
    void removeById(Integer id);
}
