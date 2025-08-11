package io.github.StardewValley.network.routing;

import com.badlogic.gdx.Gdx;
import models.network.Message;
import network.ServiceRouter;
import com.esotericsoftware.kryonet.Connection;
import io.github.StardewValley.App;
import io.github.StardewValley.controllers.GameController;

import java.lang.reflect.InvocationTargetException;
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
        System.out.println("WE ARE DISPATCHING: " + msg);
        String methodName = msg.getMethodName();
        //args is actually args
        Object[] args = (Object[]) msg.data;
        GameController g = App.getInstance().gameControllers.get(msg.username);
        Method m = methods.get(methodName);
        if (m == null) {
            throw new RuntimeException("[DISPATCH ERROR] Method not found: " + methodName);
        }
        m.setAccessible(true);
        Gdx.app.postRunnable(() -> {
            try {
                m.invoke(g, args);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        return null; // NO RETURN
    }
}
