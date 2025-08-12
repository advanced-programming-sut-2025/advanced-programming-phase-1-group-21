package io.github.StardewValley.network;

import io.github.StardewValley.App;
import models.result.Result;
import models.user.Gender;
import models.user.User;
import models.network.Message;
import models.network.MessageType;
import util.NetworkUtil;

public class NetworkDataBaseController {

    public static boolean doesUserExists(String username) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "doesUserExists";
        msg.data = NetworkUtil.mapArgs("username", username);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        Boolean result = (Boolean) response.data;
        return result;
    }

    public static void createUser(String username, String password, String email, String nickname, Gender gender, String securityQuestion, String securityAnswer) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "createUser";
        msg.data = NetworkUtil.mapArgs(
                "username", username,
                "password", password,
                "email", email,
                "nickname", nickname,
                "gender", gender.name(),
                "securityQuestion", securityQuestion,
                "answer", securityAnswer
        );
        Result r = (Result) ClientNetwork.sendMessageAndWaitForResponse(msg).data;
        System.out.println(r.getMessage());
    }

    public static Result<String> getSecurityQuestion(String username) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "getSecurityQuestion";
        msg.data = NetworkUtil.mapArgs("username", username);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (Result<String>) response.data;
    }

    public static Result<String> getSecurityAnswer(String username) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "getSecurityAnswer";
        msg.data = NetworkUtil.mapArgs("username", username);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (Result<String>) response.data;
    }

        public static void setSecurityQuestion(String question) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setSecurityQuestion";
        msg.data = NetworkUtil.mapArgs("question", question);
        ClientNetwork.sendMessageAndWaitForResponse(msg);
    }

    public static void setSecurityAnswer(String answer) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setSecurityAnswer";
        msg.data = NetworkUtil.mapArgs("answer", answer);
        ClientNetwork.sendMessageAndWaitForResponse(msg);
    }

    public static Result<Void> setEmail(String newEmail) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setEmail";
        msg.data = NetworkUtil.mapArgs("newEmail", newEmail);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<Void> setPassword(String oldPassword, String newPassword) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setPassword";
        msg.data = NetworkUtil.mapArgs("oldPassword", oldPassword, "newPassword", newPassword);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<Void> forgetPassword(String username, String password) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "forgetPassword";
        msg.data = NetworkUtil.mapArgs( "username", username, "password", password);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<Void> setNickname(String newNickname) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setNickname";
        msg.data = NetworkUtil.mapArgs("newNickname", newNickname);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<Void> setUsername(String newUsername) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "setUsername";
        msg.data = NetworkUtil.mapArgs("newUsername", newUsername);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (Result<Void>) response.data;
    }

    public static Result<User> login(String username, String password, boolean stayLoggedIn) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "login";
        msg.data = NetworkUtil.mapArgs(
                "username", username,
                "password", password,
                "stayLoggedIn", stayLoggedIn
        );
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (Result<User>) response.data;
    }

    public static void logout() {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "logout";
        msg.data = NetworkUtil.mapArgs();
        ClientNetwork.sendMessageAndWaitForResponse(msg);
    }

    public static void incrementGamesPlayed() {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "incrementGamesPlayed";
        msg.data = NetworkUtil.mapArgs();
        ClientNetwork.sendMessageAndWaitForResponse(msg);
    }

    public static void updateMaxCoinIfHigher(int currentCoin) {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "updateMaxCoinIfHigher";
        msg.data = NetworkUtil.mapArgs("currentCoin", currentCoin);
        ClientNetwork.sendMessageAndWaitForResponse(msg);
    }

    public static void updateUser() {
        Message msg = new Message(MessageType.DATABASE_SERVICE);
        msg.methodName = "getUser";
        msg.data = NetworkUtil.mapArgs();
        Message resp = ClientNetwork.sendMessageAndWaitForResponse(msg);
        Result<User> r = (Result<User>) resp.data;
        if (r.isSuccess())
            App.getInstance().logedInUser = r.getData();
    }
}
