package network;

import com.esotericsoftware.kryonet.Connection;
import models.network.Message;
import models.network.MessageType;
import util.NetworkUtil;

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