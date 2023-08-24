package rgo.tt.common.rest.api;

import static rgo.tt.common.rest.api.ErrorResponse.notFound;
import static rgo.tt.common.rest.api.SuccessResponse.noContent;

public interface Response {

    Status getStatus();

    static Response from(boolean isDeleted) {
        return isDeleted
                ? noContent()
                : notFound();
    }
}
