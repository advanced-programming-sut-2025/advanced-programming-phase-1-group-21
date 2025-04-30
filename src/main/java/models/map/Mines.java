package models.map;

import java.util.ArrayList;

public class Mines extends Building {

    public Mines() {
        this.tiles = new ArrayList<>();
        for(int i = 0 ; i < 15 ; i++){
            ArrayList<Tile> thisRowTiles = new ArrayList<>();
            for(int j = 0 ; j < 25 ; j++){
                thisRowTiles.add(new Tile(new Coord(j , i) , null ,
                        null , null));
            }
            tiles.add(thisRowTiles);
        }
        this.tiles.get(14).get(24).setDoor(true);
    }

}
