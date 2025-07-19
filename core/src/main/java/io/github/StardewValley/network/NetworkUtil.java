package io.github.StardewValley.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import packets.Message;
import packets.MessageType;

import java.io.IOException;

public class NetworkUtil {

    public static void init() throws IOException {
        Client client = new Client();

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
        MessageType type = message.type;
        if (type == MessageType.PING) {
            System.out.println("[CLIENT] Server has pinged us!");
        }
    }
}
