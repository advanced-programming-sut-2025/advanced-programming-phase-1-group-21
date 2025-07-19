package packets;

public enum MessageType {
    LOBBY_UPDATE,
    CREATE_LOBBY_REQUEST,
    PING,
    MESSAGE,
    GAME_REQUEST, //Client request a Game
    ON_GAME_ACTION, //Client performs X and wants to sync with server

}
