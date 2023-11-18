package rgo.tt.common.persistence.utils;

import org.junit.jupiter.api.Test;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.PersistenceException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;

class CommonPersistenceUtilsTest {

    @Test
    void validateSaveResult_keyIsNull() {
        Number key = null;
        assertThatThrownBy(() -> CommonPersistenceUtils.validateSaveResult(key))
                .isInstanceOf(PersistenceException.class)
                .hasMessage("The entity save error.");
    }

    @Test
    void validateSaveResult_success() {
        Number key = randomPositiveLong();
        assertThatCode(() -> CommonPersistenceUtils.validateSaveResult(key))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUpdateResult_rowsAffectedEqualTo0() {
        int rowsAffected = 0;
        assertThatThrownBy(() -> CommonPersistenceUtils.validateUpdateResult(rowsAffected))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("The entityId not found in the storage.");
    }

    @Test
    void validateUpdateResult_success() {
        int rowsAffected = 1;
        assertThatCode(() -> CommonPersistenceUtils.validateUpdateResult(rowsAffected))
                .doesNotThrowAnyException();
    }
}
