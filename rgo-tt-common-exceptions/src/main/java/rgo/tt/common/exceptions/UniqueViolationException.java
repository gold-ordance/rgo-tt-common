package rgo.tt.common.exceptions;

public class UniqueViolationException extends PersistenceException {

    public UniqueViolationException(String message) {
        super(message);
    }

    public UniqueViolationException(Throwable cause) {
        super(cause);
    }

    public UniqueViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
