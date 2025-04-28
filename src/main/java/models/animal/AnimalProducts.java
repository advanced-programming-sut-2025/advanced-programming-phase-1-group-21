package models.animal;

import models.game.Item;
import models.game.ItemType;

public class AnimalProducts extends Item {

    public AnimalProducts(String name, ItemType itemType, int cost) {
        super(name, itemType, cost, 1);
    }

    public AnimalProducts(String name, ItemType itemType, int cost, int amount) {
        super(name, itemType, cost, amount);
    }
}
