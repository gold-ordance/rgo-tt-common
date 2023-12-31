package rgo.tt.common.armeria.rest;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.ExceptionHandlerFunction;
import org.apache.commons.lang3.exception.ExceptionUtils;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.UniqueViolationException;
import rgo.tt.common.exceptions.ValidateException;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;

import static rgo.tt.common.armeria.rest.HttpDataProcessor.mapToHttp;

public class ExceptionCommonHandler implements ExceptionHandlerFunction {

    @Override
    public HttpResponse handleException(ServiceRequestContext ctx, HttpRequest req, Throwable cause) {
        if (cause instanceof ValidateException e) {
            Response response = ErrorResponse.invalidRq(e.getMessage());
            return mapToHttp(response);
        }

        if (cause instanceof InvalidEntityException e) {
            Response response = ErrorResponse.invalidEntity(e.getMessage());
            return mapToHttp(response);
        }

        if (cause instanceof UniqueViolationException e) {
            Response response = ErrorResponse.invalidEntity(e.getMessage());
            return mapToHttp(response);
        }

        Response response = ErrorResponse.error(ExceptionUtils.getStackTrace(cause));
        return mapToHttp(response);
    }
}
