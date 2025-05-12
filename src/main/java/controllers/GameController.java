package controllers;

import models.App;
import models.Item.*;
import models.Menu;
import models.data.items.SeedData;
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
import views.menu.GameTerminalView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController{

    public Result<String> showCurrentMenu(){
        return Result.success("game", "");
    }


    public Result<Game> createGame(String username1 , String username2 , String username3) throws IOException {
        if((DataBaseController.findUserByUsername(username1) == null) || (DataBaseController.findUserByUsername(username2)
                == null) || (DataBaseController.findUserByUsername(username3) == null)) {
            return Result.failure(UserError.USER_NOT_FOUND);
        }

        ArrayList<User> users = new ArrayList<>();
        users.add(App.logedInUser);
        users.add(DataBaseController.findUserByUsername(username1));
        users.add(DataBaseController.findUserByUsername(username2));
        users.add(DataBaseController.findUserByUsername(username3));

        /* TODO (DEBUG PURPOSE)
        for(User user : users) {
            if(user.isInAGame())
                return Result.failure(AuthError.IN_GAME_USER);
        }
         */

        for(User user : users) {
            user.setInAGame(true);
            DataBaseController.editUser(user);
        }

        ArrayList<Player> players = new ArrayList<>();
        for(User user : users) {
            players.add(new Player(user));
        }

        MapBuilder mapBuilder = new MapBuilder();

        for(Player player : players) {
            Map map = mapBuilder.buildFarm();
            player.setMap(map);
            player.setDefaultMap(map);
        }

        Game game = new Game(players);
        App.game = game;

        return Result.success(game , "Game created");
    }

    public Result<Game> loadGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> exitGame() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //TODO
        //Save Game
        App.game = null;
        App.play = false;
        return Result.success("Exit Game ...");
    }

    public Result<Void> nextTurn() {
        if(App.game == null)
            return Result.failure(AuthError.GAME_NOT_CREATED);

        App.game.nextTurn();

        return Result.success("Now its " + App.game.getCurrentPlayer().getUser().getUsername() + "'s turn");

    }

    public Result<Void> terminateGame() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Integer> getTime() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getGameDate().getHour());
    }

    public Result<String> getDate() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(
                "Day " + App.game.getGameDate().getDay() + ", " +
                        App.game.getSeason()
                , ""
        );
    }

    public Result<String> getDateTime() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(
                "Day " + App.game.getGameDate().getDay() + ", " +
                        App.game.getSeason() + ", " +
                        "Time: " + App.game.getGameDate().getHourInDay() + ":00",
                ""
        );
    }

    public Result<String> getDayWeek() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getGameDate().getCurrentDayOfWeek(), "");
    }

    public Result<Void> advanceTimeCheat(int days, int hours) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.game.advanceTime(days, hours);
        return Result.success(null);
    }

    public Result<String> getSeasonName() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getSeason().name(), "");
    }

    public Result<Void> struckByThorCheat(Coord coord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (App.game.getCurrentPlayerMap().thor(coord))
            return Result.success("Thor was successful");
        return Result.failure(GameError.YOU_CANT_DO_ACTION);
    }

    public Result<Weather> getWeather() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getWeather());
    }

    public Result<Weather> getWeatherForecast() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getNextWeather());
    }

    public Result<Void> setWeatherCheat(Weather weather) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.game.setNextDayWeather(weather);
        return Result.success(null);
    }

    public Result<Building> buildGreenHouse() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        List<Item> needed = List.of(
                Item.build("coin", 1000),
                Item.build("wood", 500)
        );
        Inventory inv = App.game.getCurrentPlayer().getInventory();
        if (inv.canRemoveItemList(needed))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        inv.removeItemList(needed);
        App.game.getCurrentPlayerMap().getGreenHouses().setBuild(true);
        return Result.success("now you can use your greenhouse");
    }

    public Result<Void> walk(int x , int y) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Coord coord = new Coord(x, y);
        boolean isNeigh = App.game.getCurrentPlayer().getCoord().isNeighbor(coord);

        Player player = App.game.getCurrentPlayer();
        Tile tile = player.getMap().getTile(coord);

        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);

        if(tile.getTileType().isForaging())
            return Result.failure(GameError.CANT_STAND_ON_FORAGING);
        else if(tile.getTileType() == TileType.REFRIGERATOR)
            return Result.failure(GameError.CANT_STAND_ON_FRIDGE);
        else if(tile.getTileType() == TileType.LAKE)
            return Result.failure(GameError.CANT_STAND_ON_LAKE);
        else if(tile.getTileType() == TileType.DOOR) {
            if (!isNeigh) return Result.failure(GameError.YOU_ARE_DISTANT);
            if (player.getMap().mapType == MapType.SHOP)
                player.setMap(App.game.getVillage());
            else
                player.setMap(player.getDefaultMap());
            player.setCoord(new Coord(0, 0));
        }
        else if(tile.getPlacable(Building.class) != null) {
            if (!isNeigh) return Result.failure(GameError.YOU_ARE_DISTANT);
            Building building = tile.getPlacable(Building.class);
            if (building.canEnter()) {
                player.enterBuilding(building);
            }
            else return Result.failure(GameError.CANT_ENTER);
        }
        else {
            PathFinder pf = new PathFinder(player);
            List<PathFinder.PathStep> steps = pf.findPathTo(coord);

            if (steps == null) {
                return Result.failure(GameError.NO_PATH);
            }
            for (PathFinder.PathStep step : steps) {
                player.decreaseEnergy(step.energyCost());
                player.setCoord(step.coord());
                printMapFull();
                if (player.isFainted()) return Result.success(null);
            }
        }
        return Result.success(null);
    }

    public Result<Void> putItemInRefrigerator(Refrigerator refrigerator, String itemName) {
        Inventory inv = App.game.getCurrentPlayer().getInventory();
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
        Inventory inv = App.game.getCurrentPlayer().getInventory();
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
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Refrigerator r = null;
        for (Tile tile: App.game.getCurrentPlayer().getNeighborTiles())
            if (tile.getTileType() == TileType.REFRIGERATOR)
                r = tile.getPlacable(Refrigerator.class);
        if (r == null) return Result.failure(GameError.YOU_ARE_DISTANT);

        if (action.equals("put")) {
            return putItemInRefrigerator(r, itemName);
        }
        else if (action.equals("pick")) {
            return pickItemRefrigerator(r, itemName);
        }
        throw new RuntimeException("This can't happen, Please recheck the REGEX");
    }

    public void printMapFull() {
        GameTerminalView.printWithColor(printMap(0, 0, 50).getData());
    }

    public Result<ArrayList<String>> printMap(int x, int y , int size) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getCurrentPlayerMap().printMap(new Coord(x , y) , size));
    }

    public int showEnergy() {
        if (App.game == null) return -1;
        return App.game.getCurrentPlayer().getEnergy();
    }

    public Result<Void> helpReadingMap() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        GameTerminalView.helpReadingMap();
        return Result.success("Help Reading Map");
    }

    public Result<Void> setEnergyCheat(int energyValue) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.game.getCurrentPlayer().setEnergy(energyValue);
        return Result.success(null);
    }

    public Result<Energy> setEnergyUnlimited() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.game.getCurrentPlayer().setEnergy(Integer.MAX_VALUE);
        return Result.success(null);
    }

    public Result<ArrayList<String>> showInventory() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getCurrentPlayer().getInventory().showInventory());
    }

    public Result<Void> removeFromInventory(String itemName, int numberOfItems) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getCurrentPlayer().getInventory().removeItem(Item.build(itemName , numberOfItems)));
    }

    public Result<Tool> equipTool(String toolName) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if(App.game.getCurrentPlayer().getInventory().toolEquip(toolName)) {
            return Result.success("Now " + toolName + " is in your hands");
        }

        return Result.failure(GameError.TOOL_NOT_FOUND);
    }

    public Result<String> showCurrentTool() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if(App.game.getCurrentPlayer().getItemInHand() == null)
            return Result.success("nothing is in your hands");
        return Result.success(App.game.getCurrentPlayer().getItemInHand().getName());
    }

    public Result<ArrayList<String>> showAvailableTools() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getCurrentPlayer().getInventory().showAvailableTools());
    }

    public Result<Tool> upgradeTool(String toolName) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> useTool(String direction) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Direction d = Direction.getDirection(direction);
        if(d == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);

        Coord destinyTile = App.game.getCurrentPlayer().getCoord().addCoord(d.getCoord());

        if(!App.game.getCurrentPlayer().getItemInHand().getItemType().equals(ItemType.TOOL))
            return Result.failure(GameError.TOOL_NOT_FOUND);

        Tool tool = (Tool) App.game.getCurrentPlayer().getItemInHand();
        Result<Item> item = tool.use(destinyTile);
        if (item.isSuccess()) {
            App.game.getCurrentPlayer().getInventory().addItem(item.getData());
        }
        return item;
    }

    public Result<String> craftInfo(String craftName) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        SeedData seedData = SeedData.getData(craftName);
        if (seedData == null) {
            return Result.failure(GameError.SEED_NOT_FOUND);
        }
        return Result.success(seedData.toString());
    }

    public Result<Void> plantSeed(String seedName, Direction direction) { // After MAP
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Player player = App.game.getCurrentPlayer();
        Inventory inventory = player.getInventory();
        Item seed = inventory.getItem(seedName);
        if (seed == null || seed.getItemType() != ItemType.SEED)
            return Result.failure(GameError.SEED_NOT_FOUND);
        Coord coord = player.getCoord().addCoord(direction.getCoord());
        Tile tile = player.getMap().getTile(coord);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (tile.getTileType() != TileType.PLOWED)
            return Result.failure(GameError.PLANT_ON_PLOWED);
        inventory.removeItem(seed);
        ((Seed) seed).plant(tile);
        return Result.success(null);
    }

    public Result<String> showPlant(Coord coord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        Tile tile = App.game.getCurrentPlayer().getMap().getTile(coord);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (tile.getTileType() != TileType.PLANTED_SEED)
            return Result.failure(GameError.NOT_FOUND);
        PlantedSeed plantedSeed = tile.getPlacable(PlantedSeed.class);
        return Result.success(plantedSeed.seedData.toString());
    }

    public Result<Void> fertilizePlant(FertilizerType fertilizer, Coord cord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Integer> howMuchWater() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Tool tool = (Tool) App.game.getCurrentPlayer().getItemInHand();
        if (tool == null || tool.getToolType() != ToolType.WATERING_CAN) return Result.failure(GameError.TOOL_NOT_IN_HAND);
        WateringCan wateringCan = (WateringCan) tool;
        return Result.success(wateringCan.getCapacity());
    }

    public Result<Void> water(Coord coord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Map map = App.game.getCurrentPlayerMap();
        Tile tile = map.getTile(coord);
        tile.water();
        return Result.success(null);
    }

    public Result<List<Recipe>> showCraftingRecipes() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getCurrentPlayer().getRecipes(RecipeType.CRAFTING));
    }

    public Result<Void> prepareRecipe(String recipeName, RecipeType recipeType) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Recipe recipe = App.game.getCurrentPlayer().getRecipeByName(recipeName, recipeType);
        if (recipe == null) {
            return Result.failure(GameError.RECIPE_NOT_FOUND);
        }
        Inventory inventory = App.game.getCurrentPlayer().getInventory();
        if (!inventory.canAdd())
            return Result.failure(GameError.MAXIMUM_SIZE_EXCEEDED);
        if (!inventory.canRemoveItemList(recipe.getItems()))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        inventory.removeItemList(recipe.getItems());
        inventory.addItem(recipe.getResult());
        return Result.success(null);
    }

    public Result<Void> placeItem(Item item, Direction direction) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> addItemCheat(String itemName, int quantity) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.game.getCurrentPlayer().getInventory().addItem(Item.build(itemName, quantity));
        return Result.success(null);
    }

    public Result<Void> cookingRefrigerator(Consumable consumable) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Recipe>> showCookingRecipes() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getCurrentPlayer().getRecipes(RecipeType.COOKING));
    }

    public Result<Void> eat(Consumable food){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //some foods can give power to the user.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> buildBarnOrCoop(String buildingName , int x , int y) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if((x >= 46) || (y >= 26))
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        for(int i = y ; i < y+5 ; i++){
            for(int j = x ; j < x+5 ; j++){
                if(!App.game.getCurrentPlayerMap().getTile(new Coord(i, j)).isEmpty())
                    return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
            }
        }

        if(App.game.getCurrentPlayer().getInventory().getAmount("name") < 1000)
            return Result.failure(GameError.NOT_ENOUGH_COINS);
        //TODO
        return Result.failure(GameError.NOT_IMPLEMENTED);
    }

    public Result<Animal> buyAnimal(String animal , String name){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> pet(String name){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        List<Tile> neigh = App.game.getCurrentPlayer().getNeighborTiles();

        for (Tile tile : neigh) {
            Animal animal = tile.getPlacable(Animal.class);
            if(animal == null)
                continue;

            if(animal.getName().equals(name)){
                animal.pet();
                return Result.success(null);
            }
        }
        return Result.failure(GameError.ANIMAL_NOT_FOUND);
    }

    public Result<Void> cheatFriendship(String name , int amount){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        for(Animal animal : App.game.getCurrentPlayer().getAnimals()){
            if(animal.getName().equals(name)) {
                animal.setFriendship(amount);
                return Result.success("Now your friendship with " + name + " is " + animal.getFriendship());
            }
        }
        return Result.failure(GameError.ANIMAL_NOT_FOUND);

    }

    public Result<ArrayList<String>> showAnimals(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        output.add("Animals:");
        for(Animal animal : App.game.getCurrentPlayer().getAnimals()){
            output.add(animal.getName() + " - " + animal.getFriendship());
        }
        return Result.success(output);
    }

    public Result<Void> shepherdAnimals(String name , int x , int y) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Tile tile = App.game.getCurrentPlayerMap().getTile(new Coord(x, y));
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        if (!tile.isEmpty())
            return Result.failure(GameError.TILE_IS_NOT_EMPTY);

        Animal animal = App.game.getCurrentPlayer().getAnimalByName(name);
        if (animal == null)
            return Result.failure(GameError.ANIMAL_NOT_FOUND);


        animal.shepherd(); //TODO
        App.game.getCurrentPlayerMap().getTile(new Coord(x, y)).setPlacable(animal);

        return Result.success(name + " is on tile (" + x + ", " + y + ")");
    }

    public Result<Void> feedHay(String animalName){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> showAnimalProduces(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //this function should show Products with their quality
        //show which animals have unjamavari products
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> collectProducts(String animalName) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> sellAnimal(String animalName){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> fishing(String fishingPole){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> artisanUse(String artisanName , String item) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> artisanGet(String artisanName) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //this function should get products from artisans
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> crowAttack() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Item>> showAllproducts() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Item>> showAvailableProducts() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> purchase(Item product, int count) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        // What is the Result??

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> addMoneyCheat(int amount) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> sell(Item product, int count) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> goToVillage(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (App.game.getCurrentPlayerMap().mapType != MapType.FARM)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_FARM);
        App.game.getCurrentPlayer().setMap(App.game.getVillage());
        App.game.getCurrentPlayer().setCoord(new Coord(0,0));
        return Result.success("Now you are in village");
    }

    public Result<Void> backToHome(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if (App.game.getCurrentPlayerMap().mapType != MapType.VILLAGE)
            return Result.failure(GameError.YOU_SHOULD_BE_ON_VILLAGE);
        App.game.getCurrentPlayer().setMap(App.game.getCurrentPlayer().getDefaultMap());
        App.game.getCurrentPlayer().setCoord(new Coord(0,0));
        return Result.success("Now you are back to home");
    }

    public Result<ArrayList<String>> showFriendships(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        output.add(App.game.getCurrentPlayer().getUser().getUsername() + "'s friendships :");
        for(Relation relation : App.game.getMyRelations(App.game.getCurrentPlayer()))
            output.add(relation.printRelation(App.game.getCurrentPlayer()));
        return Result.success(output);
    }

    public Result<Void> talk(String username, String massage) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!App.game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        App.game.getRelationOfUs(App.game.getCurrentPlayer(), player).addTalk(App.game.getCurrentPlayer().getUser().
                getUsername() + ": " + massage);
        App.game.getRelationOfUs(App.game.getCurrentPlayer(), player).setFriendshipXP(App.game.getRelationOfUs(App.game
                .getCurrentPlayer(), player).getFriendshipXP() + 20);
        return Result.success(null);
    }

    public Result<ArrayList<String>> talkHistory(String username) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        output.add("your chats with " + username + " :");
        output.addAll(relation.getTalks());

        return Result.success(output);

    }

    public Result<Void> sendGift(String username , String itemName , int amount) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!App.game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        if(relation.getLevel().getLevel() == 0)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        List<Item> gift = new ArrayList<>();
//        Item item = new Item(itemName , null , 0 , amount); Previews version
        Item item = Item.build(itemName, amount);
        gift.add(item);
        if(!App.game.getCurrentPlayer().getInventory().canRemoveItemList(gift))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        App.game.getCurrentPlayer().getInventory().removeItemList(gift);
        player.getInventory().addItem(item);
        relation.addGift(new Gift(App.game.getCurrentPlayer() , player , item , relation.getGifts().size()));
        return Result.success(null);
    }

    //    What's its difference with gift history :/
    public Result<ArrayList<String>> giftList() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();
        for(Player player : App.game.getPlayers()){
            if(player.equals(App.game.getCurrentPlayer()))
                continue;
            output.addAll(giftHistory(player.getUser().getUsername()).getData());
        }
        return Result.success(output);
    }

    public Result<Void> giftRate(String username , int giftID , double rate) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        if((relation.getGifts().size() <= giftID) || (giftID < 0))
            return Result.failure(GameError.GIFT_ID_DOES_NOT_EXIST);

        if((rate > 5) || (rate < 1))
            return Result.failure(GameError.RATE_MUST_BE_POSITIVE);

        relation.getGifts().get(giftID).setRate(rate);
        relation.setFriendshipXP(relation.getFriendshipXP() + (int) ((rate - 3)*30 + 15));
        relation.checkOverFlow();
        return Result.success(null);
    }

    public Result<ArrayList<String>> giftHistory(String username) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        ArrayList<String> output = new ArrayList<>();

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        output.add("your gift history with " + username + " :");
        for(Gift gift : relation.getGifts()) {
            output.add("Gift ID : " + gift.getId());
            output.add("Item : " + gift.getItem().getName());
            output.add("Sender : " + gift.getSender().getUser().getUsername());
            output.add("Receiver : " + gift.getReceiver().getUser().getUsername());
            if(gift.getRate() == 0)
                output.add("Rate : not rated yet!");
            else
                output.add("Rate : " + gift.getRate());
            output.add("-------------------");
        }
        return Result.success(output);
    }

    public Result<Void> hug(String username) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!App.game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);

        if(relation.getLevel().getLevel() < 2)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        relation.setFriendshipXP(relation.getFriendshipXP() + 60);
        relation.checkOverFlow();
        return Result.success(null);
    }

    public Result<Void> sendFlower(String username) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!App.game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        if(relation.getLevel().getLevel() == 0)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        List<Item> gift = new ArrayList<>();
//        Item item = new Item("Bouquet", null , 0 , 1); Previews version
        Item item = Item.build("Bouquet", 1);
        gift.add(item);
        if(!App.game.getCurrentPlayer().getInventory().canRemoveItemList(gift))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        App.game.getCurrentPlayer().getInventory().removeItemList(gift);
        player.getInventory().addItem(item);
        relation.setFlower(true);
        relation.checkOverFlow();
        return Result.success(null);
    }

    public Result<Void> askMarriage(String username) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

//        if(!App.game.getCurrentPlayer().weAreNextToEachOther(player))
//            return Result.failure(GameError.NOT_NEXT_TO_EACH_OTHER);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        if(relation.getLevel().getLevel() < 3)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);
        if(relation.getFriendshipXP() < 4*30 + 15)
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        List<Item> ring = new ArrayList<>();
        Item item = Item.build("Ring", 1);
        ring.add(item);
        if(!App.game.getCurrentPlayer().getInventory().canRemoveItemList(ring))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        if(App.game.getCurrentPlayer().getUser().getGender().equals(Gender.FEMALE))
            return Result.failure(GameError.YOU_ARE_GIRL);
        if(player.getUser().getGender().equals(Gender.MALE))
            return Result.failure(GameError.YOUR_WIFE_CAN_NOT_BE_A_BOY);

        String response = GameTerminalView.getResponse(player);
        if(response.equals("no")){
            relation.setFriendshipXP(0);
            relation.setLevel(FriendshipLevel.LEVEL0);
            relation.setFlower(false);
            return Result.success("aaaajaaaab rasmieeeeeh rasme zamoooneeehhh");
        }

        relation.setLevel(FriendshipLevel.LEVEL4);
        App.game.getCurrentPlayer().getInventory().removeItemList(ring);
        player.getInventory().addItem(item);
        Item coin = Item.build("coin" , App.game.getCurrentPlayer().getCoins() + player.getCoins());
        App.game.getCurrentPlayer().getInventory().setCoins(coin);
        player.getInventory().setCoins(coin);
        return Result.success(null);
    }

    //this function is useful
//    public Result<Void> respondMarriage(Boolean respond, Player player) {
//        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    public Result<Void> startTrade() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.currentMenu = Menu.TradeMenu;
        return Result.success(null);
    }

    public Result<String> meetNPC(String NPCName) {
        NPC npc = App.game.getNPCByName(NPCName);
        if(npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);

        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(App.game.getCurrentPlayer());
        if(!npcFriendship.isTodayMeet())
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 20);
        npcFriendship.setTodayMeet(true);
        return Result.success(npcFriendship.talk(App.game.getGameDate().getSeason() , App.game.getGameDate().getHour()));

    }

    public Result<Void> giftNPC(String NPCName , String item , int amount) {
        NPC npc = App.game.getNPCByName(NPCName);
        if(npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);

        if(!App.game.getCurrentPlayer().getInventory().canRemoveItem(Item.build(item, amount)))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        if(Item.build(item , 1) instanceof Tool)
            return Result.failure(GameError.YOU_CANT_GIFT_A_TOOL);

        App.game.getCurrentPlayer().getInventory().removeItem(Item.build(item, amount));
        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(App.game.getCurrentPlayer());
        if(npcFriendship.isTodayGift())
            return Result.success(null);

        npcFriendship.setTodayGift(true);
        if(npc.isMyFavoriteItem(Item.build(item, amount)))
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 200);
        else
            npcFriendship.setFriendshipXP(npcFriendship.getFriendshipXP() + 50);
        return Result.success(null);
    }

//    public Result<Void> giftNPC(String NPCName , Item gift) {
//
//    }

    public Result<ArrayList<String>> friendShipNPCList(){
        ArrayList<String> output = new ArrayList<>();
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        for(NPC npc : App.game.getNpcs()){
            for(NPCFriendship npcFriendship : npc.getFriendships()){
                if(npcFriendship.getPlayer().equals(App.game.getCurrentPlayer())){
                    output.add("NPC name: " + npc.getName());
                    output.add("Friendship XP: " + npcFriendship.getFriendshipXP());
                    output.add("Friendship Level: " + npcFriendship.getLevel().getLevel());
                    if(npcFriendship.isTodayMeet())
                        output.add("last seen recently");
                    else
                        output.add("last seen a long time ago");
                    if(npcFriendship.isTodayGift())
                        output.add("last gift recently");
                    else
                        output.add("last gift a long time ago");
                    output.add("-----------------");
                }
            }
        }
        return Result.success(output);
    }

    public Result<ArrayList<String>> showQuestList(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        ArrayList<String> output = new ArrayList<>();
        for(NPC npc : App.game.getNpcs()){
            NPCFriendship npcFriendship = npc.getFriendshipByPlayer(App.game.getCurrentPlayer());
            output.add("NPC name: " + npc.getName());
            for(int i = 0 ; i < Math.min(npcFriendship.getLevel().getLevel() , 3) ; i++){
                output.add(i + ":");
                output.add("Request Item: " + npc.getTasks().get(i).getRequestItem());
                output.add("Request Amount: " + npc.getTasks().get(i).getRequestAmount());
                output.add("Reward Item: " + npc.getTasks().get(i).getRewardItem());
                output.add("Reward Amount: " + npc.getTasks().get(i).getRewardAmount());
            }
        }
        return Result.success(output);
    }

    public Result<Item> finishQuest(String NPCName , int questID){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        NPC npc = App.game.getNPCByName(NPCName);
        if(npc == null) return Result.failure(GameError.THIS_NPC_DOES_NOT_EXIST);
        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(App.game.getCurrentPlayer());

        if(questID > npcFriendship.getLevel().getLevel())
            return Result.failure(GameError.FRIENDSHIP_LEVEL_IS_NOT_ENOUGH);

        Item requiredItem = Item.build(npc.getTasks().get(questID).getRequestItem() , npc.getTasks().get(questID).getRequestAmount());

        if(!App.game.getCurrentPlayer().getInventory().canRemoveItem(requiredItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEM);
        App.game.getCurrentPlayer().getInventory().removeItem(requiredItem);

        Item rewardItem = Item.build(npc.getTasks().get(questID).getRewardItem() , npc.getTasks().get(questID).getRewardAmount());
        App.game.getCurrentPlayer().getInventory().addItem(rewardItem);

        return Result.success(null);

    }

    public boolean isGameLockedDueToNight() {
        return (App.game != null && App.game.getGameDate().getHour() == 22);
    }

    //This means that we did 1 work... (you should advance game by 1 hour)
    public void advance() {
        if (App.game == null) return;
        App.game.advance();
    }

    //DEBUG COMMANDS:

    public String whereAmI() {
        if (App.game == null) return "No game running";
        return App.game.getCurrentPlayerMap().mapType.name();
    }

    public String whoAmI() {
        if (App.game == null) return "No game running";
        return App.game.getCurrentPlayer().toString();
    }

    public String getTileStatus(Coord coord) {
        if (App.game == null) return "No game running";
        return App.game.getCurrentPlayerMap().getTile(coord).toString();
    }

    public String getMapStatus() {
        if (App.game == null) return "No game running";
        return App.game.getCurrentPlayerMap().toString();
    }

    public String getInventoryStatus() {
        if (App.game == null) return "No game running";
        return App.game.getCurrentPlayer().getInventory().toString();
    }

    public Result<String> showAllShopProducts() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = App.game.getCurrentPlayer().getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        return Result.success(shop.showProducts());
    }

    public Result<String> showAvailableShopProducts() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = App.game.getCurrentPlayer().getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        return Result.success(shop.showAvailableItems());
    }

    public Result<Void> purchaseItem(String name, int number) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Building building = App.game.getCurrentPlayer().getBuilding();
        if (!(building instanceof Shop)) return Result.failure(GameError.YOU_SHOULD_BE_ON_SHOP);
        Shop shop = (Shop) building;
        return shop.purchaseItem(name, number);
    }

    public void addDollarsCheat(int number) {
        if (App.game == null) return;
        App.game.getCurrentPlayer().addCoins(number);
    }

    public boolean isFainted() {
        if (App.game == null) return false;
        return App.game.getCurrentPlayer().isFainted();
    }
}