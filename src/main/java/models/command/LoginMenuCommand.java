package models.command;

import java.util.regex.Pattern;

public enum LoginMenuCommand implements Command {
    ENTER_MENU("\\s*menu\\s+enter\\s+(?<menu>\\S+)\\s*"),
    EXIT_MENU("\\s*menu\\s+exit\\s*"),
    LOGIN("\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(\\s+-stay-logged-in)?\\s*"),
    FORGET_PASSWORD("\\s*forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*"),
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
