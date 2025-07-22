import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import controller.MessageHandler;
import packets.Message;
import packets.MessageType;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static Random rand = new Random();
    public static void main(String[] args) throws IOException {
        Server server = new Server();


        Kryo kryo = server.getKryo();
        kryo.register(MessageType.class);
        kryo.register(Message.class);

        server.addListener(new Listener() {
            public void connected(Connection c) {
                System.out.println("[SERVER] Client connected: " + c.getRemoteAddressTCP().getAddress().getHostAddress());
                c.sendTCP(new Message(MessageType.PING));
            }

            public void received(Connection connection, Object o) {
                if (o instanceof Message) {
                    MessageHandler.handle(connection, (Message) o);
                }
                else {
                    throw new RuntimeException("[ERROR] GOT: " + o.getClass().getName() + " Instead of Message!");
                }
            }
        });

        server.start();
        server.bind(54555, 54777);

    }
}
