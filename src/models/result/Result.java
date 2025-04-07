package models.result;

public class Result<T> {
    private final T data;
    private final Error error;
    private final String message;

    private Result(T data, Error error, String message) {
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, "");
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(data, null, message);
    }

    public static <T> Result<T> failure(Error error) {
        if (error == null) {
            throw new IllegalArgumentException("Error cannot be null for a failed result");
        }
        return new Result<>(null, error, error.toString());
    }

    public static <T> Result<T> failure(Error error, String message) {
        if (error == null) {
            throw new IllegalArgumentException("Error cannot be null for a failed result");
        }
        return new Result<>(null, error, message);
    }

    public T getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isError() {
        return error != null;
    }
}
