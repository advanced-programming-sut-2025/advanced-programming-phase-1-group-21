package controller;

import com.esotericsoftware.kryonet.Connection;
import models.game.Game;
import models.network.Message;
import models.network.MessageType;
import router.ChatServiceRouter;
import router.DatabaseServiceRouter;
import router.GameServiceRouter;
import router.LobbyServiceRouter;
import network.ServiceRouter;
import util.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {
    private static Map<MessageType, ServiceRouter> serviceMap = new HashMap<>();

    static {
        registerAll();
    }

    private static void registerAll() {
        serviceMap.put(MessageType.DATABASE_SERVICE, new DatabaseServiceRouter());
        serviceMap.put(MessageType.LOBBY_SERVICE, new LobbyServiceRouter());
        serviceMap.put(MessageType.CLIENT_SERVICE, new ChatServiceRouter());
        serviceMap.put(MessageType.GAME_SERVICE, new GameServiceRouter());
    }


    public static void handle(Connection connection, Message msg) {
        if (msg.isMethodRequest()) {
            System.out.print("[RECEIVED] ");
            NetworkUtil.printMethodMessage(msg);
            handleMethod(connection, msg);
        }
        else {
            System.out.println("[RECEIVED] " + msg + ", But we don't know what to do with this");
        }
    }

    private static void handleMethod(Connection connection, Message msg) {
        ServiceRouter serviceRouter = serviceMap.get(msg.type);
        if (serviceRouter == null)
            throw new RuntimeException("Service router not found");
        serviceRouter.route(connection, msg);
    }
}
