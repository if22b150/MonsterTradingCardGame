package at.technikum.server.handler;

import at.technikum.models.Session;
import at.technikum.models.User;
import at.technikum.repositories.session.SessionRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.*;
import at.technikum.server.middlewares.IMiddleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class AHandler implements IHandler {
    private static final UserRepository userRepository  = new UserRepository();;
    private static final SessionRepository sessionRepository = new SessionRepository();

    public ArrayList<HttpRoute> getRoutes() {
        return routes;
    }
    public ArrayList<HttpMethod> getAllowedMethods() {
        ArrayList<HttpMethod> allowedMethods = new ArrayList<>();
        for(HttpRoute route: this.routes) {
            allowedMethods.add(route.getMethod());
        }
        return allowedMethods;
    }

    public HashMap<HttpMethod, ArrayList<IMiddleware>> getMiddlewares() {
        HashMap<HttpMethod, ArrayList<IMiddleware>> methodWithMiddlewares = new HashMap<>();
        for(HttpRoute route: this.routes) {
            if(route.getMiddlewares() != null && !route.getMiddlewares().isEmpty())
                methodWithMiddlewares.put(route.getMethod(), route.getMiddlewares());
        }
        return methodWithMiddlewares;
    }

    protected ArrayList<HttpRoute> routes;
    protected User requestUser = null;

    public void setRoutes(ArrayList<HttpRoute> routes) {
        this.routes = routes;
    }

    public boolean isAuthorized(Request request)  {
        // check if request has authorization header
        if(request.getHeaderMap().getHeader("Authorization") == null)
            return false;

        // check if request is authorized
        String authorization[] = request.getHeaderMap().getHeader("Authorization").split("Bearer ");
        String token = null;
        Session session = null;
//        User user = null;
        if(     authorization.length < 2
                || (token = authorization[1]).isEmpty()
                || (session = sessionRepository.getByToken(token)) == null
                || (requestUser = userRepository.get(session.getUserId())) == null)
            return false;

        return true;
    }
    public boolean isAuthorized(Request request, String requiredUsername)  {
        return isAuthorized(request) && Objects.equals(requestUser.getUsername(), requiredUsername);
    }
}
