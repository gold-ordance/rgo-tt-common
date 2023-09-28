package rgo.tt.common.exceptions;

public class ValidateException extends BaseException {

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
