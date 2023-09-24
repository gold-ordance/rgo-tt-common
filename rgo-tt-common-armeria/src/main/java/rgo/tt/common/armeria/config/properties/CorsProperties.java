package rgo.tt.common.armeria.config.properties;

import com.linecorp.armeria.common.HttpMethod;

import java.util.List;

public class CorsProperties {

    private List<String> origins;
    private List<HttpMethod> methods;
    private long maxAgeSeconds;

    public List<String> getOrigins() {
        return origins;
    }

    public void setOrigins(List<String> origins) {
        this.origins = origins;
    }

    public List<HttpMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<HttpMethod> methods) {
        this.methods = methods;
    }

    public long getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(long maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }
}
