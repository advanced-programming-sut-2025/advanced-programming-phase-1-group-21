package models.result.errorTypes;

import models.result.Error;

public enum AuthError implements Error {
    INVALID_USERNAME("Invalid username"),
    IN_GAME_USER("this username is already in a game"),
    GAME_NOT_CREATED("The game has not been created"),
    USER_NOT_FOUND("User not found"),
    PASSWORD_LENGTH("Password too short"),
    PASSWORD_SPECIAL_CHARACTERS("Password must contain special characters"),
    PASSWORD_ALPHABET("Password must contain alphabet"),
    PASSWORD_NUMBERS("Password must contain numbers"),
    PASSWORD_CONFIRM_ERROR("Password does not equal confirm password"),
    USER_ALREADY_EXISTS("User already exists"),
    PASSWORD_TOO_WEAK("Password does not meet security requirements"),
    EMAIL_ALREADY_REGISTERED("Email is already registered"),
    INVALID_EMAIL_FORMAT("Invalid email format");

    private final String message;

    private AuthError(String message) {
        this.message = message;
    }

    private AuthError() {
        this.message = this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
