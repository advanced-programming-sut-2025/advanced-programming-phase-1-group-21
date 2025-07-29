package models.result.errorTypes;

import models.result.Error;

public enum ServerError implements Error {
    SQL_ERROR("SQL Error"),
    TOKEN_ERROR("Token Error"),
    NO_LOBBY(),
    INSUFFICENT_PERMISSION(),
    NO_SERVER_IS_RUNNING("No Server is running, Please Re-Run Server & Client");

    private final String message;

    private ServerError(String message) {
        this.message = message;
    }

    private ServerError() {
        this.message = this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
