package Model.Repositories;

import Model.Score;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Score, Long> {
    
}
