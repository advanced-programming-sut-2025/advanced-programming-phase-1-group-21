import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import controller.MessageHandler;
import Network.Message;
import Network.MessageType;
import Network.NetworkRegister;
import session.SessionManager;
import util.ServerUtil;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static Random rand = new Random();
    public static void main(String[] args) throws IOException {
        Server server = new Server();


        Kryo kryo = server.getKryo();
        NetworkRegister.register(kryo);


        server.addListener(new Listener() {
            public void connected(Connection c) {
                System.out.println("[SERVER] Client connected: " + c.getRemoteAddressTCP().getAddress().getHostAddress());
                c.sendTCP(ServerUtil.createPingMessage());
                SessionManager.add(c);

                c.setKeepAliveTCP(Integer.MAX_VALUE);
                //TODO it's bad to keep this
            }

            public void received(Connection connection, Object o) {
                if (o instanceof Message) {
                    CompletableFuture.runAsync(() -> {
                        MessageHandler.handle(connection, (Message) o);
                    });
                }
            }

            public void disconnected(Connection connection) {
                System.out.println("[SERVER] Client disconnected");
                SessionManager.disconnected(connection);
            }

            @Override
            public void idle(Connection connection) {
                System.out.println("Server idle timeout for: " + connection.getRemoteAddressTCP());
            }
        });

        server.start();
        server.bind(54555, 54777);

    }
}
