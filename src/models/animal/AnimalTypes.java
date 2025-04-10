package models.animal;

public enum AnimalTypes {
    CHICKEN(1)
    ;

    public int producePeriod;

    AnimalTypes(int producePeriod) {
        this.producePeriod = producePeriod;
    }
}
