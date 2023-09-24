package rgo.tt.common.armeria.config;

import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.cors.CorsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.common.armeria.config.properties.CorsProperties;

import java.util.function.Function;

@Configuration
public class ArmeriaCommonConfig {

    @Bean
    public CorsProperties corsProperties() {
        return new CorsProperties();
    }

    @Bean
    public Function<? super HttpService, CorsService> corsDecorator(CorsProperties properties) {
        return CorsService.builder(properties.getOrigins())
                .allowCredentials()
                .allowRequestMethods(properties.getHttpMethods())
                .maxAge(properties.getMaxAgeSeconds())
                .newDecorator();
    }
}
