package rgo.tt.common.persistence.utils;

import org.junit.jupiter.api.Test;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.PersistenceException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;

class CommonPersistenceUtilsTest {

    @Test
    void getFirstEntity_empty() {
        List<Object> empty = Collections.emptyList();
        Optional<Object> actual = CommonPersistenceUtils.getFirstEntity(empty);
        assertThat(actual).isNotPresent();
    }

    @Test
    void getFirstEntity_many() {
        List<Object> many = many();
        assertThatThrownBy(() -> CommonPersistenceUtils.getFirstEntity(many))
                .isInstanceOf(PersistenceException.class)
                .hasMessage("The number of entities is not equal to 1.");
    }

    private List<Object> many() {
        int limit = ThreadLocalRandom
                .current()
                .nextInt(2, 10);

        return Stream
                .generate(Object::new)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Test
    void getFirstEntity() {
        List<Object> listWithOneElement = List.of(new Object());
        Optional<Object> opt = CommonPersistenceUtils.getFirstEntity(listWithOneElement);

        assertThat(opt).isPresent();
        assertThat(opt.get()).isIn(listWithOneElement);
    }

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
