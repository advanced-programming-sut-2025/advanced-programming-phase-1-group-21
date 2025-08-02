package services;

import models.network.Message;
import com.esotericsoftware.kryonet.Connection;
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
        System.out.println("SENDING TO ALL: MSG: " + msg);
        for (Connection conn : toSendList) {
            System.out.println("SENDING: " + conn.getEndPoint());
            NetworkUtil.sendMessage(msg, conn);
        }
    }
}
