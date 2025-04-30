package models.game;

import models.map.*;
import models.user.User;

import java.util.ArrayList;

public class Player {
    static final int MAX_ENERGY = 200;

    private User user;
    private Map thisPlayerMap;
    private Map currentPlayerMap;
    private Coord coord = new Coord(0 , 0);
    private Map map;
    private int currentEnergy;
    private int maxEnergy;
    private Inventory inventory;
    private TrashCanType trashCanType;
    private Item itemInHand;
    private ArrayList<Relation> relations;
    private ArrayList<NPCFriendship> npcFriendships;

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
        this.maxEnergy = MAX_ENERGY;
        this.inventory = new Inventory(this);
        inventory.addItem(Game.getCoinItem(0));
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
