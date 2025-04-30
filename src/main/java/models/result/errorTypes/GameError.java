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
    SEED_NOT_FOUND("Seed not found");


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
