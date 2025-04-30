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
    private Energy energy;
    private Inventory inventory;
    private TrashCanType trashCanType;
    private int coins;
    private Item itemInHand;
    private ArrayList<Relation> relations;
    private ArrayList<NPCFriendship> npcFriendships;

    public Player(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setThisPlayerMap(Map thisPlayerMap) {
        this.thisPlayerMap = thisPlayerMap;
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
