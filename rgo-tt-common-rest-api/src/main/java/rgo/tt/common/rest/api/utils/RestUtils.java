package rgo.tt.common.rest.api.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import rgo.tt.common.rest.api.Response;

import static com.linecorp.armeria.common.HttpStatus.isContentAlwaysEmpty;

public final class RestUtils {

    private static final ObjectMapper OM = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private RestUtils() {
    }

    public static HttpResponse mapToHttp(Response response) {
        int httpCode = response.getStatus().getStatusCode().getHttpCode();
        if (isContentAlwaysEmpty(httpCode)) {
            return HttpResponse.of(httpCode);
        }

        HttpData content = HttpData.ofUtf8(json(response));
        return HttpResponse.of(HttpStatus.valueOf(httpCode), MediaType.JSON_UTF_8, content);
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

