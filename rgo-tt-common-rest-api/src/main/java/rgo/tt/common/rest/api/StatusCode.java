package rgo.tt.common.rest.api;

public enum StatusCode {

    SUCCESS(200),
    STORED(201),
    NO_CONTENT(204),
    INVALID_RQ(400),
    NOT_FOUND(404),
    INVALID_ENTITY(422),
    TOO_MANY_REQUESTS(429),
    ERROR(500);

    public static void validateSuccessCode(int code) {
        if (isUnsuccessfulCode(code)) {
            throw new IllegalStateException("The code should be equal to the 2xx pattern, but was " + code);
        }
    }

    private static boolean isUnsuccessfulCode(int code) {
        return code >= 300 || code < 200;
    }

    public static void validateErrorCode(int code) {
        if (code < 400) {
            throw new IllegalStateException("The code should be equal to the 4xx or 5xx pattern, but was " + code);
        }
    }

    private final int httpCode;

    StatusCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
