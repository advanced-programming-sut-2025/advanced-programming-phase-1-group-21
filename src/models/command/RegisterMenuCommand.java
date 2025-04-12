package models.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommand implements Command {
    register(""),
    pickQuestion("")
    ;
    public final Pattern pattern;
    RegisterMenuCommand(String input) {
        pattern = Pattern.compile(input);
    }


    @Override
    public Pattern getPattern() {
        return null;
    }
}
