package models.map;

import models.DailyUpdate;
import models.Item.Item;
import models.game.Game;
import models.game.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShippingBin implements Placable, DailyUpdate, Serializable {
    List<Item> toSell = new ArrayList<>();

    public void add(Item item) {
        toSell.add(item);
    }

    @Override
    public boolean nextDay(Game g) {
        Player owner = g.getCurrentPlayer();
        for (Item item : toSell) {
            System.err.println("tasfie hesab anjam shod!! " + owner.getUser().getUsername() + " " + item);
            owner.getInventory().changeCoin(item.getPrice() * item.getAmount());
        }
        toSell.clear();
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
}
