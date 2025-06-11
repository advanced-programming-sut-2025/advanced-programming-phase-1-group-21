package io.github.StardewValley.models.crop;

public enum FertilizerType {
    BASIC(5, "Basic Retaining Soil"),
    QUALITY(7, "Quality Retaining Soil"),
    DELUXE(1000000, "Deluxe Retaining Soil"),
    ;


    private final int days;
    private final String name;
    FertilizerType(int days, String name) {
        this.days = days;
        this.name = name;
    }

    public int getDays() {
        return days;
    }

    public String getName() {
        return name;
    }

    public static FertilizerType getFertilizerType(String name) {
        for (FertilizerType type : FertilizerType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
