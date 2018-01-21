package Model;

import Model.UserPreferences;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer Id;

    private String email;
    private String username;
    private String password;
    private String googleId;
    private boolean hasMedication;
    private boolean hasFinancialPressure;
    private boolean hasEmotionalSupport;
    private boolean isMale;
    private short age;

    @OneToOne
    private UserPreferences userPreferences;



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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public boolean isHasMedication() {
        return hasMedication;
    }

    public void setHasMedication(boolean hasMedication) {
        this.hasMedication = hasMedication;
    }

    public boolean isHasFinancialPressure() {
        return hasFinancialPressure;
    }

    public void setHasFinancialPressure(boolean hasFinancialPressure) {
        this.hasFinancialPressure = hasFinancialPressure;
    }

    public boolean isHasEmotionalSupport() {
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

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
