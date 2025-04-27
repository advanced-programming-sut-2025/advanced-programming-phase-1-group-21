package models.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    public Pattern getPattern();

    default public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(getPattern().toString()).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
