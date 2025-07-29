package Network;

import com.esotericsoftware.kryonet.Connection;
import util.NetworkUtil;

import java.util.Map;

public interface ServiceRouter {
    Object dispatch(Connection conn, String methodName, Map<String, Object> args) throws Exception;

    default void route(Connection conn, Message msg){
        if (!(msg.data instanceof Map))
            throw new RuntimeException("[ERROR] DATA is not a map");
        try {
            Object result = dispatch(conn, msg.getMethodName(), (Map<String, Object>) msg.data);
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