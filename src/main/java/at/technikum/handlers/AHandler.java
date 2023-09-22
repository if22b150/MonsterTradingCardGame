package at.technikum.handlers;

import at.technikum.controllers.AController;
import at.technikum.utils.Request;
import at.technikum.utils.Response;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AHandler implements HttpHandler {
    protected Request request;
    protected AController controller;
    protected String route;

    public AHandler(AController controller, String route) {
        this.controller = controller;
        this.route = "/api" + route;
    }

    protected void errorResponse(int errorCode) throws IOException {
        this.request.exchange.sendResponseHeaders(errorCode, 0);
        this.request.exchange.close();
    }

    protected void sendResponse(Response response)  throws IOException {
        try {
            this.request.exchange.sendResponseHeaders(response.getStatus(), response.getContent().length());
            OutputStream os = this.request.exchange.getResponseBody();
            os.write(response.getContent().getBytes());
            os.close();
        } catch (Exception e) {
            this.request.exchange.sendResponseHeaders(405, 0);
            this.request.exchange.close();
        }
    }
}
