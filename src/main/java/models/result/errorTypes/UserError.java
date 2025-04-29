package models.result.errorTypes;

import models.result.Error;

public enum UserError implements Error {
    USER_NOT_FOUND("User not found"),
    PASSWORD_DOESNT_MATCH("Password does not match with this username"),
    INCORRECT_ANSWER("Answer is incorrect"),
    ANSWER_NOT_SET("Answer is not set"),
    ;

    private final String message;

    private UserError(String message) {
        this.message = message;
    }

    private UserError() {
        this.message = this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
