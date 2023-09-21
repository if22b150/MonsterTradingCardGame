package at.technikum.handlers;

import at.technikum.controllers.UserController;
import at.technikum.utils.Helper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public abstract class AHandler {
    protected String route;
    protected HttpExchange exchange;
    protected String path;
    // Extract the part of the path after "/api/users/"
    protected String subPath;
    // Split the subPath into segments
    protected String[] pathSegments;
    protected boolean isIndexRoute = false;
    protected boolean isResourceRoute = false;


    public AHandler(String route) {
        this.route = "/api" + route;
    }

    protected void initHandler(HttpExchange exchange) throws IOException {
        this.isIndexRoute = false;
        this.isResourceRoute = false;

        this.exchange = exchange;

        this.path = exchange.getRequestURI().getPath();
        this.subPath = path.substring(route.length());
        this.pathSegments = subPath.split("/");

        // CHECK THE ROUTE
        if(this.subPath.isEmpty()) {
            if (this.isGetRequest()) {
                this.isIndexRoute = true;
                return;
            } else {
                // Handle unsupported HTTP methods
                this.errorResponse(405);
            }
        }

        if (this.pathSegments.length == 1) {
            if(!Helper.stringIsInteger(this.pathSegments[0])) {
                this.errorResponse(422);
            }

            if (this.isGetRequest()) {
                this.isResourceRoute = true;
                return;
            } else {
                // Handle unsupported HTTP methods
                this.errorResponse(405);
            }
        }
    }

    protected boolean isGetRequest() {
        return "GET".equals(exchange.getRequestMethod());
    }

    protected boolean isPutRequest() {
        return "PUT".equals(exchange.getRequestMethod());
    }

    protected boolean isPostRequest() {
        return "POST".equals(exchange.getRequestMethod());
    }

    protected boolean isPatchRequest() {
        return "PATCH".equals(exchange.getRequestMethod());
    }

    protected boolean isDeleteRequest() {
        return "DELETE".equals(exchange.getRequestMethod());
    }

    protected void errorResponse(int errorCode) throws IOException {
        this.exchange.sendResponseHeaders(errorCode, 0);
        this.exchange.close();
    }
}
