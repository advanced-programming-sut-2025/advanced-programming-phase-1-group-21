package util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class ServerUtil {
    public static Object[] mapToArgs(Method method, Map<String, Object> input) {
        Parameter[] params = method.getParameters();
        Object[] out = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            String name = params[i].getName();
            out[i] = input.get(name);
        }
        return out;
    }

}
