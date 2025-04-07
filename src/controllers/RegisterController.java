package controllers;

import models.result.Result;
import models.user.User;

public class RegisterController implements MenuStarter {
    @Override
    public void start() {}


    public Result<User> register(String username, String password,String passwordConfirm, String nickname , String email, String gender) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> pickQuestion(String answer, String repeatAnswer, int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
