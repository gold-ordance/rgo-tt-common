package rgo.tt.common.logging;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.MDC;

import java.util.Set;
import java.util.UUID;

public final class MdcUtils {

    @VisibleForTesting
    static final Set<String> PARAMETERS = Set.of("trace.id");

    private MdcUtils() {
    }

    public static void init() {
        PARAMETERS.forEach(p ->
                MDC.put(p, UUID.randomUUID().toString()));
    }

    public static void clear() {
        MDC.clear();
    }
}
