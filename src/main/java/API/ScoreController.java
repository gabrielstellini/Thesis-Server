package API;

import Model.DTO.ScoreDTO;
import Model.DTO.UserDTO;
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

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/{username}")
    public ScoreDTO[] getUserScore(@PathVariable String username){
        Score[] scores = scoreService.findByUsername(username);

        ScoreDTO[] scoresDTO = new ScoreDTO[scores.length];

        for (int i = 0; i < scores.length; i++) {
            scoresDTO[i] = new ScoreDTO();
            scoresDTO[i].toDto(scores[i], scoresDTO[i]);

            User user = scores[i].getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.toDto(user, userDTO);

            scoresDTO[i].setUser(userDTO);
        }
        return scoresDTO;
    }
}
