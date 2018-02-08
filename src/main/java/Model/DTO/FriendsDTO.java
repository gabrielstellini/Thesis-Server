package Model.DTO;

import Model.DatabaseEntities.Friends;

public class FriendsDTO {
    private String[] friends;

    public void toDto(Friends[] friends) {
        this.friends = new String[friends.length];
        for (int i = 0; i < friends.length; i++) {
            this.friends[i] = friends[i].getUser().getGoogleId();
        }
    }

    public String[] getFriends() {
        return friends;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }
}
