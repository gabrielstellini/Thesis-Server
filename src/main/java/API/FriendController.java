package API;

import Model.DTO.FriendsDTO;
import Model.DatabaseEntities.Friends;
import Model.DatabaseEntities.User;
import Service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/friends")
public class FriendController extends MainController {

    public final FriendsService friendService;

    @Autowired
    public FriendController(FriendsService friendService) {
        this.friendService = friendService;
    }


    @GetMapping("")
    public FriendsDTO getFriends(){
        String googleId = getCurrentUser().getGoogleId();

        Friends[] friends = friendService.findByGoogleId(googleId);
        FriendsDTO friendsDTO = new FriendsDTO();


        friendsDTO.toDto(friends);


        return friendsDTO;
    }

    @PostMapping("")
    public ResponseEntity addFriends(@RequestBody @NotNull FriendsDTO friendsDto){
        Friends friend = new Friends();
        friend.setUser(getCurrentUser());
        String[] newGoogleIds = friendsDto.getFriends();

        //storing current friends in memory
        Friends[] currentFriends = friendService.findByGoogleId(getCurrentUser().getGoogleId());
        String[] oldGoogleIds = new String[currentFriends.length];

        for (int j = 0; j < currentFriends.length; j++) {
            oldGoogleIds[j] = currentFriends[j].getFriend().getGoogleId();
        }

        boolean valid = true;
        //checking if already friends
        for (String newGoogleId : newGoogleIds) {
            for (String oldGoogleId : oldGoogleIds) {
                if (newGoogleId.equals(oldGoogleId)) {
                    valid = false;
                }
            }
        }

        if(valid){
            for (String newGoogleId : newGoogleIds) {
                User user = userService.findByGoogleId(newGoogleId);
                if(user != null & user != getCurrentUser()){
                    friend.setFriend(user);
                    friendService.save(friend);
                    return ResponseEntity.ok("{}");
                }else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'ERROR': 'User does not exist " +
                            "or you are trying to add yourself'}");
                }
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'ERROR': 'Already friends!'}");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'ERROR': 'Something happened!'}");
    }

    @Transactional
    @DeleteMapping("{friendsId}")
    public void deleteFriends(@PathVariable int friendsId){
        int userId = getCurrentUser().getId();
        Friends friends = friendService.findById(friendsId);
        if(friends != null){
            if(friends.getUser().getId() == userId){
                friendService.removeById(friends.getId());
            }
        }
    }
}
