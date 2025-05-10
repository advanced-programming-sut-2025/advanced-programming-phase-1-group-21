package models.game;

import models.Item.Item;
import models.map.Building;

import java.util.List;

public class BuildingRecipe {
    private final List<Item> items;
    private Building building;

    public BuildingRecipe(List<Item> items, Building building) {
        this.items = items;
        this.building = building;
    }
}