package rgo.tt.common.rest.api;

import java.util.Objects;

import static rgo.tt.common.rest.api.StatusCode.checkErrorCode;
import static rgo.tt.common.rest.api.StatusCode.checkSuccessCode;

public class Status {

    private final StatusCode code;
    private final String message;

    private Status(StatusCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Status success(StatusCode code) {
        checkSuccessCode(code.getHttpCode());
        return new Status(code, null);
    }

    public static Status error(StatusCode code, String message) {
        checkErrorCode(code.getHttpCode());
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
        return code == status.code && Objects.equals(message, status.message);
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
