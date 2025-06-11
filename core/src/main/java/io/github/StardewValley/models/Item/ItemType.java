package io.github.StardewValley.models.Item;

public enum ItemType {
    SEED,
    SAPLING,
    TOOL,
    CONSUMABLE,
    SALABLE,
    PLACEABLE,
    RECIPE,
    ;

    public static ItemType stringToItemType(String itemType) {
        for(ItemType item : ItemType.values()) {
            if(item.name().equalsIgnoreCase(itemType)) {
                return item;
            }
        }
        return null;
    }
}
