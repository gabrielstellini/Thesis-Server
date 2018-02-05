package Model.DTO;

public class UserScoreDTO {
    UserDTO user;
    ScoreDTO[] scores;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ScoreDTO[] getScores() {
        return scores;
    }

    public void setScores(ScoreDTO[] scores) {
        this.scores = scores;
    }
}
