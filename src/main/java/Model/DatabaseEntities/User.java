package Model.DatabaseEntities;

import javax.persistence.*;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String email;
    private String username;
    private String googleId;
    private boolean hasMedication;
    private boolean hasFinancialPressure;
    private boolean hasEmotionalSupport;
    private boolean isMale;
    private Date dateOfBirth;
    private String picture;

    public User(){

    }

    public User(User user){
        email = user.getEmail();
        username = user.getUsername();
        googleId = user.getGoogleId();
        hasMedication = user.hasMedication;
        hasFinancialPressure = user.hasFinancialPressure;
        hasEmotionalSupport = user.hasEmotionalSupport;
        isMale = user.isMale;
        dateOfBirth = user.dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public boolean hasMedication() {
        return hasMedication;
    }

    public void setHasMedication(boolean hasMedication) {
        this.hasMedication = hasMedication;
    }

    public boolean hasFinancialPressure() {
        return hasFinancialPressure;
    }

    public void setHasFinancialPressure(boolean hasFinancialPressure) {
        this.hasFinancialPressure = hasFinancialPressure;
    }

    public boolean hasEmotionalSupport() {
        return hasEmotionalSupport;
    }

    public void setHasEmotionalSupport(boolean hasEmotionalSupport) {
        this.hasEmotionalSupport = hasEmotionalSupport;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
