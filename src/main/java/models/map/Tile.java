package models.map;

import models.animal.Animal;
import models.crop.Tree;
import models.game.Refrigerator;

public class Tile {
    private Coord cord;
    private Animal animal = null;
    private boolean isHouse = false;
    private boolean isGreenHouse = false;
    private boolean isLake = false;
    private boolean isMines = false;
    private boolean isBarn = false;
    private boolean isCoop = false;
    private boolean shokhmi = false;
    private Tree tree = null;
    private Foraging foraging = null;
    private Refrigerator refrigerator = null;
    private boolean isDoor = false;

    public Tile(Coord cord, Animal animal, Tree tree, Foraging foraging) {
        this.cord = cord;
        this.animal = animal;
        this.tree = tree;
        this.foraging = foraging;
    }

    public Coord getCord() {
        return cord;
    }

    public Animal getAnimal() {
        return animal;
    }

    public boolean isHouse() {
        return isHouse;
    }

    public boolean isGreenHouse() {
        return isGreenHouse;
    }

    public boolean isLake() {
        return isLake;
    }

    public boolean isMines() {
        return isMines;
    }

    public Tree getTree() {
        return tree;
    }

    public Foraging getForaging() {
        return foraging;
    }

    public void setCord(Coord cord) {
        this.cord = cord;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }

    public void setGreenHouse(boolean greenHouse) {
        isGreenHouse = greenHouse;
    }

    public void setLake(boolean lake) {
        isLake = lake;
    }

    public void setMines(boolean mines) {
        isMines = mines;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public void setForaging(Foraging foraging) {
        this.foraging = foraging;
    }

    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public void setDoor(boolean door) {
        isDoor = door;
    }

    public boolean isDoor() {
        return isDoor;
    }

    public void setBarn(boolean barn) {
        isBarn = barn;
    }

    public void setCoop(boolean coop) {
        isCoop = coop;
    }

    public boolean isBarn() {
        return isBarn;
    }

    public boolean isCoop() {
        return isCoop;
    }

    public void setShokhmi(boolean shokhmi) {
        this.shokhmi = shokhmi;
    }

    public boolean tileIsEmpty(){
        if(tree!=null)
            return false;
        if(animal!=null)
            return false;
        if(isMines)
            return false;
        if(isGreenHouse)
            return false;
        if(isHouse)
            return false;
        if(isLake)
            return false;
        if(foraging!=null)
            return false;
        if(isBarn)
            return false;
        if(isCoop)
            return false;
        return true;
    }
}
