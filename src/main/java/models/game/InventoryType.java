package models.game;

public enum InventoryType {
    PRIMITIVE(12),
    BIG(24),
    UNLIMITED(Integer.MAX_VALUE);

    private final int maximumSize;

    InventoryType(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    public int getMaximumSize() {
        return maximumSize;
    }
}
