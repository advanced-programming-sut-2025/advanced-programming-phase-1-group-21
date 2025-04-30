package models.game;

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
    private Map map;
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

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void decreaseEnergy(int amount) {
        if (currentEnergy < amount) {
            throw new IllegalArgumentException("You can't decrease energy by " + amount);
        }
        currentEnergy -= amount;
    }

    public Player(User user) {
        this.user = user;
        this.inventory = new Inventory(this);
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
        if(currentPlayerMap.getCurrentLocation().equals(LocationsOnMap.Farm))
            return currentPlayerMap.getTiles();
        if(currentPlayerMap.getCurrentLocation().equals(LocationsOnMap.House))
            return currentPlayerMap.getHouse().getTiles();
        if(currentPlayerMap.getCurrentLocation().equals(LocationsOnMap.GreenHouse))
            return currentPlayerMap.getGreenHouses().getTiles();
        if(currentPlayerMap.getCurrentLocation().equals(LocationsOnMap.Mines))
            return currentPlayerMap.getMines().getTiles();
        return null;
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
}
