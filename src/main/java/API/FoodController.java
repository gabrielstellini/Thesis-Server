package API;

import Model.DTO.FoodDTO;
import Model.DatabaseEntities.Food;
import Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/food")
public class FoodController extends MainController{
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("")
    public FoodDTO[] getFoods(){
        String googleId = getCurrentUser().getGoogleId();

        Food[] foods = foodService.findByGoogleId(googleId);
        FoodDTO[] foodDTOS = new FoodDTO[foods.length];

        for (int i = 0; i < foods.length; i++) {
            foodDTOS[i] = new FoodDTO();
            foodDTOS[i].toDto(foods[i], foodDTOS[i]);
        }

        return foodDTOS;

    }

    @PostMapping("")
    public void addFood(@RequestBody @NotNull FoodDTO foodDto){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Food food = new Food();
        food.setCalories(foodDto.getCalories());
        food.setName(foodDto.getName());
        food.setQuantity(foodDto.getQuantity());
        food.setUser(getCurrentUser());
        food.setTimestamp(timestamp.getTime());

        //TODO: calculate stress

        foodService.save(food);
    }

    @Transactional
    @DeleteMapping("{foodId}")
    public void deleteFood(@PathVariable int foodId){
        int userId = getCurrentUser().getId();
        Food food = foodService.findById(foodId);
        if(food != null){
            if(food.getUser().getId() == userId){
                foodService.removeById(food.getId());
            }
        }
    }

}
