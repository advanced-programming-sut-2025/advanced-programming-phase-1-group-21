package io.github.StardewValley.models.tool;

import io.github.StardewValley.App;
import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.game.Player;
import io.github.StardewValley.models.map.Coord;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.models.result.Result;
import io.github.StardewValley.models.result.errorTypes.GameError;
import io.github.StardewValley.models.skill.SkillType;

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
    public Result<Item> use(Coord coord) {
        Tile tile = App.getInstance().game.getCurrentPlayerMap().getTile(coord);
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
        Player player = App.getInstance().game.getCurrentPlayer();

        double weatherCoefficient = App.getInstance().game.weatherCoefficient();

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
