package models.command;

import java.util.regex.Pattern;

public enum RegisterMenuCommand implements Command {
    EXIT_MENU("menu exit"),
    ENTER_MENU("\\s*menu\\s*enter\\s*(?<menuName>\\S+)\\s*"),
    SHOW_CURRENT_MENU("\\s*show\\s*current\\s*menu\\s*"),
    register("\\s*register -u (?<username>\\S+) -p (?<password>\\S+) (?<passwordConfirm>\\S+) -n (?<nickname>\\S+)" +
            " -e (?<email>\\S+) -g (?<gender>\\S+)\\s*"),
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
