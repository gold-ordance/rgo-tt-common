package rgo.tt.common.armeria.throttling;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RateLimitsProperties {

    private Map<String, List<MethodLimit>> services;

    public Map<String, Map<String, Integer>> getServicesMap() {
        return services
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .collect(Collectors.toMap(
                                        v -> v.method,
                                        v -> Integer.parseInt(v.limit)
                                ))
                ));
    }

    public Map<String, List<MethodLimit>> getServices() {
        return services;
    }

    public void setServices(Map<String, List<MethodLimit>> services) {
        this.services = services;
    }

    public static class MethodLimit {

        private String method;
        private String limit;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }
    }
}
