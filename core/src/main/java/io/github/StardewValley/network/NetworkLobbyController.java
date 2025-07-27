package io.github.StardewValley.network;

import com.esotericsoftware.kryonet.Client;
import models.Lobby;
import models.result.Result;
import models.result.errorTypes.ServerError;
import models.user.User;
import packets.Message;
import packets.MessageType;

import java.util.ArrayList;
import java.util.List;

public class NetworkLobbyController {
    private static final Client client;

    static {
        client = NetworkUtil.getClient();
        if (client == null) {
            throw new RuntimeException("[ERROR] Client is null!");
        }
    }

    public static List<User> getUsers() {
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
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        List<Lobby> result = (List<Lobby>) response.data;
        return result;
    }


    public static Result<Void> createLobby(String name, String password, boolean isPrivate, boolean isInvisible) {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "createLobby";
        msg.data = NetworkUtil.mapArgs("name", name, "password", password, "private", isPrivate, "invisible", isInvisible);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }

    public static Result<Lobby> getLobby() {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "getLobby";
        msg.data = NetworkUtil.mapArgs();
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        Result<Lobby> result = (Result<Lobby>) response.data;
        return result;
    }

    public static Result<Void> removeFromLobby(String username) {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "removeFromLobby";
        msg.data = NetworkUtil.mapArgs("username", username);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }

    public static Result<Void> joinLobby(int id) {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "joinLobby";
        msg.data = NetworkUtil.mapArgs("id", id);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }

    public static Result<Void> leaveLobby() {
        Message msg = new Message(MessageType.LOBBY_SERVICE);
        msg.methodName = "joinLobby";
        msg.data = NetworkUtil.mapArgs();
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }
}
