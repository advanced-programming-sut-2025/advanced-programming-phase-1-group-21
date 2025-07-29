package util;

import Network.Message;
import Network.MessageType;
import com.esotericsoftware.kryonet.Connection;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ServerUtil {

    public static final long AVOID_RACE = 5000;

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
