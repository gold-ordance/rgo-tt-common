package rgo.tt.common.utils;

import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class TestUtils {

    private TestUtils() {
    }

    public static <T> void assertNonNullFields(T object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.get(object) == null) {
                throw new IllegalStateException("The field={" + field + "} should not be null.");
            }
        }
    }

    public static <T> void assertNullFields(T object, List<String> nonEmptyFields) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (nonEmptyFields.contains(field.getName()) || Modifier.isStatic(field.getModifiers())) continue;

            field.setAccessible(true);

            if (field.get(object) != null) {
                throw new IllegalStateException("The field={" + field + "} should be null.");
            }
        }
    }

    public static <T extends Throwable> void assertThrowsWithMessage(Class<T> expectedType, Executable executable, String errorMessage) {
        Throwable th = assertThrows(expectedType, executable);
        assertEquals(errorMessage, th.getMessage());
    }
}
