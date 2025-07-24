package services;

import com.esotericsoftware.kryonet.Connection;
import handlers.SQLHandler;
import models.result.errorTypes.UserError;
import models.user.Gender;
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

    public Result<Void> createUser(String username, String password, String email, String nickname, String gender, Integer questionID, String answer) {
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

    public Result<Void> setEmail(String newEmail) {
        User user = SessionManager.getUser(connection);
        if (user == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setEmail(user.getUsername(), newEmail);
    }

    public Result<Void> setPassword(String newPassword) {
        User user = SessionManager.getUser(connection);
        if (user == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setPassword(user.getUsername(), newPassword);
    }

    public Result<Void> setNickname(String newNickname) {
        User user = SessionManager.getUser(connection);
        if (user == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setNickname(user.getUsername(), newNickname);
    }

    public Result<Void> setUsername(String newUsername) {
        User user = SessionManager.getUser(connection);
        if (user == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        Result<Void> result = SQLHandler.setUsername(user.getUsername(), newUsername);
        return result;
    }

    public Result<Void> setSecurityQuestionID(int id) {
        User user = SessionManager.getUser(connection);
        if (user == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setSecurityQuestionID(user.getUsername(), id);
    }

    public Result<Void> setSecurityAnswer(String answer) {
        User user = SessionManager.getUser(connection);
        if (user == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        return SQLHandler.setSecurityAnswer(user.getUsername(), answer);
    }

    public Result<String> assignToken(String username) {
        return SQLHandler.assignToken(username);
    }

    public Result<User> getUserByToken(String token) {
        return SQLHandler.getUserByToken(token);
    }
}