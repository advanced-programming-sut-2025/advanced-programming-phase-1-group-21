package models.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommand implements Command {
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
        return null;
    }
}
