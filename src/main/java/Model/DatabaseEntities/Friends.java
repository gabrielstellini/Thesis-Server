package Model.DatabaseEntities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Friends {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    @OneToOne
    private User user;

    @OneToOne
    private User[] friends;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User[] getFriends() {
        return friends;
    }

    public void setFriends(User[] friends) {
        this.friends = friends;
    }
}
