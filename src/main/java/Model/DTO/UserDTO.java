package Model.DTO;

import Model.DatabaseEntities.Score;
import Model.DatabaseEntities.User;

public class UserDTO {
    private String username;
    private String email;
    private Score score;

    public void toDTO(User user, Score score){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.score = score;
    }
}
