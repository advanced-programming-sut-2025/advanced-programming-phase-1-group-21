package models.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommand implements Command {
    GAME_NEW(""),
    GAME_MAP("game map (?<map_number>.*)"),
    LOAD_GAME("load game"),
    EXIT_GAME("exit game"),
    FORCE_TERMINATE("force terminate"),
    TIME("time"),
    DATE("date"),
    DATETIME("datetime"),
    DAY_OF_THE_WEEK("day of the week"),
    CHEAT_ADVANCE_TIME("cheat advance time (?<hour>.*)H"),
    CHEAT_ADVANCE_DATE("cheat advance date (?<day>.*)D"),
    CHEAT_THOR("cheat Thor -l <x , y>"),//TODO correct this command
    WEATHER("weather"),
    WEATHER_FORECAST("weather forecast"),
    CHEAT_WEATHER_SET("cheat weather set (?<type).*)"),
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
