package models.animal;

public enum AnimalTypes {
    CHICKEN,
    COW,
    GOAT,
    SHEEP,
    RABBIT,
    ;

//    public int producePeriod;
//
//    AnimalTypes(int producePeriod) {
//        this.producePeriod = producePeriod;
//    }

    public static AnimalTypes fromName(String animalType) {
        for (AnimalTypes type: AnimalTypes.values()) {
            if (type.name().equalsIgnoreCase(animalType)) return type;
        }
        return null;
    }
}
