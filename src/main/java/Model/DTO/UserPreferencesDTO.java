package Model.DTO;

import Model.DatabaseEntities.TimePeriod;
import Model.DatabaseEntities.UserPreferences;
import Model.EntityToDto;

public class UserPreferencesDTO extends EntityToDto<UserPreferencesDTO, UserPreferences> {
    private TimePeriod[] PreferredTimes;

    public TimePeriod[] getPreferredTimes() {
        return PreferredTimes;
    }

    public void setPreferredTimes(TimePeriod[] preferredTimes) {
        PreferredTimes = preferredTimes;
    }
}
