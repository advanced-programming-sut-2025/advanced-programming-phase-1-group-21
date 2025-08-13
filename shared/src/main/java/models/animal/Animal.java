package models.animal;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.AnimalData;
import models.DailyUpdate;
import models.Item.Item;
import models.game.Game;
import models.map.AnimalHouseType;
import models.map.Placable;
import models.map.TileType;
import models.sprite.GameSprite;
import models.time.Date;
import models.time.Season;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Animal implements Placable, DailyUpdate, Serializable {
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
    private AnimalData thisAnimalData;
    private float spriteX;
    private float spriteY;
    private transient GameSprite sprite;
    private transient String texture;
    transient private String animalReact;
    transient private GameSprite animalReactSprite;
    private float animationRunTime;

    public Animal() {}

    public Animal(String name, AnimalData animalData) {
        products = animalData.getProducts();
        this.name = name;
        price = animalData.getPrice();
        animalType = AnimalTypes.fromName(animalData.getName());
        this.friendship = 0;
        this.toolName = animalData.getToolName();
        this.productTime = animalData.getProductTime();
        this.productSeasons = animalData.getSeasons();
        this.house = animalData.getHouse();
        this.houseTypes = animalData.getHouseType();
        produceStage = animalData.getProductTime();
        thisAnimalData = animalData;
        texture = animalData.getTextureAddress();
        sprite = new GameSprite(texture);
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
        setPetAnimation();
        isTodayPet = true;
    }

    public boolean isFeedToday() {
        return isFeedToday;
    }

    public void setFeedToday(boolean feedToday) {
        setFeedAnimation();
        this.isFeedToday = feedToday;
    }

    public void shepherd(){
        this.friendship += 8;
        isOut = true;
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

    @Override
    public String getTexture() {
        return texture;
    }

    @Override
    public Sprite spriteGetter() {
        return this.sprite;
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
        produceStage = (produceStage + 1)%productTime;
        if (lastNextDayDate != null && lastNextDayDate == g.getGameDate())
            return false;
        lastNextDayDate = g.getGameDate();
        if(!isFeedToday())
            setFriendship(Math.max(getFriendship() - 20 , 0));
        if(isOut())
            setFriendship(Math.max(getFriendship() - 20 , 0));
        if(!isTodayPet())
            setFriendship(Math.max(0 , (getFriendship()/200) - 10));
        if(isFeedToday() && (produceStage == 0) && seasonIsTrue(g.getSeason())){
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

        sprite.setX(sprite.getX() - 30);
        sprite.setY(sprite.getY() + 30);
        return false;
    }

    public boolean seasonIsTrue(Season season){
        for(Season s : productSeasons){
            if(season.equals(s))
                return true;
        }
        return false;
    }

    public float getSpriteX() {
        spriteX = sprite.getX();
        return spriteX;
    }

    public float getSpriteY() {
        spriteY = sprite.getY();
        return spriteY;
    }

    public void setPetAnimation(){
        animalReact = SharedAssetManager.getHeartPath();
        if(animalReactSprite == null)
            animalReactSprite = new GameSprite(animalReact);
        animalReactSprite.setTexture(animalReact);
        animalReactSprite.setSize(30 , 30);
        animalReactSprite.setX(sprite.getX() + 20);
        animalReactSprite.setY(sprite.getY() + 70);
        animationRunTime = 1;
    }

    public void setFeedAnimation(){
        animalReact = SharedAssetManager.getFoodEmojiPath();
        if(animalReactSprite == null)
            animalReactSprite = new GameSprite(animalReact);
        animalReactSprite.setTexture(animalReact);
        animalReactSprite.setSize(30 , 30);
        animalReactSprite.setX(sprite.getX() + 20);
        animalReactSprite.setY(sprite.getY() + 70);
        animationRunTime = 1;
    }

    public void runPetAnimation(){
        animalReactSprite.setAlpha(animationRunTime);
        animationRunTime -= (float) 0.002;
        animalReactSprite.setX(getSpriteX() + 20);
        animalReactSprite.setY(getSpriteY() + 70);
        if(animationRunTime <= 0){
            animationRunTime = 0;
            animalReact = null;
            animalReactSprite = null;
        }
    }

    public Sprite getAnimalReactSprite() {
        return animalReactSprite;
    }
}
