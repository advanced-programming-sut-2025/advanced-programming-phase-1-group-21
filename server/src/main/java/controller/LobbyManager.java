package controller;

import models.Lobby;

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

    public Lobby getLobbyByID(int id) {
        for (Lobby lobby : lobbies) {
            if (lobby.getID() == id) {
                return lobby;
            }
        }
        return null;
    }
}
