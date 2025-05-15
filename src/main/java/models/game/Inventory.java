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
    public static final int MAXIMUM_ITEM_PER_SLOT = 100000;
    List<Item> items = new ArrayList<>();
    InventoryType inventoryType = InventoryType.PRIMITIVE;
    ToolMaterialType trashcanType = ToolMaterialType.PRIMITIVE;

    public static Inventory buildPlayerInventory() {
        Inventory inventory = new Inventory();
        inventory.addItem(new Hoe());
        inventory.addItem(new Pickaxe());
        inventory.addItem(new Axe());
        inventory.addItem(new Scythe());
        inventory.addItem(new WateringCan());
        inventory.addItem(new TrashCan());
        return inventory;
    }

    public Inventory() {

    }
    /**
     * Copy constructor for deep-cloning inventory (for dry-run operations).
     */
    public Inventory(Inventory other) {
        this.inventoryType = other.inventoryType;
        this.trashcanType   = other.trashcanType;
        this.items          = new ArrayList<>(other.items.size());
        for (Item i : other.items) {
            this.items.add(i.copy());
        }
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

    /**
     * Remove items, mutating inventory. Returns false if any request cannot be satisfied.
     */
    public boolean removeItems(List<Item> toRemove) {
        for (Item req : toRemove) {
            int totalHave = getAmount(req.getName());
            if (totalHave < req.getAmount()) {
                return false;
            }
        }

        for (Item req : toRemove) {
            int remaining = req.getAmount();
            Iterator<Item> itr = items.iterator();
            while (itr.hasNext() && remaining > 0) {
                Item slot = itr.next();
                if (slot.getName().equals(req.getName())) {
                    int removeAmt = Math.min(slot.getAmount(), remaining);
                    slot.changeAmount(-removeAmt);
                    remaining -= removeAmt;
                    if (slot.getAmount() == 0) itr.remove();
                }
            }
        }
        return true;
    }


    public boolean removeItem(Item item) {
        List<Item> itemsToRemove = new ArrayList<>();
        itemsToRemove.add(item);
        return removeItems(itemsToRemove);
    }

    /**
     * Dry-run: returns true if all requested removals would succeed without mutating this.
     */
    public boolean canRemoveItems(List<Item> toRemove) {
        for(Item thisItem : toRemove){
            int thisItemAmount = 0;
            for(Item otherItem : toRemove){
                if(thisItem.getName().equals(otherItem.getName())){
                    thisItemAmount += otherItem.getAmount();
                }
            }
            int itemInInventory = 0;
            for(Item inventoryItem : items){
                if(inventoryItem.getName().equals(thisItem.getName())){
                    itemInInventory += inventoryItem.getAmount();
                }
            }
            if(thisItemAmount > itemInInventory)
                return false;
        }
        return true;
    }

    public boolean canRemoveItem(Item item) {
        int requiredAmount = item.getAmount();
        int totalHave = 0;
        for(Item i : items) {
            if(i.getName().equals(item.getName())) {
                totalHave += i.getAmount();
            }
        }
        return totalHave >= requiredAmount;
    }

    public Item getItem(String name) {
        for (Item item : items)
            if (item.getName().equalsIgnoreCase(name))
                return item;
        return null;
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
