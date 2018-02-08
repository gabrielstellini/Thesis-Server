package Model.DTO;

import Model.DatabaseEntities.UserPreference;
import Model.EntityToDto;

import java.sql.Time;

public class UserPreferenceDTO extends EntityToDto<UserPreferenceDTO, UserPreference> {
    private int id;
    private Time startTime;
    private Time endTime;

    public UserPreferenceDTO(){}

    public UserPreferenceDTO(int id, Time startTime, Time endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
