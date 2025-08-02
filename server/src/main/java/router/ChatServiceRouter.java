package router;

import models.network.Message;
import network.ServiceRouter;
import com.esotericsoftware.kryonet.Connection;
import services.ChatService;
import session.SessionManager;
import util.NetworkUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ChatServiceRouter implements ServiceRouter {
    private static final Map<String, Method> methods = new HashMap<>();

    static {
        for (Method method : ChatService.class.getDeclaredMethods()) {
            methods.put(method.getName(), method);
        }
    }

    @Override
    public Object dispatch(Connection conn, Message msg) throws Exception {
        String methodName = msg.getMethodName();
        Map<String, Object> args = (Map<String, Object>) msg.data;
        Method method = methods.get(methodName);
        if (method == null) {
            throw new RuntimeException("[DISPATCH ERROR] Method not found: " + methodName);
        }
        Object[] parsedArgs = NetworkUtil.mapToArgs(method, args);
        return method.invoke(SessionManager.getChatService(conn), parsedArgs);
    }
}
