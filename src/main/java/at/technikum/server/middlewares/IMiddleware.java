package at.technikum.server.middlewares;

import at.technikum.server.Request;
import at.technikum.server.Response;

public interface IMiddleware {
    Response handle(Request request);
}
