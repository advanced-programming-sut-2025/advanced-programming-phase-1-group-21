package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.game.Game;
import models.time.Date;

public class GreenHouse extends Building {
    private boolean isBuild = false;

    public GreenHouse() {
        this.map = (new MapBuilder()).buildGreenHouse();
        texture = new Texture("assets/Textures/Buildings/DestroyedGreenHouse.png");
        sprite = new Sprite(texture);
        sprite.setSize(7 , 10);
        sprite.setSize(210 , 300);
    }

    public void setBuild(boolean build) {
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
    public boolean nextDay(Game g) {
        g.setWeather(Weather.SUNNY); //GreenHouse Weather is always sunny
        return super.nextDay(g);
    }
}
