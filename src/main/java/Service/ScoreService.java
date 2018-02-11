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
    private final UserService userService;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository, UserService userService){
        this.scoreRepository = scoreRepository;
        this.userService = userService;
    }

    public Score[] findByGoogleId(String googleId){
        User user = userService.findByGoogleId(googleId);
        Score[] scores = scoreRepository.findByUser(user);
        return scores;
    }
}
