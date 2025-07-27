package session;

import com.esotericsoftware.kryonet.Connection;
import models.Lobby;
import models.user.User;
import services.DatabaseService;
import services.LobbyService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<Connection, User> connectionUserMap = new ConcurrentHashMap<>();
    private static final Map<String, Connection> userConnectionMap = new ConcurrentHashMap<>();
    private static final Map<Connection, DatabaseService> databaseServiceMap = new ConcurrentHashMap<>();
    private static final Map<Connection, LobbyService> lobbyServiceMap = new ConcurrentHashMap<>();


    public static void add(Connection connection) {
        databaseServiceMap.put(connection, new DatabaseService(connection));
        lobbyServiceMap.put(connection, new LobbyService(connection));
    }

    public static void add(Connection conn, User user) {
        connectionUserMap.put(conn, user);
        userConnectionMap.put(user.getUsername(), conn);
    }

    public static void remove(Connection conn) {
        User user = connectionUserMap.remove(conn);
        userConnectionMap.remove(user.getUsername());
        databaseServiceMap.remove(conn);
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
}
