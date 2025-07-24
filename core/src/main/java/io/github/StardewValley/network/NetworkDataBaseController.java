package io.github.StardewValley.network;

import models.result.Result;
import models.user.Gender;
import models.user.User;
import packets.Message;
import packets.MessageType;

public class NetworkDataBaseController {

    public static boolean doesUserExists(String username) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "doesUserExists";
        msg.data = NetworkUtil.mapArgs("username", username);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        Boolean result = (Boolean) response.data;
        return result;
    }

    public static void createUser(String username, String password, String email, String nickname, Gender gender) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "createUser";
        msg.data = NetworkUtil.mapArgs(
                "username", username,
                "password", password,
                "email", email,
                "nickname", nickname,
                "gender", gender.name()
        );
        Result r = (Result) NetworkUtil.sendMessageAndWaitForResponse(msg).data;
        System.out.println(r.getMessage());
    }

    public static Result<String> getSecurityQuestion(String username) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "getSecurityQuestion";
        msg.data = NetworkUtil.mapArgs("username", username);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        return (Result<String>) response.data;
    }

    public static void setSecurityQuestionID(int i) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setSecurityQuestionID";
        msg.data = NetworkUtil.mapArgs("questionID", i);
        NetworkUtil.sendMessageAndWaitForResponse(msg);
    }

    public static void setSecurityAnswer(String answer) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setSecurityAnswer";
        msg.data = NetworkUtil.mapArgs("answer", answer);
        NetworkUtil.sendMessageAndWaitForResponse(msg);
    }

    public static Result<Void> setEmail(String newEmail) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setEmail";
        msg.data = NetworkUtil.mapArgs("newEmail", newEmail);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<Void> setPassword(String oldPassword, String newPassword) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setPassword";
        msg.data = NetworkUtil.mapArgs("oldPassword", oldPassword, "newPassword", newPassword);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<Void> setNickname(String newNickname) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setNickname";
        msg.data = NetworkUtil.mapArgs("newNickname", newNickname);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<Void> setUsername(String newUsername) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setUsername";
        msg.data = NetworkUtil.mapArgs("newUsername", newUsername);
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<Void> login(String username, String password, boolean stayLoggedIn) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "login";
        msg.data = NetworkUtil.mapArgs(
                "username", username,
                "password", password,
                "stayLoggedIn", stayLoggedIn
        );
        Message response = NetworkUtil.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }
}
