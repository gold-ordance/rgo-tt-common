package rgo.tt.common.armeria;

import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.server.DecoratingHttpServiceFunction;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.cors.CorsService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.throttling.ThrottlingService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.common.armeria.headers.HeadersService;
import rgo.tt.common.armeria.throttling.GrpcThrottlingStrategy;
import rgo.tt.common.armeria.throttling.RateLimitsProperties;
import rgo.tt.common.armeria.throttling.RestThrottlingStrategy;
import rgo.tt.common.armeria.throttling.ThrottlingStrategyCoordinator;

import java.util.function.Function;

import static rgo.tt.common.armeria.ProbeService.READINESS_PATH;

@Configuration
public class ArmeriaCommonConfig {

    @Bean
    @ConfigurationProperties("app.rate-limiter")
    public RateLimitsProperties rateLimitsProperties() {
        return new RateLimitsProperties();
    }

    @Bean
    @ConfigurationProperties("app.cors")
    public CorsProperties corsProperties() {
        return new CorsProperties();
    }

    @Bean
    public RestThrottlingStrategy restThrottlingStrategy() {
        return new RestThrottlingStrategy(rateLimitsProperties());
    }

    @Bean
    public GrpcThrottlingStrategy grpcThrottlingStrategy() {
        return new GrpcThrottlingStrategy(rateLimitsProperties());
    }

    @Bean
    public Function<? super HttpService, ThrottlingService> throttlingDecorator() {
        ThrottlingStrategyCoordinator strategy = new ThrottlingStrategyCoordinator(restThrottlingStrategy(), grpcThrottlingStrategy());
        return ThrottlingService
                .builder(strategy)
                .onRejectedRequest((delegate, ctx, req, cause) -> strategy.rejectRequest(ctx))
                .newDecorator();
    }

    @Bean
    public Function<? super HttpService, CorsService> corsDecorator(CorsProperties properties) {
        return CorsService.builder(properties.getOrigins())
                .allowCredentials()
                .allowRequestMethods(properties.getMethods())
                .maxAge(properties.getMaxAgeSeconds())
                .newDecorator();
    }

    @Bean
    public Function<? super HttpService, HeadersService> headersDecorator() {
        return HeadersService::new;
    }

    @Bean
    public Function<? super HttpService, MetricCollectingService> metricsDecorator() {
        return MetricCollectingService.builder(MeterIdPrefixFunction.ofDefault("armeria.http.service"))
                .newDecorator();
    }

    @Bean
    public DecoratingHttpServiceFunction loggingDecorator() {
        return (delegate, ctx, req) -> {
            if (req.path().contains(READINESS_PATH)) {
                return delegate.serve(ctx, req);
            } else {
                return LoggingService.builder()
                        .requestLogLevel(LogLevel.INFO)
                        .successfulResponseLogLevel(LogLevel.INFO)
                        .failureResponseLogLevel(LogLevel.WARN)
                        .build(delegate)
                        .serve(ctx, req);
            }
        };
    }

    @Bean
    public ProbeService probeService() {
        return new ProbeService();
    }
}
