package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.game.Refrigerator;
import models.sprite.GameSprite;
import models.time.Date;

public class House extends Building {
    Refrigerator refrigerator = new Refrigerator();

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public House() {
        super();
        this.map = (new MapBuilder()).buildHouse(refrigerator);
        String texture = "Textures/Buildings/HouseLevel1png.png";
        sprite = new GameSprite(texture);
        sprite.setSize(30*8 , 30*8);
    }

    @Override
    public void onPlace(Tile tile) {
        tile.setPlacable(this);
        tile.setTileType(TileType.HOUSE);
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
        return "H";
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
