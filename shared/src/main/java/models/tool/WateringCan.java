package models.tool;

import models.Item.Item;
import models.game.Game;
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
        currentWater = getCapacity();
    }

    public int getCurrentWater() {
        return currentWater;
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

    public void increaseWater() {
        currentWater = getCapacity();
    }

    @Override
    public Result<Item> use(Coord coord, Game game) {
        Tile tile = game.getCurrentPlayerMap().getTile(coord);
        if (tile == null)
            return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);

        if (tile.getTileType().isWater())
            increaseWater();
        else {
            if (currentWater == 0) {
                return Result.failure(GameError.WATERING_CAN_IS_EMPTY);
            }
            tile.water();
            currentWater -= 1;
        }
        //calc energy TODO
        Player player = game.getCurrentPlayer();

        double weatherCoefficient = game.weatherCoefficient();

        switch (getToolMaterialType()) {
            case PRIMITIVE -> player.decreaseEnergy((int)(5 * weatherCoefficient));
            case COPPER -> player.decreaseEnergy((int)(4 * weatherCoefficient));
            case STEEL -> player.decreaseEnergy((int)(3 * weatherCoefficient));
            case GOLD -> player.decreaseEnergy((int)(2 * weatherCoefficient));
            case IRIDIUM -> player.decreaseEnergy((int)(1 * weatherCoefficient));
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
