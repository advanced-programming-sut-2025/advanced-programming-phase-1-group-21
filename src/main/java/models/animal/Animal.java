package models.animal;

import models.map.Tile;

public class Animal {
    private String name;
    private AnimalTypes animalType;
    private int friendship;
    private int produceStage;
    private Tile tile;

    public String getName() {
        return name;
    }

    public void pet(){
        this.friendship += 15;
    }

    public void shepherd(){
        this.friendship += 8;
    }

    public AnimalTypes getAnimalType() {
        return animalType;
    }

    public int getFriendship() {
        return friendship;
    }

    public int getProduceStage() {
        return produceStage;
    }

    public Tile getTile() {
        return tile;
    }

    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }


}
