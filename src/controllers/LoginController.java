package controllers;

import models.result.Result;
import models.user.User;
import models.user.UserRepository;

public class LoginController implements MenuStarter {

    private UserRepository userRepository;

    public LoginController() {
        userRepository = UserRepository.getInstance();
    }

    public Result<User> login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void start() {

    }
}
