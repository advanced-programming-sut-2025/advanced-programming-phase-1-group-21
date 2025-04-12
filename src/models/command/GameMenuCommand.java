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
    WALK("walk -l "),
    PRINT_MAP("print map -l <x, y> -s <size>"),
    HELP_READING_MAP("help reading map"),
    ENERGY_SHOW("energy show"),
    CHEAT_ENERGY_SET("energy set -v (?<value>.*)"),
    CHEAT_ENERGY_UNLIMITED("energy unlimited"),
    INVENTORY_SHOW("inventory show"),
    INVENTOR_TRASH("inventory trash -i (?<item’s name>.*) -n (?<number>.*)"),
    TOOLS_EQUIP("tools equip (?<toolName>.*)"),
    TOOL_SHOW_CURRENT("tool show current"),
    TOOLS_SHOW_AVAILABLE("tools show available"),
    TOOLS_UPGRADE("tools upgrade (?<toolName>.*)>"),
    TOOL_USE("tool use -d (?<direction>.*)>"),
    CRAFTINFO("craftinfo -n <craft_name>"),
    CRAFTING_SHOW_RECIPES("crafting show recipes"),
    CRAFTING("crafting craft (?<item_name>.*"),
    PLACE_ITEM("place item -n (?<item_name>.*) -d (?<direction>.*)"),
    CHEAT_ADD_ITEM("cheat add item -n (?<item_name>.*) -c (?<count>.*)"),
    COOKING_REFRIGERATOR("cooking refrigerator [put/pick] (<item>.*)"),
    COOKING_SHOW_RECIPES("cooking show recipes"),
    EAT("eat (?<food_name>.*)"),
    BUILD("build -a <building_name> -l <x , y>"),
    BUY_ANIMAL("buy animal -a <animal> -n <name>"),
    PET("pet -n <name>"),
    CHEAT_FRIENDSHIP("cheat set friendship -n <animal name> -c <amount>"),
    SHEPHERD_ANIMALS("shepherd animals -n <animal name> -l <x , y>"),
    FEED_HAY("feed hay -n <animal name>"),
    COLLECT_PRODUCE("collect produce -n <name>"),
    SELL_ANIMAL("sell animal -n <name>"),
    FISHING("fishing -p <fishing pole>"),
    ARTISAN_USE("artisan use <artisan_name> <item1_name>"),

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
