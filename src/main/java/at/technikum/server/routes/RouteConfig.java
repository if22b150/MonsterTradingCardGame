package at.technikum.server.routes;

import at.technikum.server.HttpMethod;
import at.technikum.server.Router;
import at.technikum.server.handler.UserHandler;

import java.util.ArrayList;

public class RouteConfig {
    public static Router configureRoutes() {
        Router router = new Router();

        // /users route
        router.addRoute(
            "/users",
            new UserHandler(),
            new ArrayList<HttpMethod>(){{
                add(HttpMethod.GET);
                add(HttpMethod.POST);
            }}
        );
        // Define more routes

        return router;

    }
}
