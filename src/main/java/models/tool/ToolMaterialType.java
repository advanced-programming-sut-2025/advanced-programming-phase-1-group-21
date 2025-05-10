package models.tool;

public enum ToolMaterialType {
    PRIMITIVE,
    COPPER,
    STEEL,
    GOLD,
    IRIDIUM,

    EDUCATIONAL,
    BAMBOO,
    FIBERGLASS;
    // Fishing Pole has IRIDIUM Material as well.

    public String getName() {
        return name();
    }
}
