package Model.Repositories;

import Model.DatabaseEntities.User;
import Model.DatabaseEntities.UserPreference;
import org.springframework.data.repository.CrudRepository;


public interface UserPreferenceRepository extends CrudRepository<UserPreference, Long> {
    UserPreference[] findAllByUser(User user);
    void removeById(Integer id);
    UserPreference findById(Integer id);
}
