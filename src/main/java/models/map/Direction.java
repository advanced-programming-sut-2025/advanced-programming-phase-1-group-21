package models.map;

import models.App;
import models.game.Player;

import java.util.ArrayList;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTH_EAST(1 , 1),
    NORTH_WEST(-1 , 1),
    SOUTH_EAST(1 , -1),
    SOUTH_WEST(-1 , -1),
    ;

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction stringToDirection(String direction) {
        if(direction.equals("north")) {
            return NORTH;
        }
        else if(direction.equals("east")) {
            return EAST;
        }
        else if(direction.equals("south")) {
            return SOUTH;
        }
        else if(direction.equals("west")) {
            return WEST;
        }
        else if(direction.equals("north-east")) {
            return NORTH_EAST;
        }
        else if(direction.equals("north-west")) {
            return NORTH_WEST;
        }
        else if(direction.equals("south-east")) {
            return SOUTH_EAST;
        }
        else if(direction.equals("south-west")) {
            return SOUTH_WEST;
        }
        else {
            return null;
        }
    }


    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

        
}
