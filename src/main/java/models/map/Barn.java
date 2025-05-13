package models.map;

import models.animal.Animal;

import java.io.Serializable;
import java.util.ArrayList;

public class Barn extends Building implements UpdatableBuilding {
    ArrayList<Animal> animals;
    AnimalHouseType houseType;

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public String getSprite() {
        return "B";
    }

    @Override
    public void update(String newName) {
        houseType = AnimalHouseType.getAnimalHouseType(newName);
    }

    @Override
    public String getFullName() {
        return houseType.getName() + " " + "Barn";
    }
}
