package API;

import Model.DatabaseEntities.User;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import java.util.LinkedHashMap;

public abstract class MainController {

    @Autowired
    UserService userService;

    User getCurrentUser() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>)((OAuth2Authentication) authentication).getUserAuthentication().getDetails();

        String googleId = userDetails.get("sub");
        return userService.findByGoogleId(googleId);
    }
}
