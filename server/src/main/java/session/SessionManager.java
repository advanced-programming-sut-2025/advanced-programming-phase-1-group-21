package session;

import com.esotericsoftware.kryonet.Connection;
import models.network.Lobby;
import models.network.LobbyUser;
import models.user.User;
import services.ChatService;
import services.DatabaseService;
import services.GameService;
import services.LobbyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<Connection, User> connectionUserMap = new ConcurrentHashMap<>();
    private static final Map<String, Connection> userConnectionMap = new ConcurrentHashMap<>();
    private static final Map<Connection, DatabaseService> databaseServiceMap = new ConcurrentHashMap<>();
    private static final Map<Connection, LobbyService> lobbyServiceMap = new ConcurrentHashMap<>();
    private static final Map<Connection, ChatService> chatServiceMap = new ConcurrentHashMap<>();
    private static final List<User> listUsers = new ArrayList<>();
    private static final Map<Connection, GameService> gameServiceMap = new ConcurrentHashMap<>();


    public static void add(Connection connection) {
        databaseServiceMap.put(connection, new DatabaseService(connection));
    }

    public static void add(Connection conn, User user) {
        connectionUserMap.put(conn, user);
        userConnectionMap.put(user.getUsername(), conn);
        lobbyServiceMap.put(conn, new LobbyService(conn));
        chatServiceMap.put(conn, new ChatService(conn));
        listUsers.add(user);
    }

    public static void logout(Connection conn) {
        User user = connectionUserMap.remove(conn);
        userConnectionMap.remove(user.getUsername());
        listUsers.remove(user);

        LobbyService lobbyService = lobbyServiceMap.remove(conn);
        lobbyService.leaveLobby();

        chatServiceMap.remove(conn);
    }

    public static User getUser(String username) {
        Connection conn = userConnectionMap.get(username);
        if (conn == null) return null;
        return connectionUserMap.get(conn);
    }

    public static User getUser(Connection conn) {
        return connectionUserMap.get(conn);
    }

    public static Connection getConnection(String username) {
        return userConnectionMap.get(username);
    }

    public static void refreshUser(User updatedUser) {
        Connection conn = userConnectionMap.get(updatedUser.getUsername());
        if (conn != null) {
            connectionUserMap.put(conn, updatedUser);
        }
    }

    public static DatabaseService getDatabaseService(Connection conn) {
        return databaseServiceMap.get(conn);
    }

    public static LobbyService getLobbyService(Connection conn) {
        return lobbyServiceMap.get(conn);
    }

    public static List<User> getOnlineUsers() {
        return new ArrayList<>(listUsers);
    }

    public static ChatService getChatService(Connection conn) {
        return chatServiceMap.get(conn);
    }

    public static void disconnected(Connection connection) {
        logout(connection);
        databaseServiceMap.remove(connection);
    }

    public static void addGame(Lobby lobby) {
        List<Connection> connections = getConnectionsByLobby(lobby);
        for (Connection connection : connections) {
            gameServiceMap.put(connection, new GameService(connection, connections));
        }
    }


    public static List<Connection> getConnectionsByLobby(Lobby lobby) {
        List<Connection> connections = new ArrayList<>();
        for (LobbyUser user : lobby.getUsers()) {
            String username = user.user.getUsername();
            connections.add(userConnectionMap.get(username));
        }
        return connections;
    }

    public static GameService getGameService(Connection conn) {
        return gameServiceMap.get(conn);
    }
}
