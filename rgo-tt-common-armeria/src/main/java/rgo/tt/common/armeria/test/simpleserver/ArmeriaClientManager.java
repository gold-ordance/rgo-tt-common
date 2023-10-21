package rgo.tt.common.armeria.test.simpleserver;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.MediaType;

public final class ArmeriaClientManager {

    private static final WebClient CLIENT = WebClient.of();

    private ArmeriaClientManager() {
    }

    public static String get() {
        return internalGet(ArmeriaServerManager.getHost());
    }

    public static String get(String path) {
        return internalGet(ArmeriaServerManager.getHost() + path);
    }

    private static String internalGet(String url) {
        return CLIENT.get(url)
                .aggregate()
                .join()
                .contentUtf8();
    }

    public static String post(String content) {
        return CLIENT.prepare()
                .post(ArmeriaServerManager.getHost())
                .content(MediaType.JSON_UTF_8, content)
                .execute()
                .aggregate()
                .join()
                .contentUtf8();
    }

    public static String put(String content) {
        return CLIENT.prepare()
                .put(ArmeriaServerManager.getHost())
                .content(MediaType.JSON_UTF_8, content)
                .execute()
                .aggregate()
                .join()
                .contentUtf8();
    }

    public static String delete(String url) {
        return CLIENT.prepare()
                .delete(ArmeriaServerManager.getHost() + url)
                .execute()
                .aggregate()
                .join()
                .contentUtf8();
    }
}
