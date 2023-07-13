package rgo.tt.common.rest.api;

public class SuccessResponse implements Response {

    private final Status status;

    private SuccessResponse(Status status) {
        this.status = status;
    }

    public static SuccessResponse noContent() {
        return new SuccessResponse(Status.success(StatusCode.NO_CONTENT));
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "status=" + status +
                '}';
    }
}
