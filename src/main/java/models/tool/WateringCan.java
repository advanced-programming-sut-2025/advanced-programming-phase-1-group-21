package models.tool;

import models.App;
import models.game.Item;
import models.map.Coord;
import models.map.Tile;
import models.map.TileType;
import models.result.Result;
import models.result.errorTypes.GameError;

public class WateringCan extends Tool {
    private int currentWater;

    public WateringCan() {
        super(ToolType.WATERING_CAN);
    }

    public int getCapacity() {
        return switch (toolMaterialType) {
            case PRIMITIVE -> 40;
            case COPPER -> 55;
            case STEEL -> 70;
            case GOLD -> 85;
            case IRIDIUM -> 100;
            default -> 0;
        };
    }

    static final int WATER_PER_TIME = 10;
    public void increaseWater() {
        currentWater += WATER_PER_TIME;
        if (currentWater >= getCapacity()) {
            currentWater = getCapacity();
        }
    }

    @Override
    public Result<Item> use(Coord coord) {
        Tile tile = App.game.getCurrentPlayerMap().getTile(coord);
        if (tile == null) {
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        }
        if (!tile.getTileType().isWater())
            return Result.failure(GameError.TILE_DOESNT_HAVE_WATER);
        increaseWater();
        //calc energy TODO
        return Result.success(null);
    }

    @Override
    public ToolMaterialType getToolMaterialType() {
        return toolMaterialType;
    }
}
