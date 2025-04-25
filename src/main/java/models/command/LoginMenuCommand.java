package models.command;

import java.util.regex.Pattern;

public enum LoginMenuCommand implements Command {
    ENTER_MENU("\\s*menu\\s*enter\\s*(?<menuName>\\S+)\\s*"),
    EXIT_MENU("menu exit"),
    LOGIN(""),
    FORGET_PASSWORD(""),
    ANSWER(""),
    ;

    public final Pattern pattern;
    LoginMenuCommand(String input) {
        pattern = Pattern.compile(input);
    }

    @Override
    public Pattern getPattern() {
        return null;
    }
}
