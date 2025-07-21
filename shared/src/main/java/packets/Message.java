package packets;

public class Message {

    public MessageType type;
    public String requestId = null;

    // No-arg constructor (required for KryoNet)
    public Message() {}

    public Message(MessageType type) {
        this.type = type;
    }
}
