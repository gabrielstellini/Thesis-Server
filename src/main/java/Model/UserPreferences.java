package Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserPreferences {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    private TimePeriod[] PreferredTimes;

    public TimePeriod[] getPreferredTimes() {
        return PreferredTimes;
    }

    public void setPreferredTimes(TimePeriod[] preferredTimes) {
        PreferredTimes = preferredTimes;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
