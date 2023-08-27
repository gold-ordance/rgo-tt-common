package rgo.tt.common.rest.api;

import java.util.Objects;

import static rgo.tt.common.rest.api.StatusCode.validateErrorCode;
import static rgo.tt.common.rest.api.StatusCode.validateSuccessCode;

public class Status {

    private final StatusCode code;
    private final String message;

    private Status(StatusCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Status success() {
        return new Status(StatusCode.SUCCESS, null);
    }

    public static Status success(StatusCode code) {
        validateSuccessCode(code.getHttpCode());
        return new Status(code, null);
    }

    public static Status error(StatusCode code, String message) {
        validateErrorCode(code.getHttpCode());
        return new Status(code, message);
    }

    public StatusCode getStatusCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return code == status.code
                && Objects.equals(message, status.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
