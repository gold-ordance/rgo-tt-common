package rgo.tt.common.logging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MdcUtilsTest {

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void init() {
        MdcUtils.init();
        MdcUtils.PARAMETERS
                .forEach(this::nonNull);
    }

    private void nonNull(String parameter) {
        String value = MDC.get(parameter);
        assertNotNull(value);
    }

    @Test
    void clear() {
        MdcUtils.init();
        MdcUtils.clear();
        MdcUtils.PARAMETERS
                .forEach(this::checkIsNull);
    }

    private void checkIsNull(String parameter) {
        String value = MDC.get(parameter);
        assertNull(value);
    }
}
