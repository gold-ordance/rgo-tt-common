package rgo.tt.common.rest.api.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;

import static com.linecorp.armeria.common.HttpStatus.isContentAlwaysEmpty;

public final class RestUtils {

    public static final String DIGITS_PATTERN = "[0-9]+";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);
    private static final ObjectMapper OM = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private RestUtils() {
    }

    public static ResponseEntity<Response> convertToResponseEntity(Response response) {
        logResponse(response);
        return convert(response);
    }

    public static HttpResponse mapToHttp(Response response) {
        int httpCode = response.getStatus().getStatusCode().getHttpCode();
        if (isContentAlwaysEmpty(httpCode)) {
            return HttpResponse.of(httpCode);
        }

        HttpData content = HttpData.ofUtf8(json(response));
        return HttpResponse.of(HttpStatus.valueOf(httpCode), MediaType.JSON_UTF_8, content);
    }

    private static void logResponse(Response response) {
        if (response instanceof ErrorResponse) LOGGER.error("Response: {}", response);
        else LOGGER.info("Response: {}", response);
    }

    private static ResponseEntity<Response> convert(Response response) {
        return ResponseEntity
                .status(response.getStatus().getStatusCode().getHttpCode())
                .body(response);
    }

    public static String json(Object object) {
        try {
            return OM.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization failed. object= " + object, e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OM.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Deserialization failed. json= " + json, e);
        }
    }
}

