package models.result.errorTypes;

public enum AuthError implements Error {
    INVALID_CREDENTIALS("Invalid username or password"),
    USER_NOT_FOUND("User not found"),
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
