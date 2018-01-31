package Service;

import Model.DatabaseEntities.Score;
import Model.DatabaseEntities.User;
import Model.Repositories.ScoreRepository;
import Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository, UserRepository userRepository){
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
    }

    public Score[] findByUsername(String username){
        User user = userRepository.findByUsername(username);
        Score[] scores = scoreRepository.findByUser(user);
        return scores;
    }
}
