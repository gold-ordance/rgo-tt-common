package rgo.tt.common.rest.api;

public enum StatusCode {
    SUCCESS(200),
    STORED(201),
    NO_CONTENT(204),
    INVALID_RQ(400),
    NOT_FOUND(404),
    INVALID_ENTITY(422),
    ERROR(500);

    public static void checkSuccessCode(int code) {
        if (code >= 300 || code < 200) {
            throw new IllegalStateException("The code should be equal to the 2xx pattern, but was " + code);
        }
    }

    public static void checkErrorCode(int code) {
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
