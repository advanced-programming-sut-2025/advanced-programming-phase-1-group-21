package controller;

import models.Lobby;
import models.user.User;

import java.util.ArrayList;

public class LobbyController {
    private static final LobbyManager manager = LobbyManager.get();

    public static ArrayList<Lobby> getLobbies() {
        return manager.getLobbies();
    }

    private final Lobby lobby;
    public LobbyController(Lobby lobby) {
        this.lobby = lobby;
    }

    public void removeUser(String username) {
        removeUser(lobby.getUserByUsername(username));
    }

    private void handleEmptyLobby() {
        if (lobby.isEmpty())
            manager.removeLobby(lobby);
    }

    public void removeUser(User user) {
        lobby.removeUser(user);
        handleEmptyLobby();
    }

    public static Lobby getLobbyByID(int id) {
        return manager.getLobbyByID(id);
    }

    public static Lobby getLobbyByName(String name) {
        return manager.getLobbyByName(name);
    }
}
