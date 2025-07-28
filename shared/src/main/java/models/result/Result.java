package models.result;

public class Result<T> {
    private T data;
    private Error error;
    private String message;

    /**
     * THIS IS FOR KRYO-NET, don't USE IT
     */
    public Result() {

    }

    private Result(T data, Error error, String message) {
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, null);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(data, null, message);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(null, null, message);
    }

    public static <T> Result<T> failure(Error error) {
        if (error == null) {
            throw new IllegalArgumentException("Error cannot be null for a failed result");
        }
        return new Result<>(null, error, error.toString());
    }

    public T getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public String getMessage() {
        if (error != null)
            return error.getMessage();
        if (message != null) {
            return message;
        }
        return (data != null ? data.toString() : "");
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isError() {
        return error != null;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
