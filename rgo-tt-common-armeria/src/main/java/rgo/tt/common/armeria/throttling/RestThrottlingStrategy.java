package rgo.tt.common.armeria.throttling;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.throttling.ThrottlingStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class RestThrottlingStrategy extends ThrottlingStrategy<HttpRequest> {

    private final Map<String, Map<String, ThrottlingStrategy<HttpRequest>>> requestLimits = new HashMap<>();

    public RestThrottlingStrategy(RateLimitsProperties properties) {
        properties.getRestServicesMap()
                .forEach((serviceName, methods) ->
                        requestLimits.put(serviceName, getThrottlingLimits(methods)));
    }

    private Map<String, ThrottlingStrategy<HttpRequest>> getThrottlingLimits(Map<String, Integer> methods) {
        return methods
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        RestThrottlingStrategy::createThrottlingStrategy
                ));
    }

    private static ThrottlingStrategy<HttpRequest> createThrottlingStrategy(Map.Entry<String, Integer> method) {
        Integer limit = method.getValue();
        return limit == null || limit == 0
                ? ThrottlingStrategy.never()
                : ThrottlingStrategy.rateLimiting(limit);
    }

    @Override
    public CompletionStage<Boolean> accept(ServiceRequestContext ctx, HttpRequest request) {
        String serviceName = ctx.config().defaultServiceNaming().serviceName(ctx);
        String methodName = ctx.config().defaultLogName();

        Map<String, ThrottlingStrategy<HttpRequest>> throttlingLimits = requestLimits.getOrDefault(serviceName, Collections.emptyMap());
        ThrottlingStrategy<HttpRequest> strategy = throttlingLimits.getOrDefault(methodName, ThrottlingStrategy.always());

        return strategy.accept(ctx, request);
    }
}
