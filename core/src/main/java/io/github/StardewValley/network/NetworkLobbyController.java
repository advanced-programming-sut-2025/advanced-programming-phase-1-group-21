package io.github.StardewValley.network;

import com.esotericsoftware.kryonet.Client;
import models.Lobby;
import models.user.User;
import packets.Message;
import packets.MessageType;

import java.util.List;

public class NetworkLobbyController {
    private static final Client client;

    static {
        client = NetworkUtil.getClient();
        if (client == null) {
            throw new RuntimeException("[ERROR] Client is null!");
        }
    }

    public static List<Lobby> requestLobbies() {
        Message response = NetworkUtil.sendMessageAndWaitForResponse(new Message(MessageType.REQUEST_LOBBIES));
        try {
            List<Lobby> lobbyList = (List<Lobby>) response.data;
            return lobbyList;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("[ERROR] Failed to get lobbies!");
        }
    }

    public static List<User> getUsers() {
        Message response = NetworkUtil.sendMessageAndWaitForResponse(new Message(MessageType.REQUEST_LOBBY_USERS));
        try {
            List<User> lobbyList = (List<User>) response.data;
            return lobbyList;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("[ERROR] Failed to get lobby users!");
        }
    }
}
