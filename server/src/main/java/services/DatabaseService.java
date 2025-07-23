package services;

import com.esotericsoftware.kryonet.Connection;
import handlers.SQLHandler;
import models.user.Gender;
import models.user.User;
import models.result.Result;
import session.SessionManager;



public class DatabaseService {

    private final Connection connection;

    public DatabaseService(Connection connection) {
        this.connection = connection;
    }

    public boolean doesUserExist(String username) {
        return SQLHandler.doesUserExist(username);
    }

    public Result<Void> createUser(String username, String password, String email, String nickname, Gender gender, Integer questionID, String answer) {
        return SQLHandler.createUser(username, password, email, nickname, gender, questionID, answer);
    }

    /**
     * @param password raw password
     */
    public Result<Void> login(String username, String password, boolean stayLoggedIn) {
        Result<User> re = SQLHandler.login(username, password);
        if (re.isSuccess()) {
            SessionManager.add(connection, re.getData());
            return Result.success(null);
        }
        return Result.failure(re.getError());
    }

    public Result<String> getSecurityQuestion(String username) {
        return SQLHandler.getSecurityQuestion(username);
    }

    public Result<Void> setEmail(String username, String newEmail) {
        return SQLHandler.setEmail(username, newEmail);
    }

    public Result<Void> setPassword(String username, String newPassword) {
        return SQLHandler.setPassword(username, newPassword);
    }

    public Result<Void> setNickname(String username, String newNickname) {
        return SQLHandler.setNickname(username, newNickname);
    }

    public Result<Void> setUsername(String oldUsername, String newUsername) {
        return SQLHandler.setUsername(oldUsername, newUsername);
    }

    public Result<Void> setSecurityQuestionID(String username, int id) {
        return SQLHandler.setSecurityQuestionID(username, id);
    }

    public Result<Void> setSecurityAnswer(String username, String answer) {
        return SQLHandler.setSecurityAnswer(username, answer);
    }

    public Result<String> assignToken(String username) {
        return SQLHandler.assignToken(username);
    }

    public Result<User> getUserByToken(String token) {
        return SQLHandler.getUserByToken(token);
    }
}