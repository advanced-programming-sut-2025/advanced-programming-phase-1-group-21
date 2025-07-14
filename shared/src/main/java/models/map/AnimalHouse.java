package models.map;

import models.animal.Animal;
import models.time.Date;

import java.util.ArrayList;
import java.util.List;

public class AnimalHouse extends Building {
    ArrayList<Animal> animals = new ArrayList<>();
    AnimalHouseType houseType;
    String type;

    public AnimalHouse(String name) {
        map = (new MapBuilder().buildAnimalHouse(MapType.getMapType(name)));
        houseType = AnimalHouseType.getAnimalHouseType(name);
        this.type = name.split("\\s+")[1];
        if (houseType == null) throw new RuntimeException("Could not find animal type " + name);
    }

    public AnimalHouseType getHouseType() {
        return houseType;
    }

    @Override
    public boolean canEnter(Date date) {
        return false;
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

    public boolean hasSpace() {
        return animals.size() < houseType.getSize();
    }

    public void add(Animal animal) {
        animals.add(animal);
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
