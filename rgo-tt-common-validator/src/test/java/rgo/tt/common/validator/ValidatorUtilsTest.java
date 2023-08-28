package rgo.tt.common.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.common.utils.TestUtils.assertThrowsWithMessage;

class ValidatorUtilsTest {

    private static final String FIELD_NAME = "field";

    @Test
    void validateString_isNull() {
        String value = null;
        assertThrowsWithMessage(
                ValidateException.class,
                () -> ValidatorUtils.validateString(value, FIELD_NAME),
                "The " + FIELD_NAME + " is null.");
    }

    @Test
    void validateString_isEmpty() {
        String value = "";
        assertThrowsWithMessage(
                ValidateException.class,
                () -> ValidatorUtils.validateString(value, FIELD_NAME),
                "The " + FIELD_NAME + " is empty.");
    }

    @Test
    void validateString_success() {
        String value = randomString();
        assertDoesNotThrow(() ->
                ValidatorUtils.validateString(value, FIELD_NAME));
    }

    @Test
    void validateObjectId_isNull() {
        Long value = null;
        assertThrowsWithMessage(
                ValidateException.class,
                () -> ValidatorUtils.validateObjectId(value, FIELD_NAME),
                "The " + FIELD_NAME + " is null.");
    }

    @Test
    void validateObjectId_isNegative() {
        Long value = -randomPositiveLong();
        assertThrowsWithMessage(
                ValidateException.class,
                () -> ValidatorUtils.validateObjectId(value, FIELD_NAME),
                "The " + FIELD_NAME + " is negative.");
    }

    @Test
    void validateObjectId_success() {
        Long value = randomPositiveLong();
        assertDoesNotThrow(() ->
                ValidatorUtils.validateObjectId(value, FIELD_NAME));
    }
}
