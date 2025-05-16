package controllers;

import models.App;
import models.Item.*;
import models.Menu;
import models.animal.AnimalTypes;
import models.data.AnimalData;
import models.data.ArtisanGoodsData;
import models.data.items.SeedData;
import models.map.Map;
import models.skill.SkillType;
import models.tool.*;
import models.animal.Animal;
import models.crop.*;
import models.game.*;
import models.map.*;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.result.errorTypes.GameError;
import models.result.errorTypes.UserError;
import models.user.Gender;
import models.user.User;
import org.apache.commons.lang3.tuple.Pair;
import views.menu.GameTerminalView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class GameController {

    public Game game = null;

    public Result<String> showCurrentMenu() {
        return Result.success("game", "");
    }


    public Result<Game> createGame(String username1, String username2, String username3) throws IOException {
        if ((DataBaseController.findUserByUsername(username1) == null) || (DataBaseController.findUserByUsername(username2)
                == null) || (DataBaseController.findUserByUsername(username3) == null)) {
            return Result.failure(UserError.USER_NOT_FOUND);
        }

        ArrayList<User> users = new ArrayList<>();
        users.add(App.getInstance().logedInUser);
        users.add(DataBaseController.findUserByUsername(username1));
        users.add(DataBaseController.findUserByUsername(username2));
        users.add(DataBaseController.findUserByUsername(username3));

        /* TODO (DEBUG PURPOSE)
        for(User user : users) {
            if(user.isInAgame)
                return Result.failure(AuthError.IN_GAME_USER);
        }
         */

        for (User user : users) {
            user.setInAGame(true);
            DataBaseController.editUser(user);
        }

        ArrayList<Player> players = new ArrayList<>();
        MapBuilder mapBuilder = new MapBuilder();

        for (User user : users) {
            int seed = GameTerminalView.getSeed(user);
            Map map = mapBuilder.buildFarm(new Random(seed));
            Player player = new Player(user, map);
            players.add(player);
        }

        Game game = new Game(players);
        this.game = game;
        App.getInstance().game = game;

        return Result.success(game, "Game created");
    }

    public Result<Void> loadGame() {
        User user = App.getInstance().logedInUser;
        String targetName = user.getUsername();

        Result<App> r = GameSaver.loadApp(user.getUsername());
        if (r.isError()) return Result.failure(r.getError());
        App app = r.getData();
        App.setInstance(app);

        this.game = app.game;
        List<Player> players = game.getPlayers();
        int idx = -1;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUser().getUsername().equals(targetName)) {
                idx = i;
                App.getInstance().logedInUser = players.get(i).getUser();
                break;
            }
        }

        if (idx > 0) {
            Collections.rotate(players, -idx);
        }
        game.setCurrentPlayer(players.get(0));
        return Result.success(null);
    }

    public void saveGame() {
        for (Player player : game.getPlayers())
            GameSaver.saveApp(App.getInstance(), player.getUser().getUsername());
    }

    public Result<Void> exitGame() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        saveGame();
        game = null;
        App.getInstance().game = null;
        return Result.success("Exit Game ...");
    }

    public Result<Void> nextTurn() {
        if (game == null)
            return Result.failure(AuthError.GAME_NOT_CREATED);

        game.nextTurn();

        return Result.success("Now its " + game.getCurrentPlayer().getUser().getUsername() + "'s turn");

    }

    public Result<Void> terminateGame() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Integer> getTime() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getGameDate().getHour());
    }

    public Result<String> getDate() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(
                "Day " + game.getGameDate().getDay() + ", " +
                        game.getSeason()
                , ""
        );
    }

    public Result<String> getDateTime() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(
                "Day " + game.getGameDate().getDay() + ", " +
                        game.getSeason() + ", " +
                        "Time: " + game.getGameDate().getHourInDay() + ":00",
                ""
        );
    }

    public Result<String> getDayWeek() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getGameDate().getCurrentDayOfWeek(), "");
    }

    public Result<Void> advanceTimeCheat(int days, int hours) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        game.advanceTime(days, hours);
        return Result.success(null);
    }

    public Result<String> getSeasonName() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getSeason().name(), "");
    }

    public Result<Void> struckByThorCheat(Coord coord) {
        if (game == null)
            return Result.failure(GameError.NO_GAME_RUNNING);
        if (game.getCurrentPlayerMap().thor(coord))
            return Result.success("Thor was successful");
        return Result.failure(GameError.YOU_CANT_DO_ACTION);
    }

    public Result<Weather> getWeather() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getWeather());
    }

    public Result<Weather> getWeatherForecast() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getNextWeather());
    }

    public Result<Void> setWeatherCheat(Weather weather) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        game.setNextDayWeather(weather);
        return Result.success(null);
    }

    public Result<Building> buildGreenHouse() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        List<Item> needed = List.of(
                Item.build("coin", 1000),
                Item.build("wood", 500)
        );
        Inventory inv = game.getCurrentPlayer().getInventory();
        if (!inv.canRemoveItems(needed))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        inv.removeItems(needed);
        game.getCurrentPlayerMap().getBuilding(GreenHouse.class).setBuild(true);
        return Result.success("now you can use your greenhouse");
    }

    public Result<Void> walk(int x, int y) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Coord coord = new Coord(x, y);
        boolean isNeigh = game.getCurrentPlayer().getCoord().isNeighbor(coord);

        Player player = game.getCurrentPlayer();
        Tile tile = player.getMap().getTile(coord);

        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);

        if (tile.getTileType().isForaging())
            return Result.failure(GameError.CANT_STAND_ON_FORAGING);
        else if (tile.getTileType() == TileType.REFRIGERATOR)
            return Result.failure(GameError.CANT_STAND_ON_FRIDGE);
        else if (tile.getTileType() == TileType.LAKE)
            return Result.failure(GameError.CANT_STAND_ON_LAKE);
        else if (tile.getTileType() == TileType.DOOR) {
            if (!isNeigh) return Result.failure(GameError.YOU_ARE_DISTANT);
            player.leave();
        } else if (tile.getPlacable(Building.class) != null) {
            if (!isNeigh) return Result.failure(GameError.YOU_ARE_DISTANT);
            Building building = tile.getPlacable(Building.class);
            if (building.canEnter()) {
                player.enterBuilding(building);
            } else return Result.failure(GameError.CANT_ENTER);
        } else {
            PathFinder pf = new PathFinder(player);
            List<PathFinder.PathStep> steps = pf.findPathTo(coord);

            if (steps == null) {
                return Result.failure(GameError.NO_PATH);
            }
            for (PathFinder.PathStep step : steps) {
                player.decreaseEnergy(step.energyCost());
                player.setCoord(step.coord());
                if (player.isFainted()) break;
            }
        }
        printMapFull();
        return Result.success(null);
    }

    public Result<Void> putItemInRefrigerator(Refrigerator refrigerator, String itemName) {
        Inventory inv = game.getCurrentPlayer().getInventory();
        Inventory refInv = refrigerator.getInventory();
        Item item = inv.getItem(itemName);
        if (item == null || item.getItemType() != ItemType.CONSUMABLE)
            return Result.failure(GameError.NOT_FOUND);
        if (!refInv.canAdd())
            return Result.failure(GameError.MAXIMUM_SIZE_EXCEEDED);
        inv.removeItem(item);
        refInv.addItem(item);

        return Result.success(null);
    }

    public Result<Void> pickItemRefrigerator(Refrigerator refrigerator, String itemName) {
        Inventory inv = game.getCurrentPlayer().getInventory();
        Inventory refInv = refrigerator.getInventory();

        Item item = refInv.getItem(itemName);
        if (item == null || item.getItemType() != ItemType.CONSUMABLE)
            return Result.failure(GameError.NOT_FOUND);
        if (!inv.canAdd())
            return Result.failure(GameError.MAXIMUM_SIZE_EXCEEDED);

        refInv.removeItem(item);
        inv.addItem(item);

        return Result.success(null);
    }

    public Result<Void> actOnRefrigerator(String itemName, String action) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Refrigerator r = null;
        for (Tile tile : game.getCurrentPlayer().getNeighborTiles())
            if (tile.getTileType() == TileType.REFRIGERATOR)
                r = tile.getPlacable(Refrigerator.class);
        if (r == null) return Result.failure(GameError.YOU_ARE_DISTANT);

        if (action.equals("put")) {
            return putItemInRefrigerator(r, itemName);
        } else if (action.equals("pick")) {
            return pickItemRefrigerator(r, itemName);
        }
        throw new RuntimeException("This can't happen, Please recheck the REGEX");
    }

    public void printMapFull() {
        GameTerminalView.printWithColor(printMap(0, 0, 300).getData());
    }

    public Result<ArrayList<String>> printMap(int x, int y, int size) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getCurrentPlayerMap().printMap(new Coord(x, y), size, game.getPlayers()));
    }

    public int showEnergy() {
        if (game == null) return -1;
        return game.getCurrentPlayer().getEnergy();
    }

    public Result<Void> helpReadingMap() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        GameTerminalView.helpReadingMap();
        return Result.success("Help Reading Map");
    }

    public Result<Void> setEnergyCheat(int energyValue) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        game.getCurrentPlayer().setEnergy(energyValue);
        return Result.success(null);
    }

    public Result<Energy> setEnergyUnlimited() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        game.getCurrentPlayer().setEnergy(Integer.MAX_VALUE);
        return Result.success(null);
    }

    public Result<ArrayList<String>> showInventory() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getCurrentPlayer().getInventory().showInventory());
    }

    public Result<Void> removeFromInventory(String itemName, int numberOfItems) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Inventory inv = game.getCurrentPlayer().getInventory();
        Item itemToRemove = Item.build(itemName, numberOfItems);
        if (itemToRemove == null)
            return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);

        if (!inv.canRemoveItem(itemToRemove))
            return Result.failure(GameError.NOT_ENOUGH_ITEM);

        TrashCan trashCan = (TrashCan) inv.getItem("trashcan");
        if (trashCan == null)
            return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);

        int addedCoin = itemToRemove.getAmount() * itemToRemove.getPrice();
        if (trashCan.getToolMaterialType().equals(ToolMaterialType.PRIMITIVE)) {
            inv.removeItem(itemToRemove);
        } else if (trashCan.getToolMaterialType().equals(ToolMaterialType.COPPER)) {
            Item coin = Item.build("coin", addedCoin * 15 / 100);
            inv.addItem(coin);
            inv.removeItem(itemToRemove);
        } else if (trashCan.getToolMaterialType().equals(ToolMaterialType.STEEL)) {
            Item coin = Item.build("coin", addedCoin * 30 / 100);
            inv.addItem(coin);
            inv.removeItem(itemToRemove);
        } else if (trashCan.getToolMaterialType().equals(ToolMaterialType.GOLD)) {
            Item coin = Item.build("coin", addedCoin * 45 / 100);
            inv.addItem(coin);
            inv.removeItem(itemToRemove);
        } else if (trashCan.getToolMaterialType().equals(ToolMaterialType.IRIDIUM)) {
            Item coin = Item.build("coin", addedCoin * 60 / 100);
            inv.addItem(coin);
            inv.removeItem(itemToRemove);
        }
        return Result.success(null);
    }

    public Result<Tool> equipTool(String toolName) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (game.getCurrentPlayer().getInventory().toolEquip(toolName)) {
            return Result.success("Now " + toolName + " is in your hands");
        }

        return Result.failure(GameError.TOOL_NOT_FOUND);
    }

    public Result<String> showCurrentTool() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (game.getCurrentPlayer().getItemInHand() == null)
            return Result.success("nothing is in your hands");
        return Result.success(game.getCurrentPlayer().getItemInHand().getName());
    }

    public Result<ArrayList<String>> showAvailableTools() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getCurrentPlayer().getInventory().showAvailableTools());
    }

    public Result<Tool> upgradeTool(String toolName) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> useTool(Direction d) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (d == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);

        Coord destinyTile = game.getCurrentPlayer().getCoord().addCoord(d.getCoord());

        if (game.getCurrentPlayer().getItemInHand() == null) {
            return Result.failure(GameError.NO_TOOL_IN_HAND);
        }

        if (!game.getCurrentPlayer().getItemInHand().getItemType().equals(ItemType.TOOL))
            return Result.failure(GameError.TOOL_NOT_FOUND);

        Tool tool = (Tool) game.getCurrentPlayer().getItemInHand();
        Result<Item> item = tool.use(destinyTile);
        if (item == null)
            return Result.failure(GameError.YOU_ARE_NOT_NEAR_TO_LAKE);
        if (item.isSuccess() && (item.getData() != null)) {
            game.getCurrentPlayer().getInventory().addItem(item.getData());
        }
        return item;
    }

    public Result<String> craftInfo(String craftName) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        SeedData seedData = SeedData.getData(craftName);
        if (seedData == null) {
            return Result.failure(GameError.SEED_NOT_FOUND);
        }
        return Result.success(seedData.toString());
    }

    public Result<Void> plant(String seedName, Direction direction) { // After MAP
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (direction == null)
            return Result.failure(GameError.NO_DIRECTION);
        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();

        Item plantable = inventory.getItem(seedName);
        if (plantable == null || (plantable.getItemType() != ItemType.SEED && plantable.getItemType() != ItemType.SAPLING))
            return Result.failure(GameError.SEED_NOT_FOUND);
        Coord coord = player.getCoord().addCoord(direction.getCoord());
        Tile tile = player.getMap().getTile(coord);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (plantable.getItemType() == ItemType.SEED) {
            if (tile.getTileType() != TileType.PLOWED)
                return Result.failure(GameError.PLANT_ON_PLOWED);
        }
        inventory.removeItem(Item.build(seedName, 1));
        ((Plantable) plantable).plant(tile);
        return Result.success(null);
    }

    public Result<String> showPlant(Coord coord) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Tile tile = game.getCurrentPlayer().getMap().getTile(coord);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (tile.getTileType() != TileType.PLANTED_SEED && tile.getTileType() != TileType.PLANTED_TREE)
            return Result.failure(GameError.Plant_NOT_FOUND);

        if (tile.getTileType() == TileType.PLANTED_SEED)
            return Result.success(tile.getPlacable(PlantedSeed.class).toString());
        return Result.success(tile.getPlacable(PlantedTree.class).toString());
    }

    public Result<Void> fertilize(FertilizerType fertilizer, Direction direction) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (direction == null)
            return Result.failure(GameError.NO_DIRECTION);

        if (fertilizer == null) {
            return Result.failure(GameError.NO_FERTILIZER);
        }

        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();

        Item fertItem = inventory.getItem(fertilizer.getName());
        if (fertItem == null)
            return Result.failure(GameError.CANT_FIND_ITEM_IN_INVENTORY);
        Coord coord = player.getCoord().addCoord(direction.getCoord());
        Tile tile = player.getMap().getTile(coord);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (tile.getTileType() != TileType.PLANTED_SEED)
            return Result.failure(GameError.Plant_NOT_FOUND);


        inventory.removeItem(Item.build(fertilizer.getName(), 1));
        tile.getPlacable(PlantedSeed.class).fertilize(fertilizer);
        return Result.success(null);
    }

    public Result<Integer> howMuchWater() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Tool tool = (Tool) game.getCurrentPlayer().getItemInHand();
        if (tool == null || tool.getToolType() != ToolType.WATERING_CAN)
            return Result.failure(GameError.TOOL_NOT_IN_HAND);
        WateringCan wateringCan = (WateringCan) tool;
        return Result.success(wateringCan.getCapacity());
    }

    public Result<Void> water(Coord coord) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Map map = game.getCurrentPlayerMap();
        Tile tile = map.getTile(coord);
        tile.water();
        return Result.success(null);
    }

    public Result<List<Recipe>> showCraftingRecipes() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getCurrentPlayer().getRecipes(RecipeType.CRAFTING));
    }

    public Result<Void> prepareRecipe(String recipeName, RecipeType recipeType) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Recipe recipe = game.getCurrentPlayer().getRecipeByName(recipeName, recipeType);
        if (recipe == null) {
            return Result.failure(GameError.RECIPE_NOT_FOUND);
        }
        Inventory inventory = game.getCurrentPlayer().getInventory();
        if (!inventory.canAdd())
            return Result.failure(GameError.MAXIMUM_SIZE_EXCEEDED);
        if (!inventory.canRemoveItems(recipe.getItems()))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        inventory.removeItems(recipe.getItems());
        inventory.addItem(recipe.getResult());
        return Result.success(null);
    }

    public Result<Void> placeItem(Item item, Direction direction) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> addItemCheat(String itemName, int quantity) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (itemName.equalsIgnoreCase("milkpale"))
            game.getCurrentPlayer().getInventory().addItem(new MilkPail());
        if (itemName.equalsIgnoreCase("shear"))
            game.getCurrentPlayer().getInventory().addItem(new Shear());
        if (itemName.equalsIgnoreCase("educational pole")) {
            FishingPole fishingPole = new FishingPole();
            fishingPole.setToolMaterialType(ToolMaterialType.EDUCATIONAL);
            game.getCurrentPlayer().getInventory().addItem(fishingPole);
        }
        if (itemName.equalsIgnoreCase("bamboo pole")) {
            FishingPole fishingPole = new FishingPole();
            fishingPole.setToolMaterialType(ToolMaterialType.BAMBOO);
            game.getCurrentPlayer().getInventory().addItem(fishingPole);
        }
        if (itemName.equalsIgnoreCase("fiberglass pole")) {
            FishingPole fishingPole = new FishingPole();
            fishingPole.setToolMaterialType(ToolMaterialType.FIBERGLASS);
            game.getCurrentPlayer().getInventory().addItem(fishingPole);
        }
        if (itemName.equalsIgnoreCase("iridium pole")) {
            FishingPole fishingPole = new FishingPole();
            fishingPole.setToolMaterialType(ToolMaterialType.IRIDIUM);
            game.getCurrentPlayer().getInventory().addItem(fishingPole);
        }
        if (Item.build(itemName, quantity) != null) {
            game.getCurrentPlayer().getInventory().addItem(Item.build(itemName, quantity));
            return Result.success(null);
        }
        return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);
    }

    public Result<Void> cookingRefrigerator(Consumable consumable) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Recipe>> showCookingRecipes() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(game.getCurrentPlayer().getRecipes(RecipeType.COOKING));
    }

    public Result<Void> eat(Consumable food) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //some foods can give power to the user.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> buildBarnOrCoop(String buildingName, int x, int y) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if ((x >= 46) || (y >= 26))
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        for (int i = y; i < y + 5; i++) {
            for (int j = x; j < x + 5; j++) {
                if (!game.getCurrentPlayerMap().getTile(new Coord(i, j)).isEmpty())
                    return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
            }
        }

        if (game.getCurrentPlayer().getInventory().getAmount("name") < 1000)
            return Result.failure(GameError.NOT_ENOUGH_COINS);
        //TODO
        return Result.failure(GameError.NOT_IMPLEMENTED);
    }


    public Result<Void> pet(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        List<Tile> neigh = game.getCurrentPlayer().getNeighborTiles();

        for (Tile tile : neigh) {
            Animal animal = tile.getPlacable(Animal.class);
            if (animal == null)
                continue;

            if (animal.getName().equals(name)) {
                animal.pet();
                return Result.success(null);
            }
        }
        return Result.failure(GameError.ANIMAL_NOT_FOUND);
    }

    public Result<Void> cheatFriendship(String name, int amount) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        for (Animal animal : game.getCurrentPlayer().getAnimals()) {
            if (animal.getName().equals(name)) {
                animal.setFriendship(amount);
                return Result.success("Now your friendship with " + name + " is " + animal.getFriendship());
            }
        }
        return Result.failure(GameError.ANIMAL_NOT_FOUND);

    }

    public Result<ArrayList<String>> showAnimals() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        output.add("Animals:");
        for (Animal animal : game.getCurrentPlayer().getAnimals()) {
            output.add("animal name: " + animal.getName());
            output.add("friendship: " + animal.getFriendship());
            output.add("is today feed: " + animal.isFeedToday());
            output.add("is today pet: " + animal.isTodayPet());
            if (animal.getTodayProduct() != null)
                output.add("today product : " + animal.getTodayProduct());
            output.add("today product : " + null);
        }
        return Result.success(output);
    }

    public Result<Void> shepherdAnimals(String name, int x, int y) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Tile tile = game.getCurrentPlayerMap().getTile(new Coord(x, y));
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (!tile.isEmpty())
            return Result.failure(GameError.TILE_IS_NOT_EMPTY);

        Animal animal = game.getCurrentPlayer().getAnimalByName(name);
        if (animal == null)
            return Result.failure(GameError.ANIMAL_NOT_FOUND);


        animal.shepherd(); //TODO
        game.getCurrentPlayerMap().getTile(new Coord(x, y)).setPlacable(animal);

        return Result.success(name + " is on tile (" + x + ", " + y + ")");
    }

    public Result<Void> feedHay(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Animal animal = game.getCurrentPlayer().getAnimalByName(name);
        if (animal == null)
            return Result.failure(GameError.ANIMAL_NOT_FOUND);

        animal.setFeedToday(true);
        return Result.success(null);
    }

    public Result<ArrayList<String>> showAnimalProduces() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        for (Animal animal : game.getCurrentPlayer().getAnimals()) {
            output.add(animal.getName() + "products : ");
            if (animal.getTodayProduct() != null) {
                output.add("product name : " + animal.getTodayProduct());
                double randomDouble = 0.5 + Math.random();
                output.add("product quality : " + (animal.getFriendship() / 1000) * (0.5 + 0.5 * Math.random()));
            }
        }
        return Result.success(output);
    }

    public Result<Item> collectProducts(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Animal animal = game.getCurrentPlayer().getAnimalByName(name);
        if (animal == null)
            return Result.failure(GameError.ANIMAL_NOT_FOUND);

        if (animal.collectProduce() == null)
            return Result.failure(GameError.ANIMAL_DOES_NOT_HAVE_PRODUCT);

        if (animal.getAnimalType().equals(AnimalTypes.COW)) {
            if (game.getCurrentPlayer().getInventory().canRemoveItem(new MilkPail())) {
                game.getCurrentPlayer().getInventory().addItem(animal.collectProduce());
                animal.setFriendship(animal.getFriendship() + 5);
                animal.setTodayProduct(null);
            } else
                return Result.failure(GameError.NOT_ENOUGH_ITEM);
        } else if (animal.getAnimalType().equals(AnimalTypes.GOAT)) {
            if (game.getCurrentPlayer().getInventory().canRemoveItem(new MilkPail())) {
                game.getCurrentPlayer().getInventory().addItem(animal.collectProduce());
                animal.setFriendship(animal.getFriendship() + 5);
                animal.setTodayProduct(null);
            } else
                return Result.failure(GameError.NOT_ENOUGH_ITEM);
        } else if (animal.getAnimalType().equals(AnimalTypes.SHEEP)) {
            if (game.getCurrentPlayer().getInventory().canRemoveItem(new Shear())) {
                game.getCurrentPlayer().getInventory().addItem(animal.collectProduce());
                animal.setFriendship(animal.getFriendship() + 5);
                animal.setTodayProduct(null);
            } else
                return Result.failure(GameError.NOT_ENOUGH_ITEM);
        } else if (animal.getAnimalType().equals(AnimalTypes.RABBIT)) {
            if (game.getCurrentPlayer().getInventory().canRemoveItem(new Shear())) {
                game.getCurrentPlayer().getInventory().addItem(animal.collectProduce());
                animal.setFriendship(animal.getFriendship() + 5);
                animal.setTodayProduct(null);
            } else
                return Result.failure(GameError.NOT_ENOUGH_ITEM);
        } else {
            game.getCurrentPlayer().getInventory().addItem(animal.collectProduce());
            animal.setTodayProduct(null);
            return Result.success(null);
        }
        return Result.success(null);
    }

    public Result<Void> sell(String itemName, int number) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Item item = Item.build(itemName, number);
        if (item == null) return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);
        if (!item.isSalable()) return Result.failure(GameError.NOT_FOR_SALE);
        Inventory inventory = game.getCurrentPlayer().getInventory();
        if (!inventory.canRemoveItem(item)) return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        ShippingBin shippingBin = null;
        for (Tile tile : game.getCurrentPlayer().getNeighborTiles())
            if (tile.getPlacable(ShippingBin.class) != null)
                shippingBin = tile.getPlacable(ShippingBin.class);
        if (shippingBin == null)
            return Result.failure(GameError.YOU_ARE_DISTANT);

        inventory.removeItem(item);
        shippingBin.add(item);
        return Result.success(null);
    }

    public Result<Void> sellAnimal(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Animal animal = game.getCurrentPlayer().getAnimalByName(name);
        if (animal == null)
            return Result.failure(GameError.ANIMAL_NOT_FOUND);

        Item price = Item.build("coin", (int) (animal.getPrice() * ((double) animal.getFriendship() / 1000 + 0.3)));
        game.getCurrentPlayer().getAnimals().remove(animal);
        game.getCurrentPlayer().getInventory().addItem(price);
        return Result.success(null);
    }

    public Result<Void> fishing(String fishingPole) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        boolean isNearLake = false;
        for (Tile tile : game.getCurrentPlayer().getNeighborTiles()) {
            if (tile.getPlacable(Lake.class) != null)
                isNearLake = true;
        }
        if (!isNearLake)
            return Result.failure(GameError.YOU_ARE_NOT_NEAR_TO_LAKE);

        if (Item.build(fishingPole, 1) == null)
            return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);

        if (!game.getCurrentPlayer().getInventory().canRemoveItem(Objects.requireNonNull(Item.build(fishingPole, 1))))
            return Result.failure(GameError.TOOL_NOT_FOUND);

        Player player = game.getCurrentPlayer();
        player.setSkillExp(SkillType.FISHING, player.getSkillExp(SkillType.FISHING) + 5);
        double amount = Math.random() * (game.getCurrentPlayer().getSkillExp(SkillType.FISHING) + 2);
        if (game.getWeather().equals(Weather.SUNNY))
            amount *= 1.5;
        if (game.getWeather().equals(Weather.RAINY))
            amount *= 1.2;
        if (game.getWeather().equals(Weather.STORM))
            amount *= 0.5;
        if (fishingPole.equals("training")) {
            player.getInventory().addItem(Item.build(FishingPole.getCheapestFish(), Math.min(6, (int) amount)));
            return Result.success(null);
        } else if (fishingPole.equals("bamboo") || fishingPole.equals("iridium") || fishingPole.equals("fiberglass")) {
            player.getInventory().addItem(Item.build(FishingPole.randomFish(), Math.min(6, (int) amount)));
            return Result.success(null);
        }
        return Result.success(null);
    }

    public Result<Void> placeArtisan(String artisanName, Direction direction) {
        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();
        Item artisan = inventory.getItem(artisanName);
        if (artisan == null)
            return Result.failure(GameError.CANT_FIND_ITEM_IN_INVENTORY);

        Coord coord = player.getCoord().addCoord(direction.getCoord());

        Tile tile = player.getMap().getTile(coord);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (!tile.isEmpty()) {
            return Result.failure(GameError.TILE_IS_NOT_EMPTY);
        }

        inventory.removeItem(Item.build(artisanName, 1));
        new Artisan(ArtisanGoodsData.getRecipeData(artisanName)).onPlace(tile);
        return Result.success(null);
    }

    public Result<Void> useArtisan(String artisanName, ArrayList<String> itemNames) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();

        Result<Void> result = null;

        for (Direction direction : Direction.values()) {
            Coord coord = player.getCoord().addCoord(direction.getCoord());

            Tile tile = player.getMap().getTile(coord);
            if (tile.getTileType() == TileType.ARTISAN && !tile.getPlacable(Artisan.class).isResultReady()) {
                Artisan artisan = tile.getPlacable(Artisan.class);

                result = artisan.craft(itemNames);
                if (result.isSuccess())
                    return result;
            }
        }

        if (result != null)
            return result;

        return Result.failure(GameError.NO_FREE_ARTISAN_AROUND);
    }

    public Result<Item> getArtisanProduct(String artisanName) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Player player = game.getCurrentPlayer();
        Inventory inventory = player.getInventory();

        for (Direction direction : Direction.values()) {
            Coord coord = player.getCoord().addCoord(direction.getCoord());

            Tile tile = player.getMap().getTile(coord);
            if (tile.getTileType() == TileType.ARTISAN && tile.getPlacable(Artisan.class).isResultReady()) {
                Artisan artisan = tile.getPlacable(Artisan.class);


                if (artisan.isResultReady()) {
                    Item result = artisan.getResultWithoutReset();
                    if (inventory.addItem(result).isSuccess()) {
                        artisan.getResult();
                        return Result.success(null);
                    }
                    return Result.failure(GameError.CANT_ADD_ITEM_TO_INVENTORY);
                }
            }
        }

        return Result.failure(GameError.NO_READY_ARTISAN_AROUND);
    }

    public Result<Void> crowAttack() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> addMoneyCheat(int amount) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> goToVillage() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (game.getCurrentPlayerMap().mapType != MapType.FARM)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_FARM);
        game.getCurrentPlayer().setMap(game.getVillage());
        game.getCurrentPlayer().setCoord(new Coord(0, 0));
        return Result.success("Now you are in village");
    }

    public Result<Void> backToHome() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (game.getCurrentPlayerMap().mapType != MapType.VILLAGE)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_VILLAGE);
        game.getCurrentPlayer().setMap(game.getCurrentPlayer().getDefaultMap());
        game.getCurrentPlayer().setCoord(new Coord(0, 0));
        return Result.success("Now you are home");
    }

    public Result<ArrayList<String>> showFriendships() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        output.add(game.getCurrentPlayer().getUser().getUsername() + "'s friendships :");
        for (Relation relation : game.getMyRelations(game.getCurrentPlayer()))
            output.add(relation.printRelation(game.getCurrentPlayer()));
        return Result.success(output);
    }

    public Result<Void> talk(String username, String massage) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(game.getCurrentPlayer(), player);
        relation.addTalk(game.getCurrentPlayer().getUser().getUsername() + ": " + massage);
        if (!relation.isTodayTalk())
            relation.setFriendshipXP(relation.getFriendshipXP() + 20);
        if(relation.getLevel().getLevel() == 4){
            player.setEnergy(player.getEnergy() + 50);
            App.getInstance().game.getCurrentPlayer().setEnergy(App.getInstance().game.getCurrentPlayer().getEnergy() + 50);
        }
        player.addNotifications(App.getInstance().game.getCurrentPlayer().getUser().getUsername() + " sends you a message");
        relation.setTodayTalk(true);
        return Result.success(null);
    }

    public Result<ArrayList<String>> talkHistory(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();

        if (game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Relation relation = game.getRelationOfUs(game.getCurrentPlayer(), player);
        output.add("your chats with " + username + " :");
        output.addAll(relation.getTalks());

        return Result.success(output);

    }

    public Result<Void> sendGift(String username, String itemName, int amount) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(game.getCurrentPlayer(), player);
        if (relation.getLevel().getLevel() == 0)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        Item item = Item.build(itemName, amount);
        if (!game.getCurrentPlayer().getInventory().canRemoveItem(item))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        game.getCurrentPlayer().getInventory().removeItem(item);
        player.getInventory().addItem(item);
        relation.addGift(new Gift(game.getCurrentPlayer() , player , item , relation.getGifts().size()));
        if(relation.getLevel().getLevel() == 4){
            player.setEnergy(player.getEnergy() + 50);
            App.getInstance().game.getCurrentPlayer().setEnergy(App.getInstance().game.getCurrentPlayer().getEnergy() + 50);
        }
        player.addNotifications(App.getInstance().game.getCurrentPlayer().getUser().getUsername() + " sends you a gift");
        return Result.success(null);
    }

    //    What's its difference with gift history :/
    public Result<ArrayList<String>> giftList() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();
        for(Player player : App.getInstance().game.getPlayers()){
            if(player.equals(App.getInstance().game.getCurrentPlayer()))
                continue;
            output.addAll(giftHistory(player.getUser().getUsername()).getData());
            Relation relation = App.getInstance().game.getRelationOfUs(App.getInstance().game.getCurrentPlayer(), player);
            for(Gift gift : relation.getGifts()){
                if(gift.getReceiver().equals(App.getInstance().game.getCurrentPlayer()) && (gift.getRate() == 0)) {
                    output.add("Gift ID : " + gift.getId());
                    output.add("Item : " + gift.getItem().getName());
                    output.add("Sender : " + gift.getSender().getUser().getUsername());
                    output.add("Receiver : " + gift.getReceiver().getUser().getUsername());
                    if (gift.getRate() == 0)
                        output.add("Rate : not rated yet!");
                    else
                        output.add("Rate : " + gift.getRate());
                    output.add("-------------------");
                }
            }
        }
        return Result.success(output);
    }

    public Result<Void> giftRate(String username, int giftID, double rate) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);


        Relation relation = game.getRelationOfUs(game.getCurrentPlayer(), player);
        if ((relation.getGifts().size() <= giftID) || (giftID < 0))
            return Result.failure(GameError.GIFT_ID_DOES_NOT_EXIST);

        if ((rate > 5) || (rate < 1))
            return Result.failure(GameError.RATE_MUST_BE_IN_RANGE);

        if(relation.getGifts().get(giftID).getRate() != 0)
            return Result.failure(GameError.THIS_GIFT_HAS_BEEN_RATED);

        relation.getGifts().get(giftID).setRate(rate);
        relation.setFriendshipXP(relation.getFriendshipXP() + (int) ((rate - 3) * 30 + 15));
        return Result.success(null);
    }

    public Result<ArrayList<String>> giftHistory(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();

        if (game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Relation relation = game.getRelationOfUs(game.getCurrentPlayer(), player);
        output.add("your gift history with " + username + " :");
        for (Gift gift : relation.getGifts()) {
            output.add("Gift ID : " + gift.getId());
            output.add("Item : " + gift.getItem().getName());
            output.add("Sender : " + gift.getSender().getUser().getUsername());
            output.add("Receiver : " + gift.getReceiver().getUser().getUsername());
            if (gift.getRate() == 0)
                output.add("Rate : not rated yet!");
            else
                output.add("Rate : " + gift.getRate());
            output.add("-------------------");
        }
        return Result.success(output);
    }

    public Result<Void> hug(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(game.getCurrentPlayer(), player);

        if (relation.getLevel().getLevel() < 2)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        if (!relation.isTodayHug())
            relation.setFriendshipXP(relation.getFriendshipXP() + 60);
        if(relation.getLevel().getLevel() == 4){
            player.setEnergy(player.getEnergy() + 50);
            App.getInstance().game.getCurrentPlayer().setEnergy(App.getInstance().game.getCurrentPlayer().getEnergy() + 50);
        }
        relation.checkOverFlow();
        return Result.success(null);
    }

    public Result<Void> sendFlower(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(game.getCurrentPlayer(), player);
        if (relation.getLevel().getLevel() == 0)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        Item item = Item.build("Bouquet", 1);
        if (!game.getCurrentPlayer().getInventory().canRemoveItem(item))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        game.getCurrentPlayer().getInventory().removeItem(item);
        player.getInventory().addItem(item);
        relation.setFlower(true);
        if(relation.getLevel().getLevel() == 4){
            player.setEnergy(player.getEnergy() + 50);
            App.getInstance().game.getCurrentPlayer().setEnergy(App.getInstance().game.getCurrentPlayer().getEnergy() + 50);
        }
        relation.checkOverFlow();
        return Result.success(null);
    }

    public Result<Void> askMarriage(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(game.getCurrentPlayer(), player);
        if (relation.getLevel().getLevel() < 3)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);
        if (relation.getFriendshipXP() < 300)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        Item ring = Item.build("wedding ring", 1);
        assert ring != null;
        if (!game.getCurrentPlayer().getInventory().canRemoveItem(ring))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        if (game.getCurrentPlayer().getUser().getGender().equals(Gender.FEMALE))
            return Result.failure(GameError.YOU_ARE_GIRL);
        if (player.getUser().getGender().equals(Gender.MALE))
            return Result.failure(GameError.YOUR_WIFE_CAN_NOT_BE_A_BOY);

        String response = GameTerminalView.getResponse(player);
        if (response.equals("no")) {
            relation.setFriendshipXP(0);
            relation.setLevel(FriendshipLevel.LEVEL0);
            relation.setFlower(false);
            App.getInstance().game.getCurrentPlayer().setMaxEnergy(100 , 7);
            return Result.success("aaaajaaaab rasmieeeeeh rasme zamoooneeehhh");
        }

        relation.setLevel(FriendshipLevel.LEVEL4);
        game.getCurrentPlayer().getInventory().removeItem(ring);
        player.getInventory().addItem(ring);
        Item coin = Item.build("coin", game.getCurrentPlayer().getCoins() + player.getCoins());
        game.getCurrentPlayer().getInventory().removeItem(Item.build("coin", game.getCurrentPlayer().getCoins()));
        player.getInventory().removeItem(Item.build("coin", player.getCoins()));
        game.getCurrentPlayer().getInventory().addItem(coin);
        player.getInventory().addItem(coin);
        return Result.success("ke emshab shabe eshghe hamin emshabo darim");
    }


    public Result<Void> startTrade() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.getInstance().currentMenu = Menu.TradeMenu;
        return Result.success(null);
    }

    public Result<String> meetNPC(String NPCName) {
        NPC npc = game.getNPCByName(NPCName);
        if (npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);

        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(game.getCurrentPlayer());
        if (!npcFriendship.isTodayMeet())
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 20);
        npcFriendship.setTodayMeet(true);
        return Result.success(npcFriendship.talk(game.getGameDate().getSeason(), game.getGameDate().getHour()));

    }

    public Result<Void> giftNPC(String NPCName, String item, int amount) {
        NPC npc = game.getNPCByName(NPCName);
        if (npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);

        if (!game.getCurrentPlayer().getInventory().canRemoveItem(Item.build(item, amount)))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        if (Item.build(item, 1) instanceof Tool)
            return Result.failure(GameError.YOU_CANT_GIFT_A_TOOL);

        game.getCurrentPlayer().getInventory().removeItem(Item.build(item, amount));
        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(game.getCurrentPlayer());
        if (npcFriendship.isTodayGift())
            return Result.success(null);

        npcFriendship.setTodayGift(true);
        if (npc.isMyFavoriteItem(Item.build(item, amount)))
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 200);
        else
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 50);
        return Result.success(null);
    }

    public Result<ArrayList<String>> friendShipNPCList() {
        ArrayList<String> output = new ArrayList<>();
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        for (NPC npc : game.getNpcs()) {
            for (NPCFriendship npcFriendship : npc.getFriendships()) {
                if (npcFriendship.getPlayer().equals(game.getCurrentPlayer())) {
                    output.add("NPC name: " + npc.getName());
                    output.add("Friendship XP: " + npcFriendship.getFriendshipXP());
                    output.add("Friendship Level: " + npcFriendship.getLevel().getLevel());
                    if (npcFriendship.isTodayMeet())
                        output.add("last seen recently");
                    else
                        output.add("last seen a long time ago");
                    if (npcFriendship.isTodayGift())
                        output.add("last gift recently");
                    else
                        output.add("last gift a long time ago");
                    output.add("-----------------");
                }
            }
        }
        return Result.success(output);
    }

    public Result<ArrayList<String>> showQuestList() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        for (NPC npc : game.getNpcs()) {
            NPCFriendship npcFriendship = npc.getFriendshipByPlayer(game.getCurrentPlayer());
            output.add("NPC name: " + npc.getName());
            for (int i = 0; i < Math.min(npcFriendship.getLevel().getLevel() + 1, 3); i++) {
                output.add(i + ":");
                output.add("Request Item: " + npc.getTasks().get(i).getRequestItem());
                output.add("Request Amount: " + npc.getTasks().get(i).getRequestAmount());
                output.add("Reward Item: " + npc.getTasks().get(i).getRewardItem());
                output.add("Reward Amount: " + npc.getTasks().get(i).getRewardAmount());
                if(npc.getTasksFlag().get(i))
                    output.add("this quest has been finished");
                else
                    output.add("you finish this quest");
                output.add("--------------------");
                output.add("\n");
            }
        }
        return Result.success(output);
    }

    public Result<Item> finishQuest(String NPCName, int questID) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        NPC npc = game.getNPCByName(NPCName);
        if (npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);
        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(game.getCurrentPlayer());

        if (questID > npcFriendship.getLevel().getLevel())
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);
        if(npc.getTasksFlag().get(questID))
            return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);

        Item requiredItem = Item.build(npc.getTasks().get(questID).getRequestItem(), npc.getTasks().get(questID).getRequestAmount());

        if (!game.getCurrentPlayer().getInventory().canRemoveItem(requiredItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEM);
        game.getCurrentPlayer().getInventory().removeItem(requiredItem);

        Item rewardItem = Item.build(npc.getTasks().get(questID).getRewardItem(), npc.getTasks().get(questID).getRewardAmount());
        game.getCurrentPlayer().getInventory().addItem(rewardItem);
        npc.getTasksFlag().set(questID, true);
        return Result.success(null);

    }

    public boolean isGameLockedDueToNight() {
        return (game != null && game.getGameDate().getHour() == 22);
    }

    //This means that we did 1 work... (you should advance game by 1 hour)
    public void advance() {
        if (game == null) return;
        game.advance();
    }

    //DEBUG COMMANDS:

    public String whereAmI() {
        if (game == null) return "No game running";
        if (game.getCurrentPlayer().getBuilding() != null)
            return game.getCurrentPlayer().getBuilding().toString();
        return game.getCurrentPlayerMap().mapType.name();
    }

    public String whoAmI() {
        if (game == null) return "No game running";
        return game.getCurrentPlayer().toString();
    }

    public String getTileStatus(Coord coord) {
        if (game == null) return "No game running";
        return game.getCurrentPlayerMap().getTile(coord).toString();
    }

    public String getMapStatus() {
        if (game == null) return "No game running";
        printMapFull();
        return game.getCurrentPlayerMap().toString();
    }

    public String getInventoryStatus() {
        if (game == null) return "No game running";
        return game.getCurrentPlayer().getInventory().toString();
    }

    public Result<String> showAllShopProducts() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = game.getCurrentPlayer().getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        return Result.success(shop.showProducts());
    }

    public Result<String> showAvailableShopProducts() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = game.getCurrentPlayer().getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        return Result.success(shop.showAvailableItems());
    }

    public Result<Void> purchase(String name, int number) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = game.getCurrentPlayer().getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        Inventory inventory = game.getCurrentPlayer().getInventory();
        Result<Void> r = shop.prepareBuy(name, number, inventory);
        if (r.isError()) return r;
        return Result.success(null);
    }

    public Result<Void> purchaseAnimal(String animalName, String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Result<Void> r = purchase(animalName, 1);
        if (r.isError()) return r;
        assert AnimalData.getAnimalData(animalName) != null : "Animal " + animalName + " not found";
        Animal animal = new Animal(name, AnimalData.getAnimalData(animalName));
        List<AnimalHouse> animalHouse = game.getCurrentPlayer().getDefaultMap().getBuildings(AnimalHouse.class);
        for (AnimalHouse h : animalHouse) {
            if (animal.canEnterHouse(h.getFullName()) && animal.canEnterHouseType(h.getHouseType()) && h.hasSpace()) {
                h.add(animal);
                game.getCurrentPlayer().addAnimal(animal);
                return Result.success("Animal is in " + h.getFullName());
            }
        }
        return Result.failure(GameError.EMPTY_HOUSE_WAS_NOT_FOUND);
    }

    public Result<Void> purchaseItem(String name, int number) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Result<Void> r = purchase(name, number);
        if (r.isError()) return r;

        Item resultItem = Item.build(name, number);
        game.getCurrentPlayer().getInventory().addItem(resultItem);
        return Result.success(null);
    }

    public Result<Void> purchaseBuilding(String name, Coord coord) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Result<Void> r = purchase(name, 1);
        if (r.isError()) return r;

        MapType mapType = MapType.getMapType(name);

        Map map = game.getCurrentPlayer().getDefaultMap();
        MapBuilder mapBuilder = new MapBuilder();

        mapBuilder.buildAnimalHouse(mapType);
        TileType tileType = TileType.fromName(name);

        if (!mapBuilder.isAreaAvailable(map, coord.getY(), coord.getX(), tileType.getDefaultHeight(), tileType.getDefaultWidth()))
            return Result.failure(GameError.CANT_PLACE);

        AnimalHouse animalHouse = new AnimalHouse(name);
        map.build(animalHouse);
        mapBuilder.placeBuilding(map, Pair.of(tileType, animalHouse), coord.getY(), coord.getX(), tileType.getDefaultHeight(), tileType.getDefaultWidth());

        return Result.success(null);
    }

    public void addDollarsCheat(int number) {
        if (game == null) return;
        game.getCurrentPlayer().addCoins(number);
    }

    public boolean isFainted() {
        if (game == null) return false;
        return game.getCurrentPlayer().isFainted();
    }

    public Result<Void> setFriendship(String username , int xp){
        Player player = App.getInstance().game.getPlayerByName(username);
        Relation relation = App.getInstance().game.getRelationOfUs(player , App.getInstance().game.getCurrentPlayer());
        relation.setFriendshipXP(xp);
        return Result.success(null);
    }

    public Result<ArrayList<String>> showSkills() {
        Player player = game.getCurrentPlayer();
        ArrayList<String> skills = new ArrayList<>();
        skills.add("FARMING : " + player.getSkillExp(SkillType.FARMING));
        skills.add("FORAGING : " + player.getSkillExp(SkillType.FORAGING));
        skills.add("FISHING : " + player.getSkillExp(SkillType.FISHING));
        skills.add("MINING : " + player.getSkillExp(SkillType.MINING));
        return Result.success(skills);
    }

    public Result<Void> goToFarm(String user) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (user.equals(game.getCurrentPlayer().getUser().getUsername())) {
            return backToHome();
        }
        if (game.getCurrentPlayer().getMap().mapType != MapType.VILLAGE)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_VILLAGE);
        Relation r = game.getRelationOfUs(game.getCurrentPlayer(), game.getPlayerByName(user));
        if (r == null) return Result.failure(UserError.USER_NOT_FOUND);
        if (r.getLevel() != FriendshipLevel.LEVEL4)
            return Result.failure(GameError.THIS_FARM_DOESNT_BELONG_TO_YOU);
        game.getCurrentPlayer().setMap(game.getPlayerByName(user).getDefaultMap());
        game.getCurrentPlayer().setCoord(new Coord(0, 0));
        return Result.success(null);
    }

    public Result<ArrayList<String>> showNotifications(){
        ArrayList<String> output = new ArrayList<>(App.getInstance().game.getCurrentPlayer().getNotifications());
        App.getInstance().game.getCurrentPlayer().resetNotifications();
        return Result.success(output);
    }

}