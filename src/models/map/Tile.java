package models.map;

import models.animal.Animal;
import models.crop.Tree;
import models.game.Player;

public class Tile {
    Coord cord;
    TileMaterialType tileMaterialType;
    Animal animal;
    Tree tree;
    Player player;
}
