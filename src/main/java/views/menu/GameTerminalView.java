package views.menu;

import controllers.GameController;
import models.App;
import models.Item.RecipeType;
import models.command.GameMenuCommand;
import models.crop.FertilizerType;
import models.game.Player;
import models.map.Coord;
import models.map.Direction;
import models.map.Weather;
import models.result.Result;
import models.user.User;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

public class GameTerminalView {
    Matcher matcher;
    GameController gameController = new GameController();

    public static void printWithColor(ArrayList<String> output) {
        for (String line : output) {
            Colors color;
            switch (line) {
                case "#": color = Colors.WHITE; break;
                case "H": color = Colors.YELLOW; break;
                case "~": color = Colors.BLUE; break;
                case "M":
                case "R":
                case "F": color = Colors.BLACK; break;
                case "G":
                case "*":
                case "T":
                case "P": color = Colors.GREEN; break;
                case "@":
                case "+":
                case "C":
                case "S": color = Colors.RED; break;
                case "B": color = Colors.CYAN; break;
                case "g": color = Colors.YELLOW; break;
                case "I": color = Colors.BLUE; break;
                case "J": color = Colors.BROWN; break;
                default:
                    System.out.print(line);
                    continue;
            }

            System.out.print(Colors.color(color, line));
        }
        System.out.println("\n\n");
    }


    public static void printArrayList(ArrayList<String> output) {
        for(String line : output) {
            System.out.println(line);
        }
    }

    public static void helpReadingMap() {
        System.out.println(Colors.color(Colors.WHITE, "#") + " : An empty tile");
        System.out.println(Colors.color(Colors.YELLOW, "H") + " : House");
        System.out.println(Colors.color(Colors.GREEN, "G") + " : Greenhouse");
        System.out.println(Colors.color(Colors.BLACK, "M") + " : Mines");
        System.out.println(Colors.color(Colors.CYAN, "B") + " : Barn");
        System.out.println(Colors.color(Colors.RED, "C") + " : Coop");
        System.out.println(Colors.color(Colors.BLUE, "~") + " : Lake");
        System.out.println(Colors.color(Colors.GREEN, "T") + " : Tree");
        System.out.println(Colors.color(Colors.GREEN, "*") + " : Marijuana");
        System.out.println(Colors.color(Colors.BLACK, "R") + " : Rock");
        System.out.println(Colors.color(Colors.RED, "+") + " : Exit way");
        System.out.println(Colors.color(Colors.RED, "@") + " : Your avatar");
    }


    public static int getMap(Player player){
        System.out.print(player.getUser().getUsername() + " ,Select your map ID: ");
        return AppView.scanner.nextInt();
    }

    public static int getSeed(User user) {
        System.out.println(user.getUsername() + ", Select seed ID for your map: ");
        int seed = AppView.scanner.nextInt();
        return seed;
    }

    public static String getResponse(Player player){
        System.out.print(player.getUser().getUsername() + " ,What's your response? ");
        return AppView.scanner.nextLine();
    }

    public void Result(String command) throws IOException {
        gameController.game = App.getInstance().game;

        if (gameController.isGameLockedDueToNight()) {
            System.out.println("You are already locked due to night, Skipping turn...");
            gameController.nextTurn();
            return;
        }

        if (gameController.isFainted()) {
            System.out.println("You fainted!, skipping turn...");
            gameController.nextTurn();
            return;
        }

        if ((matcher = GameMenuCommand.PRINT_MAP.getMatcher(command)) != null) {
            Result<ArrayList<String>> R = gameController.printMap(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("size")));
            if (R.isSuccess())
                printWithColor(R.getData());
            else
                System.out.println(R.getMessage());
        }
        else if ((matcher = GameMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null) {
            System.out.println(gameController.showCurrentMenu());
        }
        else if ((matcher = GameMenuCommand.NEW_GAME.getMatcher(command)) != null) {
            System.out.println(gameController.createGame(matcher.group("username1") , matcher.group("username2") , matcher.group("username3")));
        }
        else if ((matcher = GameMenuCommand.EAT.getMatcher(command)) != null) {
            System.out.println(gameController.eat(matcher.group("name")));
        }
        else if ((matcher = GameMenuCommand.NEXT_TURN.getMatcher(command)) != null) {
            System.out.println(gameController.nextTurn());
        }
        else if ((matcher = GameMenuCommand.EXIT_GAME.getMatcher(command)) != null) {
            System.out.println(gameController.exitGame());
        }
        else if ((matcher = GameMenuCommand.LOAD_GAME.getMatcher(command)) != null) {
            System.out.println(gameController.loadGame());
        }
        else if ((matcher = GameMenuCommand.HELP_READING_MAP.getMatcher(command)) != null) {
            gameController.helpReadingMap();
        }
        else if ((matcher = GameMenuCommand.WALK.getMatcher(command)) != null) {
            System.out.println(gameController.walk(Integer.parseInt(matcher.group("x")) , Integer.parseInt(matcher.group("y"))));
        }
        else if ((matcher = GameMenuCommand.ENERGY_SHOW.getMatcher(command)) != null) {
            System.out.println("Players Energy is: " + gameController.showEnergy());
        }
        else if ((matcher = GameMenuCommand.CHEAT_ENERGY_SET.getMatcher(command)) != null) {
            gameController.setEnergyCheat(Integer.parseInt(matcher.group("value")));
        }
        else if ((matcher = GameMenuCommand.CHEAT_ENERGY_UNLIMITED.getMatcher(command)) != null) {
            gameController.setEnergyUnlimited();
        }
        else if ((matcher = GameMenuCommand.PET.getMatcher(command)) != null) {
            System.out.println(gameController.pet(matcher.group("name")));
        }
        else if ((matcher = GameMenuCommand.BUY_ANIMAL.getMatcher(command)) != null) {
            System.out.println(gameController.purchaseAnimal(matcher.group("animalName"), matcher.group("name")));
        }
        else if ((matcher = GameMenuCommand.SELL.getMatcher(command)) != null) {
            System.out.println(
                    gameController.sell(
                            matcher.group("item"),
                            matcher.group("number") == null
                                    ? 1
                                    : Integer.parseInt(matcher.group("number"))
                    )
            );
        }
        else if ((matcher = GameMenuCommand.CHEAT_ANIMAL_FRIENDSHIP.getMatcher(command)) != null) {
            System.out.println(gameController.cheatFriendship(matcher.group("name") , Integer.parseInt(matcher.group("amount"))));
        }
        else if ((matcher = GameMenuCommand.ANIMAL.getMatcher(command)) != null) {
            printArrayList(gameController.showAnimals().getData());
        }
        else if ((matcher = GameMenuCommand.CRAFTINFO.getMatcher(command)) != null) {
            System.out.println(gameController.craftInfo(matcher.group("craftName")));
        }
        else if ((matcher = GameMenuCommand.CRAFTING_SHOW_RECIPES.getMatcher(command)) != null) {
            System.out.println(gameController.showCraftingRecipes());
        }
        else if ((matcher = GameMenuCommand.CRAFTING.getMatcher(command)) != null) {
            System.out.println(gameController.prepareRecipe(matcher.group("name"), RecipeType.CRAFTING));
        }
        else if ((matcher = GameMenuCommand.COOKING_PREPARE.getMatcher(command)) != null) {
            System.out.println(gameController.prepareRecipe(matcher.group("name"), RecipeType.COOKING));
        }
        else if ((matcher = GameMenuCommand.COOKING_REFRIGERATOR.getMatcher(command)) != null) {
            System.out.println(gameController.actOnRefrigerator(matcher.group("item"), matcher.group("action")));
        }
        else if ((matcher = GameMenuCommand.COOKING_SHOW_RECIPES.getMatcher(command)) != null) {
            System.out.println(gameController.showCookingRecipes());
        }
        else if ((matcher = GameMenuCommand.WHERE_AM_I.getMatcher(command)) != null) {
            System.out.println(gameController.whereAmI());
        }
        else if ((matcher = GameMenuCommand.WHO_AM_I.getMatcher(command)) != null) {
            System.out.println(gameController.whoAmI());
        }
        else if ((matcher = GameMenuCommand.TILE_STATUS.getMatcher(command)) != null) {
            System.out.println(gameController.getTileStatus(new Coord(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")))));
        }
        else if ((matcher = GameMenuCommand.MAP_STATUS.getMatcher(command)) != null) {
            System.out.println(gameController.getMapStatus());
        }
        else if ((matcher = GameMenuCommand.INVENTORY_STATUS.getMatcher(command)) != null) {
            System.out.println(gameController.getInventoryStatus());
        }
        else if ((matcher = GameMenuCommand.CHEAT_ADD_ITEM.getMatcher(command)) != null) {
            System.out.println(gameController.addItemCheat(matcher.group("name"), Integer.parseInt(matcher.group("amount"))));
        }
        else if ((matcher = GameMenuCommand.INVENTORY_SHOW.getMatcher(command)) != null) {
            printArrayList(gameController.showInventory().getData());
        }
        else if ((matcher = GameMenuCommand.INVENTOR_TRASH.getMatcher(command)) != null) {
            System.out.println(gameController.removeFromInventory(matcher.group("name"), Integer.parseInt(matcher.group("number"))));
        }
        else if ((matcher = GameMenuCommand.SHEPHERD.getMatcher(command)) != null) {
            System.out.println(gameController.shepherdAnimals(matcher.group("animalName") , Integer.parseInt(matcher.group("x")) , Integer.parseInt(matcher.group("y"))));
        }
        else if ((matcher = GameMenuCommand.BUILD_GREENHOUSE.getMatcher(command)) != null) {
            System.out.println(gameController.buildGreenHouse());
        }
        else if ((matcher = GameMenuCommand.EQUIP_TOOL.getMatcher(command)) != null) {
            System.out.println(gameController.equipTool(matcher.group("toolName")));
        }
        else if ((matcher = GameMenuCommand.SHOW_CURRENT_TOOL.getMatcher(command)) != null) {
            System.out.println(gameController.showCurrentTool());
        }
        else if ((matcher = GameMenuCommand.SHOW_AVAILABLE_TOOL.getMatcher(command)) != null) {
            printArrayList(gameController.showAvailableTools().getData());
        }
        else if ((matcher = GameMenuCommand.USE_TOOL.getMatcher(command)) != null) {
            System.out.println(gameController.useTool(Direction.getDirection(matcher.group("direction"))));
        }
        else if ((matcher = GameMenuCommand.BACK_HOME.getMatcher(command)) != null) {
            System.out.println(gameController.backToHome());
        }
        else if ((matcher = GameMenuCommand.GO_HOME_USER.getMatcher(command)) != null) {
            System.out.println(gameController.goToFarm(matcher.group("user")));
        }
        else if ((matcher = GameMenuCommand.GO_TO_VILLAGE.getMatcher(command)) != null) {
            System.out.println(gameController.goToVillage());
        }
        else if ((matcher = GameMenuCommand.PLANT.getMatcher(command)) != null) {
            System.out.println(gameController.plant(matcher.group("seedName"), Direction.getDirection(matcher.group("direction"))));
        }
        else if ((matcher = GameMenuCommand.SHOW_PLANT.getMatcher(command)) != null) {
            System.out.println(gameController.showPlant(new Coord(Integer.parseInt(matcher.group("x")) , Integer.parseInt(matcher.group("y")))));
        }
        else if ((matcher = GameMenuCommand.HOW_MUCH_WATER.getMatcher(command)) != null) {
            System.out.println(gameController.howMuchWater());
        }
        else if ((matcher = GameMenuCommand.FERTILIZE.getMatcher(command)) != null) {
            System.out.println(gameController.fertilize(FertilizerType.getFertilizerType(matcher.group("fertilizer")), Direction.getDirection(matcher.group("direction"))));
        }
        else if ((matcher = GameMenuCommand.WATER.getMatcher(command)) != null) {
            System.out.println(gameController.water(new Coord(Integer.parseInt(matcher.group("x")) , Integer.parseInt(matcher.group("y")))));
        }
        else if ((matcher = GameMenuCommand.TIME.getMatcher(command)) != null) {
            System.out.println(gameController.getTime().getData());
        }
        else if ((matcher = GameMenuCommand.DATE.getMatcher(command)) != null) {
            System.out.println(gameController.getDate().getData());
        }
        else if ((matcher = GameMenuCommand.DATE_TIME.getMatcher(command)) != null) {
            System.out.println(gameController.getDateTime().getData());
        }
        else if ((matcher = GameMenuCommand.DAY_OF_THE_WEEK.getMatcher(command)) != null) {
            System.out.println(gameController.getDayWeek().getData());
        }
        else if ((matcher = GameMenuCommand.TIME_CHEAT.getMatcher(command)) != null) {
            System.out.println(gameController.advanceTimeCheat(0, Integer.parseInt(matcher.group("X"))));
        }
        else if ((matcher = GameMenuCommand.CHEAT_DATE.getMatcher(command)) != null) {
            System.out.println(gameController.advanceTimeCheat(Integer.parseInt(matcher.group("X")), 0));
        }
        else if ((matcher = GameMenuCommand.SEASON.getMatcher(command)) != null) {
            System.out.println(gameController.getSeasonName().getData());
        }
        else if ((matcher = GameMenuCommand.CHEAT_THOR.getMatcher(command)) != null) {
            System.out.println(gameController.struckByThorCheat(new Coord(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")))).getMessage());
        }
        else if ((matcher = GameMenuCommand.WEATHER.getMatcher(command)) != null) {
            System.out.println(gameController.getWeather().getData());
        }
        else if ((matcher = GameMenuCommand.WEATHER_FORECAST.getMatcher(command)) != null) {
            System.out.println(gameController.getWeatherForecast().getData());
        }
        else if ((matcher = GameMenuCommand.CHEAT_WEATHER.getMatcher(command)) != null) {
            System.out.println(gameController.setWeatherCheat(Weather.getWeather(matcher.group("type"))));
        }
        else if ((matcher = GameMenuCommand.SHOW_FRIENDSHIP.getMatcher(command)) != null) {
            printArrayList(gameController.showFriendships().getData());
        }
        else if ((matcher = GameMenuCommand.TALK.getMatcher(command)) != null) {
            System.out.println(gameController.talk(matcher.group("username") , matcher.group("message").trim()));
        }
        else if ((matcher = GameMenuCommand.SHOW_TALK_HISTORY.getMatcher(command)) != null) {
            if(gameController.talkHistory(matcher.group("username")).getData() != null)
                printArrayList(gameController.talkHistory(matcher.group("username")).getData());
        }
        else if ((matcher = GameMenuCommand.SHOW_GIFT_HISTORY.getMatcher(command)) != null) {
            printArrayList(gameController.giftHistory(matcher.group("username")).getData());
        }
        else if ((matcher = GameMenuCommand.HUG.getMatcher(command)) != null) {
            System.out.println(gameController.hug(matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.SEND_GIFT.getMatcher(command)) != null) {
            System.out.println(gameController.sendGift(matcher.group("username") , matcher.group("itemName")
                    , Integer.parseInt(matcher.group("amount"))));
        }
        else if ((matcher = GameMenuCommand.LIST_GIFTS.getMatcher(command)) != null) {
            printArrayList(gameController.giftList().getData());
        }
        else if ((matcher = GameMenuCommand.GIFT_RATE.getMatcher(command)) != null) {
            System.out.println(gameController.giftRate(matcher.group("username") , Integer.parseInt(matcher.
                    group("giftID")) , Double.parseDouble(matcher.group("rate"))));
        }
        else if ((matcher = GameMenuCommand.SEND_FLOWER.getMatcher(command)) != null) {
            System.out.println(gameController.sendFlower(matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.START_TRADE.getMatcher(command)) != null) {
            System.out.println(gameController.startTrade());
        }
        else if ((matcher = GameMenuCommand.SHOP_SHOW_ALL_PRODUCTS.getMatcher(command)) != null) {
            System.out.println(gameController.showAllShopProducts().getMessage());
        }
        else if ((matcher = GameMenuCommand.SHOP_SHOW_ALL_AVAILABLE_PRODUCTS.getMatcher(command)) != null) {
            System.out.println(gameController.showAvailableShopProducts().getMessage());
        }
        else if ((matcher = GameMenuCommand.SHOP_PURCHASE.getMatcher(command)) != null) {
            System.out.println(
                    gameController.purchaseItem(
                            matcher.group("name"),
                            matcher.group("number") == null
                                    ? 1
                                    : Integer.parseInt(matcher.group("number"))
                    )
            );
        }
        else if ((matcher = GameMenuCommand.BUILD.getMatcher(command)) != null) {
            System.out.println(gameController.purchaseBuilding(matcher.group("name"), new Coord(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")))));
        }
        else if ((matcher = GameMenuCommand.CHEAT_ADD_DOLLARS.getMatcher(command)) != null) {
            gameController.addDollarsCheat(Integer.parseInt(matcher.group("number")));
        }
        else if ((matcher = GameMenuCommand.MEET_NPC.getMatcher(command)) != null) {
            System.out.println(gameController.meetNPC(matcher.group("npcName")).getMessage());
        }
        else if ((matcher = GameMenuCommand.FRIENDSHIP_NPC_LIST.getMatcher(command)) != null) {
            printArrayList(gameController.friendShipNPCList().getData());
        }
        else if ((matcher = GameMenuCommand.QUESTS_LIST.getMatcher(command)) != null) {
            printArrayList(gameController.showQuestList().getData());
        }
        else if ((matcher = GameMenuCommand.QUEST_FINISH.getMatcher(command)) != null) {
            System.out.println(gameController.finishQuest(matcher.group("npcName") ,
                    Integer.parseInt(matcher.group("questID"))));
        }
        else if ((matcher = GameMenuCommand.GIFT_NPC.getMatcher(command)) != null) {
            System.out.println(gameController.giftNPC(matcher.group("npcName") , matcher.group("item") ,
                    Integer.parseInt(matcher.group("amount"))));
        }
        else if ((matcher = GameMenuCommand.ASK_MARRIAGE.getMatcher(command)) != null) {
            System.out.println(gameController.askMarriage(matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.PLACE_ARTISAN.getMatcher(command)) != null) {
            System.out.println(gameController.placeArtisan(matcher.group("name"), Direction.getDirection(matcher.group("direction"))));
        }
        else if ((matcher = GameMenuCommand.USE_ARTISAN.getMatcher(command)) != null) {
            System.out.println(gameController.useArtisan(matcher.group("name"), new ArrayList<>(Arrays.asList(matcher.group("itemNames").split("\\s+")))));
        }
        else if ((matcher = GameMenuCommand.GET_ARTISAN_PRODUCT.getMatcher(command)) != null) {
            System.out.println(gameController.getArtisanProduct(matcher.group("name")));
        }
        else if((matcher = GameMenuCommand.FISHING.getMatcher(command)) != null) {
            System.out.println(gameController.fishing(matcher.group("fishingPole")));
        }
        else if((matcher = GameMenuCommand.SELL_ANIMAL.getMatcher(command)) != null) {
            System.out.println(gameController.sellAnimal(matcher.group("name")));
        }
        else if((matcher = GameMenuCommand.COLLECT_PRODUCE.getMatcher(command)) != null){
            System.out.println(gameController.collectProducts(matcher.group("name")));
        }
        else if((matcher = GameMenuCommand.SHOW_ANIMAL_PRODUCE.getMatcher(command)) != null) {
            printArrayList(gameController.showAnimalProduces().getData());
        }
        else if((matcher = GameMenuCommand.FEED_HAY.getMatcher(command)) != null) {
            System.out.println(gameController.feedHay(matcher.group("name")));
        }
        else if((matcher = GameMenuCommand.SET_FRIENDSHIP.getMatcher(command)) != null) {
            System.out.println(gameController.setFriendship( matcher.group("username") , Integer.parseInt(matcher.group("xp"))));
        }
        else if((matcher = GameMenuCommand.SHOW_SKILLS.getMatcher(command)) != null) {
            printArrayList(gameController.showSkills().getData());
        }
        else if((matcher = GameMenuCommand.SHOW_NOTIFICATIONS.getMatcher(command)) != null) {
            printArrayList(gameController.showNotifications().getData());
        }
        else {
            System.out.println("invalid command");
            return;
        }
    }
}
