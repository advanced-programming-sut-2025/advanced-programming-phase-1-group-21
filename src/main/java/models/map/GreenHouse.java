package models.map;

import java.util.ArrayList;

public class GreenHouse extends Building {

    public GreenHouse() {
        this.tiles = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            ArrayList<Tile> thisRowTiles = new ArrayList<>();
            for(int j = 0 ; j < 20 ; j++){
                thisRowTiles.add(new Tile(new Coord(j , i) , null ,
                        null , null));
            }
            tiles.add(thisRowTiles);
        }
        this.tiles.get(9).get(19).setDoor(true);
    }

}
