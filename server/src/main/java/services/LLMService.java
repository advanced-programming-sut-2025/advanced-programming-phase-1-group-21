package services;

import com.esotericsoftware.kryonet.Connection;

public class LLMService {

    public final Connection connection;

    public LLMService(Connection conn) {
        connection = conn;
    }



}
