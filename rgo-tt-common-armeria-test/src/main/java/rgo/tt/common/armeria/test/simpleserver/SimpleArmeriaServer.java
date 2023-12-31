package rgo.tt.common.armeria.test.simpleserver;

import com.linecorp.armeria.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimpleArmeriaServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleArmeriaServer.class);

    private final int port;
    private final Object service;

    private Server server;

    public SimpleArmeriaServer(int port, Object service) {
        this.port = port;
        this.service = service;
    }

    public void start() {
        if (server != null) {
            LOGGER.warn("Simple armeria server already started on port {}.", port);
            return;
        }

        server = Server.builder()
                .http(port)
                .annotatedService(service)
                .build();

        server.start().join();
        LOGGER.info("Simple armeria server started on port {}.", port);
    }

    public void stop() {
        if (this.server == null) {
            LOGGER.warn("Simple armeria server already stopped.");
            return;
        }

        server.stop().join();
        server = null;
        LOGGER.info("Simple armeria server stopped.");
    }
}
