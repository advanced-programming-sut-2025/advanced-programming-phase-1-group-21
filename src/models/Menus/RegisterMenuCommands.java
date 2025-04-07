package models.Menus;

public enum RegisterMenuCommands {
    register(""),
    pickQuestion("")
    ;

    public String command;

    RegisterMenuCommands(String command) {
        this.command = command;
    }
}
