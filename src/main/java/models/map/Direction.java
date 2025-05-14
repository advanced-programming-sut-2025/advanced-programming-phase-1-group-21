package models.map;

public enum Direction {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1),
    NORTH_EAST(-1 , 1),
    NORTH_WEST(-1 , -1),
    SOUTH_EAST(1 , 1),
    SOUTH_WEST(1 , -1),
    ;

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction getDirection(String direction) {
		return switch (direction) {
			case "N" -> NORTH;
			case "E" -> EAST;
			case "S" -> SOUTH;
			case "W" -> WEST;
			case "NE" -> NORTH_EAST;
			case "NW" -> NORTH_WEST;
			case "SE" -> SOUTH_EAST;
			case "SW" -> SOUTH_WEST;
			default -> null;
		};
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Coord getCoord() {
        return new Coord(dx, dy);
    }
        
}
