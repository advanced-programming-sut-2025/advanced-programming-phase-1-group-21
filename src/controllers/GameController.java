package controllers;

import java.awt.geom.RectangularShape;
import java.awt.image.AreaAveragingScaleFilter;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.locks.ReentrantLock;

import models.animal.Animal;
import models.animal.AnimalProducts;
import models.crop.FertilizerType;
import models.crop.Seed;
import models.game.*;
import models.map.*;
import models.result.Result;
import models.time.Season;
import models.user.User;

public class GameController implements MenuStarter {

    public Result<Game> createGame(List<User> users) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> selectMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Game> loadGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> exitGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> nextTurn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> terminateGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public Result<String> getDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public Result<String> getDateTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public Result<String> getDayWeek() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> advanceTimeCheat(int days, int hours) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Season> getSeason() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> struckByThorCheat(Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Weather> getWeather() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Weather> getWeatherForecast() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Weather> setWeatherCheat(Weather weather) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Building> buildGreenHouse() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Player> walk(Coord cord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<String> printMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Energy> showEnergy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> helpReadingMap() {throw new UnsupportedOperationException("Not supported yet.");}

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










    @Override
    public void start() {
    }
}
