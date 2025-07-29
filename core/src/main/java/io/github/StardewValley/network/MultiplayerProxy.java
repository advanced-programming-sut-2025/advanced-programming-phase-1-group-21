package io.github.StardewValley.network;

import Network.Message;
import Network.MessageType;
import io.github.StardewValley.App;
import io.github.StardewValley.controllers.GameController;

import java.lang.reflect.*;
import java.util.*;

public class MultiplayerProxy {
    public static GameController create(GameController controller) {
        return (GameController) Proxy.newProxyInstance(
                GameController.class.getClassLoader(),
                new Class<?>[]{GameController.class},
                (proxy, method, args) -> {
                    //Call Locally
                    Object result = method.invoke(controller, args);

                    // Send to others
                    Message msg = new Message(MessageType.GAME_SERVICE);
                    msg.methodName = method.getName();
                    msg.data = args;
                    msg.username = App.getInstance().logedInUser.getUsername();
                    ClientNetwork.sendMessage(msg);

                    return result;
                });
    }
}

