package rgo.tt.common.validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;

class ValidatorUtilsTest {

    private static final String FIELD_NAME = "field";

    @Test
    void validateString_isNull() {
        String value = null;
        assertThatThrownBy(() -> ValidatorUtils.validateString(value, FIELD_NAME))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The " + FIELD_NAME + " is null.");
    }

    @Test
    void validateString_isEmpty() {
        String value = "";
        assertThatThrownBy(() -> ValidatorUtils.validateString(value, FIELD_NAME))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The " + FIELD_NAME + " is empty.");
    }

    @Test
    void validateString_success() {
        String value = randomString();
        assertThatCode(() -> ValidatorUtils.validateString(value, FIELD_NAME))
                .doesNotThrowAnyException();
    }

    @Test
    void validateObjectId_isNull() {
        Long value = null;
        assertThatThrownBy(() -> ValidatorUtils.validateObjectId(value, FIELD_NAME))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The " + FIELD_NAME + " is null.");
    }

    @Test
    void validateObjectId_isNegative() {
        Long value = -randomPositiveLong();
        assertThatThrownBy(() -> ValidatorUtils.validateObjectId(value, FIELD_NAME))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The " + FIELD_NAME + " is negative.");
    }

    @Test
    void validateObjectId_success() {
        Long value = randomPositiveLong();
        assertThatCode(() -> ValidatorUtils.validateObjectId(value, FIELD_NAME))
                .doesNotThrowAnyException();
    }
}
