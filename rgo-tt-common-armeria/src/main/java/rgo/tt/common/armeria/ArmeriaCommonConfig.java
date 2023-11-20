package rgo.tt.common.armeria;

import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.cors.CorsService;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.throttling.ThrottlingService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.common.armeria.headers.HeadersService;
import rgo.tt.common.armeria.throttling.LimitsByMethodsThrottlingStrategy;
import rgo.tt.common.armeria.throttling.RateLimitsProperties;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.utils.RestUtils;

import java.util.function.Function;

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
    public Function<? super HttpService, ThrottlingService> throttlingDecorator() {
        return ThrottlingService
                .builder(new LimitsByMethodsThrottlingStrategy(rateLimitsProperties()))
                .onRejectedRequest((delegate, ctx, req, cause) -> RestUtils.mapToHttp(ErrorResponse.tooManyRequests()))
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
    public ProbeService probeService() {
        return new ProbeService();
    }
}
