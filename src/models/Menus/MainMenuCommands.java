package models.Menus;

public enum MainMenuCommands {
    ENTER_MENU("menu enter (?<menu_name>.*)"),
    MENU_EXIT("menu exit"),
    SHOW_CURRENT_MENU("show current menu"),
    LOGOUT("user logout"),

    ;

    public String command;

    MainMenuCommands(String command) {
        this.command = command;
    }

}
