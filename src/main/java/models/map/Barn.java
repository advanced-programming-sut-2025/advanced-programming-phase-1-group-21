package models.map;

import models.animal.Animal;

import java.util.ArrayList;

public class Barn extends Building{
    ArrayList<Animal> animals;
    AnimalHouseType houseType;

    @Override
    public TileType getTileType() {
        return TileType.BARN;
    }

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public String getSprite() {
        return "B";
    }
}
