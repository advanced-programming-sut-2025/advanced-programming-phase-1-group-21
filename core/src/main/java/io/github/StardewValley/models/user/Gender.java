package io.github.StardewValley.models.user;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender getGenderByName(String gender) {
        for (Gender g : Gender.values()) {
            if (g.name().equalsIgnoreCase(gender)) {
                return g;
            }
        }
        return null;
    }
}
