package packets;

//Kinda outdated now
//It's massively covered by MethodName kind of requests

public enum MessageType {
    LOBBY_UPDATE,
    CREATE_LOBBY_REQUEST,
    PING,
    MESSAGE,
    GAME_REQUEST, //Client request a Game
    ON_GAME_ACTION, //Client performs X and wants to sync with server
    REQUEST_LOBBIES,
    REQUEST_LOBBY_USERS,
    DATABASE_SERVICE("DatabaseService"),
    RESPONSE;


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
