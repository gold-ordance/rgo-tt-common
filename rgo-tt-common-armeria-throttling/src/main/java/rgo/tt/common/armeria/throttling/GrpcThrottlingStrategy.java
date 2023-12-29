package rgo.tt.common.armeria.throttling;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.throttling.ThrottlingStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class GrpcThrottlingStrategy extends ThrottlingStrategy<HttpRequest> {

    private final Map<String, ThrottlingStrategy<HttpRequest>> requestLimits = new HashMap<>();

    public GrpcThrottlingStrategy(RateLimitsProperties properties) {
        properties.getGrcpServicesMap()
                .forEach((methodName, limit) ->
                        requestLimits.put(methodName, createThrottlingStrategy(limit)));
    }

    private static ThrottlingStrategy<HttpRequest> createThrottlingStrategy(Integer limit) {
        return limit == null || limit == 0
                ? ThrottlingStrategy.never()
                : ThrottlingStrategy.rateLimiting(limit);
    }

    @Override
    public CompletionStage<Boolean> accept(ServiceRequestContext ctx, HttpRequest request) {
        String methodName = ctx.path();
        ThrottlingStrategy<HttpRequest> strategy = requestLimits.getOrDefault(methodName, ThrottlingStrategy.always());
        return strategy.accept(ctx, request);
    }
}
