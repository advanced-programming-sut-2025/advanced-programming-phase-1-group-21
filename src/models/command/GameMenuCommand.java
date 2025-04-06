package models.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommand implements Command {
    ;
    public final Pattern pattern;
    GameMenuCommand(String input) {
        pattern = Pattern.compile(input);
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
