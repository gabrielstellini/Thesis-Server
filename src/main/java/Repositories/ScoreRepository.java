package Repositories;

import Model.DatabaseEntities.Score;
import Model.DatabaseEntities.User;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Score, Long> {
    Score[] findByUser(User User);
}
