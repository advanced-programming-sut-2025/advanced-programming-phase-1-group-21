package io.github.StardewValley.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import Network.Message;
import Network.MessageType;
import Network.NetworkRegister;
import io.github.StardewValley.network.routing.MessageRouter;
import io.github.StardewValley.views.menu.GUI.UIUtil;
import models.result.errorTypes.ServerError;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class ClientNetwork {

    private static final int TIMEOUT = 5000;
    private static Client client;

    private static final ConcurrentHashMap<String, BlockingQueue<Message>> responseMap = new ConcurrentHashMap<>();

    public static Client getClient() {
        return client;
    }

    public static void init() throws IOException {
        client = new Client();

        NetworkRegister.register(client.getKryo());


        client.addListener(new Listener() {
            public void received(Connection connection, Object o) {
                handle(connection, o);
            }
        });
        client.start();
        client.connect(5000, "localhost", 54555, 54777);
    }

    public static void handle(Connection connection, Object o) {
        if (o instanceof Message) {
            Message message = (Message) o;
            handleMessage(connection, message);
        }
        else {
            System.out.println("[RECEIVED] " + o.toString() + " -> " + o.getClass().getSimpleName());
        }
    }

    public static void handleMessage(Connection connection, Message message) {
        //THIS block is for handling Sync Message
        System.out.println("[RECEIVED] " + message);
        if (message.requestId != null && responseMap.containsKey(message.requestId)) {
            responseMap.get(message.requestId).offer(message);
            return;
        }
        CompletableFuture.runAsync(() -> {
            MessageHandler.handle(connection, message);
        });
    }

    public static Message sendMessageAndWaitForResponse(Message message) {
        System.out.println("[Send And Wait For Response] " + message);
        String requestId = UUID.randomUUID().toString();
        message.requestId = requestId;

        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(1);
        responseMap.put(requestId, queue);


        sendMessage(message);

        try {
            Message response = queue.poll(TIMEOUT, TimeUnit.MILLISECONDS);
            if (response == null) {
                throw new RuntimeException("Server did not respond within " + TIMEOUT + "ms");
            }
            responseMap.remove(requestId);
            return response;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendMessage(Message message) {
        CompletableFuture.runAsync(() -> {

            if (client != null && client.isConnected()) {
                client.sendTCP(message);
            } else {
                UIUtil.showErrorScreen(ServerError.NO_SERVER_IS_RUNNING);
                throw new RuntimeException("[ERROR] Cannot send message. Client is not connected to the server.");
            }
        });
    }

}
