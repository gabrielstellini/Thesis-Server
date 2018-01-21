package Model;

import javax.persistence.*;

@Entity
public class DataPoint {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer Id;

    private int heartRate;
    private double RRInterval;
    private double GSR;
    private int timestamp;

    @OneToOne
    private User user;

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public double getRRInterval() {
        return RRInterval;
    }

    public void setRRInterval(double RRInterval) {
        this.RRInterval = RRInterval;
    }

    public double getGSR() {
        return GSR;
    }

    public void setGSR(double GSR) {
        this.GSR = GSR;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
