package at.technikum.server;

import at.technikum.server.handler.IHandler;
import at.technikum.server.routes.HttpRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, IHandler> routeRegistry = new HashMap<>();

    /**
     * Adds a route and its associated handler to the router.
     *
     * @param route   The route to be added.
     * @param handler The handler associated with the route.
     */
    public void addRoute(String route, IHandler handler, ArrayList<HttpRoute> routes) {
        if (route != null && handler != null) {
            handler.setRoutes(routes);
            routeRegistry.put(route, handler);
        } else {
            throw new IllegalArgumentException("Route and handler must not be null.");
        }
    }

    /**
     * Resolves a route to its associated handler.
     *
     * @param route The route to be resolved.
     * @return The handler associated with the route, or null if not found.
     */
    public IHandler resolve(String route) {
        return routeRegistry.get(route);
    }
}
