package rgo.tt.common.grpc.test.simpleserver;

import io.grpc.BindableService;

import java.io.IOException;

public final class GrpcServerManager {

    private static final int DEFAULT_PORT = 9005;

    private static SimpleGrpcServer server;
    private static int port;

    private GrpcServerManager() {
    }

    public static void startGrpcServer(BindableService service) throws IOException {
        if (server == null) {
            port = DEFAULT_PORT;
            server = new SimpleGrpcServer(DEFAULT_PORT, service);
            server.start();
        }
    }

    public static void startGrpcServer(int port, BindableService service) throws IOException {
        if (server == null) {
            GrpcServerManager.port = port;
            server = new SimpleGrpcServer(DEFAULT_PORT, service);
            server.start();
        }
    }

    public static void stopServer() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }

    public static int getPort() {
        return port;
    }
}
