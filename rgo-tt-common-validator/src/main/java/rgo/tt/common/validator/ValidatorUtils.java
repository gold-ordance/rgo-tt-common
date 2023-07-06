package rgo.tt.common.validator;

public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    public static void checkString(String s, String field) {
        if (s == null) errorNull(field);
        if (s.isBlank()) error("The " + field + " is empty.");
    }

    public static void checkObjectId(Long id, String field) {
        if (id == null) errorNull(field);
        if (id < 0) error("The " + field + " is negative.");
    }

    private static void errorNull(String field) {
        error("The " + field + " is null.");
    }

    private static void error(String errorMessage) {
        throw new ValidateException(errorMessage);
    }
}
