package controllers;

import models.result.Result;

public class ProfileController implements MenuStarter {
    @Override
    public void start() {

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
}
