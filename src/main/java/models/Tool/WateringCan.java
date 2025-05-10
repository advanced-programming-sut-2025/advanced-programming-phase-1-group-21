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

    @Override
    public Result<Item> use(Coord coord) {
        Tile tile = App.game.getCurrentPlayerMap().getTile(coord);
        if (tile == null) {
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
        }
        if (!tile.getTileType().isWater())
            return Result.failure(GameError.TILE_DOESNT_HAVE_WATER);
        return Result.success(Item.buildItem("water", 1));
    }

    @Override
    public ToolMaterialType getToolMaterialType() {
        return toolMaterialType;
    }
}
