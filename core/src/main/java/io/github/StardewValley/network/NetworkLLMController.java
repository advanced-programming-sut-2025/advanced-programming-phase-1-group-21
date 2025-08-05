package io.github.StardewValley.network;

import models.network.Message;
import models.network.MessageType;
import util.NetworkUtil;

import java.io.IOException;

public class NetworkLLMController {
    public static String generateMsg(String playerQuery, String npcInfo, String gameInfo) {
        Message msg = new Message(MessageType.LLM_SERVICE);
        msg.methodName = "generateMsg";
        msg.data = NetworkUtil.mapArgs("playerQuery", playerQuery, "npcInfo", npcInfo, "gameInfo", gameInfo);
        Message response = ClientNetwork.sendMessageAndWaitForResponse(msg);
        return (String) response.data;
    }
}
