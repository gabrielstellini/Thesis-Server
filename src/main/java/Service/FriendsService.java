package Service;

import Model.DatabaseEntities.Friends;
import Model.DatabaseEntities.User;
import Repositories.FriendRepository;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsService {

    private final FriendRepository friendRepository;
    private final UserService userService;

    @Autowired
    public FriendsService(FriendRepository friendRepository, UserService userService){
        this.friendRepository = friendRepository;
        this.userService = userService;
    }

    public Friends[] findByGoogleId(String googleId){
        User user = userService.findByGoogleId(googleId);
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
