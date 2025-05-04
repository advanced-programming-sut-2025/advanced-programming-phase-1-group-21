package controllers;

import models.App;
import models.Tool.*;
import models.animal.Animal;
import models.animal.AnimalProducts;
import models.crop.*;
import models.game.*;
import models.map.*;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.result.errorTypes.GameError;
import models.result.errorTypes.UserError;
import models.time.Season;
import models.user.User;
import views.menu.GameTerminalView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController{

    public Result<String> showCurrentMenu(){
        return Result.success("game");
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

        for(User user : users) {
            if(user.isInAGame())
                return Result.failure(AuthError.IN_GAME_USER);
        }

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

    public Result<String> getTime() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success("" + App.game.getGameDate().getHourInDay());
    }

    public Result<String> getDate() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(
                "Day " + App.game.getGameDate().getDay() + ", " +
                        App.game.getSeason()
        );
    }

    public Result<String> getDateTime() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(
                "Day " + App.game.getGameDate().getDay() + ", " +
                        App.game.getSeason() + ", " +
                        "Time: " + App.game.getGameDate().getHourInDay() + ":00"
        );
    }

    public Result<String> getDayWeek() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getGameDate().getCurrentDayOfWeek());
    }

    public Result<Void> advanceTimeCheat(int days, int hours) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.game.advanceTime(days, hours);
        return Result.success(null);
    }

    public Result<Season> getSeasonName() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getSeason().toString());
    }

    public Result<Void> struckByThor(Coord cord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> struckByThorCheat(Coord cord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return struckByThor(cord);
    }

    public Result<Weather> getWeather() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getWeather().toString());
    }

    public Result<Weather> getWeatherForecast() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getNextWeather().toString());
    }

    public Result<Void> setWeatherCheat(Weather weather) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.game.setForecastWeather(weather);
        return Result.success(null);
    }

    public Result<Building> buildGreenHouse() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //TODO for building the greenhouse the player should pay 1000 coins and 500 amounts of wood

        App.game.getCurrentPlayerMap().getGreenHouses().setBuild(true);
        return Result.success("now you can use your greenhouse");
    }

    public Result<Void> walk(int x , int y) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Coord coord = new Coord(x, y);

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
        else if(tile.getTileType() == TileType.HOUSE) {
            House house = tile.getPlacable(House.class);
            player.setMap(house.getMap());
            player.setCoord(new Coord(0, 0));
            GameTerminalView.printWithColor(printMap(0 , 0 , 50));
        }
        else if(tile.getTileType() == TileType.GREEN_HOUSE) {
            GreenHouse house = tile.getPlacable(GreenHouse.class);
            if(house.isBuild()) {
                player.setMap(house.getMap());
                App.game.getCurrentPlayer().setCoord(new Coord(0, 0));
                GameTerminalView.printWithColor(printMap(0, 0, 50));
            }
            else {
                return Result.failure(GameError.GREENHOUSE_IS_NOT_YET_BUILT);
            }
        }
        else if(tile.getTileType() == TileType.MINES) {
            Mines mines = tile.getPlacable(Mines.class);
            player.setMap(mines.getMap());
            App.game.getCurrentPlayer().setCoord(new Coord(0, 0));
            GameTerminalView.printWithColor(printMap(0 , 0 , 50));
        }
        else if(tile.getTileType() == TileType.DOOR) {
            player.setMap(player.getDefaultMap());
            App.game.getCurrentPlayer().setCoord(new Coord(0, 0));
            GameTerminalView.printWithColor(printMap(0 , 0 , 50));
        }
        else if(tile.getTileType() == TileType.BLACKSMITH){
            App.game.getVillage().getShops().get(0).showProducts(); //TODO ?:(
        }
        else {
            App.game.getCurrentPlayer().setCoord(new Coord(x, y));
            GameTerminalView.printWithColor(printMap(0 , 0 , 50));
        }
        return Result.success(null);
    }

    public ArrayList<String> printMap(int x, int y , int size) {
        if (App.game == null) throw new IllegalStateException("Game is null!");
        return App.game.getCurrentPlayerMap().printMap(new Coord(x , y) , size);
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
        return Result.success(App.game.getCurrentPlayer().getInventory().removeItem(itemName , numberOfItems));
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
        if(tool.getToolType().equals(ToolType.HOE)) {
            System.out.println("HOE");
            Hoe hoe = (Hoe) App.game.getCurrentPlayer().getItemInHand();
            return hoe.use(destinyTile);
        }
        else if(tool.getToolType().equals(ToolType.PICKAXE)) {
            System.out.println("PICKAXE");
            Pickaxe pickaxe = (Pickaxe) App.game.getCurrentPlayer().getItemInHand();
            return pickaxe.use(destinyTile);
        }
        else if(tool.getToolType().equals(ToolType.AXE)) {
            System.out.println("AXE");
            Axe axe = (Axe) App.game.getCurrentPlayer().getItemInHand();
            return axe.use(destinyTile);
        }
        return Result.success("wtf");
    }

    public Result<String> craftInfo(String craftName) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        SeedInfo seedSource = App.getSeedInfoByName(craftName);
        if (seedSource == null) {
            return Result.failure(GameError.SEED_NOT_FOUND);
        }
        return Result.success(seedSource.toString());
    }

    public Result<Seed> plantSeed(String seedName, Direction direction) { // After MAP
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> showPlant(Coord cord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> fertilizePlant(FertilizerType fertilizer, Coord cord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Integer> howMuchWater() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //GET WATER_CAN FROM INVENTORY
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> water(Coord coord) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Map map = App.game.getCurrentPlayerMap();
        Tile tile = map.getTile(coord);
        tile.water();
        return Result.success(null);
    }

    public Result<List<Recipe>> craftingShowRecipes() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        return Result.success(App.game.getCurrentPlayer().getCraftingRecipe());
    }

    public Result<Void> craft(String recipeName) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        Recipe recipe = App.game.getCurrentPlayer().getRecipeByName(recipeName);
        if (recipe == null) {
            return Result.failure(GameError.CRAFT_RECIPE_NOT_FOUND);
        }
        Inventory inventory = App.game.getCurrentPlayer().getInventory();
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

    public Result<Item> addItemCheat(String itemName, int quantity) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> cookingRefrigerator(Consumable consumable) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Recipe>> cookingShowRecipes() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //this function should show list all the recipes
        //this function should show that what recipes are available and what recipes are not
        throw new UnsupportedOperationException("Not supported yet.");
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

        if(App.game.getCurrentPlayer().getInventory().getAmountByType(ItemType.COIN) < 1000)
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

    public Result<AnimalProducts> collectProducts(String animalName) {
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
        App.game.getCurrentPlayer().setMap(App.game.getVillage());
        App.game.getCurrentPlayer().setCoord(new Coord(0,0));
        return Result.success("Now you are in village");
    }

    public Result<Void> backToHome(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        App.game.getCurrentPlayer().setMap(App.game.getCurrentPlayer().getDefaultMap());
        App.game.getCurrentPlayer().setCoord(new Coord(0,0));
        return Result.success("Now you are back to home");
    }

    public Result<Void> talk(Player player, String massege) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> talkHistory(Player player) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> sendGift(Player player, Item item, int amount) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Item>> giftList() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> giftRate(int giftID, double rate) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Gift>> giftHistory(Player player) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> hug(Player player) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> sendFlower(Player player) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> askMarriage(Player player, Item ring) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> respondMarriage(Boolean respond, Player player) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> startTrade() {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<NPCFriendship>> showFriendShipNPCList(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        //this function should show NPC relations
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<String>> showQuestList(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> finishQuest(int questID){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String whereAmI() {
        if (App.game == null) return "No game running";
        return App.game.getCurrentPlayerMap().mapType.name();
    }

    public boolean isGameLockedDueToNight() {
        return (App.game != null && App.game.getGameDate().getHour() == 22);
    }

    //This means that we did 1 work... (you should advance game by 1 hour)
    public void advance() {
        if (App.game == null) return;
        App.game.advance();
    }
}