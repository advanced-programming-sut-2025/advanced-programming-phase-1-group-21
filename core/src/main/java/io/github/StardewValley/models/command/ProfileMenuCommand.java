package io.github.StardewValley.models.command;

import java.util.regex.Pattern;

public enum ProfileMenuCommand implements Command {
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*"),
    ENTER_MENU("\\s*menu\\s*enter\\s*(?<menu>\\S+)\\s*"),
    EXIT_MENU("menu exit"),
    CHANGE_USERNAME("\\s*change\\s+username\\s+-u\\s+(?<newUsername>\\S+)\\s*"),
    CHANGE_PASSWORD("\\s*change\\s+password\\s+-o\\s+(?<oldPassword>\\S+)\\s+-n\\s+(?<newPassword>\\S+)\\s*"),
    CHANGE_EMAIL("\\s*change\\s+email\\s+-e\\s+(?<newEmail>\\S+)\\s*"),
    CHANGE_NICKNAME("\\s*change\\s+nickname\\s+-n\\s+(?<newNickname>\\S+)\\s*"),
    USERINFO("\\s*show\\s+my\\s+info\\s*"),
    ;
    public final Pattern pattern;
    ProfileMenuCommand(String input) {
        pattern = Pattern.compile(input);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
