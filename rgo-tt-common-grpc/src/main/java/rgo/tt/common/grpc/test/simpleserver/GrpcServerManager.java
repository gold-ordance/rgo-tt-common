package rgo.tt.common.grpc.test.simpleserver;

import io.grpc.BindableService;

import java.io.IOException;

public final class GrpcServerManager {

    private static final int PORT = 8090;
    private static SimpleGrpcServer server;

    private GrpcServerManager() {
    }

    public static void startGrpcServer(BindableService service) throws IOException {
        if (server == null) {
            server = new SimpleGrpcServer(PORT, service);
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
        return PORT;
    }
}
