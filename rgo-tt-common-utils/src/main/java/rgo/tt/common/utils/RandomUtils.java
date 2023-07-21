package rgo.tt.common.utils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {

    private RandomUtils() {
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static Long randomPositiveLong() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }

    public static <T> T randomElement(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("The list is empty.");
        }

        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
