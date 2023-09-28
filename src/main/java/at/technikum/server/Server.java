package at.technikum.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public final class Server implements AutoCloseable {
    private static final int THREAD_POOL_SIZE = 10;
    private static final String SERVER_URL_FORMAT = "http://localhost:%d";

    private final int port;
    private final Router router;
    private final ExecutorService executorService;
    private final Logger logger = Logger.getLogger(Server.class.getName());

    public Server(int port, Router router) {
        this.port = port;
        this.router = router;
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    /**
     * Starts the server and listens for incoming connections.
     *
     * @throws IOException if an I/O error occurs while starting the server.
     */
    public void start() throws IOException {
        logger.info("Starting http-server...");
        logger.info(String.format("http-server running at: " + SERVER_URL_FORMAT, this.port));

        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                final Socket clientConnection = serverSocket.accept();
                final RequestHandler socketHandler = new RequestHandler(clientConnection, this.router);
                executorService.submit(socketHandler);
            }
        }
    }

    /**
     * Closes the server and releases associated resources.
     */
    @Override
    public void close() {
        executorService.shutdown();
        logger.info("http-server stopped.");
    }
}
