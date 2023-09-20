package rgo.tt.common.rest.api;

import java.util.Objects;

import static rgo.tt.common.rest.api.StatusCode.validateErrorCode;
import static rgo.tt.common.rest.api.StatusCode.validateSuccessCode;

public class Status {

    private StatusCode statusCode;
    private String message;

    public Status() {
    }

    public Status(StatusCode statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public static Status success() {
        return new Status(StatusCode.SUCCESS, null);
    }

    public static Status success(StatusCode statusCode) {
        validateSuccessCode(statusCode.getHttpCode());
        return new Status(statusCode, null);
    }

    public static Status error(StatusCode statusCode, String message) {
        validateErrorCode(statusCode.getHttpCode());
        return new Status(statusCode, message);
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return statusCode == status.statusCode
                && Objects.equals(message, status.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message);
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
