package controllers;

import models.Tool.Tool;
import models.animal.Animal;
import models.animal.AnimalProducts;
import models.crop.FertilizerType;
import models.crop.Seed;
import models.game.*;
import models.map.*;
import models.result.Result;
import models.result.errorTypes.AuthError;
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
        int currentPlayerIndex = 0;
        for(int i = 0 ; i < 4 ; i ++){
            if(App.game.getPlayers().get(i).equals(App.game.getCurrentPlayer()))
                currentPlayerIndex = i;
        }

        App.game.setCurrentPlayer(App.game.getPlayers().get((currentPlayerIndex + 1)%4));
        return Result.success("Now its " + App.game.getCurrentPlayer().getUser().getUsername() + "'s turn");

    }

    public Result<Void> terminateGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> getTime() {
        return Result.success("" + game.getGameDate().getHourInDay());
    }

    public Result<String> getDate() {
        return Result.success(
            "Day " + game.getGameDate().getDay() + ", " +
            game.getSeason()
        );
    }

    public Result<String> getDateTime() {
        return Result.success(
            "Day " + game.getGameDate().getDay() + ", " +
            game.getSeason() + ", " +
            "Time: " + game.getGameDate().getHourInDay() + ":00"
        );
    }

    public Result<String> getDayWeek() {
        return Result.success(game.getGameDate().getCurrentDayOfWeek());
    }

    public Result<Void> advanceTimeCheat(int days, int hours) {
        game.advanceTime(days, hours);
        return Result.success(null);
    }

    public Result<Season> getSeasonName() {
        return Result.success(game.getSeason().toString());
    }

    public Result<Void> struckByThor(Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> struckByThorCheat(Coord cord) {
        return struckByThor(cord);
    }

    public Result<Weather> getWeather() {
        return Result.success(game.getWeather().toString());
    }

    public Result<Weather> getWeatherForecast() {
        return Result.success(game.getNextWeather().toString());
    }

    public Result<Void> setWeatherCheat(Weather weather) {
        game.setForecastWeather(weather);
        return Result.success(null);
    }

    public Result<Building> buildGreenHouse() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Player> walk(Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<String> printMap(int x, int y , int size) {
        return App.game.getCurrentPlayer().getCurrentPlayerMap().printMap(new Coord(x , y) , size);
    }

    public Result<Energy> showEnergy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> helpReadingMap() {
        GameTerminalView.helpReadingMap();
        return Result.success("Help Reading Map");
    }

    public Result<Energy> setEnergyCheat(int energyValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Energy> setEnergyUnlimited() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Inventory> showInventory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Inventory> removeFromInventory(String itemName, int numberOfItems) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Tool> equipTool(String toolName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<List<Tool>> showAvailableTools() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Tool> showToolInHand() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Tool> upgradeTool(String toolName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> useTool(Direction direction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> craftInfo(String craftName) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //TODO_SOBHAN
    public Result<Item> craft(String recipeName) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    private Result<Barn> buildBarn(Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Result<Coop> buildCoop(Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> buildBarnOrCoop(String buildingName , Coord cord) {
        if(buildingName.equals("Barn"))
            buildBarn(cord);
        if(buildingName.equals("Coop"))
            buildCoop(cord);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Animal> buyAnimal(String animal , String name){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> pet(String name){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> cheatFriendship(String name , int amount){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> showAnimals(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> shepherdAnimals(String name , Coord cord){
        throw new UnsupportedOperationException("Not supported yet.");
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