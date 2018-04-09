package Controller;

import Controller.StressEngine.GSRStressEngine;
import Controller.StressEngine.RRStressEngine;
import Model.DataPointType;
import Model.DatabaseEntities.*;
import Service.*;
import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScoreEngine {
    private GSRStressEngine gsrStressEngine;
    private RRStressEngine rrStressEngine;

    private ScoreService scoreService;
    private FoodService foodService;
    private UserService userService;
    private UserPreferenceService userPreferenceService;
    private DataPointService dataPointService;
    private DataPointMetaService dataPointMetaService;

    public ScoreEngine(
            DataPointService dataPointService,
            DataPointMetaService dataPointMetaService,
            ScoreService scoreService,
            FoodService foodService,
            UserService userService,
            UserPreferenceService userPreferenceService
    ){
        this.scoreService = scoreService;
        this.foodService = foodService;
        this.userService = userService;
        this.userPreferenceService = userPreferenceService;
        this.dataPointMetaService = dataPointMetaService;
        this.dataPointService = dataPointService;
        gsrStressEngine = new GSRStressEngine(dataPointService, dataPointMetaService);
        rrStressEngine = new RRStressEngine(dataPointService, dataPointMetaService);

    }

    public void calculateScore(){
//        Get current user
        User currentUser = getCurrentUser();

//        Get foods eaten today
        Food[] foods = foodService.findByGoogleId(currentUser.getGoogleId());
        List<Food> foodAsList = new ArrayList<>();

        for (Food food:foods) {
            if(isDateToday(food.getTimestamp())){
                foodAsList.add(food);
            }
        }

        Food[] filteredFoodArr = foodAsList.toArray(new Food[foodAsList.size()]);

//        Get user preferences for current user
        UserPreference[] userPreferences = userPreferenceService.findAllByUser(currentUser);

//        Score is out of 10 for each method
        int gsrScore = (int)Math.round(GSRScore() * 1.5);
        int rrScore = RRScore() * 4;
        int calorieScore = calorieScore(currentUser, filteredFoodArr) * 2;
        int timeEatenScore = timeEatenScore(userPreferences, filteredFoodArr) * 2;
        int userScore = (int) Math.round(userScore(currentUser) * 0.5);

//      Save score
        saveScore(gsrScore + rrScore + calorieScore + timeEatenScore + userScore, currentUser);

//        Save new data as stressed/non stressed
        int isStressed = 0;
        isStressed += gsrStressEngine.checkIfStressedGSR()?0:1;
        isStressed += rrStressEngine.checkIfStressedRR()?0:1;
        isStressed += userScore(currentUser)>5?0:1;

        if(isStressed >= 2){
            updateStressStatus(DataPointType.STRESSED);
        }else {
            updateStressStatus(DataPointType.CALM);
        }
    }

    private User getCurrentUser(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationJsonWebToken authenticationJsonWebToken = (AuthenticationJsonWebToken) authentication;

        String oauthId = authenticationJsonWebToken.getName();
        return userService.findByGoogleId(oauthId);
    }

    private int StressScore(int stressType){

        boolean isStressedNow;

        if(stressType == 0){
            isStressedNow = gsrStressEngine.checkIfStressedGSR();
        }else{
            isStressedNow = rrStressEngine.checkIfStressedRR();
        }


        int noOfStressedEatingToday = 0;
        int totalEatingToday = 0;

        String googleId = getCurrentUser().getGoogleId();
        DataPointMetaData[] dataPointMetaDatas = dataPointMetaService.findByGoogleId(googleId);

        for (DataPointMetaData dataPointMetaData: dataPointMetaDatas) {
            if(isDateToday(dataPointMetaData.getTimestamp())) {
                if(dataPointMetaData.getStressStatus() == 1){
                    noOfStressedEatingToday++;
                }
                totalEatingToday++;
            }
        }

        if(totalEatingToday == 0){
            return isStressedNow?0:10;
        }else {
            noOfStressedEatingToday += isStressedNow?0:1;
            double result = (double)noOfStressedEatingToday/totalEatingToday * 10;
            return (int)Math.round(result);
        }
    }

    private int GSRScore(){
        return StressScore(0);
    }

    private int RRScore(){
        return StressScore(1);
    }

    /**
     *
     * @param user user in question
     * @param foods foods consumed during the day
     * @return score from 1 to 10
     */
    private int calorieScore(User user, Food[] foods){
        //male or female
        //total foods in a day

        int total = 0;
        int recommendedNum = 0;

        for (Food food:foods) {
            total += food.getCalories() * food.getQuantity();
        }

        if(user.isMale()){
            recommendedNum = 2500;
        }else{
            recommendedNum = 2000;
        }

        if(total < recommendedNum){
            return 6;
        } else{
            return Math.round((-10 * total)/recommendedNum + 20);
//            -10x/2500 + 20
        }

    }

    private int timeEatenScore(UserPreference[] userPreferences, Food[] foods){
        int foodsInCorrectPeriod = 0;

        for (Food food: foods) {
            for (UserPreference userPreference:userPreferences) {
                boolean inRange = isInRange(userPreference.getStartTime(), userPreference.getEndTime(), food.getTimestamp());
                if(inRange){
                    foodsInCorrectPeriod++;
                }
            }
        }

        double result = (double)foodsInCorrectPeriod/foods.length *10;
        //convert number of periods to number of base 10
        return (int) Math.round(result);
    }


    private boolean isInRange(String startTime, String endTime, long timeStamp){
        String[] startTimeNums = startTime.split(":");
        String[] endTimeNums = endTime.split(":");

        Calendar startTimeCalendar = Calendar.getInstance();
        Calendar endTimeCalendar = Calendar.getInstance();


        Calendar actualTimeCalendar = Calendar.getInstance();
        actualTimeCalendar.setTimeInMillis(timeStamp);

        startTimeCalendar.set(Calendar.DATE, actualTimeCalendar.get(Calendar.DATE));
        startTimeCalendar.set(Calendar.MONTH, actualTimeCalendar.get(Calendar.MONTH));
        startTimeCalendar.set(Calendar.YEAR, actualTimeCalendar.get(Calendar.YEAR));

        endTimeCalendar.set(Calendar.DATE, actualTimeCalendar.get(Calendar.DATE));
        endTimeCalendar.set(Calendar.MONTH, actualTimeCalendar.get(Calendar.MONTH));
        endTimeCalendar.set(Calendar.YEAR, actualTimeCalendar.get(Calendar.YEAR));

        startTimeCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeNums[0]));
        startTimeCalendar.set(Calendar.MINUTE, Integer.parseInt(startTimeNums[1]));
        endTimeCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeNums[0]));
        endTimeCalendar.set(Calendar.MINUTE, Integer.parseInt(endTimeNums[1]));


        System.out.println(startTimeCalendar.getTime());
        System.out.println(endTimeCalendar.getTime());
        System.out.println(actualTimeCalendar.getTime());

        if(actualTimeCalendar.after(startTimeCalendar) && actualTimeCalendar.before(endTimeCalendar)){
            return true;
//            System.out.println("Correct");
        }else {
            return false;
//            System.out.println("Incorrect");
        }
    }

//    Should have very low weighting as it only has a very low effect
    private int userScore(User user){
        int score = 0;

        //male users are less likely to be stressed
        if(user.isMale()){
            score++;
        }

        if(user.hasMedication()){
            score++;
        }

        if(user.hasEmotionalSupport()){
            score++;
        }

        if(!user.hasFinancialPressure()){
            score++;
        }

        return score;
    }

    private void saveScore(int newScore, User user){
        //check if there already is a score today
        Score[] scores = scoreService.findByGoogleId(user.getGoogleId());
        Score updatedScore = null;

        for (Score score:scores) {
            if(isDateToday(score.getTimestamp())){
                updatedScore = score;
            }
        }

        if(updatedScore == null){
            updatedScore = new Score();
            updatedScore.setTimestamp(System.currentTimeMillis());
        }

        updatedScore.setPoints(newScore);
        updatedScore.setUser(user);
        scoreService.save(updatedScore);
    }

    private static boolean isDateToday(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        Date getDate = calendar.getTime();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startDate = calendar.getTime();

        return getDate.compareTo(startDate) > 0;
    }

    private void updateStressStatus(DataPointType dataPointType){
        //Current user
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationJsonWebToken authenticationJsonWebToken = (AuthenticationJsonWebToken) authentication;

        String oauthId = authenticationJsonWebToken.getName();

        DataPointMetaData[] dataPointMetaDatas = dataPointMetaService.findByGoogleId(oauthId);
        for (DataPointMetaData datapointMetaData: dataPointMetaDatas) {
            if(datapointMetaData.getStressStatus() == 2  && !datapointMetaData.isBaseline()){

                int result = 2;

                switch (dataPointType){
                    case CALM:
                        result = 0;
                        break;
                    case STRESSED:
                        result = 1;
                }

                datapointMetaData.setStressStatus(result);
                dataPointMetaService.save(datapointMetaData);
            }
        }
    }

}
