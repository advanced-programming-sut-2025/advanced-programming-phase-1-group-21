package models.command;

import java.util.regex.Pattern;

public enum MainMenuCommand implements Command {
    ENTER_MENU("menu enter (?<menu_name>.*)"),
    MENU_EXIT("menu exit"),
    SHOW_CURRENT_MENU("show current menu"),
    LOGOUT("user logout"),
    ;
    public final Pattern pattern;
    MainMenuCommand(String input) {
        pattern = Pattern.compile(input);
    }

    @Override
    public Pattern getPattern() {
        return null;
    }
}
