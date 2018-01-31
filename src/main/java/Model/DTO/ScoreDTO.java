package Model.DTO;

import Model.DatabaseEntities.Score;
import Model.DatabaseEntities.User;
import Model.EntityToDto;

import java.util.Date;

public class ScoreDTO extends EntityToDto<ScoreDTO, Score> {
    private UserDTO user;
    private Date date;
    private int Points;
    private int caloriesBurnt;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public int getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public void setCaloriesBurnt(int caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
