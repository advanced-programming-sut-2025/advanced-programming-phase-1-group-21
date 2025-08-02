package models.map;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.animal.Animal;
import models.time.Date;

import java.util.ArrayList;
import java.util.List;

public class AnimalHouse extends Building {
    ArrayList<Animal> animals = new ArrayList<>();
    AnimalHouseType houseType;
    String type;

    public AnimalHouse() {}

    public AnimalHouse(String name) {
        map = (new MapBuilder().buildAnimalHouse(MapType.getMapType(name)));
        houseType = AnimalHouseType.getAnimalHouseType(name);
        this.type = name.split("\\s+")[1];
        loadTexture();
        if (houseType == null) throw new RuntimeException("Could not find animal type " + name);
    }

    public void loadTexture(){
        String textureName = houseType.getName().substring(0,1).toUpperCase() + houseType.getName().substring(1)
                + "_" + type.substring(0,1).toUpperCase() + type.substring(1);
        texture = SharedAssetManager.getAnimalHouse(textureName);
        if(sprite == null) {
            sprite = new Sprite(texture);
            sprite.setSize(150 , 150);
        }
        sprite.setTexture(texture);
    }

    public AnimalHouseType getHouseType() {
        return houseType;
    }

    @Override
    public boolean canEnter(Date date) {
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
    public Texture getTexture() {
        return null;
    }

    @Override
    public Sprite spriteGetter() {
        return null;
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
        for(int x = 0 ; x < 10 ; x ++){
            for(int y = 0 ; y < 10 ; y++){
                Tile tile = getMap().getTile(3*x + 1 , 3*y + 1);
                if(tile.getPlacable(Animal.class) == null){
                    tile.setPlacable(animal);
                    tile.loadOnTileTexture();
                    return;
                }
            }
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
