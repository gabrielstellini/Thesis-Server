package Model.DTO;

import Model.DatabaseEntities.Score;
import Model.DatabaseEntities.User;
import Model.EntityToDto;

public class UserDTO extends EntityToDto<UserDTO, User> {
    private String username;
    private String email;
    private String picture;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
