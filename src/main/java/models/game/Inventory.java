package models.game;

import models.App;
import models.Item.Item;
import models.Item.ItemType;
import models.result.errorTypes.GameError;
import models.tool.*;
import models.result.Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {
    public static final int MAXIMUM_ITEM_PER_SLOT = 999;
    List<Item> items;
    InventoryType inventoryType;
    ToolMaterialType trashcanType;

    public Inventory() {
        items = new ArrayList<>();
        items.add(new Hoe());
        items.add(new Pickaxe());
        items.add(new Axe());
        items.add(new Scythe());
        items.add(new WateringCan());
        inventoryType = InventoryType.PRIMITIVE;
        trashcanType = ToolMaterialType.PRIMITIVE;
    }

    public int getSize() {
        return items.size();
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

    public void setCoins(Item coin){
        for(int i = 0 ; i < items.size(); i++){
            if(items.get(i).getName().equals("coin"))
                items.set(i, coin);
        }
    }

    public void changeCoin(int amount){
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase("coin"))
                item.setAmount(item.getAmount() + amount);
        }
    }

    public Item getCoin() {
        for(int i = 0 ; i < items.size(); i++){
            if(items.get(i).getName().equalsIgnoreCase("coin"))
                return items.get(i);
        }
        return null;
    }

    public Result<Void> addItem(Item item) {
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
        if (!canAdd()) {
            return Result.failure(GameError.CANT_ADD_ITEM_TO_INVENTORY);
        }
        items.add(item);
        return Result.success("Adding " + item.getName() + " to the inventory was successful.");
    }

    private static boolean removeItemFromList(Item itemToRemove, List<Item> items) {
        if (itemToRemove == null || items == null) return false;

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext() && itemToRemove.getAmount() > 0) {
            Item existingItem = iterator.next();
            if (existingItem.getName().equals(itemToRemove.getName())) {
                int removeAmount = Math.min(existingItem.getAmount(), itemToRemove.getAmount());
                existingItem.changeAmount(-removeAmount);
                itemToRemove.changeAmount(-removeAmount);

                if (existingItem.getAmount() == 0) {
                    iterator.remove();
                }
            }
        }
        return itemToRemove.getAmount() == 0;
    }

    public boolean removeItemsByType(ItemType type, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        int totalAvailable = getAmountByType(type);
        if (totalAvailable < amount) {
            return false;
        }

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext() && amount > 0) {
            Item item = iterator.next();
            if (item.getItemType() == type) {
                int removeAmount = Math.min(item.getAmount(), amount);
                item.changeAmount(-removeAmount);
                amount -= removeAmount;

                if (item.getAmount() == 0) {
                    iterator.remove();
                }
            }
        }

        return true;
    }

    public static boolean removeItemsList(List<Item> itemsToRemove, List<Item> items) {
        for (Item i : itemsToRemove) {
            if (!removeItemFromList(i, items))
                return false;
        }
        return true;
    }

    public void removeItemList(List<Item> itemsToRemove) {
        boolean can = removeItemsList(itemsToRemove, items);
        if (!can) System.out.println("cant");;
    }

    public String removeItem(Item item) {
        List<Item> itemsToRemove = new ArrayList<>();
        itemsToRemove.add(item);
        removeItemList(itemsToRemove);
        return null;
    }

    public boolean canRemoveItemList(List<Item> itemsToRemove) {
        return removeItemsList(itemsToRemove, new ArrayList<>(items));
    }

    public boolean canRemoveItem(Item item) {
        List<Item> itemsToRemove = new ArrayList<>();
        itemsToRemove.add(item);
        return canRemoveItemList(itemsToRemove);
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
    // Not anymore!! -Parsa
    public int getAmountByType(ItemType itemType) {
        List<Item> items = getItemsByType(itemType);
        int amount = 0;
        for (Item item : items) {
            amount += item.getAmount();
        }
        return amount;
    }

    public int getAmount(String name) {
        Item item = getItem(name);
        if (item == null)
            return 0;
        return item.getAmount();
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

//    public String removeItem(String itemName , int amount) {
//        for(Item item : items) {
//            if(item.getName().equals(itemName)) {
//                if(item.getAmount() < amount)
//                    return GameError.NOT_ENOUGH_ITEMS.getMessage();
//                else {
//                    item.setAmount(Math.max(item.getAmount() - amount, 0));
//                    if(amount == 0)
//                        items.remove(item);
//                    return amount + " amounts of " + itemName + " removed.";
//                }
//            }
//        }
//
//        return "There is no item with this name in the inventory.";
//    }

    public boolean toolEquip(String toolName){
        for(Item item : items) {
            if(item.getName().equalsIgnoreCase(toolName) && (item.getItemType().equals(ItemType.TOOL))) {
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
                output.add("tool material :" + ((Tool) item).getToolMaterialType());
                output.add("--------------");
            }
        }

        return output;
    }

    @Override
    public String toString() {
        ArrayList<String> inventoryDetails = showInventory();

        inventoryDetails.add("Inventory Type: " + inventoryType);
        inventoryDetails.add("Trashcan Type: " + trashcanType);
        inventoryDetails.add("Maximum Size: " + getMaximumSize());

        return String.join("\n", inventoryDetails);
    }

    public boolean canAdd() {
        return getSize() < inventoryType.getMaximumSize();
    }
}
