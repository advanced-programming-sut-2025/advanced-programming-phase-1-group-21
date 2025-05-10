package models.Tool;

import models.App;
import models.game.Item;
import models.map.Coord;
import models.map.Tile;
import models.map.TileType;
import models.result.Result;
import models.result.errorTypes.GameError;

public class WateringCan extends Tool {
    private int capacity;
    private int currentWater;

    private ToolMaterialType toolMaterialType;
    public WateringCan() {
        super(ToolType.WATERING_CAN);
    }

    public void increaseWater() {
        currentWater++;
        if (currentWater >= capacity) {
            currentWater = capacity;
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
        return Result.success(null);
    }

    @Override
    public ToolMaterialType getToolMaterialType() {
        return toolMaterialType;
    }
}
