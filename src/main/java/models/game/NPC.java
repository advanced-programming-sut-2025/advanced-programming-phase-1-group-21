package models.game;

import models.Item.Item;
import models.data.*;
import models.map.Coord;
import models.map.Building;
import models.map.MapBuilder;
import models.map.Placable;

import java.util.ArrayList;

public class NPC implements Placable {
    private String name;
    // Characterization
    NPCHouse house;
    private ArrayList<VillagerTask> tasks;
    private ArrayList<NPCFriendship> friendships = new ArrayList<>();
    private ArrayList<String> favorites;
    private Coord coord;

    public NPC(String npcName , ArrayList<Player> players) {
        name = VillagerData.getData(npcName).getName();
        tasks = VillagerData.getData(npcName).getTasks();
        favorites = VillagerData.getData(npcName).getFavorites();
        for(Player player : players)
            friendships.add(new NPCFriendship(player , FriendshipLevel.LEVEL0 , 0));
        house = new NPCHouse();
    }

    public String getName() {
        return name;
    }

    public ArrayList<VillagerTask> getTasks() {
        return tasks;
    }

    public ArrayList<NPCFriendship> getFriendships() {
        return friendships;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTasks(ArrayList<VillagerTask> tasks) {
        this.tasks = tasks;
    }

    public void setFriendships(ArrayList<NPCFriendship> friendships) {
        this.friendships = friendships;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public NPCFriendship getFriendshipByPlayer(Player player) {
        for(NPCFriendship npcFriendship : friendships) {
            if(npcFriendship.getPlayer().equals(player)) {
                return npcFriendship;
            }
        }
        return null;
    }

    public boolean isMyFavoriteItem(Item item) {
        for(String favorite : favorites) {
            if(favorite.equalsIgnoreCase(item.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "N";
    }

    public NPCHouse getHouse() {
        return house;
    }

    public class NPCHouse extends Building implements Placable {

        public NPCHouse() {
            this.map = (new MapBuilder()).buildNPCHouse(NPC.this);
        }

        @Override
        public boolean canEnter() {
            return true;
        }

        @Override
        public String getSprite() {
            return "O";
        }
    }
}
