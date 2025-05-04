package models.result.errorTypes;

import models.result.Error;

public enum GameError implements Error {
    COORDINATE_DOESNT_EXISTS("this coordinate does not exist in this location"),
    CANT_STAND_ON_FORAGING("you cant walk on a foraging"),
    CANT_STAND_ON_FRIDGE("you cant walk on fridge"),
    CANT_STAND_ON_LAKE("you cant walk on a lake"),
    CRAFT_RECIPE_NOT_FOUND("Recipe not found"),
    NOT_ENOUGH_COINS("You do not have enough coins"),
    ANIMAL_NOT_FOUND("Animal not found"),
    NOT_ENOUGH_ITEMS("Not enough items"),
    TOOL_NOT_FOUND("Tool not found"),
    TILE_IS_NOT_EMPTY("This tile is not empty"),
    GREENHOUSE_IS_NOT_YET_BUILT("Greenhouse is not yet built"),
    HERE_IS_NOT_FARM("Here is not your farm"),
    YOU_CANT_USE_PICKAXE_HERE("You can't use pickaxe here to use"),
    SEED_NOT_FOUND("Seed not found"),
    NOT_IMPLEMENTED("Not implemented");


    private final String message;

    private GameError(String message) {
        this.message = message;
    }

    private GameError() {
        this.message = this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
