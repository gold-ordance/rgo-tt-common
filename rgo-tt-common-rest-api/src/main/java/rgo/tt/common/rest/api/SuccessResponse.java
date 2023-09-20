package rgo.tt.common.rest.api;

public class SuccessResponse implements Response {

    private Status status;

    public SuccessResponse() {
    }

    public SuccessResponse(Status status) {
        this.status = status;
    }

    public static SuccessResponse noContent() {
        return new SuccessResponse(Status.success(StatusCode.NO_CONTENT));
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
        return "SuccessResponse{" +
                "status=" + status +
                '}';
    }
}
