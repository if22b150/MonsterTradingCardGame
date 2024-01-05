package at.technikum.server.routes;

import at.technikum.server.HttpMethod;
import at.technikum.server.Router;
import at.technikum.server.handler.*;

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
                add(HttpMethod.PUT);
            }}
        );

        // /sessions route
        router.addRoute(
                "/sessions",
                new SessionHandler(),
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

        router.addRoute(
                "/transactions",
                new TransactionHandler(),
                new ArrayList<HttpMethod>(){{
                    add(HttpMethod.POST);
                }}
        );

        router.addRoute(
                "/cards",
                new CardHandler(),
                new ArrayList<HttpMethod>(){{
                    add(HttpMethod.GET);
                }}
        );

        router.addRoute(
                "/deck",
                new DeckHandler(),
                new ArrayList<HttpMethod>(){{
                    add(HttpMethod.GET);
                    add(HttpMethod.PUT);
                }}
        );

        router.addRoute(
                "/stats",
                new StatHandler(),
                new ArrayList<HttpMethod>(){{
                    add(HttpMethod.GET);
                }}
        );

        router.addRoute(
                "/scoreboard",
                new StatHandler(),
                new ArrayList<HttpMethod>(){{
                    add(HttpMethod.GET);
                }}
        );

        return router;

    }
}
