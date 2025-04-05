package models.result.errorTypes;

public enum AuthError implements Error {
    INVALID_CREDENTIALS("Invalid credentials"),
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXISTS("User already exists");

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
