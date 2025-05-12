package models.game;

import models.App;
import models.Item.Item;
import models.data.VillagerData;
import models.data.VillagerTask;
import models.map.Coord;
import org.apache.commons.lang3.tuple.Pair;
import models.map.Building;

import java.util.ArrayList;
import java.util.List;

public class NPC {
    private String name;
    // Characterization
    private ArrayList<VillagerTask> tasks;
    private Building house;
    private ArrayList<Dialogue> dialogues;
    private ArrayList<NPCFriendship> friendships = new ArrayList<>();
    private ArrayList<String> favorites;
    private Coord coord;

    public NPC(String npcName , ArrayList<Player> players) {
        name = VillagerData.getData(npcName).getName();
        tasks = VillagerData.getData(npcName).getTasks();
        favorites = VillagerData.getData(npcName).getFavorites();
        for(Player player : players)
            friendships.add(new NPCFriendship(player , FriendshipLevel.LEVEL0 , 0));
    }

    public String getName() {
        return name;
    }

    public ArrayList<VillagerTask> getTasks() {
        return tasks;
    }

    public Building getHouse() {
        return house;
    }

    public ArrayList<Dialogue> getDialogues() {
        return dialogues;
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

    public void setHouse(Building house) {
        this.house = house;
    }

    public void setDialogues(ArrayList<Dialogue> dialogues) {
        this.dialogues = dialogues;
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
}
