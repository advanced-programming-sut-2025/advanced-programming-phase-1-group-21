package models.map;

import models.animal.Animal;
import models.crop.Tree;
import models.game.Player;
import models.game.Refrigerator;

public class Tile {
    private Coord cord;
    private Animal animal = null;
    private boolean isHouse = false;
    private boolean isGreenHouse = false;
    private boolean isLake = false;
    private boolean isMines = false;
    private Tree tree = null;
    private Foraging foraging = null;
    private Refrigerator refrigerator = null;

    public Tile(Coord cord, Animal animal, boolean isHouse, boolean isGreenHouse, boolean isLake, boolean isMines, Tree tree, Foraging foraging) {
        this.cord = cord;
        this.animal = animal;
        this.isHouse = isHouse;
        this.isGreenHouse = isGreenHouse;
        this.isLake = isLake;
        this.isMines = isMines;
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
}
