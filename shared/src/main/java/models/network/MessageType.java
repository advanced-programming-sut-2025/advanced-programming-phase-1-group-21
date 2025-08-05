package models.network;

//Kinda outdated now
//It's massively covered by MethodName kind of requests

public enum MessageType {
    CLIENT_SERVICE("Service"),
    DATABASE_SERVICE("DatabaseService"),
    LOBBY_SERVICE("LobbyService"),
    CHAT_SERVICE("ChatService"),
    RESPONSE,
    GAME_SERVICE("GameService"),
    LLM_SERVICE("LLMService"),
    ;


    public final String serviceName;
    public final boolean isMethod;

    MessageType() {
        this.isMethod = false;
        serviceName = "";
    }

    MessageType(String serviceName) {
        this.serviceName = serviceName;
        this.isMethod = true;
    }
}
