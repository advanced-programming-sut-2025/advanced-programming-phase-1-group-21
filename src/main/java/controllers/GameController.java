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

        for(Player player : players) {
            while(true) {
                int mapID = GameTerminalView.getMap(player);
                if ((mapID < 1) || (mapID > 3))
                    System.out.println("Invalid map ID");
                else {
                    player.setThisPlayerMap(new Map(mapID));
                    player.setCurrentPlayerMap(player.getThisPlayerMap());
                    break;
                }
            }
        }

        Game game = new Game(players.getFirst() , players);
        App.game = game;
        return Result.success(game , "Game created");
    }

    public Result<Game> loadGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> exitGame() {
        //TODO
        //Save Game
        App.game = null;
        App.play = false;
        return Result.success("Exit Game ...");
    }

    public Result<Void> nextTurn() {
        if(App.game == null)
            return Result.failure(AuthError.GAME_NOT_CREATED , "Game not created");

        App.game.nextTurn();

        return Result.success("Now its " + App.game.getCurrentPlayer().getUser().getUsername() + "'s turn");

    }

    public Result<Void> terminateGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> getTime() {
        return Result.success("" + App.game.getGameDate().getHourInDay());
    }

    public Result<String> getDate() {
        return Result.success(
            "Day " + App.game.getGameDate().getDay() + ", " +
            App.game.getSeason()
        );
    }

    public Result<String> getDateTime() {
        return Result.success(
            "Day " + App.game.getGameDate().getDay() + ", " +
            App.game.getSeason() + ", " +
            "Time: " + App.game.getGameDate().getHourInDay() + ":00"
        );
    }

    public Result<String> getDayWeek() {
        return Result.success(App.game.getGameDate().getCurrentDayOfWeek());
    }

    public Result<Void> advanceTimeCheat(int days, int hours) {
        App.game.advanceTime(days, hours);
        return Result.success(null);
    }

    public Result<Season> getSeasonName() {
        return Result.success(App.game.getSeason().toString());
    }

    public Result<Void> struckByThor(Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> struckByThorCheat(Coord cord) {
        return struckByThor(cord);
    }

    public Result<Weather> getWeather() {
        return Result.success(App.game.getWeather().toString());
    }

    public Result<Weather> getWeatherForecast() {
        return Result.success(App.game.getNextWeather().toString());
    }

    public Result<Void> setWeatherCheat(Weather weather) {
        App.game.setForecastWeather(weather);
        return Result.success(null);
    }

    public Result<Building> buildGreenHouse() {
        //TODO for building the greenhouse the player should pay 1000 coins and 500 amounts of wood

        App.game.getCurrentPlayer().getThisPlayerMap().getGreenHouses().setBuild(true);
        return Result.success("now you can use your greenhouse");
    }

    public Result<Player> walk(int x , int y) {
        if(y >= App.game.getCurrentPlayer().currentLocationTiles().size())
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , GameError.COORDINATE_DOESNT_EXISTS.getMessage());
        if(x >= App.game.getCurrentPlayer().currentLocationTiles().get(0).size())
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , GameError.COORDINATE_DOESNT_EXISTS.getMessage());

        if(App.game.getCurrentPlayer().getCurrentPlayerMap().getTiles().get(y).get(x).getForaging() != null)
            return Result.failure(GameError.CANT_STAND_ON_FORAGING , GameError.CANT_STAND_ON_FORAGING.getMessage());

        if(App.game.getCurrentPlayer().getCurrentPlayerMap().getTiles().get(y).get(x).getRefrigerator() != null)
            return Result.failure(GameError.CANT_STAND_ON_FRIDGE , GameError.CANT_STAND_ON_FRIDGE.getMessage());

        if(App.game.getCurrentPlayer().getCurrentPlayerMap().getTiles().get(y).get(x).isLake())
            return Result.failure(GameError.CANT_STAND_ON_LAKE , GameError.CANT_STAND_ON_LAKE.getMessage());

        if(App.game.getCurrentPlayer().currentLocationTiles().get(y).get(x).isHouse()){
            App.game.getCurrentPlayer().getCurrentPlayerMap().setCurrentLocation(LocationsOnMap.House);
            App.game.getCurrentPlayer().setCoord(new Coord(0, 0));
            GameTerminalView.printWithColor(printMap(0 , 0 , 50));
        }

        else if(App.game.getCurrentPlayer().currentLocationTiles().get(y).get(x).isGreenHouse()){
            if(App.game.getCurrentPlayer().getThisPlayerMap().getGreenHouses().isBuild()) {
                App.game.getCurrentPlayer().getCurrentPlayerMap().setCurrentLocation(LocationsOnMap.GreenHouse);
                App.game.getCurrentPlayer().setCoord(new Coord(0, 0));
                GameTerminalView.printWithColor(printMap(0, 0, 50));
            }
            else{
                return Result.failure(GameError.GREENHOUSE_IS_NOT_YET_BUILT , GameError.GREENHOUSE_IS_NOT_YET_BUILT.getMessage());
            }
        }

        else if(App.game.getCurrentPlayer().currentLocationTiles().get(y).get(x).isMines()){
            App.game.getCurrentPlayer().getCurrentPlayerMap().setCurrentLocation(LocationsOnMap.Mines);
            App.game.getCurrentPlayer().setCoord(new Coord(0, 0));
            GameTerminalView.printWithColor(printMap(0 , 0 , 50));
        }

        else if(App.game.getCurrentPlayer().currentLocationTiles().get(y).get(x).isDoor()){
            App.game.getCurrentPlayer().getCurrentPlayerMap().setCurrentLocation(LocationsOnMap.Farm);
            App.game.getCurrentPlayer().setCoord(new Coord(0, 0));
            GameTerminalView.printWithColor(printMap(0 , 0 , 50));
        }
        else{
            App.game.getCurrentPlayer().setCoord(new Coord(x, y));
            GameTerminalView.printWithColor(printMap(0 , 0 , 50));
        }
        return Result.success(null);

    }

    public ArrayList<String> printMap(int x, int y , int size) {
        return App.game.getCurrentPlayer().getCurrentPlayerMap().printMap(new Coord(x , y) , size);
    }

    public int showEnergy() {
        return App.game.getCurrentPlayer().getEnergy();
    }

    public Result<Void> helpReadingMap() {
        GameTerminalView.helpReadingMap();
        return Result.success("Help Reading Map");
    }

    public Result<Void> setEnergyCheat(int energyValue) {
        App.game.getCurrentPlayer().setEnergy(energyValue);
        return Result.success(null);
    }

    public Result<Energy> setEnergyUnlimited() {
        App.game.getCurrentPlayer().setEnergy(Integer.MAX_VALUE);
        return Result.success(null);
    }

    public Result<ArrayList<String>> showInventory() {
         return Result.success(App.game.getCurrentPlayer().getInventory().showInventory());
    }

    public Result<Void> removeFromInventory(String itemName, int numberOfItems) {
        return Result.success(App.game.getCurrentPlayer().getInventory().removeItem(itemName , numberOfItems));
    }

    public Result<Tool> equipTool(String toolName) {
        if(App.game.getCurrentPlayer().getInventory().toolEquip(toolName)) {
            return Result.success("Now " + toolName + " is in your hands");
        }

        return Result.failure(GameError.TOOL_NOT_FOUND , GameError.TOOL_NOT_FOUND.getMessage());
    }

    public Result<String> showCurrentTool() {
        if(App.game.getCurrentPlayer().getItemInHand() == null)
            return Result.success("nothing is in your hands");
        return Result.success(App.game.getCurrentPlayer().getItemInHand().getName());
    }

    public Result<ArrayList<String>> showAvailableTools() {
        return Result.success(App.game.getCurrentPlayer().getInventory().showAvailableTools());
    }

    public Result<Tool> upgradeTool(String toolName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> useTool(String direction) {
        Direction d = Direction.stringToDirection(direction);
        if(d == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , GameError.COORDINATE_DOESNT_EXISTS.getMessage());

        Coord playerCoord = App.game.getCurrentPlayer().getCoord();
        if(playerCoord.getY() + d.getDy() < 0)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , GameError.COORDINATE_DOESNT_EXISTS.getMessage());
        if(playerCoord.getY() + d.getDy() >= App.game.getCurrentPlayer().currentLocationTiles().size())
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , GameError.COORDINATE_DOESNT_EXISTS.getMessage());
        if(playerCoord.getX() + d.getDx() < 0)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , GameError.COORDINATE_DOESNT_EXISTS.getMessage());
        if(playerCoord.getX() + d.getDx() >= App.game.getCurrentPlayer().currentLocationTiles().get(0).size())
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , GameError.COORDINATE_DOESNT_EXISTS.getMessage());

        Coord destinyTile = new Coord(playerCoord.getX() + d.getDx(), playerCoord.getY() + d.getDy());

        if(!App.game.getCurrentPlayer().getItemInHand().getItemType().equals(ItemType.TOOL))
            return Result.failure(GameError.TOOL_NOT_FOUND , GameError.TOOL_NOT_FOUND.getMessage());

        Tool tool = (Tool) App.game.getCurrentPlayer().getItemInHand();
        if(tool.getToolType().equals(ToolType.HOE)) {
            System.out.println("HOE");
            Hoe hoe = (Hoe) App.game.getCurrentPlayer().getItemInHand();
            return Result.success(hoe.use(destinyTile).getMessage());
        }
        else if(tool.getToolType().equals(ToolType.PICKAXE)) {
            System.out.println("PICKAXE");
            Pickaxe pickaxe = (Pickaxe) App.game.getCurrentPlayer().getItemInHand();
            return Result.success(pickaxe.use(destinyTile).getMessage());
        }
        else if(tool.getToolType().equals(ToolType.AXE)) {
            System.out.println("AXE");
            Axe axe = (Axe) App.game.getCurrentPlayer().getItemInHand();
            return Result.success(axe.use(destinyTile).getMessage());
        }

        return Result.success("wtf");
    }

    public Result<String> craftInfo(String craftName) {
        SeedInfo seedSource = App.getSeedInfoByName(craftName);
        if (seedSource == null) {
            return Result.failure(GameError.SEED_NOT_FOUND);
        }
        return Result.success(seedSource.toString());
    }

    public Result<Seed> plantSeed(Seed seed, Direction direction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> showPlant(Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> fertilizePlant(FertilizerType fertilizer, Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Integer> howMuchWater() {
        //GET WATER_CAN FROM INVENTORY
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<List<Recipe>> craftingShowRecipes() {
        return Result.success(App.game.getCurrentPlayer().getCraftingRecipe());
    }

    public Result<Void> craft(String recipeName) {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> addItemCheat(String itemName, int quantity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> cookingRefrigerator(Consumable consumable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Recipe>> cookingShowRecipes() {
        //this function should show list all the recipes
        //this function should show that what recipes are available and what recipes are not
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> eat(Consumable food){
        //some foods can give power to the user.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> buildBarnOrCoop(String buildingName , int x , int y) {
        if((x >= 46) || (y >= 26))
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , "this coordinate is out of bounds");
        for(int i = y ; i < y+5 ; i++){
            for(int j = x ; j < x+5 ; j++){
                if(!App.game.getCurrentPlayer().getThisPlayerMap().getTiles().get(i).get(j).tileIsEmpty())
                    return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , "this coordinate is not empty");
            }
        }

        if(App.game.getCurrentPlayer().getInventory().getAmountByType(ItemType.COIN) < 1000)
            return Result.failure(GameError.NOT_ENOUGH_COINS , GameError.NOT_ENOUGH_COINS.getMessage());



        if(buildingName.equals("Barn")){
            for(int i = y ; i < y+5 ; i++){
                for(int j = x ; j < x+5 ; j++)
                    App.game.getCurrentPlayer().getThisPlayerMap().getTiles().get(i).get(j).setBarn(true);
            }
            return Result.success("Barn build successfully");
        }
        if(buildingName.equals("Coop")){
            for(int i = y ; i < y+5 ; i++){
                for(int j = x ; j < x+5 ; j++)
                    App.game.getCurrentPlayer().getThisPlayerMap().getTiles().get(i).get(j).setCoop(true);
            }
            return Result.success("Coop build successfully");
        }
        return Result.success(null);

    }

    public Result<Animal> buyAnimal(String animal , String name){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> pet(String name){
        Coord playerCord = App.game.getCurrentPlayer().getCoord();
        for(Direction direction : Direction.values()){
            if(direction.getDx() + playerCord.getX() < 0){
                continue;
            }

            if(direction.getDx() + playerCord.getX() >= App.game.getCurrentPlayer().currentLocationTiles().get(0).size()){
                continue;
            }

            if(direction.getDy() + playerCord.getY() < 0){
                continue;
            }

            if(direction.getDy() + playerCord.getY() >= App.game.getCurrentPlayer().currentLocationTiles().size()){
                continue;
            }

            if(App.game.getCurrentPlayer().currentLocationTiles().get(playerCord.getY() + direction.getDy()).
                    get(playerCord.getX() + direction.getDx()).getAnimal() == null)
                continue;

            if(App.game.getCurrentPlayer().currentLocationTiles().get(playerCord.getY() + direction.getDy()).
                    get(playerCord.getX() + direction.getDx()).getAnimal().getName().equals(name)){
                App.game.getCurrentPlayer().currentLocationTiles().get(playerCord.getY() + direction.getDy()).
                        get(playerCord.getX() + direction.getDx()).getAnimal().pet();
                return Result.success("goooogooli");
            }
        }
        return Result.failure(GameError.ANIMAL_NOT_FOUND , "kojaie pas heyvoon");
    }

    public Result<Void> cheatFriendship(String name , int amount){
        for(Animal animal : App.game.getCurrentPlayer().getAnimals()){
            if(animal.getName().equals(name)) {
                animal.setFriendship(amount);
                return Result.success("Now your friendship with " + name + " is " + animal.getFriendship());
            }
        }
        return Result.failure(GameError.ANIMAL_NOT_FOUND , "You dont own animal with this name");

    }

    public Result<ArrayList<String>> showAnimals(){
        ArrayList<String> output = new ArrayList<>();
        output.add("Animals:");
        for(Animal animal : App.game.getCurrentPlayer().getAnimals()){
            output.add(animal.getName() + " - " + animal.getFriendship());
        }
        return Result.success(output , "okay");
    }

    public Result<Void> shepherdAnimals(String name , int x , int y){
        if((x >= 50) || (y >= 30))
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS , GameError.COORDINATE_DOESNT_EXISTS.getMessage());
        if(!App.game.getCurrentPlayer().getThisPlayerMap().getTiles().get(y).get(x).tileIsEmpty())
            return Result.failure(GameError.TILE_IS_NOT_EMPTY , GameError.TILE_IS_NOT_EMPTY.getMessage());

        int thisAnimalIndex = App.game.getCurrentPlayer().getAnimalIndex(name);
        if(thisAnimalIndex == -1)
            return Result.failure(GameError.ANIMAL_NOT_FOUND , "animal not found");


        App.game.getCurrentPlayer().getAnimals().get(thisAnimalIndex).shepherd();
        App.game.getCurrentPlayer().getThisPlayerMap().getTiles().get(y).get(x).setAnimal(App.game.getCurrentPlayer().getAnimals().get(thisAnimalIndex));

        return Result.success(name + " is on tile (" + x + ", " + y + ")");
    }

    public Result<Void> feedHay(String animalName){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> showAnimalProduces(){
        //this function should show Products with their quality
        //show which animals have unjamavari products
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<AnimalProducts> collectProducts(String animalName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> sellAnimal(String animalName){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> fishing(String fishingPole){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> artisanUse(String artisanName , String item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> artisanGet(String artisanName) {
        //this function should get products from artisans
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> crowAttack() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Item>> showAllproducts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Item>> showAvailableProducts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> purchase(Item product, int count) {
        // What is the Result??

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> addMoneyCheat(int amount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> sell(Item product, int count) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> talk(Player player, String massege) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> talkHistory(Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> sendGift(Player player, Item item, int amount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Item>> giftList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> giftRate(int giftID, double rate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Gift>> giftHistory(Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> hug(Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> sendFlower(Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> askMarriage(Player player, Item ring) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> respondMarriage(Boolean respond, Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> startTrade() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<NPCFriendship>> showFriendShipNPCList(){
        //this function should show NPC relations
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<String>> showQuestList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Item> finishQuest(int questID){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> updateGameSecond() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> updateGameNight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> updateGameMonth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}