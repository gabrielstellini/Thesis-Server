package API;

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

//    private UserRepository userRepository;
//
//    public UserController(UserRepository UserRepository) {
//        this.userRepository = UserRepository;
//    }


    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        System.out.println("User searched for username: " + username);
        User user = userService.findByUsername(username);
        System.out.println(user.getAge());
        System.out.println(user);
//        Iterable<User> users = userService.findAll();
//        users.forEach(user -> System.out.println(user.getAge()));
        return user;
    }

//THIS WORKS!
//    @GetMapping("/{username}")
//    public String getUser(@PathVariable String username) {
//        System.out.println("User searched for username: " + username);
////        User user = userService.findByUsername(username);
////        System.out.println(user.getAge());
//        Iterable<User> users = userService.findAll();
//        users.forEach(user -> System.out.println(user.getAge()));
//        return "Success?";
//    }
}