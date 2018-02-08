package API;

import Model.DTO.SignUpUserDTO;
import Model.DTO.UserDTO;
import Model.DTO.UserPreferenceDTO;
import Model.DatabaseEntities.User;
import Model.DatabaseEntities.UserPreference;
import Service.UserPreferenceService;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@RestController
@RequestMapping("/user")
public class UserController extends MainController{

    private final UserService userService;
    private final UserPreferenceService userPreferenceService;

    @Autowired
    public UserController(UserService userService, UserPreferenceService userPreferenceService) {
        this.userService = userService;
        this.userPreferenceService = userPreferenceService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody @NotNull SignUpUserDTO signUpUserDTO) {
        User user = getCurrentUser();

        user.setHasEmotionalSupport(signUpUserDTO.hasEmotionalSupport());
        user.setHasFinancialPressure(signUpUserDTO.hasFinancialPressure());
        user.setHasMedication(signUpUserDTO.hasMedication());
        user.setUsername(signUpUserDTO.getUsername());
        user.setMale(signUpUserDTO.isMale());
        user.setDateOfBirth(signUpUserDTO.getDateOfBirth());


        userService.save(user);
    }

    @GetMapping("/{googleId}")
    public UserDTO getUser(@PathVariable String googleId) {
        System.out.println("User requested user: " + googleId);
        User user = userService.findByGoogleId(googleId);
        UserDTO userDTO = new UserDTO();
        userDTO.toDto(user, userDTO);
        return userDTO;
    }

    @GetMapping("/search/{username}")
    public UserDTO[] getAllUsers(@PathVariable String username){

        User[] users = userService.findByUsernameIsContaining(username);
        UserDTO[] usersDTO = new UserDTO[users.length];

        for (int i = 0; i < users.length; i++) {
            usersDTO[i] = new UserDTO();
            usersDTO[i].toDto(users[i], usersDTO[i]);
        }

        return usersDTO;
    }

    @GetMapping("/preferences")
    public UserPreferenceDTO[] getUserPreferences(){
        User user = getCurrentUser();
        UserPreference[] userPreferences = userPreferenceService.findAllByUser(user);
        UserPreferenceDTO[] userPreferenceDTOS = new UserPreferenceDTO[userPreferences.length];

        for (int i = 0; i < userPreferences.length; i++) {
            userPreferenceDTOS[i] =  new UserPreferenceDTO();
            userPreferenceDTOS[i].toDto(userPreferences[i], userPreferenceDTOS[i]);
        }

        return userPreferenceDTOS;
    }

    @PostMapping("/preferences")
    public ResponseEntity addPreference(@RequestBody @NotNull UserPreferenceDTO userPreferenceDTO ) {
        User user = getCurrentUser();

        if (userPreferenceDTO.getStartTime().before(userPreferenceDTO.getEndTime())) {
            UserPreference[] savedUserPreferences = userPreferenceService.findAllByUser(user);

            boolean valid = true;

            Time startDate2 = userPreferenceDTO.getStartTime();
            Time endDate2 = userPreferenceDTO.getEndTime();

            //check for overlap
            for (UserPreference userPreference : savedUserPreferences) {
                Time startDate1 = userPreference.getStartTime();
                Time endDate1 = userPreference.getEndTime();
                //!(StartDate1 <= EndDate2) and (StartDate2 <= EndDate1){
                if (
                        (startDate1.before(endDate2) || startDate1.equals(endDate2)) &&
                                (startDate2.before(endDate1) || startDate2.equals(endDate1))
                        ) {
                    valid = false;
                }
            }

            //check there is a minimum of 1min delay
            long timeDifference = userPreferenceDTO.getEndTime().getTime() - userPreferenceDTO.getStartTime().getTime();
            if(timeDifference<60000){
                valid = false;
            }


            if (valid) {
                UserPreference userPreference = new UserPreference();

                userPreference.setStartTime(userPreferenceDTO.getStartTime());
                userPreference.setEndTime(userPreferenceDTO.getEndTime());
                userPreference.setUser(user);

                userPreferenceService.save(userPreference);
                return ResponseEntity.ok("{}");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'ERROR': 'Time overlaps or <1min!'}");
            }


        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'ERROR': 'Start is before end!'}");
        }
    }


    @Transactional
    @DeleteMapping("/preference/{preferenceId}")
    public ResponseEntity deletePreference(@PathVariable int preferenceId){

        UserPreference userPreference = this.userPreferenceService.findById(preferenceId);
        if(userPreference.getUser() == getCurrentUser()){
            userPreferenceService.removeById(preferenceId);
            return ResponseEntity.ok("{}");
        }else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unauthorised to edit other user's" +
                    " preferences or does not exist");
        }
    }

    @GetMapping("/deprecated/all")
    public User[] getAllUsers(){
        //TODO: Delete me (kept to check API is up)
        return userService.findByUsernameIsContaining("");
    }




}