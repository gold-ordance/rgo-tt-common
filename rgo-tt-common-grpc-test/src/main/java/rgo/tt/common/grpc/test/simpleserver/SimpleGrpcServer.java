package rgo.tt.common.grpc.test.simpleserver;


import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SimpleGrpcServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleGrpcServer.class);

    private final int port;
    private final BindableService service;

    private Server server;

    public SimpleGrpcServer(int port, BindableService service) {
        this.port = port;
        this.service = service;
    }

    public void start() throws IOException {
        if (server != null) {
            LOGGER.warn("Simple grpc-server already started on port {}.", port);
            return;
        }

        server = ServerBuilder
                .forPort(port)
                .addService(service)
                .build();

        server.start();
        LOGGER.info("Simple grpc-server started on port {}.", port);
    }

    public void stop() {
        if (this.server == null) {
            LOGGER.warn("Simple grpc-server already stopped.");
            return;
        }

        server.shutdownNow();
        server = null;
        LOGGER.info("Simple grpc-server stopped.");
    }
}
