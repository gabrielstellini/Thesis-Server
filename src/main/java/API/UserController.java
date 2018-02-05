package API;

import Model.DTO.SignUpUserDTO;
import Model.DTO.UserDTO;
import Model.DatabaseEntities.User;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody @NotNull SignUpUserDTO signUpUserDTO) {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>)((OAuth2Authentication) authentication).getUserAuthentication().getDetails();

        String userId = userDetails.get("sub");

        User user = userService.findByGoogleId(userId);

        user.setHasEmotionalSupport(signUpUserDTO.hasEmotionalSupport());
        user.setHasFinancialPressure(signUpUserDTO.hasFinancialPressure());
        user.setHasMedication(signUpUserDTO.hasMedication());
        user.setUsername(signUpUserDTO.getUsername());
        user.setMale(signUpUserDTO.isMale());
        user.setDateOfBirth(signUpUserDTO.getDateOfBirth());


        userService.save(user);
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username) {
        System.out.println("User searched for username: " + username);
        User user = userService.findByUsername(username);
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

    @GetMapping("/deprecated/all")
    public User[] getAllUsers(){
        //TODO: Delete me (kept to check API is up)
        return userService.findByUsernameIsContaining("");
    }

}