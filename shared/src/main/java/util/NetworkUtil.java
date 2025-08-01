package util;

import models.network.Message;
import com.esotericsoftware.kryonet.Connection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class NetworkUtil {
    public static Random random = new Random();
    public static Object[] mapToArgs(Method method, Map<String, Object> input) {
        Parameter[] params = method.getParameters();
        Object[] out = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            String name = params[i].getName();
            out[i] = input.get(name);
        }
        return out;
    }

    public static void printMethodMessage(Message msg) {
        System.out.println(msg.type + " " + msg.methodName + " DATA{" + msg.data + "}");
    }


    public static Map<String, Object> mapArgs(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("You must provide pairs of key and value");
        }

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            map.put((String) keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return map;
    }

    public static void sendMessage(Message msg, Connection c) {
        CompletableFuture.runAsync(() -> {
            c.sendTCP(msg);
        });
    }
}
