package models.map;

import models.animal.Animal;

import java.util.ArrayList;

public class Barn extends Building{
    ArrayList<Animal> animals;
    AnimalHouseType houseType;

    @Override
    public boolean canEnter() {
        return true;
    }
}
