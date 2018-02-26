package API.Auth;

import Model.DatabaseEntities.User;
import Service.UserService;
import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.UserInfo;
import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;


public class OAuthUserHandler {

    private UserService userService;

    @Autowired
    public OAuthUserHandler(UserService userService) {
        this.userService = userService;
    }

    public void handleUser(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationJsonWebToken basicDetails = ((AuthenticationJsonWebToken) authentication);

        String oauthId = basicDetails.getName();
        boolean foundUser = userService.existsByGoogleId(oauthId);
        System.out.println("Database found user: " + foundUser);

        if(!foundUser) {
            Object credentials = basicDetails.getCredentials();

            Map<String, Object> userDetails = fetchUserId((String)credentials);
            saveUser(userDetails);
        }
    }

    private void saveUser(Map<String, Object> userDetails){

        //extract user details from API
        String name = (String)userDetails.get("name");
        String email = (String)userDetails.get("email");
        String picture = (String)userDetails.get("picture");
        String userId = (String)userDetails.get("sub");

        //create a new user
        User user = new User();
        user.setGoogleId(userId);
        user.setUsername(name);
        user.setEmail(email);
        user.setPicture(picture);

        //save user
        userService.save(user);
    }


    public void testDecodeJWT(String jwtToken){
        System.out.println("------------ Decode JWT ------------");
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);

        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println("JWT Body : "+body);
    }

    private Map<String, Object> fetchUserId(String accessToken) {
        final AuthAPI client = new AuthAPI("sehm.eu.auth0.com","NTIW1Y2FqDAQP8Op4gD9UjGavzoYRoji", "M-_7fst7QIMXkLaU8h-H-BKj1aozGr5Mst6t0-h6tSOM4_A_GOLtu1VlQIdZDZnI");

        UserInfo info = null;
        try {

            info = client
                    .userInfo(accessToken)
                    .execute();

        } catch (Auth0Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> userDetails = info.getValues();

        return userDetails;
    }
}
