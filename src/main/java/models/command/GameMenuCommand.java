package models.command;

import java.util.regex.Pattern;

public enum GameMenuCommand implements Command {
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*"),
    NEW_GAME("\\s*new\\s+game\\s+(?<username1>\\S+)\\s+(?<username2>\\S+)\\s+(?<username3>\\S+)\\s*"),
    NEXT_TURN("\\s*next\\s+turn\\s*"),
    EXIT_GAME("\\s*exit\\s+game\\s*"),
    HELP_READING_MAP("\\s*help\\s+reading\\s+map\\s*"),
    WALK("\\s*walk\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s*"),
    BUILD("\\s*build\\s+-a\\s+(?<buildingName>)\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s*"),
    PET("\\s*pet\\s+-n\\s+(?<name>\\S+)\\s*"),
    CHEAT_ANIMAL_FRIENDSHIP("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>\\S+)\\s+-c\\s+(?<amount>\\d+)\\s*"),
    ANIMAL("show my animals"),
    SHEPHERD("\\s*shepherd\\s+animals\\s+-n\\s+(?<animalName>\\S+)\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s*"),
    BUILD_GREENHOUSE("\\s*build\\s+greenhouse\\s*"),
    SHOW_ENERGY("\\s*show\\s+energy\\s*"),
    ENERGY_SET("\\s*energy\\s+set\\s+-v\\s+(?<value>\\d+)\\s*"),
    ENERGY_UNLIMITED("\\s*energy\\s+unlimited\\s*"),
    TIME("time"),
    DATE("date"),
    DATE_TIME("datetime"),
    DAY_OF_THE_WEEK("\\s*day\\s+of\\s+the\\s+week\\s*"),
    TIME_CHEAT("\\s*cheat\\s+advance\\s+time\\s+(?<X>)h\\s*"),
    CHEAT_DATE("\\s*cheat\\s+advance\\s+date\\s+(?<X>)d\\s*"),
    SEASON("season"),
    CHEAT_THOR("\\s*cheat\\s+Thor\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s*"),
    WEATHER("weather"),
    WEATHER_FORECAST("weather forecast"),
    CHEAT_WEATHER("\\s*cheat\\s+weather\\s+set\\s+(?<type>\\S+)\\s*"),
//    ENTER_MENU("\\s*menu\\s*enter\\s*(?<menuName>\\S+)\\s*"),
//    GAME_NEW(""),
//    GAME_MAP("game map (?<map_number>.*)"),
//    LOAD_GAME("load game"),
//    EXIT_GAME("exit game"),
//    FORCE_TERMINATE("force terminate"),
//    TIME("time"),
//    DATE("date"),
//    DATETIME("datetime"),
//    DAY_OF_THE_WEEK("day of the week"),
//    CHEAT_ADVANCE_TIME("cheat advance time (?<hour>.*)H"),
//    CHEAT_ADVANCE_DATE("cheat advance date (?<day>.*)D"),
//    CHEAT_THOR("cheat Thor -l <x , y>"),//TODO correct this command
//    WEATHER("weather"),
//    WEATHER_FORECAST("weather forecast"),
//    CHEAT_WEATHER_SET("cheat weather set (?<type).*)"),
//    WALK("walk -l "),
    PRINT_MAP("print\\s+map\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s+-s\\s+(?<size>\\d+)"),
//    HELP_READING_MAP("help reading map"),
    ENERGY_SHOW("energy show"),
    CHEAT_ENERGY_SET("energy set -v (?<value>.*)"),
    CHEAT_ENERGY_UNLIMITED("energy unlimited"),
    INVENTORY_SHOW("inventory show"),
    INVENTOR_TRASH("inventory trash -i (?<name>.+) -n (?<number>\\d+)"),
//    TOOLS_EQUIP("tools equip (?<toolName>.*)"),
//    TOOL_SHOW_CURRENT("tool show current"),
//    TOOLS_SHOW_AVAILABLE("tools show available"),
//    TOOLS_UPGRADE("tools upgrade (?<toolName>.*)>"),
//    TOOL_USE("tool use -d (?<direction>.*)>"),
    CRAFTINFO("craftinfo -n (?<craftName>.+)"),
//    CRAFTING_SHOW_RECIPES("crafting show recipes"),
//    CRAFTING("crafting craft (?<item_name>.*"),
//    PLACE_ITEM("place item -n (?<item_name>.*) -d (?<direction>.*)"),
//    CHEAT_ADD_ITEM("cheat add item -n (?<item_name>.*) -c (?<count>.*)"),
//    COOKING_REFRIGERATOR("cooking refrigerator [put/pick] (<item>.*)"),
//    COOKING_SHOW_RECIPES("cooking show recipes"),
//    EAT("eat (?<food_name>.*)"),
//    BUILD("build -a <building_name> -l <x , y>"),
//    BUY_ANIMAL("buy animal -a <animal> -n <name>"),
//    PET("pet -n <name>"),
//    CHEAT_FRIENDSHIP("cheat set friendship -n <animal name> -c <amount>"),
//    SHEPHERD_ANIMALS("shepherd animals -n <animal name> -l <x , y>"),
//    FEED_HAY("feed hay -n <animal name>"),
//    COLLECT_PRODUCE("collect produce -n <name>"),
//    SELL_ANIMAL("sell animal -n <name>"),
//    FISHING("fishing -p <fishing pole>"),
//    ARTISAN_USE("artisan use <artisan_name> <item1_name>"),

    ;
    public final Pattern pattern;
    GameMenuCommand(String input) {
        pattern = Pattern.compile(input);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
