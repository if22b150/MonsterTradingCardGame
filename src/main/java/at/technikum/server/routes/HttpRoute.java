package at.technikum.server.routes;

import at.technikum.enums.HttpMethod;
import at.technikum.server.middlewares.IMiddleware;

import java.util.ArrayList;

public class HttpRoute {
    public HttpMethod getMethod() {
        return method;
    }

    public ArrayList<IMiddleware> getMiddlewares() {
        return middlewares;
    }

    HttpMethod method;
    ArrayList<IMiddleware> middlewares;

    public HttpRoute(HttpMethod method, ArrayList<IMiddleware> middlewares) {
        this.method = method;
        this.middlewares = middlewares;
    }
}
