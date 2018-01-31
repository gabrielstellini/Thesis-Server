package Service;

import Model.DatabaseEntities.User;
import Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findByUsername(String userName){
        return userRepository.findByUsername(userName);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }
}
