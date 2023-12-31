package rgo.tt.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public final class TestUtils {

    private TestUtils() {
    }

    public static <T> void validateNonNullFields(T object) {
        try {
            validate(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void validate(T object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.get(object) == null) {
                throw new IllegalStateException("The field={" + field + "} should not be null.");
            }
        }
    }

    public static <T> void validateNullFieldsExcept(T object, List<String> nonNullFields) {
        try {
            validate(object, nonNullFields);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void validate(T object, List<String> nonEmptyFields) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (shouldSkipField(nonEmptyFields, field)) continue;

            field.setAccessible(true);

            if (field.get(object) != null) {
                throw new IllegalStateException("The field={" + field + "} should be null.");
            }
        }
    }

    private static boolean shouldSkipField(List<String> nonEmptyFields, Field field) {
        return nonEmptyFields.contains(field.getName())
                || Modifier.isStatic(field.getModifiers());
    }
}
