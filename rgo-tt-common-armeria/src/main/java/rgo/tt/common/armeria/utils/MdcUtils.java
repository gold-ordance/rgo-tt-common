package rgo.tt.common.armeria.utils;

import com.linecorp.armeria.common.logging.RequestScopedMdc;
import com.linecorp.armeria.server.ServiceRequestContext;

import java.util.Set;
import java.util.UUID;

public final class MdcUtils {

    static final Set<String> PARAMETERS = Set.of("trace.id");

    private MdcUtils() {
    }

    public static void processRequestContext(ServiceRequestContext ctx) {
        RequestScopedMdc.clear(ctx);

        String value = UUID.randomUUID().toString();
        PARAMETERS.forEach(p -> {
            RequestScopedMdc.put(ctx, p, value);
            ctx.addAdditionalResponseHeader(p, value);
        });
    }
}
