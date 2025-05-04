package models.game;

public enum ItemType {
    SEED,
    TOOL,
    CONSUMABLE,
    COIN,
    WOOD,
    SALABLE,
    ORE,
    FUEL,
    FLOWER
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
