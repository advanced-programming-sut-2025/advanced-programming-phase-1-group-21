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

import java.util.ArrayList;
import java.util.Random;

public class Player implements DailyUpdate {
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
    private TrashCanType trashCanType;
    private Item itemInHand;
    private ArrayList<NPCFriendship> npcFriendships;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Skill skill = new Skill();

    public ArrayList<Recipe> getRecipes(RecipeType type) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getType() == type) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addRecipes(Recipe recipe) {
        recipes.add(recipe);
    }

    public Recipe getRecipeByName(String name, RecipeType type) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name) && recipe.getType() == type) {
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
        building = null;
        this.map = map;
    }

    public Map getMap() {
        if (building == null) return map;
        return building.getMap();
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

    public ArrayList<Coord> getNeighborCoord() {
        ArrayList<Coord> output = new ArrayList<>();
        Coord playerCord = App.game.getCurrentPlayer().getCoord();
        for(Direction direction : Direction.values()) {
            Coord c = direction.getCoord().addCoord(playerCord);
            Tile tile = App.game.getCurrentPlayerMap().getTile(c);
            if (tile == null)
                continue;
            output.add(c);
        }
        return output;
    }

    public boolean weAreNextToEachOther(Player otherPlayer) {
        if(!App.game.getCurrentPlayer().getMap().equals(otherPlayer.getMap()))
            return false;
        Coord otherPlayerCord = otherPlayer.getCoord();
        Coord myCoord = App.game.getCurrentPlayer().getCoord();

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
        defaultMap.nextDay();//DON'T UPDATE ANY OTHERRRR MAPPP !!! it's done recursivly in nextDay in map class

        for(Animal animal : animals) {
            if(!animal.isFeedToday())
                animal.setFriendship(Math.max(animal.getFriendship() - 20 , 0));
            if(animal.isOut())
                animal.setFriendship(Math.max(animal.getFriendship() - 20 , 0));
            if(!animal.isTodayPet())
                animal.setFriendship(Math.max(0 , (animal.getFriendship()/200) - 10));
            if(animal.isFeedToday()){
                if(animal.getFriendship() < 100){
                    animal.setTodayProduct(animal.getProducts().get(0));
                }
                else{
                    Random rand = new Random();
                    double randomDouble = 0.5 + Math.random();
                    int RandomInt = (int) (animal.getFriendship() + (150 * randomDouble));
                    int randomVariable = rand.nextInt(1500);
                    if(randomVariable < RandomInt){
                        animal.setTodayProduct(animal.getProducts().get(1 % animal.getProducts().size()));
                    }
                    else
                        animal.setTodayProduct(animal.getProducts().get(0));
                }
            }
            animal.setOut(false);
            animal.setFeedToday(false);
            animal.setTodayPet(false);
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
        this.building = building;
        setCoord(new Coord(0, 0));
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
}
