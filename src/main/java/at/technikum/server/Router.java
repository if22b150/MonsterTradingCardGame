package at.technikum.server;

import at.technikum.server.handler.IHandler;

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
    public void addRoute(String route, IHandler handler, ArrayList<HttpMethod> allowedMethods) {
        if (route != null && handler != null) {
            handler.setAllowedMethods(allowedMethods);
            routeRegistry.put(route, handler);
        } else {
            throw new IllegalArgumentException("Route and handler must not be null.");
        }
    }

    /**
     * Removes a route and its associated handler from the router.
     *
     * @param route The route to be removed.
     */
    public void removeRoute(String route) {
        if (route != null) {
            routeRegistry.remove(route);
        } else {
            throw new IllegalArgumentException("Route must not be null.");
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

    // You can add additional methods as needed, such as listing all registered routes.
}
