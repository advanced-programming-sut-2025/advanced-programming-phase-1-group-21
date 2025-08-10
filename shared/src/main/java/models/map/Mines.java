package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.sprite.GameSprite;
import models.time.Date;

import java.util.Random;

public class Mines extends Building {

    public Mines() {}

    public Mines(Random rand) {
        map = (new MapBuilder()).buildMines(rand);
        sprite = new GameSprite("Textures/Buildings/mines.png");
        sprite.setSize(30*5 , 30*5);
    }

    @Override
    public TileType getTileType() {
        return TileType.ARTISAN;
    }

    @Override
    public boolean canEnter(Date date) {
        return true;
    }

    @Override
    public String getSprite() {
        return "M";
    }

    @Override
    public String getTexture() {
        return null;
    }

    @Override
    public Sprite spriteGetter() {
        return null;
    }
}
