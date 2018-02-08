package Service;

import Model.DatabaseEntities.Friends;
import Model.DatabaseEntities.User;
import Model.Repositories.FriendRepository;
import Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendsService(FriendRepository friendRepository, UserRepository userRepository){
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public Friends[] findByGoogleId(String googleId){
        User user = userRepository.findByGoogleId(googleId);
        Friends[] friends = friendRepository.findByUser(user);
        return friends;
    }

    public void save(Friends Friends){
        friendRepository.save(Friends);
    }

    public Friends findById(Integer id){
        return friendRepository.findById(id);
    }

    public void removeById(int id){
        friendRepository.removeById(id);
    }
}
