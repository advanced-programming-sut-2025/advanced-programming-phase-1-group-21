package io.github.StardewValley.network;

import models.network.Message;
import models.network.MessageType;
import io.github.StardewValley.App;
import io.github.StardewValley.controllers.GameController;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;

public class MultiplayerProxy {
    public static GameController create(GameController controller) throws InstantiationException, IllegalAccessException {
        InvocationHandler handler = (proxy, method, args) -> {
            // Call original method
            Object result = method.invoke(controller, args);

            // Network logic

//            Message msg = new Message(MessageType.GAME_SERVICE);
//            msg.methodName = method.getName();
//            msg.data = args;
//            msg.username = App.getInstance().logedInUser.getUsername();
//            ClientNetwork.sendMessage(msg);


            return result;
        };

        return new ByteBuddy()
                .subclass(GameController.class)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(handler))
                .make()
                .load(GameController.class.getClassLoader())
                .getLoaded()
                .newInstance();
    }
}