package models.game;

import models.App;
import models.DailyUpdate;
import models.Item.Item;
import models.Item.Recipe;
import models.Item.RecipeType;
import models.animal.Animal;
import models.map.*;
import models.map.Coord;
import models.map.Map;
import models.skill.Skill;
import models.skill.SkillType;
import models.user.User;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Player implements DailyUpdate, Serializable {
    static final int MAX_ENERGY = 200;

    private User user;

    private Building building;
    private Map map;
    private Map defaultMap;
    private Coord coord = new Coord(0 , 0);
    private int agronomicAbility = 0;
    private int miningAbility = 0;
    private int foragingAbility = 0;
    private int fishingAbility = 0;
    private ArrayList<Animal> animals = new ArrayList<>();
    private Energy energy;
    private Inventory inventory;
    private Item itemInHand;
    private ArrayList<NPCFriendship> npcFriendships;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Skill skill = new Skill();
    private ArrayList<String> notifications = new ArrayList<>();


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

    public Player(User user, Map defaultMap) {
        this.user = user;
        this.inventory = Inventory.buildPlayerInventory();
        inventory.addItem(Game.getCoinItem(0));
        energy = new Energy();
        this.defaultMap = defaultMap;
        this.map = defaultMap;

        House house = defaultMap.getBuilding(House.class);

        this.coord = defaultMap.getCoord(house);
        this.coord = coord.addCoord(new Coord(-1, 0)); //left of it!

        enterBuilding(defaultMap.getBuilding(House.class));
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

    public void setMap(Map map) {
        building = null;
        this.map = map;
    }

    public Map getMap() {
        if (building == null) return map;
        return building.getMap();
    }

    public ArrayList<Tile> getNeighborTiles(){
        ArrayList<Tile> output = new ArrayList<>();
        Coord playerCord = App.getInstance().game.getCurrentPlayer().getCoord();
        for(Direction direction : Direction.values()) {
            Tile tile = App.getInstance().game.getCurrentPlayerMap().getTile(direction.getCoord().addCoord(playerCord));
            if (tile == null)
                continue;
            output.add(tile);
        }
        return output;
    }

    public ArrayList<Coord> getNeighborCoord() {
        ArrayList<Coord> output = new ArrayList<>();
        Coord playerCord = App.getInstance().game.getCurrentPlayer().getCoord();
        for(Direction direction : Direction.values()) {
            Coord c = direction.getCoord().addCoord(playerCord);
            Tile tile = App.getInstance().game.getCurrentPlayerMap().getTile(c);
            if (tile == null)
                continue;
            output.add(c);
        }
        return output;
    }

    public boolean weAreNextToEachOther(Player otherPlayer) {
        if(!App.getInstance().game.getCurrentPlayer().getMap().equals(otherPlayer.getMap()))
            return false;
        Coord otherPlayerCord = otherPlayer.getCoord();
        Coord myCoord = App.getInstance().game.getCurrentPlayer().getCoord();

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
        sb.append("Agronomic Ability: ").append(agronomicAbility).append("\n");
        sb.append("Mining Ability: ").append(miningAbility).append("\n");
        sb.append("Foraging Ability: ").append(foragingAbility).append("\n");
        sb.append("Fishing Ability: ").append(fishingAbility).append("\n");
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

        sb.append("Items in Hand: ").append(itemInHand != null ? itemInHand.getName() : "None").append("\n");

        //TODO ADD THIS
        //sb.append("Relations: ").append(relations.size()).append(" relations\n");
        //sb.append("NPC Friendships: ").append(npcFriendships.size()).append(" friendships\n");

        return sb.toString();
    }

    public void enterBuilding(Building building) {
        locStack.add(Pair.of(getMap(), getCoord()));
        this.building = building;
        setCoord(new Coord(0, 0));
    }

    public void leave() {
        Pair<Map, Coord> loc = locStack.removeLast();
        setMap(loc.getLeft());
        setCoord(loc.getRight());
    }

    public Building getBuilding() {
        return building;
    }

    public boolean isFainted() {
        return energy.getCurrentEnergy() == 0;
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
}
