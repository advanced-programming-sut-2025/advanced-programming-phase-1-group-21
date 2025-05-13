package models.tool;

import models.App;
import models.Item.Item;
import models.game.Player;
import models.map.Coord;
import models.map.Tile;
import models.result.Result;
import models.result.errorTypes.GameError;
import models.skill.SkillType;

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
        Tool waterCan = (Tool) App.game.getCurrentPlayer().getItemInHand();
        Player player = App.game.getCurrentPlayer();
        switch (waterCan.getToolMaterialType()) {
            case PRIMITIVE -> player.decreaseEnergy(5);
            case COPPER -> player.decreaseEnergy(4);
            case STEEL -> player.decreaseEnergy(3);
            case GOLD -> player.decreaseEnergy(2);
            case IRIDIUM -> player.decreaseEnergy(1);
        }
        if(player.getSkillLevel(SkillType.FARMING) >= 4)
            player.setEnergy(player.getEnergy() + 1);
        return Result.success(null);
    }

    @Override
    public ToolMaterialType getToolMaterialType() {
        return toolMaterialType;
    }
}
