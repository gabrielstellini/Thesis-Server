package Model.DatabaseEntities;

import javax.persistence.*;

@Entity
public class DataPoint {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer id;

    private int heartRate;
    private double RRInterval;
    private double GSR;
    private long timestamp;

    private String quality;
    private String contactStatus;

    @OneToOne
    private DataPointMetaData dataPointMetaData;

    @OneToOne
    private User user;

    public DataPoint(){
    }

    public DataPoint(int heartRate, double RRInterval, double GSR, long timestamp, User user, String quality, String contactStatus, DataPointMetaData dataPointMetaData) {
        this.heartRate = heartRate;
        this.RRInterval = RRInterval;
        this.GSR = GSR;
        this.timestamp = timestamp;
        this.user = user;
        this.quality = quality;
        this.contactStatus = contactStatus;
        this.dataPointMetaData = dataPointMetaData;
    }

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    public DataPointMetaData getDataPointMetaData() {
        return dataPointMetaData;
    }

    public void setDataPointMetaData(DataPointMetaData dataPointMetaData) {
        this.dataPointMetaData = dataPointMetaData;
    }
}
