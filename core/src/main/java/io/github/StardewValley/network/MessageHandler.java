package io.github.StardewValley.network;

import io.github.StardewValley.network.routing.GameServiceRouter;
import models.network.Message;
import models.network.MessageType;
import network.ServiceRouter;
import com.esotericsoftware.kryonet.Connection;
import io.github.StardewValley.network.routing.ClientServiceRouter;
import util.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private static Map<MessageType, ServiceRouter> serviceMap = new HashMap<>();

    static {
        registerAll();
    }

    private static void registerAll() {
        serviceMap.put(MessageType.CLIENT_SERVICE, new ClientServiceRouter());
        serviceMap.put(MessageType.GAME_SERVICE, new GameServiceRouter());
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
