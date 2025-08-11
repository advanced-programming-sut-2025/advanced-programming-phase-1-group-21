package models.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.AnimalData;
import models.DailyUpdate;
import models.Item.Item;
import models.Item.Recipe;
import models.Item.RecipeType;
import models.MusicData;
import models.animal.Animal;
import models.map.*;
import models.skill.Skill;
import models.skill.SkillType;
import models.sprite.GameSprite;
import models.user.User;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Player implements DailyUpdate, Serializable {
    static final int MAX_ENERGY = 200;

    private User user;

    private Building building;
    private Map map;
    private Map defaultMap;
    private Coord coord = new Coord(0 , 0);
    private ArrayList<Animal> animals = new ArrayList<>();
    private Energy energy;
    private Inventory inventory;
    private ArrayList<NPCFriendship> npcFriendships;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Skill skill = new Skill();
    private ArrayList<String> notifications = new ArrayList<>();
    private float speed = (float) 4;
    private Animal shepherdingAnimal;
    private boolean faint = false;
    private int quest = 0;
    private String suitor;
    private Reaction reaction = new Reaction();
    private final transient List<MusicData> musics = new ArrayList<>();
    private int mapID;

    private List<String[]> animations;
    private int frameIndex = 0;
    private float frameTime = 0f;
    private float frameDuration = 0.3f;
    private transient GameSprite sprite;

    public Player() {}

    public Player(User user, Map defaultMap) {
        this.user = user;
        this.inventory = Inventory.buildPlayerInventory();
        inventory.addItem(Game.getCoinItem(10000));
        energy = new Energy();
        this.defaultMap = defaultMap;
        this.map = defaultMap;

        House house = defaultMap.getBuilding(House.class);

        this.coord = defaultMap.getCoord(house);
        this.coord = coord.addCoord(new Coord(-1, 0)); //left of it!

        animations = new ArrayList<>();

        animations.add(new String[]{
                "Textures/Players/Left1.png",
                "Textures/Players/Left2.png",
                "Textures/Players/Left3.png"
        });
        animations.add(new String[]{
                "Textures/Players/Right1.png",
                "Textures/Players/Right2.png",
                "Textures/Players/Right3.png"
        });
        animations.add(new String[]{
                "Textures/Players/Up1.png",
                "Textures/Players/Up2.png",
                "Textures/Players/Up3.png"
        });
        animations.add(new String[]{
                "Textures/Players/Down1.png",
                "Textures/Players/Down2.png",
                "Textures/Players/Down3.png"
        });

        sprite = new GameSprite(animations.get(3)[0]);

        enterBuilding(defaultMap.getBuilding(House.class));
        sprite.setX(getMap().mapType.getDistanceX());
        sprite.setY(getMap().mapType.getDistanceY() + (getMap().getMaxY() - 1)*30);
        sprite.setSize(30 , 78);
        Animal animal = new Animal("ali", AnimalData.getAnimalData("cow"));
        getMap().getTile(18 , 8).setPlacable(animal);
        this.addAnimal(animal);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public void resetNotifications() {
        this.notifications = new ArrayList<>();
    }

    public void addNotifications(String notification) {
        this.notifications.add(notification);
    }

    private Stack<Pair<Map, Coord>> locStack = new Stack<>();

    public ArrayList<Recipe> getRecipes(RecipeType type) {
        ArrayList<Recipe> rp = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getType() == type) {
                rp.add(recipe);
            }
        }
        return rp;
    }

    public Inventory getInventory() {
        return inventory;
    }



    public void addRecipes(Recipe recipe) {
        recipes.add(recipe);
    }

    public Recipe getRecipeByName(String name, RecipeType type) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equalsIgnoreCase(name) && recipe.getType() == type) {
                return recipe;
            }
        }
        return null;
    }

    public Map getDefaultMap() {
        return defaultMap;
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

    public float getEnergy() {
        return energy.getCurrentEnergy();
    }

    public void decreaseEnergy(float amount) {
        energy.decreaseEnergy(amount);
    }

    public void increaseEnergy(float amount) {
        energy.increaseEnergy(amount);
    }

    public void setEnergy(float energyValue) {
        energy.setEnergy(energyValue);
    }

    public User getUser() {
        return user;
    }

    public void setMap(Map map) {
        building = null;
        this.map = map;
    }

    public Map getMap() {
        if (building == null) return map;
        return building.getMap();
    }

    public ArrayList<Tile> getNeighborTiles(Game game){
        ArrayList<Tile> output = new ArrayList<>();
        Coord playerCord = getCoord();
        for(Direction direction : Direction.values()) {
            Tile tile = getMap().getTile(direction.getCoord().addCoord(playerCord));
            if (tile == null)
                continue;
            output.add(tile);
        }
        return output;
    }

    public ArrayList<Coord> getNeighborCoord(Game game) {
        ArrayList<Coord> output = new ArrayList<>();
        Coord playerCord = getCoord();
        for(Direction direction : Direction.values()) {
            Coord c = direction.getCoord().addCoord(playerCord);
            Tile tile = getMap().getTile(c);
            if (tile == null)
                continue;
            output.add(c);
        }
        return output;
    }

    public boolean weAreNextToEachOther(Player otherPlayer) {
        if(!getMap().equals(otherPlayer.getMap()))
            return false;
        Coord otherPlayerCord = otherPlayer.getCoord();
        Coord myCoord = getCoord();

        if((otherPlayerCord.getX() - myCoord.getX()) * (otherPlayerCord.getX() - myCoord.getX()) > 1)
            return false;
        if((otherPlayerCord.getY() - myCoord.getY()) * (otherPlayerCord.getY() - myCoord.getY()) > 1)
            return false;
        return true;
    }


    public void addCoins(int coins) {
        inventory.addItem(Game.getCoinItem(coins));
    }

    public int getCoins() {
        return inventory.getAmount("coin");
    }

    public Item getItemInHand() {
        return inventory.getItemInHand();
//        return itemInHand;
    }

    public void setItemInHand(Item item) {
        inventory.setItemInHand(item);
//        this.itemInHand = item;
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

    public Animal getAnimalByName(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name))
                return animal;
        }
        return null;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    static final int DURATION_ENERGY = 7;
    static final int FAINTED_ENERGY = 100;
    @Override
    public boolean nextDay(Game g) {
        Building building = getBuilding();
        if (!(building instanceof House)) {
            energy.setMaxEnergy(FAINTED_ENERGY, DURATION_ENERGY);
        }

        energy.nextDay(g);
        defaultMap.nextDay(g);//DON'T UPDATE ANY OTHERRRR MAPPP !!! it's done recursivly in nextDay in map class

        for(Animal animal : animals) {
            animal.nextDay(g);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Player Info:\n");
        sb.append("Username: ").append(user.getUsername()).append("\n");
        sb.append("Current Coordinates: ").append(coord).append("\n");
        sb.append("Energy: ").append(energy.getCurrentEnergy()).append("/").append(energy.getMaxEnergy()).append("\n");
        sb.append("Coins: ").append(getCoins()).append("\n");
        sb.append("Skills: ").append(skill).append("\n");

        sb.append("Animals Owned: ");
        if (animals.isEmpty()) {
            sb.append("None");
        } else {
            for (Animal animal : animals) {
                sb.append(animal.getName()).append(" ");
            }
        }
        sb.append("\n");

        sb.append("Crafting Recipes: ");
        if (getRecipes(RecipeType.CRAFTING).isEmpty()) {
            sb.append("None");
        } else {
            for (Recipe recipe : getRecipes(RecipeType.CRAFTING)) {
                sb.append(recipe.getName()).append(" ");
            }
        }
        sb.append("\n");

//        sb.append("Items in Hand: ").append(itemInHand != null ? itemInHand.getName() : "None").append("\n");

        //TODO ADD THIS
        //sb.append("Relations: ").append(relations.size()).append(" relations\n");
        //sb.append("NPC Friendships: ").append(npcFriendships.size()).append(" friendships\n");

        return sb.toString();
    }

    public void enterBuilding(Building building) {
        locStack.add(Pair.of(getMap(), getCoord()));
        this.building = building;
        setCoord(new Coord(0, 0));
        sprite.setX(getMap().mapType.getDistanceX());
        sprite.setY(getMap().mapType.getDistanceY() + (getMap().getMaxY() - 1)*30);
    }

    public void leave() {
        Pair<Map, Coord> loc = locStack.remove(locStack.size() - 1);
        resetPosition(getMap() , loc);
        setMap(loc.getLeft());
    }

    public void resetPosition(Map map , Pair<Map , Coord> loc){
        Tile tile = loc.getLeft().getCornerOfBuilding(building);
        if(tile == null)
            return;

        sprite.setX(tile.spriteGetter().getX());
        sprite.setY(tile.spriteGetter().getY());

        coord.setX((int) (sprite.getX() - map.mapType.distanceX)/30);
        coord.setY(map.getMaxY() - 1 - (int) (sprite.getY() - map.mapType.distanceY)/30);
    }

    public Building getBuilding() {
        return building;
    }

    public boolean isFainted() {
        if(!faint && energy.getCurrentEnergy() <= 0){
            faint = true;
            sprite.rotate(90);
        }
        return energy.getCurrentEnergy() <= 0;
    }

    public int getSkillExp(SkillType skillType) {
        return skill.getSkillExp(skillType);
    }

    public int getSkillLevel(SkillType skillType) {
        return skill.getSkillLevel(skillType);
    }

    public void setSkillExp(SkillType skillType, int exp) {
        skill.setSkillExp(skillType, exp);
    }

    public void resetEnergy() {
        energy.reset();
    }

    public float getSpeed() {
        return speed;
    }

    public void setShepherdingAnimal(Animal shepherdingAnimal) {
        this.shepherdingAnimal = shepherdingAnimal;
        if(shepherdingAnimal != null) {
            shepherdingAnimal.spriteGetter().setX(sprite.getX());
            shepherdingAnimal.spriteGetter().setY(sprite.getY());
        }
    }

    public Animal getShepherdingAnimal() {
        return shepherdingAnimal;
    }

    public void addQuest() {
        quest++;
    }

    public int getQuest() {
        return quest;
    }

    public int getSkill() {
        return skill.getOverallSkill();
    }

    public void setSuitor(String suitor) {
        this.suitor = suitor;
    }

    public String getSuitor() {
        return suitor;
    }

    public Reaction getReaction() {
        return reaction;
    }

    String defaultReaction = "";
    public void setDefaultReaction(String defaultReaction) {
        this.defaultReaction = defaultReaction;
    }

    public String getDefaultReaction() {
        return defaultReaction;
    }

    public List<MusicData> getMusics() {
        return musics;
    }

    public void addMusic(MusicData music) {
        musics.add(music);
    }

    public int getMapID() {
        return mapID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    private Direction mapToMainDirection(Direction dir) {
        return switch (dir) {
            case NORTH, NORTH_EAST, NORTH_WEST -> Direction.NORTH;
            case SOUTH, SOUTH_EAST, SOUTH_WEST -> Direction.SOUTH;
            case EAST -> Direction.EAST;
            case WEST -> Direction.WEST;
        };
    }

    public void move(Direction dir) {
        Direction mainDir = mapToMainDirection(dir);

        frameTime += Gdx.graphics.getDeltaTime();

        if (frameTime >= frameDuration) {
            frameTime = 0f;
            frameIndex = (frameIndex + 1) % 3;
            sprite.setTexture(getTexturePath(mainDir, frameIndex));
        }
    }

    private String getTexturePath(Direction dir, int frame) {
        return switch (dir) {
            case WEST -> animations.get(0)[frame];
            case EAST -> animations.get(1)[frame];
            case NORTH -> animations.get(2)[frame];
            case SOUTH -> animations.get(3)[frame];
            default -> animations.get(3)[frame];
        };
    }
}
