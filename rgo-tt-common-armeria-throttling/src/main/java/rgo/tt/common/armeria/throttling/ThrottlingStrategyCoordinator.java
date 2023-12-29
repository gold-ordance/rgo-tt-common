package rgo.tt.common.armeria.throttling;

import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.throttling.ThrottlingStrategy;
import rgo.tt.common.armeria.rest.RestUtils;
import rgo.tt.common.rest.api.ErrorResponse;

import java.util.concurrent.CompletionStage;

public class ThrottlingStrategyCoordinator extends ThrottlingStrategy<HttpRequest> {

    private final RestThrottlingStrategy restThrottlingStrategy;
    private final GrpcThrottlingStrategy grpcThrottlingStrategy;

    public ThrottlingStrategyCoordinator(
            RestThrottlingStrategy restThrottlingStrategy,
            GrpcThrottlingStrategy grpcThrottlingStrategy
    ) {
        this.restThrottlingStrategy = restThrottlingStrategy;
        this.grpcThrottlingStrategy = grpcThrottlingStrategy;
    }

    @Override
    public CompletionStage<Boolean> accept(ServiceRequestContext ctx, HttpRequest request) {
        return isGrpcRequest(ctx)
                ? grpcThrottlingStrategy.accept(ctx, request)
                : restThrottlingStrategy.accept(ctx, request);
    }

    public HttpResponse rejectRequest(ServiceRequestContext ctx) {
        return isGrpcRequest(ctx)
                ? HttpResponse.of(HttpStatus.TOO_MANY_REQUESTS, MediaType.create("application", "grpc+proto"), HttpData.empty())
                : RestUtils.mapToHttp(ErrorResponse.tooManyRequests());
    }

    private static boolean isGrpcRequest(ServiceRequestContext ctx) {
        return "application/grpc".equals(ctx.request().headers().get("content-type"));
    }
}
