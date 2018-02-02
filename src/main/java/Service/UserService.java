package Service;

import Model.DatabaseEntities.User;
import Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User[] findByUsernameIsContaining(String query){
        return userRepository.findByUsernameIsContaining(query);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public void save(User user){
        if(user.getPassword() != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        //TODO: more robust checks for whether a user is valid
        if(user.getUsername() != null && (user.getPassword() != null || user.getGoogleId() != null)) {
            userRepository.save(user);
        }
    }
}
