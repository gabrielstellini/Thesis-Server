package API;

import Model.DTO.FoodDTO;
import Model.DatabaseEntities.Food;
import Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/")
    public FoodDTO[] getFoods(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>)((OAuth2Authentication) authentication).getUserAuthentication().getDetails();

        String googleId = userDetails.get("sub");

        Food[] foods = foodService.findByGoogleId(googleId);
        FoodDTO[] foodDTOS = new FoodDTO[foods.length];

        for (int i = 0; i < foods.length; i++) {
            foodDTOS[i] = new FoodDTO();
            foodDTOS[i].toDto(foods[i], foodDTOS[i]);
        }

        return foodDTOS;

    }

}
