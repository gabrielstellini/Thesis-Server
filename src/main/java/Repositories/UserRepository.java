package Repositories;

import Model.DatabaseEntities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User[] findByUsernameIsContaining(String query);
    boolean existsByGoogleId(String googleId);
    User findByGoogleId(String googleId);
}