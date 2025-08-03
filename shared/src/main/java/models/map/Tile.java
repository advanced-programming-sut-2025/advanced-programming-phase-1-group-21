package models.map;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import models.DailyUpdate;
import models.animal.Animal;
import models.crop.Harvestable;
import models.crop.PlantedSeed;
import models.Item.Item;
import models.game.Game;
import models.game.Player;
import models.time.Season;
import org.w3c.dom.Text;

import java.io.Serializable;

public class Tile implements DailyUpdate, Serializable {
    private TileType tileType;
    private Placable placable;
    transient private Texture texture = new Texture("Textures/map/SpringBasicTile.png");;
    transient private Sprite sprite = new Sprite(texture);
    transient private Texture onTileTexture;
    transient private Sprite onTileSprite;
    transient private Sprite lightningSprite;

    MapType mapType;

    public Tile() {}

    private Tile(TileType tileType, Placable placable) {
        this.tileType = tileType;
        this.placable = placable;
    }

    public void setTexture(MapType mapType) {
        this.mapType = mapType;
        float x = sprite.getX();
        float y = sprite.getY();
        if(mapType.equals(MapType.HOUSE))
            texture = new Texture("Textures/Flooring/HouseFloor.png");
        else if(tileType.equals(TileType.SIMPLE_STABLE))
            texture = SharedAssetManager.getStable("Simple");
        else if(tileType.equals(TileType.FOOD_STABLE))
            texture = SharedAssetManager.getStable("Food");
        else if(getPlacable(Building.class) != null)
            texture = new Texture("Textures/map/SpringBasicTile.png");
        else if(isEmpty())
            texture = new Texture("Textures/map/SpringBasicTile.png");

        if(tileType.getTextureAddress() != null)
            onTileTexture = new Texture(tileType.getTextureAddress());
        else if(placable!= null && placable.getTexture() != null) {
            onTileTexture = placable.getTexture();
        }


        sprite = new Sprite(texture);
        sprite.setSize(30 , (float) (30 * texture.getHeight()) / texture.getWidth());
        sprite.setX(x);
        sprite.setY(y);

        if(onTileTexture != null) {
            onTileSprite = new Sprite(onTileTexture);
            onTileSprite.setX(x);
            onTileSprite.setY(y);
            if(placable != null && placable.spriteGetter() != null){
                placable.spriteGetter().setX(x);
                placable.spriteGetter().setY(y);
            }
        }
    }

    public void loadOnTileTexture(){
        float x = sprite.getX();
        float y = sprite.getY();
        if(tileType.getTextureAddress() != null)
            onTileTexture = new Texture(tileType.getTextureAddress());
        else if(placable!= null && placable.getTexture() != null)
            onTileTexture = placable.getTexture();
        if(onTileTexture != null) {
            onTileSprite = new Sprite(onTileTexture);
            onTileSprite.setX(x);
            onTileSprite.setY(y);
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite spriteGetter(){
        return sprite;
    }

    public Sprite getOnTileSprite() {
        return onTileSprite;
    }

    public static Tile createEmpty() {
        return new Tile(TileType.UNPLOWED, null);
    }

    public boolean isEmpty() {
        return placable == null && tileType == TileType.UNPLOWED;
    }

    public boolean isWalkable() {
        return placable == null || placable.isWalkable();
    }

    public void water() {
        if (placable instanceof PlantedSeed) {
            System.err.println("WATERED!");
            ((PlantedSeed) placable).water();
        }
    }

    public Item harvest(Player player) {
        if (placable instanceof Harvestable) {
            Item item = ((Harvestable) placable).harvest(player);
            if (placable instanceof PlantedSeed && ((PlantedSeed) placable).isOneTime()) {
                placable = null;
            }
            return item;
        }
        return null;
    }

    public boolean nextDay(Game g) {
        if (g.getWeather() == Weather.RAINY) {
            water();
        }
        if (placable instanceof DailyUpdate) {
            boolean shouldRemove = ((DailyUpdate) placable).nextDay(g);
            if (shouldRemove) {
                placable = null;
                tileType = TileType.UNPLOWED;
            }
        }
        setTexture(g.getSeason());
        return true;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public <T extends Placable> T getPlacable(Class<T> type) {
        return type.isInstance(placable) ? type.cast(placable) : null;
    }

    public boolean isPlacable() {
        return placable != null;
    }

    public void setPlacable(Placable placable) {
        this.placable = placable;
        if (placable == null) {
            setTileType(TileType.UNPLOWED);
            onTileTexture = null;
            onTileSprite = null;
        }
        else {
            onTileTexture = placable.getTexture();
            if(onTileTexture != null) {
                onTileSprite = new Sprite(onTileTexture);
                onTileSprite.setX(sprite.getX());
                onTileSprite.setY(sprite.getY());
                if(placable.spriteGetter() != null && placable.spriteGetter() != null){
                    placable.spriteGetter().setX(sprite.getX());
                    placable.spriteGetter().setY(sprite.getY());
                }
            }
        }
    }

    public String getSprite() {
        if (placable != null) return placable.getSprite();
        return "" + tileType.getSymbol();
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileType=" + tileType +
                ", placable=" + (placable != null ? placable.getClass().getSimpleName() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Tile other = (Tile) obj;

        if (tileType != other.tileType) return false;
        if (placable == null && other.placable == null) return true;
        if (placable != null && other.placable != null) {
            return placable.equals(other.placable);
        }
        return false;
    }

    public void setTexture(Season season){
        if(!mapType.equals(MapType.VILLAGE) && !mapType.equals(MapType.FARM))
            return;
        else if(season.equals(Season.SPRING))
            texture = SharedAssetManager.getTile("Spring");
        else if(season.equals(Season.WINTER))
            texture = SharedAssetManager.getTile("Winter");
        else if(season.equals(Season.AUTUMN))
            texture = SharedAssetManager.getTile("Fall");
        else if(season.equals(Season.SUMMER))
            texture = SharedAssetManager.getTile("Summer");

        sprite.setTexture(texture);
    }

    public void startLightning(){
        lightningSprite = new Sprite(SharedAssetManager.getLightning());
        lightningSprite.setY(1052);
        lightningSprite.setX(sprite.getX());
        lightningSprite.setSize(100 , 3);
    }

    public void runLightning(){
        if(lightningSprite.getY() > sprite.getY()){
            lightningSprite.setY(lightningSprite.getY() * (float) 0.9);
            lightningSprite.setSize(100, 1055 - lightningSprite.getY());
        }
        else
            lightningSprite = null;
    }

    public Sprite getLightningSprite() {
        return lightningSprite;
    }

    public void resetTile(){
        tileType = null;
        placable = null;
        onTileSprite = null;
        onTileTexture = null;
    }
}
