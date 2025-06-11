package io.github.StardewValley.models.result.errorTypes;
import io.github.StardewValley.models.result.Error;


public enum MenuError implements Error {
    MENU_ACCESS_DENIED("You can't access this menu");
    private final String message;

    private MenuError(String message) {
        this.message = message;
    }

    private MenuError() {
        this.message = this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
