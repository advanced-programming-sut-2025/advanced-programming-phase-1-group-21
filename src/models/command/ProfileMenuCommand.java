package models.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommand implements Command {
    ;
    public final Pattern pattern;
    ProfileMenuCommand(String input) {
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
