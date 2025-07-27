package session;

import com.esotericsoftware.kryonet.Connection;
import models.Lobby;
import models.user.User;
import services.DatabaseService;
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
    private static final List<User> listUsers = new ArrayList<>();


    public static void add(Connection connection) {
        databaseServiceMap.put(connection, new DatabaseService(connection));
    }

    public static void add(Connection conn, User user) {
        connectionUserMap.put(conn, user);
        userConnectionMap.put(user.getUsername(), conn);
        lobbyServiceMap.put(conn, new LobbyService(conn));
        listUsers.add(user);
    }

    public static void remove(Connection conn) {
        User user = connectionUserMap.remove(conn);
        userConnectionMap.remove(user.getUsername());
        databaseServiceMap.remove(conn);
        listUsers.remove(user);
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
}
