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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.TestUtils.assertThrowsWithMessage;

class CommonPersistenceUtilsTest {

    @Test
    void getFirstEntity_empty() {
        List<Object> empty = Collections.emptyList();
        Optional<Object> actual = CommonPersistenceUtils.getFirstEntity(empty);
        assertFalse(actual.isPresent());
    }

    @Test
    void getFirstEntity_many() {
        List<Object> many = many();
        assertThrowsWithMessage(
                PersistenceException.class,
                () -> CommonPersistenceUtils.getFirstEntity(many),
                "The number of entities is not equal to 1.");
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

        assertTrue(opt.isPresent());
        assertEquals(opt.get(), listWithOneElement.get(0));
    }

    @Test
    void validateSaveResult_keyIsNull() {
        Number key = null;
        assertThrowsWithMessage(
                PersistenceException.class,
                () -> CommonPersistenceUtils.validateSaveResult(key),
                "The entity save error.");
    }

    @Test
    void validateSaveResult_success() {
        Number key = randomPositiveLong();
        assertDoesNotThrow(
                () -> CommonPersistenceUtils.validateSaveResult(key));
    }

    @Test
    void validateUpdateResult_rowsAffectedEqualTo0() {
        int rowsAffected = 0;
        assertThrowsWithMessage(
                InvalidEntityException.class,
                () -> CommonPersistenceUtils.validateUpdateResult(rowsAffected),
                "The entityId not found in the storage.");
    }

    @Test
    void validateUpdateResult_success() {
        int rowsAffected = 1;
        assertDoesNotThrow(() ->
                CommonPersistenceUtils.validateUpdateResult(rowsAffected));
    }
}
