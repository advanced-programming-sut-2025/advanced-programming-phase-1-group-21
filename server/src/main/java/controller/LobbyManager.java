package controller;

import models.network.Lobby;
import models.network.LobbyUser;
import models.user.User;

import java.util.ArrayList;

public class LobbyManager {
    private static LobbyManager manager;

    private LobbyManager() {
    }

    public static LobbyManager get() {
        if (manager == null) {
            manager = new LobbyManager();
        }
        return manager;
    }

    ArrayList<Lobby> lobbies = new ArrayList<>();

    public void addLobby(Lobby lobby) {
        lobbies.add(lobby);
    }

    public void removeLobby(Lobby lobby) {
        lobbies.remove(lobby);
    }

    public ArrayList<Lobby> getLobbies() {
        ArrayList<Lobby> visibleLobbies = new ArrayList<>();
        for (Lobby lobby : lobbies) {
            if (lobby.isVisible()) {
                visibleLobbies.add(lobby);
            }
        }
        return visibleLobbies;
    }

    public Lobby getLobbyByName(String name) {
        for (Lobby lobby : lobbies) {
            if (lobby.getName().equals(name)) {
                return lobby;
            }
        }
        return null;
    }

    public Lobby getLobbyByID(String id) {
        for (Lobby lobby : lobbies) {
            if (lobby.getID().equals(id)) {
                return lobby;
            }
        }
        return null;
    }

    public Lobby getLobbyByUsername(String username) {
        for (Lobby lobby : lobbies) {
            for (LobbyUser user : lobby.getUsers()) {
                if (user.user.getUsername().equals(username)) {
                    return lobby;
                }
            }
        }
        return null;
    }
}
