package io.github.StardewValley.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import packets.Message;
import packets.MessageType;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;

public class NetworkUtil {

    private static final int TIMEOUT = 1000;
    private static Client client;

    private static final ConcurrentHashMap<String, BlockingQueue<Message>> responseMap = new ConcurrentHashMap<>();

    public static Client getClient() {
        return client;
    }

    public static void init() throws IOException {
        client = new Client();
        Kryo kryo = client.getKryo();
        kryo.register(MessageType.class);
        kryo.register(Message.class);

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
    }

    public static void handleMessage(Connection connection, Message message) {
        //THIS block is for handling Sync Message
        if (message.requestId != null && responseMap.containsKey(message.requestId)) {
            responseMap.get(message.requestId).offer(message);
            return;
        }

        MessageType type = message.type;
        if (type == MessageType.PING) {
            System.out.println("[CLIENT] Server has pinged us!");
        }
    }

    public static Message sendMessageAndWaitForResponse(Message message) {
        String requestId = UUID.randomUUID().toString();
        message.requestId = requestId;

        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(1);
        responseMap.put(requestId, queue);

        sendMessage(message);

        try {
            Message response = queue.poll(TIMEOUT, TimeUnit.MILLISECONDS);
            responseMap.remove(requestId);
            return response;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendMessage(Message message) {
        client.sendTCP(message);
    }
}
