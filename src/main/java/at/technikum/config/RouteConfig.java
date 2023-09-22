package at.technikum.config;

import at.technikum.handlers.UserHandler;
import com.sun.net.httpserver.HttpServer;

public class RouteConfig {
    public static void configureRoutes(HttpServer server) {
        server.createContext("/api/users/", new UserHandler());
        // Define more routes
    }
}
