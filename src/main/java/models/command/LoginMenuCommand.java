package models.command;

import java.util.regex.Pattern;

public enum LoginMenuCommand implements Command {
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
