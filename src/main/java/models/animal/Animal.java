package models.animal;

import models.App;
import models.DailyUpdate;
import models.Item.Item;
import models.data.AnimalData;
import models.game.Game;
import models.map.AnimalHouseType;
import models.map.Placable;
import models.map.Tile;
import models.map.TileType;
import models.time.Date;
import models.time.Season;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements Placable, DailyUpdate {
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
        produceStage = animalData.getProductTime();
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

    @Override
    public String toString() {
        return String.format(
                "Animal{name='%s', type=%s, friendship=%d, fed=%s, pet=%s, outside=%s, todayProduct=%s}",
                name,
                animalType,
                friendship,
                isFeedToday ? "yes" : "no",
                isTodayPet ? "yes" : "no",
                isOut ? "yes" : "no",
                todayProduct == null ? "none" : todayProduct
        );
    }

    private Date lastNextDayDate = null;

    @Override
    public boolean nextDay(Game g) {
        if (lastNextDayDate != null && lastNextDayDate == g.getGameDate())
            return false;
        lastNextDayDate = g.getGameDate();
        if(!isFeedToday())
            setFriendship(Math.max(getFriendship() - 20 , 0));
        if(isOut())
            setFriendship(Math.max(getFriendship() - 20 , 0));
        if(!isTodayPet())
            setFriendship(Math.max(0 , (getFriendship()/200) - 10));
        if(isFeedToday() && (produceStage == 0) && seasonIsTrue()){
            if(getFriendship() < 100){
                setTodayProduct(getProducts().get(0));
            }
            else{
                Random rand = new Random();
                double randomDouble = 0.5 + Math.random();
                int RandomInt = (int) (getFriendship() + (150 * randomDouble));
                int randomVariable = rand.nextInt(1500);
                if(randomVariable < RandomInt){
                    setTodayProduct(getProducts().get(1 % getProducts().size()));
                }
                else
                    setTodayProduct(getProducts().get(0));
            }
        }
        setOut(false);
        setFeedToday(false);
        setTodayPet(false);
        produceStage = (produceStage + 1)%productTime;
        return false;
    }

    public boolean seasonIsTrue(){
        for(Season season : productSeasons){
            if(App.game.getSeason().equals(season))
                return true;
        }
        return false;
    }
}
