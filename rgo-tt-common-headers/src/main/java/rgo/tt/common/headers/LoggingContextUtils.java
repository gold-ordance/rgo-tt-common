package rgo.tt.common.headers;

import com.linecorp.armeria.common.logging.RequestScopedMdc;
import com.linecorp.armeria.server.ServiceRequestContext;

import java.util.Set;
import java.util.UUID;

final class LoggingContextUtils {

    private static final Set<String> PARAMETERS = Set.of("trace.id");

    private LoggingContextUtils() {
    }

    static void processRequestContext(ServiceRequestContext ctx) {
        RequestScopedMdc.clear(ctx);

        String value = UUID.randomUUID().toString();
        PARAMETERS.forEach(p -> {
            RequestScopedMdc.put(ctx, p, value);
            ctx.addAdditionalResponseHeader(p, value);
        });
    }
}
