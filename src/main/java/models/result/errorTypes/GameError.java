package models.result.errorTypes;

import models.result.Error;

public enum GameError implements Error {
    COORDINATE_DOESNT_EXISTS("this coordinate does not exist in this location"),
    CANT_STAND_ON_FORAGING("you cant walk on a foraging"),
    ;
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
