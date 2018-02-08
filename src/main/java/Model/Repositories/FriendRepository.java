package Model.Repositories;

import Model.DatabaseEntities.Friends;
import Model.DatabaseEntities.User;
import org.springframework.data.repository.CrudRepository;

public interface FriendRepository extends CrudRepository<Friends, Long>{
    Friends[] findByUser(User user);
    Friends findById(Integer id);
    void removeById(Integer id);
}
