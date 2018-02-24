package Model.DatabaseEntities;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class UserPreference {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private User user;

    private String startTime;
    private String endTime;

    public UserPreference(){}

    public UserPreference(User user, String startTime, String endTime) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
