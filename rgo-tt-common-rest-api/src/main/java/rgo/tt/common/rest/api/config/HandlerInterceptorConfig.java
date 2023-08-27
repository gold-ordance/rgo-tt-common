package rgo.tt.common.rest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import rgo.tt.common.rest.api.interceptor.MdcInterceptor;

@Configuration
public class HandlerInterceptorConfig {

    @Bean
    public HandlerInterceptor mdcInterceptor() {
        return new MdcInterceptor();
    }
}
