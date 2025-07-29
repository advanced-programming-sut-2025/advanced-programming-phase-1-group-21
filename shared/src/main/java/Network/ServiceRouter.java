package Network;

import com.esotericsoftware.kryonet.Connection;
import util.NetworkUtil;

import java.util.Map;

public interface ServiceRouter {
    Object dispatch(Connection conn, Message msg) throws Exception;

    default void route(Connection conn, Message msg) {
        try {
            Object result = dispatch(conn, msg);
            /*if (result == null) {
                NetworkUtil.sendMessage(null, conn);
                return;
            }*/
            Message response = new Message(MessageType.RESPONSE, result);
            response.requestId = msg.requestId;
            System.out.print("[SENDING] ");
            NetworkUtil.printMethodMessage(response);
            NetworkUtil.sendMessage(response, conn);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("[ERROR] Something went wrong while dispatching!");
        }
    }
}