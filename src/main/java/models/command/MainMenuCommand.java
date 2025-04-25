package models.command;

import java.util.regex.Pattern;

public enum MainMenuCommand implements Command {
    ENTER_MENU("\\s*menu\\s*enter\\s*(?<menuName>\\S+)\\s*"),
    EXIT_MENU("menu exit"),
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
