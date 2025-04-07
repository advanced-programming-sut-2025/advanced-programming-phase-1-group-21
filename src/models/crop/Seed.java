package models.crop;

public class Seed extends Item {
    SeedType seedType;
    FertilizerType fertilizerType;
    int stage;
    int day;

    public void plant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void water() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void harvest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
