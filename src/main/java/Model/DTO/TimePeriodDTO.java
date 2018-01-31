package Model.DTO;

import Model.DatabaseEntities.TimePeriod;
import Model.EntityToDto;

import java.sql.Time;

public class TimePeriodDTO extends EntityToDto<TimePeriodDTO, TimePeriod> {
    private Time startTime;
    private Time endTime;

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
