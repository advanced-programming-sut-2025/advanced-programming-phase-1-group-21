package models.animal;

import models.Item.Item;
import models.data.AnimalData;
import models.map.AnimalHouseType;
import models.map.Placable;
import models.map.Tile;
import models.map.TileType;
import models.time.Season;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class Animal implements Placable {
    private String name;
    private AnimalTypes animalType;
    private int price;
    private int friendship;
    private boolean isFeedToday = false;
    private boolean isTodayPet = false;
    private boolean isOut = false;
    private ArrayList<String> products;
    private ArrayList<Season> productSeasons;
    private String todayProduct ;
    private int produceStage;
    private String toolName;
    private int productTime;
    private ArrayList<AnimalHouseType> houseTypes;
    private String house;

    public Animal(String name, AnimalData animalData) {
        products = animalData.getProducts();
        this.name = name;
        price = animalData.getPrice();
        animalType = AnimalTypes.fromName(name);
        this.friendship = 0;
        this.toolName = animalData.getToolName();
        this.productTime = animalData.getProductTime();
        this.productSeasons = animalData.getSeasons();
        this.house = animalData.getHouse();
        this.houseTypes = animalData.getHouseType();
    }

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

    public void pet() {
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

    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }

    public Item collectProduce(){
        if(todayProduct == null)
            return null;
        return Item.build(todayProduct , 1);
    }

    @Override
    public TileType getTileType() {
        return TileType.ANIMAL;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "A";
    }

    public boolean canEnterHouseType(AnimalHouseType animalHouseType) {
        return houseTypes.contains(animalHouseType);
    }

    public boolean canEnterHouse(String name) {
        return StringUtils.containsIgnoreCase(name, house);
    }
}
