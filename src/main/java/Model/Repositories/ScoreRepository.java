package Model.Repositories;

import Model.DatabaseEntities.Score;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Score, Long> {
    Score findByUsername(String username);
}
