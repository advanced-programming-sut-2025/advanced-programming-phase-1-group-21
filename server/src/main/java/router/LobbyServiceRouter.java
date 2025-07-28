package router;

import com.esotericsoftware.kryonet.Connection;
import Network.ServiceRouter;
import services.LobbyService;
import session.SessionManager;
import util.NetworkUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LobbyServiceRouter implements ServiceRouter {
    private static final Map<String, Method> methods = new HashMap<>();

    static {
        for (Method method : LobbyService.class.getDeclaredMethods()) {
            methods.put(method.getName(), method);
        }
    }

    @Override
    public Object dispatch(Connection conn, String methodName, Map<String, Object> args) throws Exception {
        Method method = methods.get(methodName);
        if (method == null) {
            throw new RuntimeException("[DISPATCH ERROR] Method not found: " + methodName);
        }
        Object[] parsedArgs = NetworkUtil.mapToArgs(method, args);
        return method.invoke(SessionManager.getLobbyService(conn), parsedArgs);
    }
}
