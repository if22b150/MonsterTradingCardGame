package at.technikum.server.routes;

import at.technikum.server.HttpMethod;
import at.technikum.server.Router;
import at.technikum.server.handler.AuthHandler;
import at.technikum.server.handler.PackageHandler;
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

        router.addRoute(
                "/sessions",
                new AuthHandler(),
                new ArrayList<HttpMethod>(){{
                    add(HttpMethod.POST);
                }}
        );

        router.addRoute(
                "/packages",
                new PackageHandler(),
                new ArrayList<HttpMethod>(){{
                    add(HttpMethod.POST);
                }}
        );

        return router;

    }
}
