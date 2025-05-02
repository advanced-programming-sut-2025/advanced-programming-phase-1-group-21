package models.map;

import models.App;
import models.result.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Map {
    protected ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    protected House house = new House();
    protected ArrayList<Lake> lakes = new ArrayList<>();
    protected Mines mines = new Mines();
    protected GreenHouse greenHouses = new GreenHouse();
    protected Barn barn;
    protected Coop coop;
    protected LocationsOnMap currentLocation = LocationsOnMap.Farm;



    public Map(int id) {
        if(id == 1){
            for(int i = 0 ; i < 30 ; i++){
                ArrayList<Tile> thisRowTiles = new ArrayList<>();
                for(int j = 0 ; j < 50 ; j++){
                    thisRowTiles.add(new Tile(new Coord(j , i) , null ,
                            null , null));
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
            this.lakes.add(new Lake());
        }

        if(id == 2){
            for(int i = 0 ; i < 30 ; i++){
                ArrayList<Tile> thisRowTiles = new ArrayList<>();
                for(int j = 0 ; j < 50 ; j++){
                    thisRowTiles.add(new Tile(new Coord(j , i) , null ,
                            null , null));
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
            this.lakes.add(new Lake());

            for(int i = 15 ; i < 18 ; i ++){
                for(int j = 45 ; j < 48 ; j++){
                    tiles.get(i).get(j).setLake(true);
                }
            }
            this.lakes.add(new Lake());
        }

        if(id == 3){
            for(int i = 0 ; i < 30 ; i++){
                ArrayList<Tile> thisRowTiles = new ArrayList<>();
                for(int j = 0 ; j < 50 ; j++){
                    thisRowTiles.add(new Tile(new Coord(j , i) , null ,
                            null , null));
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
            this.lakes.add(new Lake());

            for(int i = 26 ; i < 29 ; i ++){
                for(int j = 45 ; j < 49 ; j++){
                    tiles.get(i).get(j).setLake(true);
                }
            }
            this.lakes.add(new Lake());
        }

        //this map is fot house
        if(id == 4){
            for(int i = 0 ; i < 8 ; i++){
                ArrayList<Tile> thisRowTiles = new ArrayList<>();
                for(int j = 0 ; j < 16 ; j++){
                    thisRowTiles.add(new Tile(new Coord(j , i) , null ,
                            null , null));
                }
                tiles.add(thisRowTiles);
            }

        }

        this.greenHouses = new GreenHouse();
        this.house = new House();
        this.mines = new Mines();

        int randomForagingNumber = 20;
        Random random = new Random();
        while(randomForagingNumber > 0){
            ArrayList<Foraging> foraging = new ArrayList<>(Arrays.asList(Foraging.values()));
            int randomTileIndex = random.nextInt(1500);
            Tile thisTile = tiles.get(randomTileIndex/50).get(randomTileIndex%50);
            if(thisTile.tileIsEmpty()){
                thisTile.setForaging(foraging.get(random.nextInt(foraging.size())));
                randomForagingNumber--;
            }
        }
    }

    public int calculateDistance(Coord start, Coord end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<String> printMap(Coord center, int radius) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<ArrayList<Tile>> tilesToPrint = App.game.getCurrentPlayer().currentLocationTiles();
        for(int i = center.getY() ; i < Math.min(center.getY() + radius , tilesToPrint.size()) ; i++){
            for(int j = center.getX() ; j < Math.min(center.getX() + radius , tilesToPrint.get(0).size())  ; j++){
                Tile tile = tilesToPrint.get(i).get(j);
                if(tile.isHouse())
                    output.add("H");
                else if(tile.isGreenHouse())
                    output.add("G");
                else if(tile.isLake())
                    output.add("~");
                else if(tile.isMines())
                    output.add("M");
                else if(tile.isBarn())
                    output.add("B");
                else if(tile.isCoop())
                    output.add("C");
                else if(tile.getForaging() != null) {
                    if (tile.getForaging().equals(Foraging.SIMPLE_ROCK))
                        output.add("R");
                    else if (tile.getForaging().equals(Foraging.STEEL_ROCK))
                        output.add("R");
                    else if (tile.getForaging().equals(Foraging.GOLD_ROCK))
                        output.add("g");
                    else if (tile.getForaging().equals(Foraging.COPPER_ROCK))
                        output.add("R");
                    else if (tile.getForaging().equals(Foraging.IRIDIUM_ROCK))
                        output.add("I");
                    else if (tile.getForaging().equals(Foraging.TREE))
                        output.add("T");
                    else if (tile.getForaging().equals(Foraging.LEAF))
                        output.add("*");
                }
                else if((App.game.getCurrentPlayer().getCoord().getX() == j) && (App.game.getCurrentPlayer().getCoord().getY() == i))
                    output.add("@");
                else if(tile.getRefrigerator() != null)
                    output.add("F");
                else if(tile.isDoor())
                    output.add("+");
                else
                    output.add("#");
            }
            output.add("\n");
        }
        return output;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public House getHouse() {
        return house;
    }

    public LocationsOnMap getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LocationsOnMap currentLocation) {
        this.currentLocation = currentLocation;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public Mines getMines() {
        return mines;
    }

    public GreenHouse getGreenHouses() {
        return greenHouses;
    }

    public static Result<String> helpReadingMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
