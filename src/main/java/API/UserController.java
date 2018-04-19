package API;

import API.Auth.OAuthUserHandler;
import Model.DTO.ScoreDTO;
import Model.DTO.SignUpUserDTO;
import Model.DTO.UserDTO;
import Model.DTO.UserPreferenceDTO;
import Model.DatabaseEntities.Score;
import Model.DatabaseEntities.User;
import Model.DatabaseEntities.UserPreference;
import Service.ScoreService;
import Service.UserPreferenceService;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
public class UserController extends MainController{

    private final UserService userService;
    private final UserPreferenceService userPreferenceService;
    private final ScoreService scoreService;

    @Autowired
    public UserController(UserService userService, UserPreferenceService userPreferenceService, ScoreService scoreService) {
        this.userService = userService;
        this.userPreferenceService = userPreferenceService;
        this.scoreService = scoreService;
    }

    @GetMapping("/")
    public SignUpUserDTO userInitialisation(){
        OAuthUserHandler oAuthUserHandler = new OAuthUserHandler(userService);
        oAuthUserHandler.handleUser();
        SignUpUserDTO signUpUserDTO = new SignUpUserDTO();
        signUpUserDTO.toDto(getCurrentUser(), signUpUserDTO);
        return signUpUserDTO;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @NotNull SignUpUserDTO signUpUserDTO) {
        //remove these 2 lines if there's an error (UNTESTED)
        OAuthUserHandler oAuthUserHandler = new OAuthUserHandler(userService);
        oAuthUserHandler.handleUser();

        User user = getCurrentUser();

        user.setHasEmotionalSupport(signUpUserDTO.hasEmotionalSupport());
        user.setHasFinancialPressure(signUpUserDTO.hasFinancialPressure());
        user.setHasMedication(signUpUserDTO.hasMedication());
        //user.setUsername(signUpUserDTO.getUsername());
        user.setMale(signUpUserDTO.isMale());
        //user.setDateOfBirth(signUpUserDTO.getDateOfBirth());


        userService.save(user);
        return ResponseEntity.ok("{}");
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
        UserPreference userPreference = new UserPreference();

        userPreference.setStartTime(userPreferenceDTO.getStartTime());
        userPreference.setEndTime(userPreferenceDTO.getEndTime());
        userPreference.setUser(user);

        userPreferenceService.save(userPreference);
        return ResponseEntity.ok("{}");
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

    @GetMapping("/score")
    public ScoreDTO[] getPersonalScores(){
        User user = getCurrentUser();
        Score[] scores = scoreService.findByGoogleId(user.getGoogleId());
        ScoreDTO[] scoreDTO = new ScoreDTO[scores.length];

        for (int i = 0; i < scores.length; i++) {
            scoreDTO[i] =  new ScoreDTO();
            scoreDTO[i].toDto(scores[i], scoreDTO[i]);
        }

        return scoreDTO;
    }
}