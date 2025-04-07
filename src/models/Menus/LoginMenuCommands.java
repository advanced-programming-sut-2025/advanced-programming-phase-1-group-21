package models.Menus;

public enum LoginMenuCommands {
    login(""),
    FORGET_PASSWORD(""),
    ANSWER(""),

    ;

    public String command;

    LoginMenuCommands(String command) {
        this.command = command;
    }
}
