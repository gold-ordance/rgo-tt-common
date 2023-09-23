package rgo.tt.common.utils;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RandomUtilsTest {

    @Test
    void randomElement() {
        List<Object> objects = randomList();
        Object element = RandomUtils.randomElement(objects);
        assertThat(element).isIn(objects);
    }

    private List<Object> randomList() {
        int limit = ThreadLocalRandom.current().nextInt(1, 10);
        return Stream
                .generate(Object::new)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Test
    void randomElement_listIsEmpty() {
        List<Object> objects = Collections.emptyList();
        assertThatThrownBy(() -> RandomUtils.randomElement(objects))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The list is empty.");
    }

    @Test
    void randomPositiveLong() {
        long actual = RandomUtils.randomPositiveLong();
        assertThat(isPositiveNumber(actual)).isTrue();
    }

    private static boolean isPositiveNumber(long actual) {
        return actual > 0;
    }
}
