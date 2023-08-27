package rgo.tt.common.rest.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Import(HandlerInterceptorConfig.class)
public class WebCommonConfig implements WebMvcConfigurer {

    private static final String ORIGIN = "http://localhost:5173";

    private final List<HandlerInterceptor> interceptors;

    public WebCommonConfig(List<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(ORIGIN)
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
