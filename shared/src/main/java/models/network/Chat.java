package models.network;

import com.badlogic.gdx.graphics.Color;

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


    public static Chat parseChatMessage(String senderUsername, String rawMessage) {
        if (rawMessage.startsWith("To @")) {
            int spaceAfterUsername = rawMessage.indexOf(' ', 4); // Find space after "@username"
            if (spaceAfterUsername > 0) {
                String receiver = rawMessage.substring(4, spaceAfterUsername);
                String message = rawMessage.substring(spaceAfterUsername + 1);
                return new Chat(ChatType.TO_USER, message, senderUsername, receiver);
            }
        }
        return new Chat(ChatType.TO_LOBBY, rawMessage, senderUsername, null);
    }

    public Color determineColor(String username) {
        return switch (getType()) {
            case TO_USER -> Color.RED;
            case TO_LOBBY -> getMessage().contains("@" + username) ? Color.YELLOW : Color.WHITE;
        };
    }
}
