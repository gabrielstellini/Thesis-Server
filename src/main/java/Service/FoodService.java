package Service;

import Model.DatabaseEntities.Food;
import Model.DatabaseEntities.User;
import Repositories.FoodRepository;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final UserService userService;

    @Autowired
    public FoodService(FoodRepository foodRepository, UserService userService){
        this.foodRepository = foodRepository;
        this.userService = userService;
    }

    public Food[] findByGoogleId(String googleId){
        User user = userService.findByGoogleId(googleId);
        Food[] foods = foodRepository.findByUser(user);
        return foods;
    }

    public void save(Food food){
        foodRepository.save(food);
    }

    public Food findById(Integer id){
        return foodRepository.findById(id);
    }

    public void removeById(int id){
        foodRepository.removeById(id);
    }


}
