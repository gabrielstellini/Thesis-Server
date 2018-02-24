package API;

import Model.DatabaseEntities.User;
import Service.UserService;
import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import java.util.LinkedHashMap;

public abstract class MainController {

    @Autowired
    UserService userService;

    User getCurrentUser() {

        //TODO: verify token - https://auth0.com/docs/api-auth/tutorials/verify-access-token
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationJsonWebToken authenticationJsonWebToken = (AuthenticationJsonWebToken) authentication;

        String oauthId = authenticationJsonWebToken.getName();
        return userService.findByGoogleId(oauthId);
    }
}
