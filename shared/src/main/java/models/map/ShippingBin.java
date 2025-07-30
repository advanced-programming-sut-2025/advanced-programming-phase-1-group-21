package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.DailyUpdate;
import models.Item.Item;
import models.game.Game;
import models.game.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShippingBin implements Placable, DailyUpdate, Serializable {
    List<Item> toSell = new ArrayList<>();
    Map<Item, Player> map = new HashMap<>();

    public void add(Item item, Player player) {
        toSell.add(item);
        map.put(item, player);
    }

    @Override
    public boolean nextDay(Game g) {
        for (Item item : toSell) {
            Player owner = map.get(item);
            System.err.println("tasfie hesab anjam shod!! " + owner.getUser().getUsername() + " " + item);
            owner.getInventory().changeCoin(item.getPrice() * item.getAmount());
        }
        toSell.clear();
        map.clear();
        return false;
    }

    @Override
    public TileType getTileType() {
        return TileType.SHIPPING_BIN;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "S";
    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public Sprite spriteGetter() {
        return null;
    }
}
