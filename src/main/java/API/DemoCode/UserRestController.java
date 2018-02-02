package API.DemoCode;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class UserRestController {

    @RequestMapping(
            value = "user",
            method = RequestMethod.GET)
    public Principal user(Principal user) {

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String clientId = ((OAuth2Authentication) a).getOAuth2Request().getClientId();

        System.out.println(clientId);
        return user;
    }
}
