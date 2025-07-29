package services;

import Network.Message;
import Network.MessageType;
import com.esotericsoftware.kryonet.Connection;
import controller.LobbyManager;
import models.network.Lobby;
import models.network.LobbyUser;
import models.result.Result;
import models.result.errorTypes.ServerError;
import models.user.User;
import session.SessionManager;
import util.NetworkUtil;
import util.ServerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        lobby = new Lobby(name, password, !isInvisible, isPrivate);
        LobbyManager.get().addLobby(lobby);
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
        if (lobby == null) {
            return Result.failure(ServerError.NO_LOBBY);
        }
        if (!lobby.getAdmin().equals(user))
            return Result.failure(ServerError.INSUFFICENT_PERMISSION);
        LobbyUser userToRemove = lobby.getUserByUsername(username);
        if (userToRemove == null) {
            throw new RuntimeException("[REMOVING FROM LOBBY] User not found");
        }
        lobby.removeUser(userToRemove);
        makeRefresh();
        return Result.success(null);
    }

    public Result<Void> joinLobby(String id, String password) {
        lobby = LobbyManager.get().getLobbyByID(id);
        if (lobby == null) {
            return Result.failure(ServerError.NO_LOBBY);
        }
        if (lobby.getPassword() != null && !lobby.getPassword().equals(password))
            return Result.failure(ServerError.INSUFFICENT_PERMISSION);
        lobby.addUser(user);
        makeRefresh();
        return Result.success(null);
    }

    public Result<Void> leaveLobby() {
        if (lobby == null) {
            return Result.failure(ServerError.NO_LOBBY);
        }
        lobby.removeUser(lobby.getUserByUsername(user.getUsername()));
        if (lobby.isEmpty())
            LobbyManager.get().removeLobby(lobby);
        makeRefresh();
        return Result.success(null);
    }

    public List<User> getOnlineUsers() {
        return SessionManager.getOnlineUsers();
    }

    public void sendAllLobby(Message message) {
        List<Connection> connections = SessionManager.getConnectionsByLobby(lobby);
        for (Connection c : connections) {
            if (c != null) {
                NetworkUtil.sendMessage(message, c);
            }
        }
    }

    public void makeRefresh() {
        sendAllLobby(ServerUtil.createRefreshMessage());
    }

    public void setMapID(int mapID) {
        lobby.setMapID(user.getUsername(), mapID);
        makeRefresh();
    }

    public void toggleReady() {
        lobby.toggleReady(user.getUsername());
        makeRefresh();

        CompletableFuture.runAsync(this::checkReadyLobby);
    }

    private void checkReadyLobby() {
        for (LobbyUser user: lobby.getUsers()) {
            if (!user.isReady)
                return;
        }
        sendAllLobby(new Message(MessageType.CLIENT_SERVICE, "sendMapID"));
        try {
            Thread.sleep(ServerUtil.AVOID_RACE);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        //All are READY
        SessionManager.addGame(lobby);
        sendAllLobby(ServerUtil.createGameStart());
    }
}
