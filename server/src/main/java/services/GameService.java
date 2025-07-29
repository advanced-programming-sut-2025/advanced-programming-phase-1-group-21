package services;

import Network.Message;
import com.esotericsoftware.kryonet.Connection;
import models.network.Lobby;
import models.network.LobbyUser;
import session.SessionManager;
import util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    private final Connection connection;
    private final List<Connection> toSendList = new ArrayList<>();

    public GameService(Connection connection, List<Connection> connections) {
        this.connection = connection;
        for (Connection conn : connections) {
            if (!connection.equals(conn)) {
                toSendList.add(conn);
            }
        }
    }

    public void sendAll(Message msg) {
        for (Connection conn : toSendList) {
            NetworkUtil.sendMessage(msg, conn);
        }
    }
}
