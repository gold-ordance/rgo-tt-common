package rgo.tt.common.armeria.config;

import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.cors.CorsService;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.common.armeria.config.properties.CorsProperties;
import rgo.tt.common.armeria.service.HeadersService;
import rgo.tt.common.armeria.service.ProbeService;

import java.util.function.Function;

@Configuration
public class ArmeriaCommonConfig {

    @Bean
    @ConfigurationProperties("cors")
    public CorsProperties corsProperties() {
        return new CorsProperties();
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
