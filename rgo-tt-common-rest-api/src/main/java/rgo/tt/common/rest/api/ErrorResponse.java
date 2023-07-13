package rgo.tt.common.rest.api;

public class ErrorResponse implements Response {

    private final Status status;

    private ErrorResponse(Status status) {
        this.status = status;
    }

    public static ErrorResponse invalidRq(String errorMsg) {
        return new ErrorResponse(Status.error(StatusCode.INVALID_RQ, errorMsg));
    }

    public static ErrorResponse notFound() {
        return new ErrorResponse(Status.error(StatusCode.NOT_FOUND, null));
    }

    public static ErrorResponse invalidEntity(String errorMsg) {
        return new ErrorResponse(Status.error(StatusCode.INVALID_ENTITY, errorMsg));
    }

    public static ErrorResponse error(String errorMsg) {
        return new ErrorResponse(Status.error(StatusCode.ERROR, errorMsg));
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                '}';
    }
}
