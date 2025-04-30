package models.game;

import models.map.Coop;
import models.map.Coord;
import models.map.Map;
import models.user.User;

import java.util.ArrayList;

public class Player {
    private User user;
    private Map thisPlayerMap;
    private Map currentPlayerMap;
    private Coord coord;
    private Map map;
    private int currentEnergy;
    private int maxEnergy;
    private Inventory inventory;
    private TrashCanType trashCanType;
    private int coins;
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

    public void setCurrentPlayerMap(Map currentPlayerMap) {
        this.currentPlayerMap = currentPlayerMap;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public int getCoins() {
        return coins;
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
}
