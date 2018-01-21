package Controller;

import Model.Food;
import Model.TimePeriod;
import Model.User;

import java.sql.Timestamp;

public class SessionManager {

    StressManager stressManager = new StressManager();
    DatabaseConnection databaseConnection = new DatabaseConnection();
    ScoreManager scoreManager = new ScoreManager();

    public void login(String username, String password){

    }

    public void signUp(User user){

    }

    public void updateDetails(User user){

    }

    public String getFriendDetails(){
        throw new UnsupportedOperationException();
    }

    public void addFriend(String userId){

    }

    public void getFood(TimePeriod timePeriod){

    }

    public void addFood(Food food, Timestamp timestamp){

    }

    public void disconnect(){

    }
}
