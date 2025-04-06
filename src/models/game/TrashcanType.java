package models.game;

public enum TrashcanType {
    PRIMITIVE(0),
    COPPER(15),
    STEEL(30),
    GOLD(45),
    IRIDIUM(60);

    private final int repayPercent;

    TrashcanType(int repayPercent) {
        this.repayPercent = repayPercent;
    }

    public int getRepayPercent() {
        return repayPercent;
    }

    public int getRepay(int cost) {
        return repayPercent * cost / 100;
    }


    public String getName() {
        return name();
    }
}
