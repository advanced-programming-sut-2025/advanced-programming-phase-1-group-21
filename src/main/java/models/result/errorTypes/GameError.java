package models.result.errorTypes;

import models.result.Error;

public enum GameError implements Error {
    COORDINATE_DOESNT_EXISTS("this coordinate does not exist in this location"),
    CANT_STAND_ON_FORAGING("you cant walk on a foraging"),
    CANT_STAND_ON_FRIDGE("you cant walk on fridge"),
    CANT_STAND_ON_LAKE("you cant walk on a lake"),
    CRAFT_RECIPE_NOT_FOUND("Recipse not found"),
    NOT_ENOUGH_COINS("You do not have enough coins"),
    ANIMAL_NOT_FOUND("Animal not found"),
    NOT_ENOUGH_ITEMS("Not enough items"),
    TOOL_NOT_FOUND("Tool not found"),
    TILE_IS_NOT_EMPTY("This tile is not empty"),
    GREENHOUSE_IS_NOT_YET_BUILT("Greenhouse is not yet built"),
    HERE_IS_NOT_FARM("Here is not your farm"),
    YOU_CANT_USE_PICKAXE_HERE("You can't use pickaxe here to use"),
    SEED_NOT_FOUND("Seed not found"),
    NOT_IMPLEMENTED("Not implemented"),
    NO_PLAYER_FOUND("No player found"),
    NOT_NEXT_TO_EACH_OTHER("You are not next to each other"),
    FRIENDSHIP_LEVEL_IS_NOT_ENOUGH("Friendship level is not enough to do this"),
    NO_GAME_RUNNING("No game running"),
    YOU_CANT_DO_ACTION("You can't do that here"),
    TILE_DOESNT_HAVE_WATER("Tile does not have water"),
    NOT_ENOUGH_ITEM("Not enough items"),
    GIFT_ID_DOES_NOT_EXIST("Gift id does not exist"),
    RATE_MUST_BE_POSITIVE("Rate must be positive"),
    YOU_ARE_GIRL("You are girl"),
    YOUR_WIFE_CAN_NOT_BE_A_BOY("Your wife can't be a boy"),
    CANT_ENTER("Cant enter"),
    YOU_SHOULD_BE_ON_FARM("You should have been on farm"),
    YOU_SHOULD_BE_ON_VILLAGE("You should have been on village"),
    YOU_SHOULD_BE_ON_SHOP("You should have been on shop"),
    RESPONSE_IS_NOT_SUPPORTED("Response is not supported"),
    TRADE_ID_DOES_NOT_EXIST("Trade id does not exist"),
    ITEM_IS_NOT_AVAILABLE("Item is not available"),
    THIS_NPC_DOES_NOT_EXIST("This NPC does not exist"),
    YOU_CANT_GIFT_A_TOOL("You can't gift a tool"),
    YOU_ARE_DISTANT("You should be near to do action"),
    NO_PATH("There is no path to the destination"),
    PLANT_ON_PLOWED("You can't plant unless it's plowed!"),
    NOT_FOUND("There is no such item"),
    TOOL_NOT_IN_HAND("Tool is not in hand"),
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
