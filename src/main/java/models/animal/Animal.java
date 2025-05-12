package models.animal;

import models.Item.Item;
import models.map.Placable;
import models.map.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Animal implements Placable {
    private String name;
    private AnimalTypes animalType;
    private int price;
    private int friendship;
    private boolean ifFeedToday = false;
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
    }

    public boolean isIfFeedToday() {
        return ifFeedToday;
    }

    public void setIfFeedToday(boolean ifFeedToday) {
        this.ifFeedToday = ifFeedToday;
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
