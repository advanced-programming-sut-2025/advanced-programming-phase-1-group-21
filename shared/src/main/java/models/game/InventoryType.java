package models.game;

public enum InventoryType {
    PRIMITIVE(14),
    BIG(26),
    UNLIMITED(Integer.MAX_VALUE);

    private final int maximumSize;

    InventoryType(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    public int getMaximumSize() {
        return maximumSize;
    }
}
