package controller;

import com.esotericsoftware.kryonet.Connection;
import packets.Message;
import packets.MessageType;
import router.DatabaseServiceRouter;
import router.LobbyServiceRouter;
import router.ServiceRouter;
import services.DatabaseService;
import services.LobbyService;
import util.*;

import java.lang.reflect.Method;
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
    }


    public static void handle(Connection connection, Message msg) {
        if (msg.isMethodRequest()) {
            printMethodMessage(msg);
            handleMethod(connection, msg);
        }
    }

    private static void printMethodMessage(Message msg) {
        System.out.println("[RECIEVED] " + msg.type + " " + msg.methodName);
        Map<String, Object> map = (Map) msg.data;
        System.out.println("DATA : {");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("}");
    }

    private static void handleMethod(Connection connection, Message msg) {
        ServiceRouter serviceRouter = serviceMap.get(msg.type);
        if (serviceRouter == null)
            throw new RuntimeException("Service router not found");
        serviceRouter.route(connection, msg);
    }
}
