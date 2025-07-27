package io.github.StardewValley.network;

import Network.Message;
import Network.MessageType;
import Network.ServiceRouter;
import com.esotericsoftware.kryonet.Connection;
import io.github.StardewValley.network.routing.MessageRouter;
import util.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private static Map<MessageType, ServiceRouter> serviceMap = new HashMap<>();

    static {
        registerAll();
    }

    private static void registerAll() {
        serviceMap.put(MessageType.CLIENT_SERVICE, new MessageRouter());
    }


    public static void handle(Connection connection, Message msg) {
        if (msg.isMethodRequest()) {
            System.out.println("[RECEIVED] ");
            NetworkUtil.printMethodMessage(msg);
            handleMethod(connection, msg);
        }
    }

    private static void handleMethod(Connection connection, Message msg) {
        ServiceRouter serviceRouter = serviceMap.get(msg.type);
        if (serviceRouter == null)
            throw new RuntimeException("Service router not found");
        serviceRouter.route(connection, msg);
    }
}
