package io.github.StardewValley.network;

import com.esotericsoftware.kryonet.Client;
import models.network.Lobby;
import models.network.LobbyUser;
import models.result.Result;
import models.user.User;
import models.network.Message;
import models.network.MessageType;
import util.NetworkUtil;

import java.util.List;

public class NetworkLobbyController {
    private static final Client client;

    static {
        client = ClientNetwork.getClient();
        if (client == null) {
            throw new RuntimeException("[ERROR] Client is null!");
        }
    }

    public static List<LobbyUser> getUsersOfLobby() {
        Result<Lobby> lobbyResult = getLobby();
        if (lobbyResult.isSuccess()) {
            return lobbyResult.getData().getUsers();
        }
        return null;
    }


    public static List<Lobby> getAllLobbies() {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "getAllLobbies";
        msg.data = NetworkUtil.mapArgs();
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        List<Lobby> result = (List<Lobby>) response.data;
        return result;
    }


    public static Result<Void> createLobby(String name, String password, boolean isPrivate, boolean isInvisible) {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "createLobby";
        msg.data = NetworkUtil.mapArgs("name", name, "password", password, "isPrivate", isPrivate, "isInvisible", isInvisible);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }

    public static Result<Lobby> getLobby() {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "getLobby";
        msg.data = NetworkUtil.mapArgs();
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        Result<Lobby> result = (Result<Lobby>) response.data;
        return result;
    }

    public static Result<Void> removeFromLobby(String username) {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "removeFromLobby";
        msg.data = NetworkUtil.mapArgs("username", username);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }

    public static Result<Void> joinLobby(String id, String password) {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "joinLobby";
        msg.data = NetworkUtil.mapArgs("id", id, "password", password);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }

    public static Result<Void> leaveLobby() {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "leaveLobby";
        msg.data = NetworkUtil.mapArgs();
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }

    public static List<User> getOnlineUsers() {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "getOnlineUsers";
        msg.data = NetworkUtil.mapArgs();
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        List<User> result = (List<User>) response.data;
        return result;
    }

    public static void sendMap(int mapNumber) {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "setMapID";
        msg.data = NetworkUtil.mapArgs("mapID", mapNumber);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
    }

    public static void toggleReady() {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "toggleReady";
        msg.data = NetworkUtil.mapArgs();
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
    }
}
