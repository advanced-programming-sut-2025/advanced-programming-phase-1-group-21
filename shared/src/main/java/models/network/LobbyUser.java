package models.network;

import models.user.User;

public class LobbyUser {
    public User user;
    public boolean isReady;
    public int mapID;

    public LobbyUser(User user) {
        this.user = user;
        this.isReady = false;
        this.mapID = 1;
    }

    //KRYO-NET
    public LobbyUser() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() == User.class) {
            return o.equals(user);
        }
        if (getClass() != o.getClass()) return false;
        LobbyUser that = (LobbyUser) o;
        return that.user.equals(this.user);
    }

    @Override
    public String toString() {
        return "{user=" + user.toString() + ", isReady=" + isReady + ", mapID=" + mapID + "}";
    }
}
