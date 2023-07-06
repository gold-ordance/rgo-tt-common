package rgo.tt.common.validator;

import rgo.tt.common.exceptions.BaseException;

public class ValidateException extends BaseException {

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
