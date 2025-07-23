package controller;

import com.esotericsoftware.kryonet.Connection;
import packets.Message;
import packets.MessageType;
import router.DatabaseServiceRouter;
import router.ServiceRouter;
import services.DatabaseService;
import util.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    static {
        registerAll();
    }

    private static Map<MessageType, ServiceRouter> serviceMap = new HashMap<>();

    private static void registerAll() {
        serviceMap.put(MessageType.DATABASE_SERVICE, new DatabaseServiceRouter());
    }


    public static void handle(Connection connection, Message msg) {
        if (msg.isMethodRequest()) {
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
