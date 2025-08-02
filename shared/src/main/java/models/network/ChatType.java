package models.network;

public enum ChatType {
    TO_USER("user"),
    TO_LOBBY("lobby");

    public final String UI;

    ChatType(String ui) {
        this.UI = ui;
    }
    public String getUI() {
        return UI;
    }
}
