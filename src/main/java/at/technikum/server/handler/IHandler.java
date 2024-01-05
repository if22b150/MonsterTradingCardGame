package at.technikum.server.handler;

import at.technikum.server.HttpMethod;
import at.technikum.server.HttpRoute;
import at.technikum.server.Request;
import at.technikum.server.Response;
import at.technikum.server.middlewares.IMiddleware;

import java.util.ArrayList;
import java.util.HashMap;

public interface IHandler {
    Response handleRequest(Request request);

    void setRoutes(ArrayList<HttpRoute> routes);

    ArrayList<HttpMethod> getAllowedMethods();
    HashMap<HttpMethod, ArrayList<IMiddleware>> getMiddlewares();
}
