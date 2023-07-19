package rgo.tt.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public final class TestUtils {

    private TestUtils() {
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
}
