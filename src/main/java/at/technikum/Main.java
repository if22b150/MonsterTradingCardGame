package at.technikum;

import at.technikum.server.Server;
import at.technikum.server.routes.RouteConfig;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8080, RouteConfig.configureRoutes());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}