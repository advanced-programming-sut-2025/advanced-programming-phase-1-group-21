package services;

import com.esotericsoftware.kryonet.Connection;
import handlers.SQLHandler;
import models.result.errorTypes.UserError;
import models.user.User;
import models.result.Result;
import session.SessionManager;

public class DatabaseService {

    private final Connection connection;

    public DatabaseService(Connection connection) {
        this.connection = connection;
    }

    public Boolean doesUserExists(String username) {
        return SQLHandler.doesUserExist(username);
    }

    public Result<Void> createUser(String username, String password, String email, String nickname, String gender,
                                   String securityQuestion, String answer) {
        return SQLHandler.createUser(username, password, email, nickname, gender, securityQuestion, answer);
    }

    public Result<User> login(String username, String password, boolean stayLoggedIn) {
        Result<User> re = SQLHandler.login(username, password);
        if (re.isSuccess()) {
            SessionManager.add(connection, re.getData());
            return Result.success(re.getData().copy());
        }
        return Result.failure(re.getError());
    }

    public void logout() {
        SessionManager.logout(connection);
    }

    public Result<String> getSecurityQuestion(String username) {
        return SQLHandler.getSecurityQuestion(username);
    }

    public Result<String> getSecurityAnswer(String username) {
        return SQLHandler.getSecurityAnswer(username);
    }

    public Result<Void> setEmail(String newEmail) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setEmail(user.getUsername(), newEmail);
    }


    public Result<Void> setPassword(String oldPassword, String newPassword) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        if (SQLHandler.login(user.getUsername(), oldPassword).isError())
            return Result.failure(UserError.PASSWORD_DOESNT_MATCH);
        return SQLHandler.setPassword(user.getUsername(), newPassword);
    }

    public Result<Void> forgetPassword(String username, String password) {
        return SQLHandler.setPassword(username, password);
    }

    public Result<Void> setNickname(String newNickname) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setNickname(user.getUsername(), newNickname);
    }

    public Result<Void> setUsername(String newUsername) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setUsername(user.getUsername(), newUsername);
    }

    public Result<Void> setSecurityQuestion(String question) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setSecurityQuestion(user.getUsername(), question);
    }

    public Result<Void> setSecurityAnswer(String answer) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setSecurityAnswer(user.getUsername(), answer);
    }

    public Result<Void> setMaxCoin(int maxCoin) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setMaxCoin(user.getUsername(), maxCoin);
    }

    public Result<Void> updateMaxCoinIfHigher(int currentCoin) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);

        if (currentCoin > user.getMaxCoin()) {
            return SQLHandler.setMaxCoin(user.getUsername(), currentCoin);
        }
        return Result.success(null);
    }

    public Result<Void> setGamesPlayed(int gamesPlayed) {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setGamesPlayed(user.getUsername(), gamesPlayed);
    }

    public Result<Void> incrementGamesPlayed() {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);

        int newCount = user.getGamesPlayed() + 1;
        return SQLHandler.setGamesPlayed(user.getUsername(), newCount);
    }

    public Result<String> assignToken(String username) {
        return SQLHandler.assignToken(username);
    }

    public Result<User> getUserByToken(String token) {
        return SQLHandler.getUserByToken(token);
    }

    public Result<User> getUser() {
        User user = SessionManager.getUser(connection);
        if (user == null) return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.getUserByUsername(user.getUsername());
    }
}