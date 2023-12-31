package rgo.tt.common.armeria.headers;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.logging.RequestLogProperty;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingHttpService;

public class HeadersDecorator extends SimpleDecoratingHttpService {

    public HeadersDecorator(HttpService delegate) {
        super(delegate);
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        if (ctx.log().isAvailable(RequestLogProperty.REQUEST_HEADERS)) {
            LoggingContextUtils.processRequestContext(ctx);
        } else {
            ctx.log()
                    .whenAvailable(RequestLogProperty.REQUEST_HEADERS)
                    .thenAccept(requestLog -> LoggingContextUtils.processRequestContext(ctx));
        }

        return unwrap().serve(ctx, req);
    }
}
