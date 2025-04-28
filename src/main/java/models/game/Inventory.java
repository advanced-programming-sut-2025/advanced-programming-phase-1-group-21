package models.game;

import models.result.Result;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static final int MAXIMUM_ITEM_PER_SLOT = 999;
    List<Item> items;
    InventoryType inventoryType;
    ToolMaterialType trashcanType;
    Player owner;

    public Inventory(Player owner) {
        items = new ArrayList<>();
        inventoryType = InventoryType.PRIMITIVE;
        trashcanType = ToolMaterialType.PRIMITIVE;
        this.owner = owner;
    }

    public void upgradeSize(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void upgradeTrashcan(ToolMaterialType trashcanType) {
        this.trashcanType = trashcanType;
    }

    public int getMaximumSize() {
        return inventoryType.getMaximumSize();
    }

    public Result<Item> addItem(Item item) {
        for (Item i : items) {
            if (i.name.equals(item.name)) {
                int newSize = Math.min(i.getAmount() + item.getAmount(), MAXIMUM_ITEM_PER_SLOT);
                int remains = item.getAmount() + i.getAmount() - newSize;
                i.setAmount(newSize);
                item.setAmount(remains);
                if (item.getAmount() == 0) {
                    return Result.success("Adding " + item.name + " to the inventory was successful.");
                }
            }
        }
        if (items.size() == inventoryType.getMaximumSize()) {
            return Result.success(item, "Adding " + item.name + " to the inventory was not completely. " + item.getAmount() + " remains.");
        }
        items.add(item);
        return Result.success("Adding " + item.name + " to the inventory was successful.");
    }

    public void removeItem(Item item) {
        // If you're using TrashCan, don't forget to get the money and use it in the player object;

        for (Item i : items)
            if (i.name.equals(item.name)) {
                int del = Math.min(i.getAmount(), item.getAmount());
                item.changeAmount(-del);
                i.changeAmount(-del);
                if (i.getAmount() == 0)
                    items.remove(i);
                if (item.getAmount() == 0)
                    break;
            }
    }

    public Item getItem(String name) {
        for (Item item : items)
            if (item.getName().equals(name))
                return item;
        return null;
    }


    // What is the purpose of these function?
    public List<Item> getItemsByType(ItemType itemType) {
        List<Item> itemsByType = new ArrayList<>();
        for (Item item : items)
            if (item.getItemType() == itemType)
                itemsByType.add(item);
        return itemsByType;
    }
}
