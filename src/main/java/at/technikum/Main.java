package at.technikum;

import at.technikum.config.RouteConfig;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Configure routes
        RouteConfig.configureRoutes(server);

        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on http://localhost:" + port);
    }
}