package rgo.tt.common.exceptions;

public class InvalidEntityException extends BaseException {

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
