package models.user;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender getGenderByName(String gender) {
        for (Gender g : Gender.values()) {
            if (g.name().toLowerCase().equals(gender.toLowerCase())) {
                return g;
            }
        }
        return null;
    }
}
