package Service;

import Model.DatabaseEntities.Food;
import Model.DatabaseEntities.User;
import Model.Repositories.FoodRepository;
import Model.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository, UserRepository userRepository){
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    public Food[] findByGoogleId(String googleId){
        User user = userRepository.findByGoogleId(googleId);
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
