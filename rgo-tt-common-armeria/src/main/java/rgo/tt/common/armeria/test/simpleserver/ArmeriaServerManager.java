package rgo.tt.common.armeria.test.simpleserver;

public final class ArmeriaServerManager {

    private static final int DEFAULT_PORT = 8090;
    private static final String DEFAULT_HOST = "http://127.0.0.1:" + DEFAULT_PORT;

    private static SimpleArmeriaServer server;
    private static String host;

    private ArmeriaServerManager() {
    }

    public static void startArmeriaServer(Object restService) {
        if (server == null) {
            host = DEFAULT_HOST;
            server = new SimpleArmeriaServer(DEFAULT_PORT, restService);
            server.start();
        }
    }

    public static void startArmeriaServer(int port, Object restService) {
        if (server == null) {
            host = "http://127.0.0.1:" + port;
            server = new SimpleArmeriaServer(port, restService);
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
        return host;
    }
}
