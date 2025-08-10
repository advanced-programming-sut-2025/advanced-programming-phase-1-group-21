package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.sprite.GameSprite;
import models.time.Date;

public class Lake extends Building {

    public Lake() {
        String texture = "Textures/Buildings/lake.png";
        sprite = new GameSprite(texture);
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
    public String getTexture() {
        return null;
    }

    @Override
    public Sprite spriteGetter() {
        return null;
    }
}
