package models.game;

public enum ItemType {
    SEED,
    TOOL,
    CONSUMABLE,
    ;

    public String name;

    public String getName() {
        return name;
    }

    public static ItemType fromName(String name) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getName().equals(name))
                return itemType;
        }
        return null;
    }
}
