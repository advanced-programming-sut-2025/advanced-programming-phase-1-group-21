package io.github.StardewValley.network;

import models.network.Chat;
import models.network.Message;
import models.network.MessageType;
import models.result.Result;
import util.NetworkUtil;

public class NetworkChatController {

    public static Result<Void> sendMessage(Chat chat) {
        Message msg = new Message(MessageType.CHAT_SERVICE);
        msg.methodName = "sendMessage";
        msg.data = NetworkUtil.mapArgs("chat", chat);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        Result<Void> result = (Result<Void>) response.data;
        return result;
    }
}
