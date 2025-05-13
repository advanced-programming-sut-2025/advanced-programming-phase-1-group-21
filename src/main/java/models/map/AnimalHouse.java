package models.map;

import models.animal.Animal;

import java.io.Serializable;
import java.util.ArrayList;

public class AnimalHouse extends Building {
    ArrayList<Animal> animals;
    AnimalHouseType houseType;
    String type;

    public AnimalHouse(String name) {
        map = (new MapBuilder().buildAnimalHouse(MapType.getMapType(name)));
        houseType = AnimalHouseType.getAnimalHouseType(name);
        this.type = name.split("\\s+")[1];
        if (houseType == null) throw new RuntimeException("Could not find animal type " + name);
    }

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public TileType getTileType() {
        return TileType.ANIMAL;
    }

    @Override
    public String getSprite() {
        return ("" + type.charAt(0)).toUpperCase();
    }

    @Override
    public String getFullName() {
        return houseType.getName() + " " + type;
    }
}
