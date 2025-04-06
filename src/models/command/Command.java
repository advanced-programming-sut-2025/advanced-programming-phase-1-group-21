package models.command;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;

public interface Command {
    public Matcher getMatcher(String input);
}
