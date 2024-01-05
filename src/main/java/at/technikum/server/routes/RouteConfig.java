package at.technikum.server.routes;

import at.technikum.server.HttpMethod;
import at.technikum.server.HttpRoute;
import at.technikum.server.Router;
import at.technikum.server.handler.*;
import at.technikum.server.middlewares.AuthMiddleware;

import java.util.ArrayList;

public class RouteConfig {
    public static Router configureRoutes() {
        Router router = new Router();

        // /users route
        router.addRoute(
            "/users",
            new UserHandler(),
            new ArrayList<HttpRoute>(){{
                add(new HttpRoute(HttpMethod.GET, null));
                add(new HttpRoute(HttpMethod.POST, null));
                add(new HttpRoute(HttpMethod.PUT, null));
            }}
        );

        // /sessions route
        router.addRoute(
                "/sessions",
                new SessionHandler(),
                new ArrayList<HttpRoute>(){{
                    add(new HttpRoute(HttpMethod.POST, null));
                }}
        );

        router.addRoute(
                "/packages",
                new PackageHandler(),
                new ArrayList<HttpRoute>(){{
                    add(
                        new HttpRoute(
                            HttpMethod.POST,
                            new ArrayList<>(){{
                                add(
                                    new AuthMiddleware(
                                        new ArrayList<>(){{
                                            add("admin");
                                        }}
                                    )
                                );
                            }}
                        )
                    );
                }}
        );

        router.addRoute(
                "/transactions",
                new TransactionHandler(),
                new ArrayList<HttpRoute>(){{
                    add(new HttpRoute(HttpMethod.POST, null));
                }}
        );

        router.addRoute(
                "/cards",
                new CardHandler(),
                new ArrayList<HttpRoute>(){{
                    add(
                        new HttpRoute(
                            HttpMethod.GET,
                            new ArrayList<>(){{
                                add(new AuthMiddleware());
                            }}
                        )
                    );
                }}
        );

        router.addRoute(
                "/deck",
                new DeckHandler(),
                new ArrayList<HttpRoute>(){{
                    add(
                        new HttpRoute(
                            HttpMethod.GET,
                            new ArrayList<>(){{
                                add(new AuthMiddleware());
                            }}
                        )
                    );
                    add(
                        new HttpRoute(
                            HttpMethod.PUT,
                            new ArrayList<>(){{
                                add(new AuthMiddleware());
                            }}
                        )
                    );
                }}
        );

        router.addRoute(
                "/stats",
                new StatHandler(),
                new ArrayList<HttpRoute>(){{
                    add(new HttpRoute(HttpMethod.GET, null));
                }}
        );

        router.addRoute(
                "/scoreboard",
                new ScoreboardHandler(),
                new ArrayList<HttpRoute>(){{
                    add(new HttpRoute(HttpMethod.GET, null));
                }}
        );

        router.addRoute(
                "/battles",
                new BattleHandler(),
                new ArrayList<HttpRoute>(){{
                    add(
                        new HttpRoute(
                            HttpMethod.POST,
                            new ArrayList<>(){{
                                add(new AuthMiddleware());
                            }}
                        )
                    );
                }}
        );

        return router;

    }
}
