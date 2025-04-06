package models.game;

import java.util.ArrayList;
import java.util.List;
import models.user.User;

public class Inventory {
    static final int MAXIMUM_SIZE = 12;
    List<Item> items;
    InventoryType inventoryType;
    TrashcanType trashcanType;
    Player owner;

    public Inventory(Player owner) {
        items = new ArrayList<>();
        inventoryType = InventoryType.PRIMITIVE;
        trashcanType = TrashcanType.PRIMITIVE;
        this.owner = owner;
    }

    public void upgradeSize(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void upgradeTrashcan(TrashcanType trashcanType) {
        this.trashcanType = trashcanType;
    }

    public int getMaximumSize() {
        return inventoryType.getMaximumSize();
    }

    public Result<Void> addItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO
        /*
        if (items.size() + 1 > getMaximumSize());
            //return Result.failure(new Error("Inventory is full"));
        items.add(item);
        */
    }

    public void removeItem(Item item) {
        owner.addCoins(trashcanType.getRepay(item.getCost()));
        items.remove(item);
    }

    public Item getItemByName(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItemsByType(ItemType itemType) {
        List<Item> itemsByType = new ArrayList<>();
        for (Item item : items) {
            if (item.getItemType() == itemType) {
                itemsByType.add(item);
            }
        }
        return itemsByType;
    }
}
