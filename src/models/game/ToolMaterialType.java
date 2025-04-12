package models.game;

public enum ToolMaterialType {
    PRIMITIVE,
    COPPER,
    STEEL,
    GOLD,
    IRIDIUM;

    public String getName() {
        return name();
    }
}
