package rgo.tt.common.armeria.test.ratelimiter;

import com.linecorp.armeria.client.WebClient;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;

public abstract class AbstractRestRateLimiterTest {

    private static final int NUMBER_OF_RETRY_REQUESTS = 10;
    private static final int TOO_MANY_REQUESTS_CODE = 429;

    private final WebClient client;

    protected AbstractRestRateLimiterTest(int port) {
        String host = "http://127.0.0.1:" + port;
        client = WebClient.of(host);
    }

    protected boolean isRequestRejected(IntSupplier httpCode) {
        return IntStream.range(0, NUMBER_OF_RETRY_REQUESTS)
                .mapToObj(ignored -> httpCode.getAsInt())
                .anyMatch(code -> code == TOO_MANY_REQUESTS_CODE);
    }

    protected int get(String path) {
        return client.get(path)
                .aggregate()
                .join()
                .status()
                .code();
    }

    protected int post(String path) {
        return client.post(path, "")
                .aggregate()
                .join()
                .status()
                .code();
    }

    protected int put(String path) {
        return client.put(path, "")
                .aggregate()
                .join()
                .status()
                .code();
    }

    protected int delete(String path) {
        return client.delete(path)
                .aggregate()
                .join()
                .status()
                .code();
    }
}
