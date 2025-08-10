package models.map;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.game.Game;
import models.sprite.GameSprite;
import models.time.Date;

public class GreenHouse extends Building {
    private boolean isBuild = false;

    public GreenHouse() {
        this.map = (new MapBuilder()).buildGreenHouse();
        sprite = new GameSprite(SharedAssetManager.getGreenHousePath("Destroyed"));
        sprite.setSize(210 , 300);
    }

    public void setBuild(boolean build) {
        sprite.setTexture(SharedAssetManager.getGreenHouse("Normal"));
        isBuild = build;
    }

    public boolean isBuild() {
        return isBuild;
    }

    @Override
    public TileType getTileType() {
        return TileType.GREEN_HOUSE;
    }

    @Override
    public boolean canEnter(Date date) {
        return isBuild;
    }

    @Override
    public String getSprite() {
        return "G";
    }

    @Override
    public String getTexture() {
        return null;
    }

    @Override
    public Sprite spriteGetter() {
        return null;
    }

    @Override
    public boolean nextDay(Game g) {
        g.setWeather(Weather.SUNNY); //GreenHouse Weather is always sunny
        return super.nextDay(g);
    }
}
