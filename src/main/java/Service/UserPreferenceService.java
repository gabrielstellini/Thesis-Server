package Service;

import Model.DatabaseEntities.User;
import Model.DatabaseEntities.UserPreference;
import Repositories.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceService {
    private final UserPreferenceRepository preferenceRepository;
    private final UserService userService;

    @Autowired
    public UserPreferenceService(UserPreferenceRepository preferenceRepository, UserService userService) {
        this.preferenceRepository = preferenceRepository;
        this.userService = userService;
    }

    public UserPreference[] findAllByUser(String googleId){
        User user = userService.findByGoogleId(googleId);
        UserPreference[] userPreferences = this.preferenceRepository.findAllByUser(user);
        return userPreferences;
    }

    public UserPreference[] findAllByUser(User user){
        UserPreference[] userPreferences = this.preferenceRepository.findAllByUser(user);
        return userPreferences;
    }

    public UserPreference findById(int Id){
        UserPreference userPreference = this.preferenceRepository.findById(Id);
        return userPreference;
    }

    public void removeById(int id){
        this.preferenceRepository.removeById(id);
    }

    public void save(UserPreference userPreference){
        this.preferenceRepository.save(userPreference);
    }
}
