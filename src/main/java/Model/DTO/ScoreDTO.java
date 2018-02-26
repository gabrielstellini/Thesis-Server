package Model.DTO;

import Model.DatabaseEntities.Score;
import Model.EntityToDto;

public class ScoreDTO extends EntityToDto<ScoreDTO, Score> {
    private long timestamp;
    private int points;
    private int caloriesBurnt;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public void setCaloriesBurnt(int caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
