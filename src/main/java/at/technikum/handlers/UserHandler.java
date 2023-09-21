package at.technikum.handlers;

import at.technikum.controllers.UserController;
import at.technikum.utils.Helper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UserHandler extends AHandler implements HttpHandler {
    public UserHandler() {
        super("/users/");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.initHandler(exchange);

        if(this.isIndexRoute)
            UserController.index(this.exchange);
        if(this.isResourceRoute)
            UserController.show(exchange, Integer.parseInt(this.pathSegments[0]));

        this.errorResponse(404);
    }
}
