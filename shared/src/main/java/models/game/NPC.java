package models.game;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.VillagerData;
import data.VillagerTask;
import models.BriefInfo;
import models.DailyUpdate;
import models.Item.Item;
import models.map.*;
import models.sprite.GameSprite;
import models.time.Date;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class NPC implements Placable, DailyUpdate, Serializable, BriefInfo {
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
    private String texture;
    private transient GameSprite sprite;
    private transient String responseToMessage;
    private transient GameSprite cloudSprite;
    private transient GameSprite reactSprite;
    private transient String reactTexture;
    private float animationTime;

    public NPC() {}

    public NPC(String npcName , ArrayList<Player> players) {
        name = VillagerData.getData(npcName).getName();
        tasks = VillagerData.getData(npcName).getTasks();
        favorites = VillagerData.getData(npcName).getFavorites();
        for(Player player : players)
            friendships.add(new NPCFriendship(player , FriendshipLevel.LEVEL0 , 0));
        house = new NPCHouse(this);
        this.building = house;
        tasksFlag.add(false);
        tasksFlag.add(false);
        tasksFlag.add(false);
        texture = SharedAssetManager.getNPCTexturePath(name.toLowerCase());
        sprite = new GameSprite(texture);
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

    public void talk(String responseToMessage) {
        this.responseToMessage = responseToMessage;
        cloudSprite = new GameSprite(SharedAssetManager.getCloudPath());
        cloudSprite.setSize(60 , 60);
        cloudSprite.setX(sprite.getX() + 50);
        cloudSprite.setY(sprite.getY() + 90);
    }

    public Sprite getCloudSprite() {
        return cloudSprite;
    }

    public String getResponseToMessage() {
        return responseToMessage;
    }

    public void setResponseToMessage(String responseToMessage) {
        this.responseToMessage = responseToMessage;
    }

    public void setCloudSprite(GameSprite cloudSprite) {
        this.cloudSprite = cloudSprite;
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
    public String getTexture() {
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

    public String getInfo() {
        return "Name {" + name + "}";
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

    public void setGiftAnimation(){
        reactTexture = SharedAssetManager.getHeartPath();
        if(reactSprite == null)
            reactSprite = new GameSprite(reactTexture);
        reactSprite.setTexture(reactTexture);
        reactSprite.setSize(30 , 30);
        reactSprite.setX(sprite.getX() + 20);
        reactSprite.setY(sprite.getY() + 110);
        animationTime = 1;
    }

    public void setQuestAnimation(){
        reactTexture = SharedAssetManager.getCupPath();
        if(reactSprite == null)
            reactSprite = new GameSprite(reactTexture);
        reactSprite.setTexture(reactTexture);
        reactSprite.setSize(30 , 30);
        reactSprite.setX(sprite.getX() + 20);
        reactSprite.setY(sprite.getY() + 110);
        animationTime = 1;
    }


    public void runAnimation(){
        reactSprite.setAlpha(animationTime);
        animationTime -= (float) 0.002;
        reactSprite.setX(sprite.getX() + 20);
        reactSprite.setY(sprite.getY() + 110);
        if(animationTime <= 0){
            animationTime = 0;
            reactSprite = null;
            reactTexture = null;
        }
    }

    public Sprite getReactSprite() {
        return reactSprite;
    }
}
