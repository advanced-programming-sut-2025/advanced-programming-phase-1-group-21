package models.game;

import models.App;
import models.Tool.Axe;
import models.Tool.Tool;
import models.Tool.ToolMaterialType;
import models.Tool.ToolType;
import models.result.Result;
import models.result.errorTypes.GameError;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Inventory {
    public static final int MAXIMUM_ITEM_PER_SLOT = 999;
    List<Item> items;
    InventoryType inventoryType;
    ToolMaterialType trashcanType;
    ArrayList<Tool> tools;

    public Inventory() {
        items = new ArrayList<>();
        inventoryType = InventoryType.PRIMITIVE;
        trashcanType = ToolMaterialType.PRIMITIVE;
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
            if (i.getName().equals(item.getName())) {
                int newSize = Math.min(i.getAmount() + item.getAmount(), MAXIMUM_ITEM_PER_SLOT);
                int remains = item.getAmount() + i.getAmount() - newSize;
                i.setAmount(newSize);
                item.setAmount(remains);
                if (item.getAmount() == 0) {
                    return Result.success("Adding " + item.getName() + " to the inventory was successful.");
                }
            }
        }
        if (items.size() == inventoryType.getMaximumSize()) {
            return Result.success(item, "Adding " + item.getName() + " to the inventory was not completely. " + item.getAmount() + " remains.");
        }
        items.add(item);
        return Result.success("Adding " + item.getName() + " to the inventory was successful.");
    }

    private static boolean removeItemFromList(Item item, List<Item> items) {
        // If you're using TrashCan, don't forget to get the money and use it in the player object;

        for (Item i : items)
            if (i.getName().equals(item.getName())) {
                int del = Math.min(i.getAmount(), item.getAmount());
                item.changeAmount(-del);
                i.changeAmount(-del);
                if (i.getAmount() == 0)
                    items.remove(i);
                if (item.getAmount() == 0)
                    break;
            }
        return item.getAmount() == 0; //If == 0 -> All were removed
    }

//    public boolean removeItem(Item item) {
//        return removeItemFromList(item, items);
//    }

    public static boolean removeItemsList(List<Item> itemsToRemove, List<Item> items) {
        for (Item i : itemsToRemove) {
            if (!removeItemFromList(i, items))
                return false;
        }
        return true;
    }

    public void removeItemList(List<Item> itemsToRemove) {
        boolean can = removeItemsList(itemsToRemove, items);
        if (!can) throw new RuntimeException("Can't remove items from the inventory.");
    }

    public boolean canRemoveItemList(List<Item> itemsToRemove) {
        return removeItemsList(itemsToRemove, new ArrayList<>(items));
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

    //can be used for getting number of coins
    public int getAmountByType(ItemType itemType) {
        List<Item> items = getItemsByType(itemType);
        int amount = 0;
        for (Item item : items) {
            amount += item.getAmount();
        }
        return amount;
    }


    public ArrayList<String> showInventory() {
        ArrayList<String> output = new ArrayList<>();
        output.add(App.game.getCurrentPlayer().getUser().getUsername() + "'s Inventory Items:");
        for (Item item : items) {
            output.add("name :" + item.getName());
            output.add("amount :" + item.getAmount());
            output.add("-----------------");
        }
        return output;
    }

    public String removeItem(String itemName , int amount) {
        for(Item item : items) {
            if(item.getName().equals(itemName)) {
                if(item.getAmount() < amount)
                    return GameError.NOT_ENOUGH_ITEMS.getMessage();
                else {
                    item.setAmount(Math.max(item.getAmount() - amount, 0));
                    if(amount == 0)
                        items.remove(item);
                    return amount + " amounts of " + itemName + " removed.";
                }
            }
        }

        return "There is no item with this name in the inventory.";
    }

    public boolean toolEquip(String toolName){
        for(Item item : items) {
            if(item.getName().equals(toolName)) {
                App.game.getCurrentPlayer().setItemInHand(item);
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> showAvailableTools(){
        ArrayList<String> output = new ArrayList<>();
        output.add(App.game.getCurrentPlayer().getUser().getUsername() + "'s Available Tools:");
        for(Item item : items) {
            if(item.getItemType().equals(ItemType.TOOL)){
                output.add("name :" + item.getName());
                output.add("amount :" + item.getAmount());
            }
        }

        return output;
    }
}
