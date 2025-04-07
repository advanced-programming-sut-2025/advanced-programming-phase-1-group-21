package models.Menus;

public enum ProfileMenuCommands {
    CHANGE_USERNAME(""),
    CHANGE_PASSWORD(""),
    CHANGE_EMAIL(""),
    CHANGE_NICKNAME(""),
    USERINFO("")
    ;

    public String command;

    ProfileMenuCommands(String command) {
        this.command = command;
    }
}
