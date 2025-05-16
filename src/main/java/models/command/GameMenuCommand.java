package models.command;

import java.util.regex.Pattern;

public enum GameMenuCommand implements Command {
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*"),
    NEW_GAME("\\s*new\\s+game\\s+(?<username1>\\S+)\\s+(?<username2>\\S+)\\s+(?<username3>\\S+)\\s*"),
    NEXT_TURN("\\s*next\\s+turn\\s*"),
    EXIT_GAME("\\s*exit\\s+game\\s*"),
    HELP_READING_MAP("\\s*help\\s+reading\\s+map\\s*"),
    WALK("\\s*walk\\s+-l\\s+\\((?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\)\\s*"),
    PET("\\s*pet\\s+-n\\s+(?<name>.+)\\s*"),
    CHEAT_ANIMAL_FRIENDSHIP("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>.+)\\s+-c\\s+(?<amount>\\d+)\\s*"),
    ANIMAL("show my animals"),
    SHEPHERD("\\s*shepherd\\s+animals\\s+-n\\s+(?<animalName>.+)\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s*"),
    BUILD_GREENHOUSE("\\s*build\\s+greenhouse\\s*"),
    SHOW_ENERGY("\\s*show\\s+energy\\s*"),
    ENERGY_SET("\\s*energy\\s+set\\s+-v\\s+(?<value>\\d+)\\s*"),
    ENERGY_UNLIMITED("\\s*energy\\s+unlimited\\s*"),
    TIME("time"),
    DATE("date"),
    DATE_TIME("datetime"),
    DAY_OF_THE_WEEK("\\s*day\\s+of\\s+the\\s+week\\s*"),
    TIME_CHEAT("\\s*cheat\\s+advance\\s+time\\s+(?<X>\\d+)h\\s*"),
    CHEAT_DATE("\\s*cheat\\s+advance\\s+date\\s+(?<X>\\d+)d\\s*"),
    SEASON("season"),
    CHEAT_THOR("\\s*cheat\\s+Thor\\s+-l\\s+\\((?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\)\\s*"),
    WEATHER("weather"),
    WEATHER_FORECAST("weather forecast"),
    CHEAT_WEATHER("\\s*cheat\\s+weather\\s+set\\s+(?<type>\\S+)\\s*"),
    GO_TO_VILLAGE("go to village"),
    BACK_HOME("back home"),
    CHEAT_ADD_ITEM("cheat\\s+add\\s+item\\s+-n\\s+(?<name>.+)\\s+-c\\s+(?<amount>\\d+)\\s*"),
    ADD_ITEM("\\s*add\\s+item\\s+(?<name>.+)\\s+(?<itemType>\\S+)\\s+(?<cost>\\d+)\\s+(?<amount>\\d+)\\s*"),
    SHOW_FRIENDSHIP("\\s*show\\s+friendships\\s*"),
    TALK("\\s*talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>.+)\\s*"),
    SHOW_TALK_HISTORY("\\s*show\\s+talks\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    SHOW_GIFT_HISTORY("\\s*show\\s+gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    HUG("\\s*hug\\s+-u\\s+(?<username>\\S+)\\s*"),
    SEND_GIFT("\\s*send\\s+gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<itemName>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*"),
    LIST_GIFTS("\\s*list\\s+gifts\\s*"),
    GIFT_RATE("\\s*gift\\s+rate\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<giftID>\\d+)\\s+-r\\s+(?<rate>.+)\\s*"),
    SEND_FLOWER("\\s*send\\s+flower\\s+-u\\s+(?<username>\\S+)\\s*"),
    ASK_MARRIAGE("\\s*ask\\s+marriage\\s+-u\\s+(?<username>\\S+)\\s*"),
    TRADE_HISTORY("\\s*trade\\s+history\\s*"),
    TRADE_RESPONSE("\\s*trade\\s+response\\s+-u\\s+(?<username>\\S+)\\s+-r\\s+(?<response>\\S+)\\s+-i\\s+(?<ID>\\d+)"),
    START_TRADE("\\s*start\\s+trade\\s*"),
    TRADE_WITH_MONEY("\\s*trade\\s+with\\s+money\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<ite" +
            "m>.+)\\s+-a\\s+(?<amount>\\d+)\\s+-p\\s+(?<price>\\d+)\\s*"),
    TRADE_WITH_ITEM("\\s*trade\\s+with\\s+money\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<ite" +
            "m1>.+)\\s+-a\\s+(?<amount1>\\d+)\\s+-i\\s+(?<item2>.+)\\s+-a\\s+(?<amount2>)\\s*"),
    TRADE_LIST("\\s*trade\\s+list\\s*"),
    BACK_TO_THE_GAME("\\s*back\\s+to\\s+the\\s+game\\s*"),
    MEET_NPC("\\s*meet\\s+npc\\s+-n\\s+(?<npcName>\\S+)\\s*"),
    GIFT_NPC("\\s*gift\\s+npc\\s+-n\\s+(?<npcName>\\S+)\\s+-i\\s+(?<item>.+)\\s+-a\\s+(?<amount>\\d+)\\s*"),
    FRIENDSHIP_NPC_LIST("\\s*friendship\\s+npc\\s+list\\s*"),
    QUESTS_LIST("\\s*quests\\s+list\\s*"),
    QUEST_FINISH("\\s*quest\\s+finish\\s+-n\\s+(?<npcName>\\S+)\\s+-i\\s+(?<questID>\\d+)\\s*"),
//    FORCE_TERMINATE("force terminate"),ß
    COOKING_PREPARE("\\s*cooking\\s+prepare\\s*(?<name>\\.+)\\s*"),
    PRINT_MAP("print\\s+map\\s+-l\\s+\\((?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\)\\s+-s\\s+(?<size>\\d+)"),
    ENERGY_SHOW("energy show"),
    CHEAT_ENERGY_SET("energy set -v (?<value>.*)"),
    CHEAT_ENERGY_UNLIMITED("energy unlimited"),
    INVENTORY_SHOW("inventory show"),
    INVENTOR_TRASH("\\s*inventory\\s+trash\\s+-i\\s+(?<name>.+)\\s+-n\\s+(?<number>\\d+)\\s*"),
    EQUIP_TOOL("\\s*equip\\s+tool\\s+-t\\s+(?<toolName>.+)\\s*"),
    SHOW_CURRENT_TOOL("show current tool"),
    SHOW_AVAILABLE_TOOL("show available tools"),
    USE_TOOL("\\s*use\\s+tool\\s+-d\\s+(?<direction>\\S+)\\s*"),
    //    TOOL_SHOW_CURRENT("tool show current"),
//    TOOLS_SHOW_AVAILABLE("tools show available"),
//    TOOLS_UPGRADE("tools upgrade (?<toolName>.*)>"),
//    TOOL_USE("tool use -d (?<direction>.*)>"),
    CRAFTINFO("craftinfo -n (?<craftName>.+)"),
    CRAFTING_SHOW_RECIPES("\\s+crafting\\s+show\\s+recipes"),
    CRAFTING("\\s*crafting\\s+craft\\s+(?<name>.+)\\s*"),
//    PLACE_ITEM("place item -n (?<item_name>.*) -d (?<direction>.*)"),
//    CHEAT_ADD_ITEM("cheat add item -n (?<item_name>.*) -c (?<count>.*)"),
    COOKING_REFRIGERATOR("\\s*cooking\\s+refrigerator\\s+(?<action>put|pick)\\s+(?<item>\\.+)\\s*"),
    COOKING_SHOW_RECIPES("cooking show recipes"),
    PLACE_ARTISAN("^\\s*place\\s+artisan\\s+-n\\s+(?<name>\\S+)\\s+-d\\s+(?<direction>(N)|(E)|(S)|(W)|(NE)|(NW)|(SE)|(SW))\\s*$"),
    USE_ARTISAN("^\\s*artisan\\s+use\\s+(?<name>\\S+)\\s+(?<itemNames>.+)$"),
    GET_ARTISAN_PRODUCT("^\\s*artisan\\s+get\\s+(?<name>\\S+)$"),
//    EAT("eat (?<food_name>.*)"),
//    BUY_ANIMAL("buy animal -a <animal> -n <name>"),
//    PET("pet -n <name>"),
//    CHEAT_FRIENDSHIP("cheat set friendship -n <animal name> -c <amount>"),
//    SHEPHERD_ANIMALS("shepherd animals -n <animal name> -l <x , y>"),
    FEED_HAY("\\s*feed\\s+hay\\s+-n\\s+(?<name>\\S+)\\s*"),
    COLLECT_PRODUCE("\\s*collect\\s+produce\\s+-n\\s+(?<name>\\S+)\\s*"),
    SELL_ANIMAL("\\s*sell\\s+animal\\s+-n\\s+(?<name>\\S+)\\s*"),
    FISHING("\\s*fishing\\s+-p\\s+(?<fishingPole>.+)\\s*"),
    SHOW_ANIMAL_PRODUCE("\\s*show\\s+animal\\s+produce\\s*"),
    SELL("\\s*sell\\s+(?<item>.+)\\s+-n\\s+(?<number>\\d+)\\s*"),
//    ARTISAN_USE("artisan use <artisan_name> <item1_name>"),
    WATER("\\s*water\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s*"),
    PLANT("^\\s*plant\\s+-s\\s+(?<seedName>.*)\\s+-d\\s+(?<direction>(N)|(E)|(S)|(W)|(NE)|(NW)|(SE)|(SW))\\s*$"),
    SHOW_PLANT("\\s*show\\s+plant\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s*"),
    HOW_MUCH_WATER("\\s*how much\\s+water\\s*"),
    //SHOP
    BUILD("\\s*build\\s+-a\\s+(?<name>[A-Za-z]+(?:\\s+[A-Za-z]+)*)\\s+-l\\s*\\(?\\s*(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*\\)?\\s*"),
    SHOP_SHOW_ALL_PRODUCTS("show all products"),
    SHOP_SHOW_ALL_AVAILABLE_PRODUCTS("show all available products"),
    SHOP_PURCHASE("purchase (?<name>.+) -n (?<number>\\d+)"),
    CHEAT_ADD_DOLLARS("cheat add (?<number>\\d+) dollars"),

    BUY_ANIMAL("buy\\s+animal\\s+-a\\s+(?<animalName>.+)\\s+-n\\s+(?<name>.+)\\s*"),

    //THESE ARE DEBUG COMMANDS
    WHERE_AM_I("whereami"),
    TILE_STATUS("\\s*tile\\s+status\\s+-l\\s+\\((?<x>\\d+),(?<y>\\d+)\\)\\s*"),
    WHO_AM_I("whoami"),
    MAP_STATUS("map status"),
    INVENTORY_STATUS("inventory status"),
    SET_FRIENDSHIP("set friendship -u (?<username>\\S+) -x (?<xp>\\d+)"),
    SHOW_SKILLS("show skills"),
    SHOW_NOTIFICATIONS("show notifications"),
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

/*
go to login menu
login -u Ali -p Alireza123!
menu enter mainmenu
menu enter game
new game sobi sobhan maryam
print map -l (0,0) -s 50
walk -l (10,10)
cheat add item -n Loom -c 1
inventory status
place artisan -n Loom -d S
inventory status
cheat add item -n Wool -c 1
inventory status
artisan use Loom Wool
inventory status
print map -l (0,0) -s 50
next turn
next turn
next turn
next turn
artisan get Loom
cheat add item -n Keg -c 1
cheat add item -n Apple -c 1
inventory status
place artisan -n Keg -d W
print map -l (0,0) -s 50
artisan use Keg Apple
 */