package util;

import models.network.Message;
import models.network.MessageType;


public class ServerUtil {

    public static final long AVOID_RACE = 1000;

    public static Message createPingMessage() {
        Message msg = new Message(MessageType.CLIENT_SERVICE);
        msg.methodName = "ping";
        msg.data = NetworkUtil.mapArgs();
        return msg;
    }

    public static Message createRefreshMessage() {
        Message msg = new Message(MessageType.CLIENT_SERVICE);
        msg.methodName = "refresh";
        msg.data = NetworkUtil.mapArgs();
        return msg;
    }

    public static Message createGameStart() {
        Message msg = new Message(MessageType.CLIENT_SERVICE);
        msg.methodName = "startGame";
        msg.data = NetworkUtil.mapArgs();
        return msg;
    }
}
