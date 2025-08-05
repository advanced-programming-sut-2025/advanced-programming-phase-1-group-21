package services;

import com.esotericsoftware.kryonet.Connection;
import handlers.LLMHandler;

import java.io.IOException;

public class LLMService {

    public final Connection connection;

    public LLMService(Connection conn) {
        connection = conn;
    }

    public static String generateMsg(String playerQuery, String npcInfo, String gameInfo) {
        try {
            return LLMHandler.generateMsg(playerQuery, npcInfo, gameInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return "UMMM LLM is not working...";
        }
    }

}
