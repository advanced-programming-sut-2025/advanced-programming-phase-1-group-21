package models.game;

import models.App;
import models.animal.Animal;
import models.map.*;
import models.map.Coord;
import models.map.Map;
import models.user.User;

import java.util.ArrayList;

public class Player {
    static final int MAX_ENERGY = 200;

    private User user;
    private Map thisPlayerMap;
    private Map currentPlayerMap;
    private Coord coord = new Coord(0 , 0);
    private int agronomicAbility = 0;
    private int miningAbility = 0;
    private int foragingAbility = 0;
    private int fishingAbility = 0;
    private ArrayList<Animal> animals = new ArrayList<>();
    private Energy energy;
    private Inventory inventory;
    private TrashCanType trashCanType;
    private Item itemInHand;
    private ArrayList<Relation> relations;
    private ArrayList<NPCFriendship> npcFriendships;
    private ArrayList<Recipe> craftingRecipe = new ArrayList<>();

    public ArrayList<Recipe> getCraftingRecipe() {
        return craftingRecipe;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addCraftRecipe(Recipe recipe) {
        craftingRecipe.add(recipe);
    }

    public Recipe getRecipeByName(String name) {
        for (Recipe recipe : craftingRecipe) {
            if (recipe.getRecipeName().equals(name)) {
                return recipe;
            }
        }
        return null;
    }

    public Player(User user) {
        this.user = user;
        this.inventory = new Inventory();
        inventory.addItem(Game.getCoinItem(0));
        energy = new Energy();
    }

    /*
        Set max energy to X for D days
     */
    public void setMaxEnergy(int maxEnergy, int days) {
        energy.setMaxEnergy(maxEnergy, days);
    }

    public int getMaxEnergy() {
        return energy.getMaxEnergy();
    }

    public int getEnergy() {
        return energy.getCurrentEnergy();
    }

    public void decreaseEnergy(int amount) {
        energy.decreaseEnergy(amount);
    }

    public void setEnergy(int energyValue) {
        energy.setEnergy(energyValue);
    }

    public User getUser() {
        return user;
    }

    public void setThisPlayerMap(Map thisPlayerMap) {
        this.thisPlayerMap = thisPlayerMap;
    }

    public Map getThisPlayerMap() {
        return thisPlayerMap;
    }

    public Map getCurrentPlayerMap() {
        return currentPlayerMap;
    }

    public ArrayList<ArrayList<Tile>> currentLocationTiles(){
        LocationsOnMap loc = currentPlayerMap.getCurrentLocation();
        if(loc == LocationsOnMap.Farm)
            return currentPlayerMap.getTiles();
        if(loc == LocationsOnMap.House)
            return currentPlayerMap.getHouse().getTiles();
        if(loc == LocationsOnMap.GreenHouse)
            return currentPlayerMap.getGreenHouses().getTiles();
        if(loc == LocationsOnMap.Mines)
            return currentPlayerMap.getMines().getTiles();
        return null;
    }

    public ArrayList<Tile> getNeighborTiles(){
        ArrayList<Tile> output = new ArrayList<>();
        Coord playerCord = App.game.getCurrentPlayer().getCoord();
        for(Direction direction : Direction.values()) {
            if (direction.getCoord().addCoord(playerCord).getValidCoord() == null)
                continue;
            output.add(App.game.getCurrentPlayer().currentLocationTiles().get(playerCord.getY() + direction.getDy()).get(playerCord.getX() + direction.getDx()));
        }
        return output;
    }


    public void setCurrentPlayerMap(Map currentPlayerMap) {
        this.currentPlayerMap = currentPlayerMap;
    }

    public void addCoins(int coins) {
        inventory.addItem(Game.getCoinItem(coins));
    }

    public int getCoins() {
        return inventory.getAmountByType(ItemType.COIN);
    }

    public Item getItemInHand() {
        return itemInHand;
    }

    public void setItemInHand(Item item) {
        this.itemInHand = item;
    }

    public void faint() {

    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public int getAnimalIndex(String name){
        for(int i = 0 ; i < animals.size(); i++){
            if(animals.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    public int getAgronomicAbility() {
        return agronomicAbility;
    }

    public int getMiningAbility() {
        return miningAbility;
    }

    public int getForagingAbility() {
        return foragingAbility;
    }

    public int getFishingAbility() {
        return fishingAbility;
    }

    public void raiseAgronomicAbility() {
        this.agronomicAbility += 5;
    }

    public void raiseMiningAbility() {
        this.miningAbility += 10;
    }

    public void raiseForagingAbility() {
        this.foragingAbility += 10;
    }

    public void setFishingAbility() {
        this.fishingAbility += 5;
    }
}
