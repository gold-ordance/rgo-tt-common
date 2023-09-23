package rgo.tt.common.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.RandomUtils.randomBigString;
import static rgo.tt.common.utils.RandomUtils.randomShortString;

class HelperUtilsTest {

    @Test
    void size() {
        String text = randomBigString();
        String expected = "{size=" + text.length() + "}";
        String actual = HelperUtils.size(text);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getFirstSymbol() {
        String text = randomShortString();
        String expected = String.valueOf(text.charAt(0));
        String actual = HelperUtils.getFirstSymbol(text);
        assertThat(actual).isEqualTo(expected);
    }
}
