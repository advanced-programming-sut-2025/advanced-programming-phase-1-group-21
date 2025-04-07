package controllers;

import models.result.Result;
import models.user.User;
import models.user.UserRepository;

public class LoginMenuController implements MenuStarter {

    @Override
    public void start() {

    }

    private UserRepository userRepository;

    public LoginMenuController() {
        userRepository = UserRepository.getInstance();
    }

    public Result<User> login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> forgetPassword(String username) {throw new UnsupportedOperationException("Not supported yet.");}

    private Result<Void> answer(String answer) {throw new UnsupportedOperationException("Not supported yet.");}

    private Result<Void> userLogout() {throw new UnsupportedOperationException("Not supported yet.");}

}
