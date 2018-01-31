package API;

import Model.DTO.UserDTO;
import Model.DatabaseEntities.User;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
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

        User[] users = userService.findAllBySimilarUsername(username);
        UserDTO[] usersDTO = new UserDTO[users.length];

        for (int i = 0; i < users.length; i++) {
            usersDTO[i] = new UserDTO();
            usersDTO[i].toDto(users[i], usersDTO[i]);
        }

        return usersDTO;
    }

    @GetMapping("c")
    public User[] getAllUsers(){
        //TODO: Delete me (kept to expedite sign up)
        return userService.findAllBySimilarUsername("");
    }

}