package handlers;

import models.result.Result;
import models.result.errorTypes.AuthError;
import models.result.errorTypes.ServerError;
import models.result.errorTypes.UserError;
import models.user.Gender;
import models.user.Hash;
import models.user.User;

import java.sql.*;
import java.util.UUID;

/**
 * Token is actually used for automatic login
 * Client sends the TOKEN
 * if we find the USER, Then we automatically send a msg to client that he can login!
 */
public class SQLHandler {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    static {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String createTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "username TEXT PRIMARY KEY,"
                    + "email TEXT,"
                    + "nickname TEXT,"
                    + "gender TEXT,"
                    + "password TEXT,"
                    + "security_question TEXT,"
                    + "security_answer TEXT,"
                    + "max_coin INTEGER DEFAULT 0,"
                    + "games_played INTEGER DEFAULT 0,"
                    + "token TEXT"
                    + ")";
            conn.createStatement().execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean doesUserExist(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM users WHERE username = ?");
            stmt.setString(1, username);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Result<Void> createUser(String username, String password, String email, String nickname,
                                          String gender, String question, String answer) {
        if (doesUserExist(username)) {
            return Result.failure(AuthError.USER_ALREADY_EXISTS);
        }
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (username, email, nickname, gender, password, security_question, security_answer, max_coin, games_played, token) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, nickname);
            stmt.setString(4, gender);
            stmt.setString(5, Hash.hashPassword(password));
            stmt.setString(6, question);
            stmt.setString(7, answer);
            stmt.setInt(8, 0);  // maxCoin default
            stmt.setInt(9, 0);  // gamesPlayed default
            stmt.setString(10, null); // token
            stmt.executeUpdate();
            return Result.success(null);
        } catch (SQLException e) {
            return Result.failure(ServerError.SQL_ERROR);
        }
    }

    public static Result<User> login(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return Result.failure(AuthError.USER_NOT_FOUND);

            String realPasswordHash = rs.getString("password");
            String hash = Hash.hashPassword(password);

            if (!hash.equals(realPasswordHash)) return Result.failure(UserError.PASSWORD_DOESNT_MATCH);

            User user = extractUser(rs);
            return Result.success(user);
        } catch (SQLException e) {
            return Result.failure(ServerError.SQL_ERROR);
        }
    }

    public static Result<String> getSecurityQuestion(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT security_question FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return Result.failure(UserError.USER_NOT_FOUND);
            return Result.success(rs.getString("security_question"));
        } catch (SQLException e) {
            return Result.failure(ServerError.SQL_ERROR);
        }
    }

    public static Result<String> getSecurityAnswer(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT security_answer FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) return Result.failure(UserError.USER_NOT_FOUND);
            return Result.success(rs.getString("security_answer"));
        } catch (SQLException e) {
            return Result.failure(ServerError.SQL_ERROR);
        }
    }

    public static Result<Void> setEmail(String username, String newEmail) {
        return updateField(username, "email", newEmail);
    }

    public static Result<Void> setPassword(String username, String newPassword) {
        return updateField(username, "password", Hash.hashPassword(newPassword));
    }

    public static Result<Void> setNickname(String username, String newNickname) {
        return updateField(username, "nickname", newNickname);
    }

    public static Result<Void> setUsername(String oldUsername, String newUsername) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            int updated = stmt.executeUpdate();
            return updated > 0 ? Result.success(null) : Result.failure(UserError.USER_NOT_FOUND);
        } catch (SQLException e) {
            return Result.failure(ServerError.SQL_ERROR);
        }
    }

    public static Result<Void> setSecurityQuestion(String username, String question) {
        return updateField(username, "security_question", question);
    }

    public static Result<Void> setSecurityAnswer(String username, String answer) {
        return updateField(username, "security_answer", answer);
    }

    public static Result<Void> setMaxCoin(String username, int maxCoin) {
        return updateField(username, "max_coin", maxCoin);
    }

    public static Result<Void> setGamesPlayed(String username, int gamesPlayed) {
        return updateField(username, "games_played", gamesPlayed);
    }

    public static Result<String> assignToken(String username) {
        String token = UUID.randomUUID().toString();
        if (updateField(username, "token", token).isSuccess()) {
            return Result.success(token);
        }
        return Result.failure(ServerError.TOKEN_ERROR);
    }

    public static Result<User> getUserByToken(String token) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE token = ?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return Result.failure(UserError.USER_NOT_FOUND);
            return Result.success(extractUser(rs));
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.failure(ServerError.SQL_ERROR);
        }
    }

    private static Result<Void> updateField(String username, String field, Object value) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET " + field + " = ? WHERE username = ?");
            stmt.setObject(1, value);
            stmt.setString(2, username);
            int updated = stmt.executeUpdate();
            return updated > 0 ? Result.success(null) : Result.failure(UserError.USER_NOT_FOUND);
        } catch (SQLException e) {
            return Result.failure(ServerError.SQL_ERROR);
        }
    }

    private static User extractUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("nickname"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("security_question"),
                rs.getString("security_answer"),
                false,
                rs.getInt("max_coin"),
                rs.getInt("games_played")
        );
    }

    public static Result<User> getUserByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Result.success(extractUser(rs));
            } else {
                return Result.failure(UserError.USER_NOT_FOUND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.failure(ServerError.SQL_ERROR);
        }
    }
}