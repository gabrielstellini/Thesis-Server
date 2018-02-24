package Model.DTO;

import Model.DatabaseEntities.UserPreference;
import Model.EntityToDto;

import java.sql.Time;

public class UserPreferenceDTO extends EntityToDto<UserPreferenceDTO, UserPreference> {
    private int id;
    private String startTime;
    private String endTime;

    public UserPreferenceDTO(){}

    public UserPreferenceDTO(int id, String startTime, String endTime) {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
