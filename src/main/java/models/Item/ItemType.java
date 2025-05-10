package models.Item;

public enum ItemType {
    SEED,
    SAPLING,
    TOOL,
    CONSUMABLE,
    WOOD,
    SALABLE,
    ORE,
    FUEL,
    FLOWER,
    PLACEABLE
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
