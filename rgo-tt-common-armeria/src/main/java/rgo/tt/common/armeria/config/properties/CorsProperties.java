package rgo.tt.common.armeria.config.properties;

import com.linecorp.armeria.common.HttpMethod;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class CorsProperties {

    @Value("${cors.origins}")
    private List<String> origins;

    @Value("${cors.methods}")
    private List<HttpMethod> httpMethods;

    @Value("${cors.maxAgeSeconds}")
    private long maxAgeSeconds;

    public List<String> getOrigins() {
        return origins;
    }

    public void setOrigins(List<String> origins) {
        this.origins = origins;
    }

    public List<HttpMethod> getHttpMethods() {
        return httpMethods;
    }

    public void setHttpMethods(List<HttpMethod> httpMethods) {
        this.httpMethods = httpMethods;
    }

    public long getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(long maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }
}
