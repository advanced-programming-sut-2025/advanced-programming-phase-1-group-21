package models.result.errorTypes;

import models.result.Error;

public enum GameError implements Error {
    CRAFT_RECIPE_NOT_FOUND("Recipe not found"),
    NOT_ENOUGH_ITEMS("Not enough items");

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
