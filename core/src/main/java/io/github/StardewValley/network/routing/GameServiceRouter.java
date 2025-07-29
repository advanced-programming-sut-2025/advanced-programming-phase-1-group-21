package io.github.StardewValley.network.routing;

import Network.Message;
import Network.ServiceRouter;
import com.esotericsoftware.kryonet.Connection;
import io.github.StardewValley.App;
import io.github.StardewValley.controllers.GameController;
import models.game.Game;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GameServiceRouter implements ServiceRouter {
    private static final Map<String, Method> methods = new HashMap<>();

    static {
        for (Method method : GameController.class.getDeclaredMethods()) {
            methods.put(method.getName(), method);
        }
    }

    @Override
    public Object dispatch(Connection conn, Message msg) throws Exception {
        String methodName = msg.getMethodName();
        //args is actually args
        Object[] args = (Object[]) msg.data;
        GameController g = App.getInstance().gameControllers.get(msg.username);
        Method m = methods.get(methodName);
        if (m == null) {
            throw new RuntimeException("[DISPATCH ERROR] Method not found: " + methodName);
        }
        m.setAccessible(true);
        m.invoke(g, args);

        return null; // NO RETURN
    }
}
