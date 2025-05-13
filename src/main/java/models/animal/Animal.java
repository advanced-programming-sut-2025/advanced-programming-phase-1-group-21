package models.animal;

import models.Item.Item;
import models.map.Placable;
import models.map.Tile;

import java.util.ArrayList;

public class Animal implements Placable {
    private String name;
    private AnimalTypes animalType;
    private int price;
    private int friendship;
    private boolean isFeedToday = false;
    private boolean isTodayPet = false;
    private boolean isOut = false;
    private ArrayList<String> products = new ArrayList<>();
    private String todayProduct ;
    private int produceStage;
    private Tile tile;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isTodayPet() {
        return isTodayPet;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public void setTodayPet(boolean todayPet) {
        isTodayPet = todayPet;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTodayProduct() {
        return todayProduct;
    }

    public void setTodayProduct(String todayProduct) {
        this.todayProduct = todayProduct;
    }

    public void pet(){
        this.friendship += 15;
        isTodayPet = true;
    }

    public boolean isFeedToday() {
        return isFeedToday;
    }

    public void setFeedToday(boolean feedToday) {
        this.isFeedToday = feedToday;
    }

    public void shepherd(){
        this.friendship += 8;
        isFeedToday = true;
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

    public Item collectProduce(){
        if(todayProduct == null)
            return null;
        return Item.build(todayProduct , 1);
    }


    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "A";
    }
}
