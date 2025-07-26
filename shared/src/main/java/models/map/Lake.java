package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.time.Date;

public class Lake extends Building {

    public Lake() {
        texture = new Texture("assets/Textures/Buildings/lake.png");
        sprite = new Sprite(texture);
        sprite.setSize(150 , 120);
    }

    @Override
    public void onPlace(Tile tile) {
        tile.setPlacable(this);
        tile.setTileType(TileType.BARN);
    }

    @Override
    public TileType getTileType() {
        return TileType.ARTISAN;
    }

    @Override
    public boolean canEnter(Date date) {
        return false;
    }

    @Override
    public String getSprite() {
        return "~";
    }

    @Override
    public Texture getTexture() {
        return null;
    }
}
