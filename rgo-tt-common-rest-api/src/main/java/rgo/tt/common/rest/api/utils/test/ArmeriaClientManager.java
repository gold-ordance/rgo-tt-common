package rgo.tt.common.rest.api.utils.test;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.MediaType;

import static rgo.tt.common.rest.api.utils.test.ArmeriaServerManager.getHost;

public final class ArmeriaClientManager {

    private static final WebClient CLIENT = WebClient.of();

    private ArmeriaClientManager() {
    }

    public static String get() {
        return internalGet(getHost());
    }

    public static String get(String path) {
        return internalGet(getHost() + path);
    }

    private static String internalGet(String url) {
        return CLIENT.get(url)
                .aggregate()
                .join()
                .contentUtf8();
    }

    public static String post(String content) {
        return CLIENT.prepare()
                .post(getHost())
                .content(MediaType.JSON_UTF_8, content)
                .execute()
                .aggregate()
                .join()
                .contentUtf8();
    }

    public static String put(String content) {
        return CLIENT.prepare()
                .put(getHost())
                .content(MediaType.JSON_UTF_8, content)
                .execute()
                .aggregate()
                .join()
                .contentUtf8();
    }

    public static String delete(String url) {
        return CLIENT.prepare()
                .delete(getHost() + url)
                .execute()
                .aggregate()
                .join()
                .contentUtf8();
    }
}
