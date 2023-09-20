package rgo.tt.common.rest.api;

public class ErrorResponse implements Response {

    private Status status;

    public ErrorResponse() {
    }

    public ErrorResponse(Status status) {
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

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                '}';
    }
}
