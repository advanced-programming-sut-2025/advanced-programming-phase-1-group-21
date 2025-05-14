package models.time;

public enum Season {
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER;

    public Season nextSeason() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static Season fromName(String name) {
        for (Season season : values()) {
            if (season.name().equalsIgnoreCase(name)) {
                return season;
            }
        }
        return null;
    }
}
