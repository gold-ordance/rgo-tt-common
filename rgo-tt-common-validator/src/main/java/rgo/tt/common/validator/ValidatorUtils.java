package rgo.tt.common.validator;

public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    public static void validateString(String value, String fieldName) {
        if (value == null) errorNull(fieldName);
        if (value.isBlank()) error("The " + fieldName + " is empty.");
    }

    public static void validateObjectId(Long value, String fieldName) {
        if (value == null) errorNull(fieldName);
        if (value < 0) error("The " + fieldName + " is negative.");
    }

    private static void errorNull(String fieldName) {
        error("The " + fieldName + " is null.");
    }

    private static void error(String errorMessage) {
        throw new ValidateException(errorMessage);
    }
}
