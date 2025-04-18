package models.result.errorTypes;

public enum UserError implements Error {
    USER_NOT_FOUND("User not found");

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
