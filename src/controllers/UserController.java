package controllers;

import models.result.*;
import models.user.User;
import models.user.UserRepository;

public class UserController {

    private UserRepository userRepository;

    public UserController() {
        userRepository = UserRepository.getInstance();
    }

    public Result<User> login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<User> register(String username, String password, String email, String nickname, String gender) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<User> logout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> saveUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> changeUsername(String currentUsername, String newUsername) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> changePassword(String username, String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> changeEmail(String username, String newEmail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> changeNickname(String username, String newNickname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> showUserInfo(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}