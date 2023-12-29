package rgo.tt.common.logger;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.RequestContext;
import com.linecorp.armeria.common.logging.LogFormatter;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;
import com.linecorp.armeria.common.logging.RequestLog;
import com.linecorp.armeria.common.logging.ResponseLogLevelMapper;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.SimpleDecoratingHttpService;
import com.linecorp.armeria.server.logging.LoggingService;

import java.util.function.BiFunction;

public class LoggingDecorator extends SimpleDecoratingHttpService {

    private static final String IGNORED_PATH = "/readiness";

    private final LoggingService service;

    public LoggingDecorator(HttpService delegate) {
        super(delegate);
        service = LoggingService.builder()
                .logWriter(LogWriter.builder()
                        .logger(ApplicationLogger.LOGGER)
                        .logFormatter(logFormatter())
                        .responseCauseFilter(throwable -> false)
                        .requestLogLevel(LogLevel.INFO)
                        .responseLogLevelMapper(LOG_LEVEL_MAPPER)
                        .build())
                .build(delegate);
    }

    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        if (req.path().contains(IGNORED_PATH)) {
            return unwrap().serve(ctx, req);
        }

        return service.serve(ctx, req);
    }

    private static LogFormatter logFormatter() {
        return LogFormatter.builderForText()
                .responseContentSanitizer(RESPONSE_CONTENT_SANITIZER)
                .build();
    }

    private static final BiFunction<? super RequestContext, Object, String> RESPONSE_CONTENT_SANITIZER =
            (requestContext, object) -> object.toString().replaceAll("password: \"[^\"]+\"", "password: \"<secret>\"");

    private static final ResponseLogLevelMapper LOG_LEVEL_MAPPER = rq -> isSuccess(rq) ? LogLevel.INFO : LogLevel.ERROR;

    private static boolean isSuccess(RequestLog rq) {
        return rq.responseCause() == null && rq.responseStatus().isSuccess();
    }
}
