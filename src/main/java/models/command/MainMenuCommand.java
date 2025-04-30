package models.command;

import java.util.regex.Pattern;

public enum MainMenuCommand implements Command {
    ENTER_MENU("\\s*menu\\s+enter\\s+(?<menu>\\S+)\\s*"),
    EXIT_MENU("menu exit"),
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*"),
    LOGOUT("user logout"),
    ;
    public final Pattern pattern;
    MainMenuCommand(String input) {
        pattern = Pattern.compile(input);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
