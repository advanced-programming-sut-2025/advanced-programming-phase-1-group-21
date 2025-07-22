package packets;

public class Message {

    public MessageType type;
    public String requestId = null;
    public Object data = null;
    public String methodName = null;

    // No-arg constructor (required for KryoNet)
    public Message() {}

    public Message(MessageType type) {
        this.type = type;
    }

    public Message(MessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public String getMethodName() {
        return type.serviceName + "." + methodName;
    }

    public boolean isMethodRequest() {
        return type.isMethod;
    }
}
