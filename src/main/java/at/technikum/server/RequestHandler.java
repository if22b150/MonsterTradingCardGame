package at.technikum.server;

import at.technikum.server.handler.IHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class RequestHandler implements Runnable, AutoCloseable {
    private static final Logger logger = Logger.getLogger(RequestHandler.class.getName());

    private final Socket clientSocket;
    private final Router router;
    private final PrintWriter printWriter;
    private final BufferedReader bufferedReader;

    public RequestHandler(Socket clientSocket, Router router) throws IOException {
        this.clientSocket = clientSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.router = router;
    }

    @Override
    public void run() {
        try {
            handleRequest();
        } catch (IOException e) {
            logger.severe(Thread.currentThread().getName() + " Error: " + e.getMessage());
        } catch (Exception e) {
            logger.severe(Thread.currentThread().getName() + " Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void handleRequest() throws IOException {
        Response response;
        Request request = new RequestBuilder().buildRequest(this.bufferedReader);

        // Log the resolved route
        String resolvedRoute = request.getServiceRoute();
        logger.info("Resolved Route: " + resolvedRoute);

        if (request.getPathname() == null) {
            response = new Response(
                    HttpStatus.BAD_REQUEST,
                    EContentType.JSON,
                    "[]"
            );
        } else {
//            response = this.router.resolve(request.getServiceRoute()).handleRequest(request);
            IHandler handler = this.router.resolve(resolvedRoute);
            if (handler != null) {
                response = handler.handleRequest(request);
            } else {
                // Log that the handler is null
                logger.warning("Handler not found for route: " + resolvedRoute);
                response = new Response(
                        HttpStatus.NOT_FOUND,
                        EContentType.JSON,
                        "[\"Route not found\"]"
                );
            }
        }
        printWriter.write(response.get());
    }

    @Override
    public void close() {
        try {
            if (printWriter != null) {
                printWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            logger.severe("Error closing resources: " + e.getMessage());
        }
    }
}