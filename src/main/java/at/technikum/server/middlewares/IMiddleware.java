package at.technikum.server.middlewares;

import at.technikum.server.Request;
import at.technikum.server.Response;
import at.technikum.server.handler.IHandler;

public interface IMiddleware {
    Response handle(Request request, IHandler handler);
}
