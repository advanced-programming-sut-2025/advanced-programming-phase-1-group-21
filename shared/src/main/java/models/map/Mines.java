package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.time.Date;

import java.util.Random;

public class Mines extends Building {

    public Mines(Random rand) {
        map = (new MapBuilder()).buildMines(rand);
        texture = new Texture("assets/Textures/Buildings/mines.png");
        sprite = new Sprite(texture);
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
    public Texture getTexture() {
        return null;
    }
}
