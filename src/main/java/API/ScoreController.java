package API;

import Model.DTO.ScoreDTO;
import Model.DTO.UserDTO;
import Model.DTO.UserScoreDTO;
import Model.DatabaseEntities.Score;
import Model.DatabaseEntities.User;
import Service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/{userId}")
    public UserScoreDTO getUserScore(@PathVariable String userId){
        UserScoreDTO userScoreDTO = new UserScoreDTO();

        Score[] scores = scoreService.findByGoogleId(userId);
        UserDTO userDTO = new UserDTO();


        if(scores != null && scores.length > 0){
            User user = scores[0].getUser();
            userDTO.toDto(user, userDTO);


            ScoreDTO[] scoresDTO = new ScoreDTO[scores.length];

            for (int i = 0; i < scores.length; i++) {
                scoresDTO[i] = new ScoreDTO();
                scoresDTO[i].toDto(scores[i], scoresDTO[i]);
            }

            userScoreDTO.setUser(userDTO);
            userScoreDTO.setScores(scoresDTO);

            return userScoreDTO;

        }else {
            return new UserScoreDTO();
        }
    }


}
