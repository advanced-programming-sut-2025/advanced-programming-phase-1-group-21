import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import packets.Message;
import packets.MessageType;

import java.io.IOException;

public class Main {
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
        });

        server.start();
        server.bind(54555, 54777);

    }
}
