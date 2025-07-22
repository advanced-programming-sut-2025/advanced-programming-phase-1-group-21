package io.github.StardewValley.network;

import models.result.Result;
import models.user.Gender;
import models.user.User;

public class NetworkDataBaseController {

    public static boolean doesUserExists(String username) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public static void createUser(String username, String password, String email, String nickname, Gender gender) {
        //User user = new User(username , password , email , nickname , gender , null , null , false);

    }

    public static Result<String> getSecurityQuestion(String username) {

    }

    public static void setSecurityQuestionID(int i) {
    }

    public static void setSecurityAnswer(String answer) {
    }

    public static Result<Void> setEmail(String newEmail) {
    }

    public static Result<Void> setPassword(String oldPassword, String newPassword) {
    }

    public static Result<Void> setNickname(String newNickname) {
    }

    public static Result<Void> setUsername(String newUsername) {
    }

    public static Result<Void> login(String username, String password, boolean stayLoggedIn) {
    }
}
