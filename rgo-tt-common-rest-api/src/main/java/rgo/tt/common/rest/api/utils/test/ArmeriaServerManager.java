package rgo.tt.common.rest.api.utils.test;

public final class ArmeriaServerManager {

    private static final int PORT = 8081;
    private static final String HOST = "http://127.0.0.1:" + PORT;

    private static SimpleArmeriaServer server;

    private ArmeriaServerManager() {
    }

    public static void startServerWithService(Object restService) {
        if (server == null) {
            server = new SimpleArmeriaServer(PORT, restService);
            server.start();
        }
    }

    public static void stopServer() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }

    public static String getHost() {
        return HOST;
    }
}
