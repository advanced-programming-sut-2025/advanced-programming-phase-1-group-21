package models.map;

import models.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    protected ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    protected House houses;
    protected ArrayList<Lake> lakes;
    protected Mines mines;
    protected GreenHouse greenHouses;


    public Map(int id) {
        if(id == 1){
            for(int i = 0 ; i < 30 ; i++){
                ArrayList<Tile> thisRowTiles = new ArrayList<>();
                for(int j = 0 ; j < 50 ; j++){
                    thisRowTiles.add(new Tile(new Coord(j , i) , null , false , false ,
                            false , false , null , null));
                }
                tiles.add(thisRowTiles);
            }

            for(int i = 1 ; i < 5 ; i++){
                for(int j = 45 ; j < 49 ; j++){
                    tiles.get(i).get(j).setHouse(true);
                }
            }

            for(int i = 10 ; i < 15 ; i++ ){
                for(int j = 1 ; j < 5 ; j++){
                    tiles.get(i).get(j).setGreenHouse(true);
                }
            }

            for(int i = 1 ; i < 7 ; i++){
                for(int j = 1 ; j < 7 ; j++){
                    tiles.get(i).get(j).setMines(true);
                }
            }

            for(int i = 24 ; i < 29 ; i ++){
                for(int j = 23 ; j < 27 ; j++){
                    tiles.get(i).get(j).setLake(true);
                }
            }
        }

        if(id == 2){
            for(int i = 0 ; i < 30 ; i++){
                ArrayList<Tile> thisRowTiles = new ArrayList<>();
                for(int j = 0 ; j < 50 ; j++){
                    thisRowTiles.add(new Tile(new Coord(j , i) , null , false , false ,
                            false , false , null , null));
                }
                tiles.add(thisRowTiles);
            }

            for(int i = 1 ; i < 5 ; i++){
                for(int j = 1 ; j < 5 ; j++){
                    tiles.get(i).get(j).setHouse(true);
                }
            }

            for(int i = 1 ; i < 6 ; i++ ){
                for(int j = 45 ; j < 49 ; j++){
                    tiles.get(i).get(j).setGreenHouse(true);
                }
            }

            for(int i = 20 ; i < 29 ; i++){
                for(int j = 1 ; j < 10 ; j++){
                    tiles.get(i).get(j).setMines(true);
                }
            }

            for(int i = 24 ; i < 29 ; i ++){
                for(int j = 23 ; j < 27 ; j++){
                    tiles.get(i).get(j).setLake(true);
                }
            }

            for(int i = 15 ; i < 18 ; i ++){
                for(int j = 45 ; j < 48 ; j++){
                    tiles.get(i).get(j).setLake(true);
                }
            }
        }

        if(id == 3){
            for(int i = 0 ; i < 30 ; i++){
                ArrayList<Tile> thisRowTiles = new ArrayList<>();
                for(int j = 0 ; j < 50 ; j++){
                    thisRowTiles.add(new Tile(new Coord(j , i) , null , false , false ,
                            false , false , null , null));
                }
                tiles.add(thisRowTiles);
            }

            for(int i = 13 ; i < 17 ; i++){
                for(int j = 23 ; j < 27 ; j++){
                    tiles.get(i).get(j).setHouse(true);
                }
            }

            for(int i = 1 ; i < 6 ; i++ ){
                for(int j = 45 ; j < 49 ; j++){
                    tiles.get(i).get(j).setGreenHouse(true);
                }
            }

            for(int i = 20 ; i < 29 ; i++){
                for(int j = 1 ; j < 10 ; j++){
                    tiles.get(i).get(j).setMines(true);
                }
            }

            for(int i = 1 ; i < 5 ; i ++){
                for(int j = 1 ; j < 5 ; j++){
                    tiles.get(i).get(j).setLake(true);
                }
            }

            for(int i = 26 ; i < 29 ; i ++){
                for(int j = 45 ; j < 49 ; j++){
                    tiles.get(i).get(j).setLake(true);
                }
            }
        }

        int randomForagingNumber = 20;
        Random random = new Random();
        while(randomForagingNumber > 0){
            ArrayList<Foraging> foragings = new ArrayList<>();
            foragings.add(Foraging.TREE);
            foragings.add(Foraging.ROCK);
            foragings.add(Foraging.MARIJUANA);
            int randomTileIndex = random.nextInt(1500);
            Tile thisTile = tiles.get(randomTileIndex/50).get(randomTileIndex%50);
            if(!thisTile.isHouse() && !thisTile.isGreenHouse() && !thisTile.isMines() && !thisTile.isLake() && (thisTile.getForaging() == null)){
                thisTile.setForaging(foragings.get(random.nextInt(foragings.size())));
                randomForagingNumber--;
            }
        }
    }

    public int calculateDistance(Coord start, Coord end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //TODO
    public ArrayList<String> printMap(Coord center, int radius) {
        ArrayList<String> output = new ArrayList<>();
        for(ArrayList<Tile> rowTiles : tiles) {
            for(Tile tile : rowTiles) {
                if(tile.isHouse())
                    output.add("H");
                else if(tile.isGreenHouse())
                    output.add("G");
                else if(tile.isLake())
                    output.add("~");
                else if(tile.isMines())
                    output.add("M");
                else if(tile.getForaging() != null) {
                    if (tile.getForaging().equals(Foraging.ROCK))
                        output.add("R");
                    else if (tile.getForaging().equals(Foraging.TREE))
                        output.add("T");
                    else if (tile.getForaging().equals(Foraging.MARIJUANA))
                        output.add("*");
                }
                else
                    output.add("#");
            }
            output.add("\n");
        }
        return output;
    }

    public static Result<String> helpReadingMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
