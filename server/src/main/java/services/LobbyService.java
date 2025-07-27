package services;

import com.esotericsoftware.kryonet.Connection;
import controller.LobbyManager;
import models.Lobby;
import models.result.Result;
import models.result.errorTypes.ServerError;
import models.user.User;
import session.SessionManager;
import util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class LobbyService {


    private final Connection connection;
    private final User user;
    private Lobby lobby;

    public LobbyService(Connection connection) {
        this.connection = connection;
        this.user = SessionManager.getUser(connection);
    }

    public List<Lobby> getAllLobbies() {
        return new ArrayList<>(LobbyManager.get().getLobbies());
    }


    public Result<Void> createLobby(String name, String password, boolean isPrivate, boolean isInvisible) {
        lobby = new Lobby(name, password, !isInvisible, isPrivate, NetworkUtil.random);
        LobbyManager.get().addLobby(lobby);
        lobby.setAdmin(user);
        lobby.addUser(user);
        return Result.success(null);
    }

    public Result<Lobby> getLobby() {
        if (lobby == null) {
            return Result.failure(ServerError.NO_LOBBY);
        }
        return Result.success(lobby);
    }

    public Result<Void> removeFromLobby(String username) {
        if (!lobby.getAdmin().equals(user))
            return Result.failure(ServerError.INSUFFICENT_PERMISSION);
        User userToRemove = lobby.getUserByUsername(username);
        if (userToRemove == null) {
            throw new RuntimeException("[REMOVING FROM LOBBY] User not found");
        }
        lobby.removeUser(userToRemove);
        return Result.success(null);
    }

    public Result<Void> joinLobby(int id, String password) {
        lobby = LobbyManager.get().getLobbyByID(id);
        if (lobby == null) {
            return Result.failure(ServerError.NO_LOBBY);
        }
        if (lobby.getPassword() != null && !lobby.getPassword().equals(password))
            return Result.failure(ServerError.INSUFFICENT_PERMISSION);
        lobby.addUser(user);
        return Result.success(null);
    }

    public Result<Void> leaveLobby() {
        lobby.removeUser(user);
        if (lobby.isEmpty())
            LobbyManager.get().removeLobby(lobby);
        return Result.success(null);
    }

    public List<User> getOnlineUsers() {
        return SessionManager.getOnlineUsers();
    }

}
