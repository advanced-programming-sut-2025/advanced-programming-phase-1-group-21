package services;

import Network.Message;
import Network.MessageType;
import com.esotericsoftware.kryonet.Connection;
import controller.LobbyManager;
import models.network.Chat;
import models.network.ChatType;
import models.result.Result;
import models.result.errorTypes.UserError;
import models.user.User;
import session.SessionManager;
import util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class ChatService {


    private final Connection connection;
    private final User user;

    public ChatService(Connection connection) {
        this.connection = connection;
        this.user = SessionManager.getUser(connection);
    }

    //this means, CLIENT HAS given us "chat"
    public Result<Void> sendMessage(Chat chat) {
        List<Connection> recivers;
        if (chat.getType() == ChatType.TO_USER)
            recivers = handleUserMessage(chat);
        else
            recivers = handleLobbyMessage(chat);

        for (Connection conn : recivers) {
            Message msg = new Message(MessageType.CLIENT_SERVICE);
            msg.methodName = "handleChat";
            msg.data = NetworkUtil.mapArgs("chat", chat);
            conn.sendTCP(msg);
        }
        return Result.success(null);
    }

    //this shouldn't be called from CLIENT
    //throws an ERROR if run from CLIENT (because of private access)
    private List<Connection> handleUserMessage(Chat chat) {
        Connection reciver = SessionManager.getConnection(chat.getReceiver());
        return List.of(reciver);
    }

    private List<Connection> handleLobbyMessage(Chat chat) {
        List<User> users = LobbyManager.get().getLobbyByUsername(chat.getSender()).getUsers();
        List<Connection> connections = new ArrayList<>();
        for (User user : users) {
            Connection conn = SessionManager.getConnection(user.getUsername());
            connections.add(conn);
        }
        return connections;
    }

}
