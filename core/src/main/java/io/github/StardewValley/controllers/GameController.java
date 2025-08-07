package io.github.StardewValley.controllers;

/**
 * Each operation here is forward through NETWORK
 * SO please keep that IN MIND
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.ArtisanRecipeData;
import io.github.StardewValley.App;

import data.AnimalData;
import data.ArtisanGoodsData;
import data.items.SeedData;
import io.github.StardewValley.network.NetworkLLMController;
import models.Item.*;
import models.MusicData;
import models.animal.Animal;
import models.animal.AnimalTypes;
import models.crop.FertilizerType;
import models.crop.Plantable;
import models.crop.PlantedSeed;
import models.crop.PlantedTree;
import models.game.*;
import models.map.*;
import models.map.Map;
import models.result.Result;
import models.result.errorTypes.GameError;
import models.result.errorTypes.UserError;
import models.skill.SkillType;
import models.tool.*;
import models.user.Gender;
import org.apache.commons.lang3.tuple.Pair;
import io.github.StardewValley.views.menu.CLI.GameTerminalView;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class GameController {

    private Game game;
    private Player player;

    /**
     * Required for ByteBuddy INIT
     * DON'T EVER USE THIS
     */
    public GameController() {

    }



    public GameController(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public Result<String> showCurrentMenu() {
        return Result.success("game", "");
    }

    public Result<Void> loadGame() {
        /*
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

         */
        return null;
    }

    public void saveGame() {
        for (Player player : game.getPlayers())
            GameSaver.saveApp(App.getInstance(), player.getUser().getUsername());
    }

    public Result<Void> exitGame() {
        /*
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        saveGame();
        game = null;
        App.getInstance().game = null;
        return Result.success("Exit Game ...");

         */
        return null;
    }

    public Result<Void> nextTurn() {
        /*
        if (game == null)
            return Result.failure(AuthError.GAME_NOT_CREATED);

        game.nextTurn();

        return Result.success("Now its " + player.getUser().getUsername() + "'s turn");

         */
        return null;
    }

    public Result<Void> terminateGame() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
//        throw new UnsupportedOperationException("Not supported yet.");
        System.err.println("Terminating game is not supported yet");
        return null;
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
        if (player.getMap().thor(coord))
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
        if (player.getMap().mapType != MapType.FARM)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_FARM);

        List<Item> needed = List.of(
                Item.build("coin", 1000),
                Item.build("wood", 500)
        );
        Inventory inv = player.getInventory();
        if (!inv.canRemoveItems(needed))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        inv.removeItems(needed);
        player.getMap().getBuilding(GreenHouse.class).setBuild(true);
        return Result.success("now you can use your greenhouse");
    }

    public void walk(Direction direction) {
//        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
//        Coord coord = new Coord(x, y);
//        boolean isNeigh = player.getCoord().isNeighbor(coord);
//
//        Tile tile = player.getMap().getTile(coord);
//
//        if (tile == null)
//            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
//
//        if (tile.getTileType().isForaging())
//            return Result.failure(GameError.CANT_STAND_ON_FORAGING);
//        else if (tile.getTileType() == TileType.REFRIGERATOR)
//            return Result.failure(GameError.CANT_STAND_ON_FRIDGE);
//        else if (tile.getTileType() == TileType.LAKE)
//            return Result.failure(GameError.CANT_STAND_ON_LAKE);
//        else if (tile.getTileType() == TileType.DOOR) {
//            if (!isNeigh) return Result.failure(GameError.YOU_ARE_DISTANT);
//            player.leave();
//        } else if (tile.getPlacable(Building.class) != null) {
//            if (!isNeigh) return Result.failure(GameError.YOU_ARE_DISTANT);
//            Building building = tile.getPlacable(Building.class);
//            if (building.canEnter(game.getGameDate())) {
//                player.enterBuilding(building);
//            } else return Result.failure(GameError.CANT_ENTER);
//        } else if (!coord.equals(player.getCoord())) {
//            PathFinder pf = new PathFinder(player);
//            List<PathFinder.PathStep> steps = pf.findPathTo(coord);
//
//            if (steps == null || steps.isEmpty()) {
//                return Result.failure(GameError.NO_PATH);
//            }
//            for (PathFinder.PathStep step : steps) {
//                player.decreaseEnergy(step.energyCost());
//                player.setCoord(step.coord());
//                if (player.isFainted()) break;
//            }
//        }
//        printMapFull();
//        return Result.success(null);
        Map map = player.getMap();
        Sprite sprite = player.getSprite();
        float speed = player.getSpeed();

        if((speed*direction.getDx() + sprite.getX()) > (map.getMaxX()*30 + map.mapType.distanceX - 26))
            return;
        if((speed*direction.getDy() + sprite.getY()) > (map.getMaxY()*30 + map.mapType.distanceY - 26))
            return;
        if((speed*direction.getDx() + sprite.getX()) < map.mapType.distanceX)
            return;
        if((speed*direction.getDy() + sprite.getY()) < map.mapType.distanceY)
            return;


        Tile tile = map.getTile((int) (sprite.getX() + speed*direction.getDx() - map.mapType.distanceX)/30 ,
                map.getMaxY() - 1 - (int) (sprite.getY() + speed*direction.getDy() - map.mapType.distanceY)/30);
        if(tile.getTileType().isForaging())
            return;
        else if (tile.getTileType() == TileType.REFRIGERATOR)
            return;
        else if (tile.getTileType() == TileType.LAKE)
            return;
        else if (tile.getTileType() == TileType.DOOR) {
            player.leave();
        }
        else if(tile.getTileType() == TileType.BRIDGE && map.mapType == MapType.FARM){
            goToVillage();
        }
        else if(tile.getTileType() == TileType.BRIDGE && map.mapType == MapType.VILLAGE) {
            backToHome();
        }
        else if (tile.getPlacable(Building.class) != null) {
            return;
        }

        sprite.setX(speed*direction.getDx() + sprite.getX());
        sprite.setY(speed*direction.getDy() + sprite.getY());
        player.getCoord().setX((int) (sprite.getX() - map.mapType.distanceX)/30);
        player.getCoord().setY(map.getMaxY() - 1 - (int) (sprite.getY() - map.mapType.distanceY)/30);

        if(player.getShepherdingAnimal() != null){
            player.getShepherdingAnimal().spriteGetter().setX(speed*direction.getDx() + sprite.getX() + 30);
            player.getShepherdingAnimal().spriteGetter().setY(speed*direction.getDy() + sprite.getY());
        }
        player.decreaseEnergy((float) 0.001);

    }

    public Result<Void> putItemInRefrigerator(Refrigerator refrigerator, String itemName) {
        Inventory inv = player.getInventory();
        Inventory refInv = refrigerator.getInventory();
        Item item = inv.getItem(itemName);
        if (item == null)
            return Result.failure(GameError.NOT_FOUND);
        if (item.getItemType() != ItemType.CONSUMABLE)
            return Result.failure(GameError.ITEM_DOESNT_HAVE_VALID_TYPE);
        if (!refInv.canAdd())
            return Result.failure(GameError.MAXIMUM_SIZE_EXCEEDED);
        int amount = item.getAmount();
        inv.removeItem(item);
        refInv.addItem(item);
        item.setAmount(amount);

        return Result.success(null);
    }

    public Result<Void> pickItemRefrigerator(Refrigerator refrigerator, String itemName) {
        Inventory inv = player.getInventory();
        Inventory refInv = refrigerator.getInventory();

        Item item = refInv.getItem(itemName);
        if (item == null)
            return Result.failure(GameError.NOT_FOUND);
        if (item.getItemType() != ItemType.CONSUMABLE)
            return Result.failure(GameError.ITEM_DOESNT_HAVE_VALID_TYPE);
        if (!inv.canAdd())
            return Result.failure(GameError.MAXIMUM_SIZE_EXCEEDED);

        int amount = item.getAmount();
        refInv.removeItem(item);
        inv.addItem(item);
        item.setAmount(amount);

        return Result.success(null);
    }

    public Result<Void> actOnRefrigerator(String itemName, String action) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Item item = Item.build(itemName, 1);
        if (item == null) return Result.failure(GameError.NOT_FOUND);

        Refrigerator r = null;
        for (Tile tile : player.getNeighborTiles(game))
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
        return Result.success(player.getMap().printMap(new Coord(x, y), size, game.getPlayers()));
    }

    public float showEnergy() {
        if (game == null) return -1;
        return player.getEnergy();
    }

    public Result<Void> helpReadingMap() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        GameTerminalView.helpReadingMap();
        return Result.success("Help Reading Map");
    }

    public Result<Void> setEnergyCheat(int energyValue) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        player.setEnergy(energyValue);
        return Result.success(null);
    }

    public Result<Energy> setEnergyUnlimited() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        player.setEnergy(Integer.MAX_VALUE);
        return Result.success(null);
    }

    public Result<ArrayList<String>> showInventory() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(player.getInventory().showInventory());
    }

    public Result<Void> removeFromInventory(String itemName, int numberOfItems) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Inventory inv = player.getInventory();
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
        if (player.getInventory().toolEquip(player, toolName)) {
            return Result.success("Now " + toolName + " is in your hands");
        }

        return Result.failure(GameError.TOOL_NOT_FOUND);
    }

    public Result<String> showCurrentTool() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (player.getItemInHand() == null)
            return Result.success("nothing is in your hands");
        return Result.success(player.getItemInHand().getName());
    }

    public Result<ArrayList<String>> showAvailableTools() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(player.getInventory().showAvailableTools(player));
    }

    public Result<Void> upgradeTool(String toolName) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = player.getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);

        Shop shop = (Shop) building;
        if (shop.getShopType() != TileType.BLACKSMITH)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_BLACKSMITH);
        Inventory inventory = player.getInventory();
        Item item = inventory.getItem(toolName);
        if (item == null || item.getItemType() != ItemType.TOOL)
            return Result.failure(GameError.ITEM_IS_NOT_TOOL);
        Tool t = (Tool) item;
        String recipe = t.getToolMaterialType().getNextToolName();
        if (recipe == null) return Result.failure(GameError.NOT_UPGRADABLE);

        Result<Void> r = shop.prepareBuy(recipe, 1, inventory, game, player);
        if (r.isError()) return r;

        t.setToolMaterialType(t.getToolMaterialType().getNextType());

        return Result.success(null);

    }

    public Result<Item> useTool(Direction d) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (d == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);

        Coord destinyTile = player.getCoord().addCoord(d.getCoord());

        if (player.getItemInHand() == null) {
            return Result.failure(GameError.NO_TOOL_IN_HAND);
        }

        if (!player.getItemInHand().getItemType().equals(ItemType.TOOL))
            return Result.failure(GameError.TOOL_NOT_FOUND);

        Tool tool = (Tool) player.getItemInHand();
        Result<Item> item = tool.use(destinyTile, game, player);
        if (item.isError())
            return item;
        if (item == null)
            return Result.failure(GameError.YOU_ARE_NOT_NEAR_TO_LAKE);
        if (item.isSuccess() && (item.getData() != null)) {
            player.getInventory().addItem(item.getData());
        }
        ((Tool) player.getItemInHand()).setAnimationTime((float) 0.5);
        ((Tool) player.getItemInHand()).setAnimationDirection(d.getDx());
        return item;
    }

    public Result<Void> addFishToInventory(String fishName , boolean isPerfect){
        Item item = Item.build(fishName , 1);
        if(item == null)
            throw new RuntimeException("Name = " + fishName);
        player.getInventory().addItem(item);
        if(isPerfect) {
            int beforeSkill = player.getSkillExp(SkillType.FISHING);
            if (beforeSkill == 0)
                beforeSkill = 1;
            player.setSkillExp(SkillType.FISHING, (int) (2.4 * beforeSkill));
        }
        return Result.success(null);
    }

    public Result<String> craftInfo(String craftName) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        SeedData seedData = SeedData.getData(craftName);
        if (seedData == null) {
            return Result.failure(GameError.SEED_NOT_FOUND);
        }
        return Result.success(seedData.toString());
    }

    public void plant(Coord coord, String seedName) {
        Plantable plantable = (Plantable) Item.build(seedName, 1);
        Tile tile = player.getMap().getTile(coord);

        plantable.plant(tile);
        player.getInventory().removeItem((Item) plantable);
    }

    public void harvest(Coord coord) {
        Item item = player.getMap().getTile(coord).harvest(player);
        player.getInventory().addItem(item);
    }

    public Result<Void> plant(String seedName, Direction direction) { // After MAP
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (direction == null)
            return Result.failure(GameError.NO_DIRECTION);
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

        Tile tile = player.getMap().getTile(coord);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (tile.getTileType() != TileType.PLANTED_SEED && tile.getTileType() != TileType.PLANTED_TREE)
            return Result.failure(GameError.Plant_NOT_FOUND);

        if (tile.getTileType() == TileType.PLANTED_SEED)
            return Result.success(tile.getPlacable(PlantedSeed.class).toString());
        return Result.success(tile.getPlacable(PlantedTree.class).toString());
    }

    public void fertilize(Coord coord, String fertilizerName) {
        Tile tile = player.getMap().getTile(coord);
        Item fertilizer = Item.build(fertilizerName, 1);
        tile.getPlacable(PlantedSeed.class).fertilize(FertilizerType.getFertilizerType(fertilizerName));
        player.getInventory().removeItem(fertilizer);
    }

    public Result<Void> fertilize(FertilizerType fertilizer, Direction direction) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (direction == null)
            return Result.failure(GameError.NO_DIRECTION);

        if (fertilizer == null) {
            return Result.failure(GameError.NO_FERTILIZER);
        }

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
        Tool tool = (Tool) player.getItemInHand();
        if (tool == null || tool.getToolType() != ToolType.WATERING_CAN)
            return Result.failure(GameError.TOOL_NOT_IN_HAND);
        WateringCan wateringCan = (WateringCan) tool;
        return Result.success(wateringCan.getCurrentWater());
    }

    public Result<Item> water(Coord coord) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Map map = player.getMap();
        Tool tool = (Tool) player.getItemInHand();
        if (tool == null || tool.getToolType() != ToolType.WATERING_CAN)
            return Result.failure(GameError.TOOL_NOT_IN_HAND);
        return tool.use(coord, game, player);
    }

    public Result<List<Recipe>> showCraftingRecipes() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (player.getMap().mapType != MapType.HOUSE)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_HOUSE);
        return Result.success(player.getRecipes(RecipeType.CRAFTING));
    }

    public Inventory getInventoryForRecipe(RecipeType recipeType) {
        Inventory inv = player.getInventory();
        if (recipeType == RecipeType.CRAFTING)
            return inv;

        Inventory refInv = ((House) player.getBuilding()).getRefrigerator().getInventory();
        List<Item> items = new ArrayList<>(inv.getItems());
        items.addAll(refInv.getItems());
        return new Inventory(items);
    }

    public Result<Void> prepareRecipe(String recipeName, RecipeType recipeType) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (player.getMap().mapType != MapType.HOUSE)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_HOUSE);

        Recipe recipe = player.getRecipeByName(recipeName, recipeType);
        if (recipe == null) {
            return Result.failure(GameError.RECIPE_NOT_FOUND);
        }
        if (player.getEnergy() < recipeType.getEnergy())
            return Result.failure(GameError.ENERGY_IS_NOT_ENOUGH);
        Inventory all = getInventoryForRecipe(recipeType);
        Inventory inv = player.getInventory();

        if (!inv.canAdd())
            return Result.failure(GameError.MAXIMUM_SIZE_EXCEEDED);
        if (!all.canRemoveItems(recipe.getItems()))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        all.removeItems(recipe.getItems());
        inv.addItem(recipe.getResult());

        inv.normalize();
        ((House) player.getBuilding()).getRefrigerator().getInventory().normalize();

        player.decreaseEnergy(recipeType.getEnergy());

        return Result.success(null);
    }

    public Result<Void> placeItem(String item, Direction direction) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Coord coord = player.getCoord().addCoord(direction.getCoord());
        Map map = player.getMap();
        Tile tile = ((models.map.Map) map).getTile(coord);
        if (tile == null || !tile.isEmpty()) return Result.failure(GameError.TILE_IS_NOT_EMPTY);
        Placeable placeable = (Placeable) Item.build(item, 1);
        placeable.onPlace(tile);
        return Result.success(null);
    }

    public Result<Void> addItemCheat(String itemName, int quantity) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (quantity <= 0) return Result.failure(GameError.INVALID_NUMBER);
        if (itemName.equalsIgnoreCase("milkpale")) {
            player.getInventory().addItem(new MilkPail());
            return Result.success(null);
        }
        if (itemName.equalsIgnoreCase("shear")) {
            player.getInventory().addItem(new Shear());
            return Result.success(null);
        }
        if (itemName.equalsIgnoreCase("educational pole")) {
            FishingPole fishingPole = new FishingPole();
            fishingPole.setToolMaterialType(ToolMaterialType.EDUCATIONAL);
            player.getInventory().addItem(fishingPole);
            return Result.success(null);
        }
        if (itemName.equalsIgnoreCase("bamboo pole")) {
            FishingPole fishingPole = new FishingPole();
            fishingPole.setToolMaterialType(ToolMaterialType.BAMBOO);
            player.getInventory().addItem(fishingPole);
            return Result.success(null);
        }
        if (itemName.equalsIgnoreCase("fiberglass pole")) {
            FishingPole fishingPole = new FishingPole();
            fishingPole.setToolMaterialType(ToolMaterialType.FIBERGLASS);
            player.getInventory().addItem(fishingPole);
            return Result.success(null);
        }
        if (itemName.equalsIgnoreCase("iridium pole")) {
            FishingPole fishingPole = new FishingPole();
            fishingPole.setToolMaterialType(ToolMaterialType.IRIDIUM);
            player.getInventory().addItem(fishingPole);
            return Result.success(null);
        }
        if (Item.build(itemName, quantity) != null) {
            player.getInventory().addItem(Item.build(itemName, quantity));
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
        if (player.getMap().mapType != MapType.HOUSE)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_HOUSE);
        return Result.success(player.getRecipes(RecipeType.COOKING));
    }

    public Result<Void> eat(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Item item = player.getInventory().getItem(name);
        if (item == null) return Result.failure(GameError.NOT_FOUND);
        if (item.getItemType() != ItemType.CONSUMABLE)
            return Result.failure(GameError.ITEM_IS_NOT_CONSUMABLE);
        item = item.copy();
        item.setAmount(1);
        Consumable consumable = (Consumable) item;

        Inventory inventory = player.getInventory();
        if (!inventory.canRemoveItem(consumable))
            return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);
        inventory.removeItem(consumable);
        System.err.println("ate " + name);
        player.decreaseEnergy(-consumable.getEnergy());
        return Result.success(null);
    }

    public Result<Void> buildBarnOrCoop(String buildingName, int x, int y) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if ((x >= 46) || (y >= 26))
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        for (int i = y; i < y + 5; i++) {
            for (int j = x; j < x + 5; j++) {
                if (!player.getMap().getTile(new Coord(i, j)).isEmpty())
                    return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
            }
        }

        if (player.getInventory().getAmount("name") < 1000)
            return Result.failure(GameError.NOT_ENOUGH_COINS);
        //TODO
        return Result.failure(GameError.NOT_IMPLEMENTED);
    }


    public Result<Void> pet(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        List<Tile> neigh = player.getNeighborTiles(game);

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
        for (Animal animal : player.getAnimals()) {
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
        for (Animal animal : player.getAnimals()) {
            output.add("animal name: " + animal.getName());
            output.add("friendship: " + animal.getFriendship());
            output.add("is today feed: " + animal.isFeedToday());
            output.add("is today pet: " + animal.isTodayPet());
            if (animal.getTodayProduct() != null)
                output.add("today product : " + animal.getTodayProduct());
            else
                output.add("today product : " + null);
        }
        return Result.success(output);
    }

    public Result<Void> shepherdAnimals(String name, int x, int y) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Tile tile = player.getMap().getTile(new Coord(x, y));
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (!tile.isEmpty())
            return Result.failure(GameError.TILE_IS_NOT_EMPTY);

        Animal animal = player.getAnimalByName(name);
        if (animal == null)
            return Result.failure(GameError.ANIMAL_NOT_FOUND);


        animal.shepherd();
        player.getMap().getTile(new Coord(x, y)).setPlacable(animal);

        return Result.success(name + " is on tile (" + x + ", " + y + ")");
    }

    public Result<Void> startShepherd(String animalName){
        List<Tile> neigh = player.getNeighborTiles(game);

        for (Tile tile : neigh) {
            Animal animal = tile.getPlacable(Animal.class);
            if (animal == null)
                continue;

            if (animal.getName().equals(animalName)) {
                animal.shepherd();
                tile.setPlacable(null);
                player.setShepherdingAnimal(animal);
                return Result.success(null);
            }
        }

        return Result.failure(GameError.ANIMAL_NOT_FOUND);
    }

    public Result<Void> endShepherd(String animalName){
        Tile tile = player.getMap().getTile(player.getCoord());
        tile.setPlacable(player.getShepherdingAnimal());
        player.setShepherdingAnimal(null);
        return Result.success(null);
    }

    public Result<Void> feedHay(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Animal animal = player.getAnimalByName(name);
        if (animal == null)
            return Result.failure(GameError.ANIMAL_NOT_FOUND);

        animal.setFeedToday(true);
        return Result.success(null);
    }

    public Result<ArrayList<String>> showAnimalProduces() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        for (Animal animal : player.getAnimals()) {
            output.add(animal.getName() + "products : ");
            if (animal.getTodayProduct() != null) {
                output.add("name : " + animal.getName());
                output.add("product name : " + animal.getTodayProduct());
                double quality = ( (double) animal.getFriendship() / 1000) * (0.5 + 0.5 * Math.random());
                output.add("product quality : " + String.format("%f" , quality));
                if(quality < 0.5)
                    output.add("product quality : PRIMITIVE");
                else if(quality < 0.7)
                    output.add("product quality : SILVER");
                else if(quality < 0.9)
                    output.add("product quality : GOLD");
                else
                    output.add("product quality : IRIDIUM");

            }
        }
        return Result.success(output);
    }

    public Result<Item> collectProducts(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Animal animal = player.getAnimalByName(name);
        if (animal == null)
            return Result.failure(GameError.ANIMAL_NOT_FOUND);

        if (animal.collectProduce() == null)
            return Result.failure(GameError.ANIMAL_DOES_NOT_HAVE_PRODUCT);

        if (animal.getAnimalType().equals(AnimalTypes.COW)) {
            if (player.getInventory().canRemoveItem(new MilkPail())) {
                player.getInventory().addItem(animal.collectProduce());
                animal.setFriendship(animal.getFriendship() + 5);
                animal.setTodayProduct(null);
            } else
                return Result.failure(GameError.NOT_ENOUGH_ITEM);
        } else if (animal.getAnimalType().equals(AnimalTypes.GOAT)) {
            if (player.getInventory().canRemoveItem(new MilkPail())) {
                player.getInventory().addItem(animal.collectProduce());
                animal.setFriendship(animal.getFriendship() + 5);
                animal.setTodayProduct(null);
            } else
                return Result.failure(GameError.NOT_ENOUGH_ITEM);
        } else if (animal.getAnimalType().equals(AnimalTypes.SHEEP)) {
            if (player.getInventory().canRemoveItem(new Shear())) {
                player.getInventory().addItem(animal.collectProduce());
                animal.setFriendship(animal.getFriendship() + 5);
                animal.setTodayProduct(null);
            } else
                return Result.failure(GameError.NOT_ENOUGH_ITEM);
        } else if (animal.getAnimalType().equals(AnimalTypes.RABBIT)) {
            if (player.getInventory().canRemoveItem(new Shear())) {
                player.getInventory().addItem(animal.collectProduce());
                animal.setFriendship(animal.getFriendship() + 5);
                animal.setTodayProduct(null);
            } else
                return Result.failure(GameError.NOT_ENOUGH_ITEM);
        } else {
            player.getInventory().addItem(animal.collectProduce());
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
        Inventory inventory = player.getInventory();
        if (!inventory.canRemoveItem(item)) return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        ShippingBin shippingBin = null;
        for (Tile tile : player.getNeighborTiles(game))
            if (tile.getPlacable(ShippingBin.class) != null)
                shippingBin = tile.getPlacable(ShippingBin.class);
        if (shippingBin == null)
            return Result.failure(GameError.YOU_ARE_DISTANT);

        inventory.removeItem(item);
        shippingBin.add(item, player);
        return Result.success(null);
    }

    public Result<Void> sellAnimal(String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        List<Tile> neigh = player.getNeighborTiles(game);

        for (Tile tile : neigh) {
            Animal animal = tile.getPlacable(Animal.class);
            if (animal == null)
                continue;

            if (animal.getName().equals(name)) {
                Item price = Item.build("coin", (int) (animal.getPrice() * ((double) animal.getFriendship() / 1000 + 0.3)));
                player.getAnimals().remove(animal);
                player.getInventory().addItem(price);
                tile.setPlacable(null);
                tile.loadOnTileTexture();
            }
        }

        return Result.success(null);
    }

    public Result<Void> fishing(String fishingPole) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        boolean isNearLake = false;
        for (Tile tile : player.getNeighborTiles(game)) {
            if (tile.getPlacable(Lake.class) != null)
                isNearLake = true;
        }
        if (!isNearLake)
            return Result.failure(GameError.YOU_ARE_NOT_NEAR_TO_LAKE);

        if (Item.build(fishingPole, 1) == null)
            return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);

        if (!player.getInventory().canRemoveItem(Objects.requireNonNull(Item.build(fishingPole, 1))))
            return Result.failure(GameError.TOOL_NOT_FOUND);

        player.setSkillExp(SkillType.FISHING, player.getSkillExp(SkillType.FISHING) + 5);
        double amount = Math.random() * (player.getSkillExp(SkillType.FISHING) + 2);
        if (game.getWeather().equals(Weather.SUNNY))
            amount *= 1.5;
        if (game.getWeather().equals(Weather.RAINY))
            amount *= 1.2;
        if (game.getWeather().equals(Weather.STORM))
            amount *= 0.5;
        if (fishingPole.equals("training")) {
            player.getInventory().addItem(Item.build(FishingPole.getCheapestFish(game), Math.min(6, (int) amount)));
            return Result.success(null);
        } else if (fishingPole.equals("bamboo") || fishingPole.equals("iridium") || fishingPole.equals("fiberglass")) {
            player.getInventory().addItem(Item.build(FishingPole.randomFish(game, player), Math.min(6, (int) amount)));
            return Result.success(null);
        }
        return Result.success(null);
    }

    public Result<Void> placeArtisan(String artisanName, Direction direction) {
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
        new Artisan(ArtisanGoodsData.getRecipeData(artisanName), player.getInventory()).onPlace(tile);
        tile.setPlacableLoc();
        return Result.success(null);
    }

    public Result<Void> placeArtisan(Coord coord, String artisanName) {
        Inventory inventory = player.getInventory();
        Item artisan = Item.build(artisanName, 1);
        Tile tile = player.getMap().getTile(coord);

        if (artisan == null)
            return Result.failure(GameError.CANT_FIND_ITEM_IN_INVENTORY);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (!tile.isEmpty()) {
            return Result.failure(GameError.TILE_IS_NOT_EMPTY);
        }

        inventory.removeItem(artisan);
        new Artisan(ArtisanGoodsData.getRecipeData(artisanName), player.getInventory()).onPlace(tile);
        tile.setPlacableLoc();
        return Result.success(null);
    }

    public Result<Void> useArtisan(Artisan artisan, String resultName, java.util.Map <String, Integer> itemList) {
        ArrayList<Item> requiredItems = new ArrayList<>();
        for (String ing: itemList.keySet())
            requiredItems.add(Item.build(ing, itemList.get(ing)));
        if (player.getInventory().canRemoveItems(requiredItems)) {
            player.getInventory().removeItems(requiredItems);
            artisan.startProcess(resultName);
        }
        else
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        return Result.success(null);
    }
  
    public void enterBuilding(String buildingName) {
        Building building = player.getMap().getBuildingByName(buildingName);
        player.enterBuilding(building);
    }

    public Result<Void> useArtisan(String artisanName, ArrayList<String> itemNames) {

        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Inventory inventory = player.getInventory();

        Result<Void> result = null;

        for (Direction direction : Direction.values()) {
            Coord coord = player.getCoord().addCoord(direction.getCoord());

            Tile tile = player.getMap().getTile(coord);
            if (tile.getTileType() == TileType.ARTISAN && tile.getPlacable(Artisan.class).getName().equalsIgnoreCase(artisanName) && !tile.getPlacable(Artisan.class).isResultReady()) {
                Artisan artisan = tile.getPlacable(Artisan.class);

                result = artisan.craft(itemNames, player.getInventory());
                if (result.isSuccess())
                    return result;
            }
        }

        if (result != null)
            return result;

        return Result.failure(GameError.NO_FREE_ARTISAN_AROUND);
    }

    public Result<Item> getArtisanProduct(Artisan artisan) {
        if (artisan.isResultReady()) {
            Item result = artisan.getResultWithoutReset();
            if (player.getInventory().addItem(result).isSuccess()) {
                artisan.getResult(player.getInventory());
                return Result.success(null);
            }
            return Result.failure(GameError.CANT_ADD_ITEM_TO_INVENTORY);
        }
        return Result.failure(GameError.NO_READY_ARTISAN_AROUND);
    }

    public Result<Item> getArtisanProduct(String artisanName) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        for (Direction direction : Direction.values()) {
            Coord coord = player.getCoord().addCoord(direction.getCoord());
            Tile tile = player.getMap().getTile(coord);
            if (tile == null) continue;
            if (tile.getTileType() == TileType.ARTISAN && tile.getPlacable(Artisan.class).isResultReady() && tile.getPlacable(Artisan.class).getName().equalsIgnoreCase(artisanName)) {
                Artisan artisan = tile.getPlacable(Artisan.class);
                return getArtisanProduct(artisan);
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
        player.setMap(game.getVillage());
        player.setCoord(new Coord(0, 0));
        player.getSprite().setX(player.getMap().mapType.getDistanceX());
        player.getSprite().setY(player.getMap().mapType.getDistanceY() + (player.getMap().getMaxY() - 1)*30);
        return Result.success("Now you are in village");
    }

    public Result<Void> backToHome() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (player.getMap().mapType != MapType.VILLAGE)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_VILLAGE);
        player.setMap(player.getDefaultMap());
        player.setCoord(new Coord(0, 0));
        player.getSprite().setX(player.getMap().mapType.getDistanceX());
        player.getSprite().setY(player.getMap().mapType.getDistanceY() + (player.getMap().getMaxY() - 1)*30);
        return Result.success("Now you are home");
    }

    public Result<ArrayList<String>> showFriendships() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        output.add(player.getUser().getUsername() + "'s friendships with other players:");
        for (Relation relation : game.getMyRelations(player))
            output.add(relation.printRelation(player));
        return Result.success(output);
    }

    public String showFriendship(String otherPlayerUsername){
        for(Relation relation : game.getMyRelations(player)){
            if(relation.getPlayer1().getUser().getUsername().equals(otherPlayerUsername) ||
                    relation.getPlayer2().getUser().getUsername().equals(otherPlayerUsername)){
                return relation.printRelation(player);
            }
        }
        return null;
    }

    public Result<Void> talk(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player1 = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        if(!player.weAreNextToEachOther(player1))
            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(player1, player);
        if (!relation.isTodayTalk())
            relation.setFriendshipXP(relation.getFriendshipXP() + 20);
        if(relation.getLevel().getLevel() == 4){
            player1.setEnergy(player1.getEnergy() + 50);
            player.setEnergy(player.getEnergy() + 50);
        }
//        player1.addNotifications(player.getUser().getUsername() + " sends you a message");
        relation.setTodayTalk(true);
        return Result.success(null);
    }

    public Result<ArrayList<String>> talkHistory(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();

        if (player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Relation relation = game.getRelationOfUs(player, player);
        output.add("your chats with " + username + " :");
        output.addAll(relation.getTalks());

        return Result.success(output);

    }

    public Result<Void> sendGift(String username, String itemName, int amount) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

//        if (player.getUser().getUsername().equals(username))
//            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player1 = game.getPlayerByName(username);
        if (player1 == null) return Result.failure(GameError.NO_PLAYER_FOUND);
//
//        if(!player.weAreNextToEachOther(player1))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(player, player1);
        if (relation.getLevel().getLevel() == 0)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        Item item = Item.build(itemName, amount);
        if (!player.getInventory().canRemoveItem(item))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        player.getInventory().removeItem(item);
        player1.getInventory().addItem(item);
        relation.addGift(new Gift(player , player1 , item , relation.getGifts().size()));
        if(relation.getLevel().getLevel() == 4){
            player1.setEnergy(player1.getEnergy() + 50);
            player.setEnergy(player.getEnergy() + 50);
        }
        player1.addNotifications(player.getUser().getUsername() + " sends you a gift");
        return Result.success(null);
    }

    //    What's its difference with gift history :/
    public Result<ArrayList<String>> receivedGiftList() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();
        for(Player player1 : game.getPlayers()){
            if(player.equals(player1))
                continue;
            Relation relation = game.getRelationOfUs(player1, player);
            for(Gift gift : relation.getGifts()){
                if(gift.getReceiver().equals(player)) {
                    StringBuilder string = new StringBuilder();
                    string.append("GiftID : ").append(gift.getId()).append(" | ");
                    string.append("Sender : ").append(gift.getSender().getUser().getUsername()).append(" | ");
                    string.append("Item : ").append(gift.getItem().getName()).append(" | ");
                    string.append("Receiver : ").append(gift.getReceiver().getUser().getUsername()).append(" | ");
                    if (gift.getRate() == 0)
                        string.append("Rate : not rated yet!");
                    else
                        string.append("Rate : ").append(gift.getRate());
                    output.add(string.toString());
                }
            }
        }
        return Result.success(output);
    }

    public Result<ArrayList<String>> allGifts(){
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();
        for(Player player1 : game.getPlayers()){
            if(player.equals(player1))
                continue;
            Relation relation = game.getRelationOfUs(player1, player);
            for(Gift gift : relation.getGifts()){
                StringBuilder string = new StringBuilder();
                string.append("Item : ").append(gift.getItem().getName()).append(" | ");
                string.append("Sender : ").append(gift.getSender().getUser().getUsername()).append(" | ");
                string.append("Receiver : ").append(gift.getReceiver().getUser().getUsername()).append(" | ");
                if (gift.getRate() == 0)
                    string.append("Rate : not rated yet!");
                else
                    string.append("Rate : ").append(gift.getRate());
                output.add(string.toString());
            }
        }
        return Result.success(output);
    }

    public Result<Void> giftRate(String username, int giftID, double rate) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player1 = game.getPlayerByName(username);
        if (player1 == null) return Result.failure(GameError.NO_PLAYER_FOUND);


        Relation relation = game.getRelationOfUs(player1, player);
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

        if (player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player1 = game.getPlayerByName(username);
        if (player1 == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Relation relation = game.getRelationOfUs(player1, player);
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

        if (player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player1 = game.getPlayerByName(username);
        if (player1 == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!player.weAreNextToEachOther(player1))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(player1, player);

        if (relation.getLevel().getLevel() < 2)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        if (!relation.isTodayHug())
            relation.setFriendshipXP(relation.getFriendshipXP() + 60);
        if(relation.getLevel().getLevel() == 4){
            player1.setEnergy(player1.getEnergy() + 50);
            player.setEnergy(player.getEnergy() + 50);
        }
//        player1.setEnergy(0);
        relation.checkOverFlow();
        return Result.success(null);
    }

    public Result<Void> sendFlower(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player1 = game.getPlayerByName(username);
        if (player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!player.weAreNextToEachOther(player1))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(player, player1);
        if (relation.getLevel().getLevel() == 0)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        Item item = Item.build("Bouquet", 1);
        if (!player.getInventory().canRemoveItem(item))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        player.getInventory().removeItem(item);
        player1.getInventory().addItem(item);
//        player1.setEnergy(0);
        relation.setFlower(true);
        if(relation.getLevel().getLevel() == 4){
            player.setEnergy(player.getEnergy() + 50);
            player1.setEnergy(player1.getEnergy() + 50);
        }
        relation.checkOverFlow();
//        StringBuilder stringBuilder = new StringBuilder();
//        for(Item item1 : player1.getInventory().getItems()){
//            stringBuilder.append(item1.getName()).append(" ").append(item1.getAmount()).append("\n");
//        }
//        throw new RuntimeException(stringBuilder.toString());
        return Result.success(null);
    }

    public Result<Void> askMarriage(String username) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player1 = game.getPlayerByName(username);
        if (player1 == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!player1.weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = game.getRelationOfUs(player1, player);
        if (relation.getLevel().getLevel() < 3)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);
        if (relation.getFriendshipXP() < 300)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        Item ring = Item.build("wedding ring", 1);
        assert ring != null;
        if (!player.getInventory().canRemoveItem(ring))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        if (player.getUser().getGender().equals(Gender.FEMALE))
            return Result.failure(GameError.YOU_ARE_GIRL);
        if (player1.getUser().getGender().equals(Gender.MALE))
            return Result.failure(GameError.YOUR_WIFE_CAN_NOT_BE_A_BOY);

        player1.setSuitor(player.getUser().getUsername());
        player1.addNotifications("Would you marry " + player.getUser().getUsername() + "?");
        return Result.success(null);
    }

    public Result<Void> marriageResponse(String response){
        Player sender = game.getPlayerByName(player.getSuitor());
        Relation relation = game.getRelationOfUs(player , sender);
        if (response.equalsIgnoreCase("no")) {
            relation.setFriendshipXP(0);
            relation.setLevel(FriendshipLevel.LEVEL0);
            relation.setFlower(false);
            player.setMaxEnergy(100 , 7);
            sender.addNotifications("Geryeh kon aghab mande, friend zone shodi");
            return Result.success(null);
        }

        relation.setLevel(FriendshipLevel.LEVEL4);
        Item ring = Item.build("wedding ring" , 1);
        sender.getInventory().removeItem(ring);
        player.getInventory().addItem(ring);
        Item coin = Item.build("coin", player.getCoins() + player.getCoins());
        player.getInventory().removeItem(Item.build("coin", player.getCoins()));
        sender.getInventory().removeItem(Item.build("coin", player.getCoins()));
        player.getInventory().addItem(coin);
        sender.getInventory().addItem(coin);
        sender.addNotifications("dige rafti ghati morgha");
        return Result.success(null);
    }

    public Result<Void> meetNPC(String NPCName , String message) {
        NPC npc = game.getNPCByName(NPCName);
        if (npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);

        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(player);
        if (!npcFriendship.isTodayMeet())
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 20);
        npcFriendship.setTodayMeet(true);
        npc.talk(NetworkLLMController.generateMsg(message, game.getInfo(), npc.getInfo()));
        return Result.success(null);

    }

    public Result<Void> resetNPCResponse(String NPCName){
        NPC npc = game.getNPCByName(NPCName);
        npc.setResponseToMessage(null);
        npc.setCloudSprite(null);
        return Result.success(null);
    }

    public Result<Void> giftNPC(String NPCName, String item, int amount) {
        NPC npc = game.getNPCByName(NPCName);
        if (npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);

        if (!player.getInventory().canRemoveItem(Item.build(item, amount)))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        if (Item.build(item, 1) instanceof Tool)
            return Result.failure(GameError.YOU_CANT_GIFT_A_TOOL);

        player.getInventory().removeItem(Item.build(item, amount));
        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(player);
        if (npcFriendship.isTodayGift())
            return Result.success(null);

        npcFriendship.setTodayGift(true);
        if (npc.isMyFavoriteItem(Item.build(item, amount)))
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 200);
        else
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 50);
        npc.setGiftAnimation();
        return Result.success(null);
    }

    public Result<ArrayList<String>> friendShipNPCList() {
        ArrayList<String> output = new ArrayList<>();
        output.add("NPC FRIENDSHIPS : ");
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        for (NPC npc : game.getNpcs()) {
            for (NPCFriendship npcFriendship : npc.getFriendships()) {
                if (npcFriendship.getPlayer().equals(player)) {
                    String stringBuilder = "NPC name: " + npc.getName() + " | " +
                            "Friendship XP: " + npcFriendship.getFriendshipXP() + " | " +
                            "Friendship Level: " + npcFriendship.getLevel().getLevel();
//                    if (npcFriendship.isTodayMeet())
//                        output.add("last seen recently");
//                    else
//                        output.add("last seen a long time ago");
//                    if (npcFriendship.isTodayGift())
//                        output.add("last gift recently");
//                    else
//                        output.add("last gift a long time ago");
//                    output.add("-----------------");
                    output.add(stringBuilder);
                }
            }
        }
        return Result.success(output);
    }

    public Result<ArrayList<String>> showQuestList(String NPCName) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();
        NPC npc = game.getNPCByName(NPCName);
        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(player);
        for (int i = 0; i < Math.min(npcFriendship.getLevel().getLevel() + 1, 3); i++) {
            String stringBuilder = (i) + ": " +
                    "Request Item: " + npc.getTasks().get(i).getRequestItem() +
                    " :" + npc.getTasks().get(i).getRequestAmount() + " | " +
                    "Reward Item: " + npc.getTasks().get(i).getRewardItem() +
                    " :" + npc.getTasks().get(i).getRewardAmount();
            output.add(stringBuilder);
        }
        return Result.success(output);
    }

    public Result<Item> finishQuest(String NPCName, int questID) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        NPC npc = game.getNPCByName(NPCName);
        if (npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);
        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(player);

        if (questID > npcFriendship.getLevel().getLevel())
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);
        if(npc.getTasksFlag().get(questID))
            return Result.failure(GameError.QUEST_HAS_BEEN_FINISHED);

        Item requiredItem = Item.build(npc.getTasks().get(questID).getRequestItem(), npc.getTasks().get(questID).getRequestAmount());

        if (!player.getInventory().canRemoveItem(requiredItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEM);
        player.getInventory().removeItem(requiredItem);

        int amount = 1;
        if(npcFriendship.getLevel().getLevel() >= 2)
            amount =2;
        Item rewardItem = Item.build(npc.getTasks().get(questID).getRewardItem(), npc.getTasks().get(questID).getRewardAmount() * amount);

        player.getInventory().addItem(rewardItem);
        npc.getTasksFlag().set(questID, true);
        player.addQuest();
        npc.setQuestAnimation();
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
        if (player.getBuilding() != null)
            return player.getBuilding().toString();
        return player.getMap().mapType.name();
    }

    public String whoAmI() {
        if (game == null) return "No game running";
        return player.toString();
    }

    public String getTileStatus(Coord coord) {
        if (game == null) return "No game running";
        return player.getMap().getTile(coord).toString();
    }

    public String getMapStatus() {
        if (game == null) return "No game running";
        printMapFull();
        return player.getMap().toString();
    }

    public String getInventoryStatus() {
        if (game == null) return "No game running";
        return player.getInventory().toString();
    }

    public Result<String> showAllShopProducts() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = player.getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        return Result.success(shop.showProducts());
    }

    public Result<String> showAvailableShopProducts() {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = player.getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        return Result.success(shop.showAvailableItems(game));
    }

    public Result<Void> purchase(String name, int number) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = player.getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        Inventory inventory = player.getInventory();
        Result<Void> r = shop.prepareBuy(name, number, inventory, game, player);
        if (r.isError()) return r;
        return Result.success(null);
    }

    public Result<Void> purchaseAnimal(String animalName, String name) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        for(Animal animal : player.getAnimals()){
            if(animal.getName().equals(name))
                return Result.failure(GameError.ANIMAL_NOT_FOUND);
        }
        Result<Void> r = purchase(animalName, 1);
        if (r.isError()) return r;
        assert AnimalData.getAnimalData(animalName) != null : "Animal " + animalName + " not found";
        Animal animal = new Animal(name, AnimalData.getAnimalData(animalName));
        List<AnimalHouse> animalHouse = player.getDefaultMap().getBuildings(AnimalHouse.class);
        for (AnimalHouse h : animalHouse) {
            if (animal.canEnterHouse(h.getFullName()) && animal.canEnterHouseType(h.getHouseType()) && h.hasSpace()) {
                h.add(animal);
                player.addAnimal(animal);
                player.leave();
                backToHome();
                enterBuilding(h.getFullName());
                return Result.success("Animal is in " + h.getFullName());
            }
        }
        return Result.failure(GameError.EMPTY_HOUSE_WAS_NOT_FOUND);
    }

    private Result<Void> purchasePack(String name) {
        Building building = player.getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Inventory inventory = player.getInventory();
        InventoryType iType = inventory.getInventoryType();
        Shop shop = (Shop) building;

        InventoryType newIType = null;
        if (name.equalsIgnoreCase("Large Pack")) {
            if (iType != InventoryType.PRIMITIVE)
                return Result.failure(GameError.YOU_ALREADY_BOUGHT_THIS_ITEM);
            newIType = InventoryType.BIG;
        }
        else if (name.equalsIgnoreCase("Deluxe Pack")) {
            if (iType == InventoryType.PRIMITIVE)
                return Result.failure(GameError.BUY_LARGE_FIRST);
            else if (iType == InventoryType.UNLIMITED)
                return Result.failure(GameError.YOU_ALREADY_BOUGHT_THIS_ITEM);
            newIType = InventoryType.UNLIMITED;
        }
        else {
            return Result.failure(GameError.NOT_FOUND);
        }
        Result<Void> r = shop.prepareBuy(name, 1, inventory, game, player);
        if (r.isError()) return r;
        inventory.upgradeSize(newIType);

        return Result.success(null);
    }

    public Result<Void> purchaseItem(String name, int number) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if (name.contains("Pack")) {
            Result<Void> r = purchasePack(name);
            if (r.isError())
                return r;
            return Result.success(null);
        }
        Result<Void> r = purchase(name, number);
        if (r.isError()) return r;

        Item resultItem = Item.build(name, number);
        if (resultItem.getItemType() == ItemType.RECIPE)
            player.addRecipes((Recipe) resultItem);
        else
            player.getInventory().addItem(resultItem);
        return Result.success(null);
    }

    public Result<Void> purchaseBuilding(String name, Coord coord) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Result<Void> r = purchase(name, 1);
        if (r.isError()) return r;

        MapType mapType = MapType.getMapType(name);

        Map map = player.getDefaultMap();
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
        player.addCoins(number);
    }

    public boolean isFainted() {
        if (game == null) return false;
        return player.isFainted();
    }

    public Result<Void> setFriendship(String username , int xp){
        Player player1 = game.getPlayerByName(username);
        Relation relation = game.getRelationOfUs(player1 , player);
        relation.setFriendshipXP(xp);
        return Result.success(null);
    }

    public Result<ArrayList<String>> showSkills() {
        ArrayList<String> skills = new ArrayList<>();
        skills.add("FARMING : " + player.getSkillExp(SkillType.FARMING));
        skills.add("FORAGING : " + player.getSkillExp(SkillType.FORAGING));
        skills.add("FISHING : " + player.getSkillExp(SkillType.FISHING));
        skills.add("MINING : " + player.getSkillExp(SkillType.MINING));
        return Result.success(skills);
    }

    public Result<Void> goToFarm(String user) {
        if (game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (user.equals(player.getUser().getUsername())) {
            return backToHome();
        }
        if (player.getMap().mapType != MapType.VILLAGE)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_VILLAGE);
        Relation r = game.getRelationOfUs(player, game.getPlayerByName(user));
        if (r == null) return Result.failure(UserError.USER_NOT_FOUND);
        if (r.getLevel() != FriendshipLevel.LEVEL4)
            return Result.failure(GameError.THIS_FARM_DOESNT_BELONG_TO_YOU);
        player.setMap(game.getPlayerByName(user).getDefaultMap());
        player.setCoord(new Coord(0, 0));
        return Result.success(null);
    }

    public Result<ArrayList<String>> showNotifications(){
        ArrayList<String> output = new ArrayList<>(player.getNotifications());
        player.resetNotifications();
        return Result.success(output);
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public void handleToolMove(int screenX , int screenY) {
        if (player.getItemInHand() instanceof Tool) {
            Tool tool = (Tool) player.getItemInHand();
            tool.handleRotation(screenX , Gdx.graphics.getHeight() - screenY);
        }
    }

    /*
     * TRADES FROM HERE
     */

    public Result<Void> tradeWithMoney(String username , String type , String item , int amount , int price) {
        if(player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player trader = game.getPlayerByName(username);
        if(trader == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Item thisItem = Item.build(item , amount);
        assert thisItem != null;
        if(type.equals("offer") && !player.getInventory().canRemoveItem(thisItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        Relation relation = game.getRelationOfUs(player, trader);
        if(type.equals("offer")) {
            relation.addTrade(new Trade(relation.getTrades().size(), player, trader, 0 ,
                    thisItem , price , null , TradeType.OFFER_MONEY));
        }
        else{
            relation.addTrade(new Trade(relation.getTrades().size(), player, trader, price ,
                    Item.build("coin" , price) , 0 , thisItem , TradeType.REQUEST_MONEY));
        }

        return Result.success(null);

    }

    public Result<Void> tradeWithItem(String username , String type , String item1 , int amount1 , String item2 , int amount2) {
        if(player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player trader = game.getPlayerByName(username);
        if(trader == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Item firstItem = Item.build(item1 , amount1);
        Item secondItem = Item.build(item2 , amount2);
        if(type.equals("offer") && !player.getInventory().canRemoveItem(firstItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        if(type.equals("request") && !player.getInventory().canRemoveItem(secondItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        Relation relation = game.getRelationOfUs(player, trader);
        if(type.equals("offer")) {
            relation.addTrade(new Trade(relation.getTrades().size(), player, trader, 0 ,
                    firstItem , 0 , secondItem , TradeType.OFFER_ITEM));
        }
        else{
            relation.addTrade(new Trade(relation.getTrades().size(), player, trader, 0 ,
                    secondItem , 0 , firstItem , TradeType.REQUEST_ITEM));
        }

        return Result.success(null);

    }

    public Result<ArrayList<String>> tradeList(){
        ArrayList<String> output = new ArrayList<>();
        for(Player trader : game.getPlayers()){
            if(trader.equals(player))
                continue;
            Relation relation = game.getRelationOfUs(player, trader);
            for(Trade trade : relation.getTrades()){
                if(trade.isResponsed())
                    continue;
                output.add("Sender : " + trade.getSender().getUser().getUsername());
                output.add("Receiver : " + trade.getReceiver().getUser().getUsername());
                output.add("ID : " + trade.getID());
                if(TradeType.OFFER_ITEM.equals(trade.getTradeType())) {
                    output.add("Offered Item : " + trade.getOfferItem().getName() + " " + trade.getOfferItem().getAmount());
                    output.add("Requested Item : " + trade.getRequestItem().getName() + " " + trade.getRequestItem().getAmount());
                }
                if(TradeType.REQUEST_ITEM.equals(trade.getTradeType())) {
                    output.add("Requested Item : " + trade.getRequestItem().getName() + " " + trade.getRequestItem().getAmount());
                    output.add("Offered Item : " + trade.getOfferItem().getName() + " " + trade.getOfferItem().getAmount());
                }
                if(TradeType.OFFER_MONEY.equals(trade.getTradeType())) {
                    output.add("Offered Item : " + trade.getOfferItem().getName() + " " + trade.getOfferItem().getAmount());
                    output.add("Requested Price : " + trade.getRequestPrice());
                }
                if(TradeType.REQUEST_MONEY.equals(trade.getTradeType())) {
                    output.add("Requested Item : " + trade.getRequestItem().getName() + " " + trade.getRequestItem().getAmount());
                    output.add("Offered Price : " + trade.getOfferPrice());
                }
                output.add("----------");
            }
        }
        return Result.success(output);
    }

    public Result<Void> tradeResponse(String username , String response , int ID){
        if(player.getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player trader = game.getPlayerByName(username);
        if(trader == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        if((!response.equals("accept") && !response.equals("reject")))
            return Result.failure(GameError.RESPONSE_IS_NOT_SUPPORTED);

        Relation relation = game.getRelationOfUs(player, trader);
        if(ID >= relation.getTrades().size())
            return Result.failure(GameError.TRADE_ID_DOES_NOT_EXIST);

        Trade trade = relation.getTrades().get(ID);

        if(response.equals("reject")){
            trade.setResponse(false);
            trade.setResponsed(true);
            relation.setFriendshipXP(relation.getFriendshipXP() - 30);
            return Result.success(null);
        }
        if(trade.getTradeType().equals(TradeType.OFFER_ITEM)){
            if(!trader.getInventory().canRemoveItem(trade.getOfferItem()) || !player.getInventory()
                    .canRemoveItem(trade.getRequestItem())){
                trade.setResponse(false);
                trade.setResponsed(true);
                relation.setFriendshipXP(relation.getFriendshipXP() - 30);
                return Result.failure(GameError.NOT_ENOUGH_ITEMS);
            }
            trader.getInventory().removeItem(trade.getOfferItem());
            player.getInventory().addItem(trade.getOfferItem());
            player.getInventory().removeItem(trade.getRequestItem());
            trader.getInventory().addItem(trade.getRequestItem());
            trade.setResponse(true);
            trade.setResponsed(true);
        }

        if(trade.getTradeType().equals(TradeType.REQUEST_ITEM)){
            if(!trader.getInventory().canRemoveItem(trade.getRequestItem()) || !player.getInventory()
                    .canRemoveItem(trade.getOfferItem())){
                trade.setResponse(false);
                trade.setResponsed(true);
                relation.setFriendshipXP(relation.getFriendshipXP() - 30);
                return Result.failure(GameError.NOT_ENOUGH_ITEMS);
            }
            trader.getInventory().removeItem(trade.getRequestItem());
            player.getInventory().addItem(trade.getRequestItem());
            player.getInventory().removeItem(trade.getOfferItem());
            trader.getInventory().addItem(trade.getOfferItem());
            trade.setResponse(true);
            trade.setResponsed(true);
        }

        if(trade.getTradeType().equals(TradeType.OFFER_MONEY)){
            if(!trader.getInventory().canRemoveItem(trade.getOfferItem()) || (player.getInventory()
                    .getCoin().getAmount() < trade.getRequestPrice())){
                trade.setResponse(true);
                trade.setResponsed(true);
                return Result.failure(GameError.NOT_ENOUGH_ITEMS);
            }
            trader.getInventory().removeItem(trade.getOfferItem());
            player.getInventory().addItem(trade.getOfferItem());
            player.getInventory().changeCoin(-trade.getRequestPrice());
            trader.getInventory().changeCoin(trade.getRequestPrice());
            trade.setResponse(true);
            trade.setResponsed(true);
        }

        if(trade.getTradeType().equals(TradeType.REQUEST_MONEY)){
            if(!player.getInventory().canRemoveItem(trade.getRequestItem()) || (trader.getInventory()
                    .getCoin().getAmount() < trade.getOfferPrice())){
                trade.setResponse(true);
                trade.setResponsed(true);
                return Result.failure(GameError.NOT_ENOUGH_ITEMS);
            }
            trader.getInventory().changeCoin(-trade.getOfferPrice());
            player.getInventory().changeCoin(trade.getOfferPrice());
            player.getInventory().removeItem(trade.getRequestItem());
            trader.getInventory().addItem(trade.getRequestItem());
            trade.setResponse(true);
            trade.setResponsed(true);
        }
        relation.setFriendshipXP(relation.getFriendshipXP() + 50);
        return Result.success(null);

    }

    public Result<ArrayList<String>> tradeHistory() {
        ArrayList<String> output = new ArrayList<>();
        for(Player trader : game.getPlayers()){
            if(trader.equals(player))
                continue;
            Relation relation = game.getRelationOfUs(player, trader);
            for(Trade trade : relation.getTrades()){
                output.add("Sender : " + trade.getSender().getUser().getUsername());
                output.add("Receiver : " + trade.getReceiver().getUser().getUsername());
                output.add("ID : " + trade.getID());
                if(TradeType.OFFER_ITEM.equals(trade.getTradeType())) {
                    output.add("Offered Item : " + trade.getOfferItem().getName() + " " + trade.getOfferItem().getAmount());
                    output.add("Requested Item : " + trade.getRequestItem().getName() + " " + trade.getRequestItem().getAmount());
                }
                if(TradeType.REQUEST_ITEM.equals(trade.getTradeType())) {
                    output.add("Requested Item : " + trade.getRequestItem().getName() + " " + trade.getRequestItem().getAmount());
                    output.add("Offered Item : " + trade.getOfferItem().getName() + " " + trade.getOfferItem().getAmount());
                }
                if(TradeType.OFFER_MONEY.equals(trade.getTradeType())) {
                    output.add("Offered Item : " + trade.getOfferItem().getName() + " " + trade.getOfferItem().getAmount());
                    output.add("Requested Price : " + trade.getRequestPrice());
                }
                if(TradeType.REQUEST_MONEY.equals(trade.getTradeType())) {
                    output.add("Requested Item : " + trade.getRequestItem().getName() + " " + trade.getRequestItem().getAmount());
                    output.add("Offered Price : " + trade.getOfferPrice());
                }
                output.add("----------");
            }
        }
        return Result.success(output);
    }

    public void reactEmoji(Reaction.EmojiType emoji) {
        player.getReaction().showEmoji(emoji, 5);
    }

    public void reactText(String text) {
        player.getReaction().showText(text, 5);
    }

    public void setDefaultReaction(String defaultReaction) {
        player.setDefaultReaction(defaultReaction);
    }

    public void setReaction(String str) {
        player.getReaction().fromString(str);
    }


    public void setReactionToDeafult() {
        player.getReaction().fromString(player.getDefaultReaction());
    }

    /**
     * ummm IDK what to explain
     * this function here runs with NO PROXY (NO NETWORK)
     * and ALL CIENTS (IT'S GOOD FOR STH LIKE ADDING TIME ON REGULAR BASIS)
     */
    public void init() {
        CompletableFuture.runAsync(
                () -> game.advance(),
                CompletableFuture.delayedExecutor(30, TimeUnit.SECONDS)
        );
    }

    public void addMusic(MusicData musicData) {
        player.addMusic(musicData);
    }
}
