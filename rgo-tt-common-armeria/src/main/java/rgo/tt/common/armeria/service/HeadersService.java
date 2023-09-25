package rgo.tt.common.armeria.service;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.logging.RequestLogProperty;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingHttpService;
import rgo.tt.common.armeria.utils.MdcUtils;

public class HeadersService extends SimpleDecoratingHttpService {

    public HeadersService(HttpService delegate) {
        super(delegate);
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        if (ctx.log().isAvailable(RequestLogProperty.REQUEST_HEADERS)) {
            MdcUtils.processRequestContext(ctx);
        } else {
            ctx.log()
                    .whenAvailable(RequestLogProperty.REQUEST_HEADERS)
                    .thenAccept(requestLog -> MdcUtils.processRequestContext(ctx));
        }

        return unwrap().serve(ctx, req);
    }
}
