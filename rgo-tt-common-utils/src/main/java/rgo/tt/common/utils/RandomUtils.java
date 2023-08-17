package rgo.tt.common.utils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {

    private static final int MAX_SIZE_SHORT_STRING = 8;
    private static final int MAX_STRING_REPEAT = 32;

    private RandomUtils() {
    }

    public static String randomShortString() {
        return randomString().substring(0, MAX_SIZE_SHORT_STRING);
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static String randomBigString() {
        int repeat = randomPositiveInt(MAX_STRING_REPEAT);
        return randomString().repeat(repeat);
    }

    public static long randomPositiveLong() {
        return randomPositiveLong(Long.MAX_VALUE);
    }

    private static long randomPositiveLong(long exclusiveMaxValue) {
        return ThreadLocalRandom.current().nextLong(exclusiveMaxValue);
    }

    private static int randomPositiveInt(int exclusiveMaxValue) {
        return ThreadLocalRandom.current().nextInt(exclusiveMaxValue);
    }

    public static <T> T randomElement(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("The list is empty.");
        }

        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
