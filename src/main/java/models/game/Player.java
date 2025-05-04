package models.game;

import models.App;
import models.DailyUpdate;
import models.animal.Animal;
import models.map.*;
import models.map.Coord;
import models.map.Map;
import models.user.User;

import java.util.ArrayList;

public class Player implements DailyUpdate {
    static final int MAX_ENERGY = 200;

    private User user;

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

    public Player(User user) {
        this.user = user;
        this.inventory = new Inventory();
        inventory.addItem(Game.getCoinItem(0));
        energy = new Energy();
    }

    public Map getDefaultMap() {
        return defaultMap;
    }

    public void setDefaultMap(Map defaultMap) {
        this.defaultMap = defaultMap;
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
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Tile> getNeighborTiles(){
        ArrayList<Tile> output = new ArrayList<>();
        Coord playerCord = App.game.getCurrentPlayer().getCoord();
        for(Direction direction : Direction.values()) {
            Tile tile = App.game.getCurrentPlayerMap().getTile(direction.getCoord().addCoord(playerCord));
            if (tile == null)
                continue;
            output.add(tile);
        }
        return output;
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

    @Override
    public boolean nextDay() {
        return false;
    }
}
