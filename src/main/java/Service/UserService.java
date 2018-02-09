package Service;

import Model.DatabaseEntities.User;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean existsByGoogleId(String clientId){
        return userRepository.existsByGoogleId(clientId);
    }

    public User findByGoogleId(String clientId){
        return userRepository.findByGoogleId(clientId);
    }

    public void save(User user){
        //TODO: more robust checks for whether a user is valid
        if(user.getUsername() != null && user.getGoogleId() != null) {
            userRepository.save(user);
        }
    }
}
