package util;

import Network.Message;
import Network.MessageType;

public class ServerUtil {

    public static Message createPingMessage() {
        Message msg = new Message(MessageType.CLIENT_SERVICE);
        msg.methodName = "ping";
        msg.data = NetworkUtil.mapArgs();
        return msg;
    }
}
