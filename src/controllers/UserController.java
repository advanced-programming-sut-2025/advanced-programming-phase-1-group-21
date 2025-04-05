package controllers;

import models.result.*;
import models.user.User;

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
}
