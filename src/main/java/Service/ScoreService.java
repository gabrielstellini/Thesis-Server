package Service;

import Model.DatabaseEntities.Score;
import Model.DatabaseEntities.User;
import Repositories.ScoreRepository;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Score[] findByGoogleId(String googleId){
        User user = userRepository.findByGoogleId(googleId);
        Score[] scores = scoreRepository.findByUser(user);
        return scores;
    }
}
