package models.network;

public class Chat {
    private ChatType type;
    private String message;
    private String sender;
    private String receiver;

    //for kryo
    public Chat() {

    }

    public Chat(ChatType type, String message, String sender, String receiver) {
        this.type = type;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public ChatType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

}
