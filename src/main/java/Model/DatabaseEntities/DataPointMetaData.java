package Model.DatabaseEntities;

import javax.persistence.*;

@Entity
public class DataPointMetaData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public  Integer id;

    @OneToOne
    private User user;

//    2 is not calculated
//    0 is calm
//    1 is stressed

    private int stressStatus;
    private boolean isBaseline;
    private long timestamp;

    public boolean isBaseline() {
        return isBaseline;
    }

    public void setBaseline(boolean baseline) {
        isBaseline = baseline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStressStatus() {
        return stressStatus;
    }

    public void setStressStatus(int stressStatus) {
        this.stressStatus = stressStatus;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
