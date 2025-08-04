package models.game;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.VillagerData;
import data.VillagerTask;
import models.DailyUpdate;
import models.Item.Item;
import models.map.*;
import models.time.Date;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class NPC implements Placable, DailyUpdate, Serializable {
    private String name;
    // Characterization
    NPCHouse house;
    Map npcMap;
    Building building;
    private ArrayList<VillagerTask> tasks;
    private ArrayList<Boolean> tasksFlag = new ArrayList<>();
    private ArrayList<NPCFriendship> friendships = new ArrayList<>();
    private ArrayList<String> favorites;
    private Coord coord;
    private transient Texture texture;
    private transient Sprite sprite;

    public NPC() {}

    public NPC(String npcName , ArrayList<Player> players) {
        name = VillagerData.getData(npcName).getName();
        tasks = VillagerData.getData(npcName).getTasks();
        favorites = VillagerData.getData(npcName).getFavorites();
        for(Player player : players)
            friendships.add(new NPCFriendship(player , FriendshipLevel.LEVEL0 , 0));
        house = new NPCHouse();
        this.building = house;
        tasksFlag.add(false);
        tasksFlag.add(false);
        tasksFlag.add(false);
        texture = SharedAssetManager.getNPCTexture(name.toLowerCase());
        sprite = new Sprite(texture);
        sprite.setX(getMap().mapType.getDistanceX());
        sprite.setY(getMap().mapType.getDistanceY() + (getMap().getMaxY() - 1)*30);
        sprite.setSize(60 , 100);
        coord = new Coord(0,0);
    }

    public void setNpcMap(Map npcMap) {
        this.npcMap = npcMap;
    }

    public Map getMap() {
        if(building == null) return npcMap;
        return building.getMap();
    }

    public ArrayList<Boolean> getTasksFlag() {
        return tasksFlag;
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
    public TileType getTileType() {
        return TileType.NPC;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "N";
    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public Sprite spriteGetter() {
        return sprite;
    }

    public NPCHouse getHouse() {
        return house;
    }

    @Override
    public boolean nextDay(Game g) {
        for(NPCFriendship friendship : this.getFriendships()) {
            friendship.nextDay(g);
            if(friendship.getLevel().equals(FriendshipLevel.LEVEL3)) {
                Random random = new Random();
                int randomNum = random.nextInt(3);
                if(randomNum != 0) {
                    friendship.getPlayer().getInventory().addItem(Item.build(tasks.get(randomNum).getRewardItem() , 1));
                }
            }
        }
        return false;
    }

    public class NPCHouse extends Building implements Placable {

        public NPCHouse() {
            this.map = (new MapBuilder()).buildNPCHouse(NPC.this);
            texture = new Texture("Textures/Buildings/" + name + "House.png");
            sprite = new Sprite(texture);
            sprite.setSize(5 * 30 , 5 * 30);
        }

        @Override
        public TileType getTileType() {
            return TileType.NPC;
        }

        @Override
        public String getFullName() {
            return name + "'s House";
        }

        @Override
        public boolean canEnter(Date date) {
            return true;
        }

        @Override
        public String getSprite() {
            return "O";
        }

        @Override
        public Texture getTexture() {
            return null;
        }

        @Override
        public Sprite spriteGetter() {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "NPC{name=%s, tasks=%d, friendships=%d, favorites=%s, coord=%s}",
                name,
                tasks != null ? tasks.size() : 0,
                friendships != null ? friendships.size() : 0,
                favorites,
                coord
        );
    }

}
