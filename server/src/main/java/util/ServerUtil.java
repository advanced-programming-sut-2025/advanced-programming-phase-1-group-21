package util;

import models.result.Error;
import packets.Message;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Random;

public class ServerUtil {
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
        System.out.println(msg.type + " " + msg.methodName);
        System.out.println("DATA:  ");
        System.out.println(msg.data);
    }
}
