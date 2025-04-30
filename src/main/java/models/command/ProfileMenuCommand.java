package models.command;

import java.util.regex.Pattern;

public enum ProfileMenuCommand implements Command {
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*"),
    ENTER_MENU("\\s*menu\\s*enter\\s*(?<menuName>\\S+)\\s*"),
    EXIT_MENU("menu exit"),
    CHANGE_USERNAME(""),
    CHANGE_PASSWORD(""),
    CHANGE_EMAIL(""),
    CHANGE_NICKNAME(""),
    USERINFO("")
    ;
    public final Pattern pattern;
    ProfileMenuCommand(String input) {
        pattern = Pattern.compile(input);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
