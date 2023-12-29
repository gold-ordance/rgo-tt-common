package rgo.tt.common.armeria.throttling;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RateLimitsProperties {

    private Map<String, List<MethodLimit>> restServices;
    private List<MethodLimit> grpcServices;

    public Map<String, Map<String, Integer>> getRestServicesMap() {
        return restServices == null
                ? Map.of()
                : restServices
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .collect(Collectors.toMap(
                                        m -> m.method,
                                        m -> m.limit
                                ))
                ));
    }

    public Map<String, List<MethodLimit>> getRestServices() {
        return restServices;
    }

    public void setRestServices(Map<String, List<MethodLimit>> restServices) {
        this.restServices = restServices;
    }

    public Map<String, Integer> getGrcpServicesMap() {
        return grpcServices == null
                ? Map.of()
                : grpcServices
                .stream()
                .collect(Collectors.toMap(m -> m.method, m -> m.limit));
    }

    public List<MethodLimit> getGrpcServices() {
        return grpcServices;
    }

    public void setGrpcServices(List<MethodLimit> grpcServices) {
        this.grpcServices = grpcServices;
    }

    public static class MethodLimit {

        private String method;
        private Integer limit;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }
    }
}
