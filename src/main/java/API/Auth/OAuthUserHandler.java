package API.Auth;

import Model.DatabaseEntities.User;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.LinkedHashMap;


public class OAuthUserHandler {

    private UserService userService;

    @Autowired
    public OAuthUserHandler(UserService userService) {
        this.userService = userService;
    }

    public void handleUser(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>)((OAuth2Authentication) authentication).getUserAuthentication().getDetails();

        String userId = userDetails.get("sub");

        boolean foundUser = userService.existsByGoogleId(userId);

        System.out.println("User ID: " + userId);
        System.out.println("Database found user: " + foundUser);

        if(!foundUser) {
            saveUser(userDetails);
        }
    }

    private void saveUser(LinkedHashMap<String, String> userDetails){

        //extract user details from API
        String name = userDetails.get("name");
        String email = userDetails.get("email");
        String picture = userDetails.get("picture");
        String userId = userDetails.get("sub");

        //create a new user
        User user = new User();
        user.setGoogleId(userId);
        user.setUsername(name);
        user.setEmail(email);
        user.setPicture(picture);

        //save user
        userService.save(user);
    }

}
