package Controller;

import Model.DatabaseEntities.Food;
import Model.DatabaseEntities.TimePeriod;
import Model.DatabaseEntities.User;

import java.sql.Timestamp;

public class SessionManager {

    StressManager stressManager = new StressManager();
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
