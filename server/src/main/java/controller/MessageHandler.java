package controller;

import com.esotericsoftware.kryonet.Connection;
import packets.Message;
import packets.MessageType;
import services.DatabaseService;
import util.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    public static final Class<?>[] serviceClasses = {
            DatabaseService.class,
    };

    static {
        registerAll();
    }

    private static Map<String, Method> methodMap = new HashMap<>();

    private static void registerAll() {
        try {
            for (Class<?> serviceClass : serviceClasses) {
                for (Method method : serviceClass.getDeclaredMethods()) {
                    String key = serviceClass.getSimpleName() + "." + method.getName();
                    methodMap.put(key, method);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("[ERROR] Something went wrong while initializing services");
        }
    }


    public static void handle(Connection connection, Message msg) {
        if (msg.isMethodRequest()) {
            handleMethod(connection, msg);
        }
    }

    private static void handleMethod(Connection connection, Message msg) {
        Method method = methodMap.get(msg.getMethodName());
        if (method == null) {
            throw new RuntimeException("[ERROR] Something went wrong while invoking " + msg.getMethodName());
        }
        if (!(msg.data instanceof Map))
            throw new RuntimeException("[ERROR] DATA is not a map");
        Object[] args = ServerUtil.mapToArgs(method, (Map<String, Object>)msg.data);
        try {
            Object result = method.invoke(null, args);
            Message response = new Message(MessageType.RESPONSE, result);
            response.requestId = msg.requestId;
            connection.sendTCP(response);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("[ERROR] Something went wrong while invoking " + msg.getMethodName() + ". Are you sure the method is a STATIC FUNCTION?");
        }
    }
}
