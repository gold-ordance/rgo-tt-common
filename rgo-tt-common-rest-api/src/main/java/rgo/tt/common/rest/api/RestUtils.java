package rgo.tt.common.rest.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

public final class RestUtils {

    public static final String DIGITS_PATTERN = "[0-9]+";

    private static final ObjectMapper OM = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private RestUtils() {
    }

    public static ResponseEntity<Response> convert(Response response) {
        return ResponseEntity
                .status(response.getStatus().getStatusCode().getHttpCode())
                .body(response);
    }

    public static String json(Object o) {
        try {
            return OM.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize the object: ", e);
        }
    }
}

