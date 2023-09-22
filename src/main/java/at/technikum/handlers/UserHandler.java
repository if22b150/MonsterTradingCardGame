package at.technikum.handlers;

import at.technikum.controllers.UserController;
import at.technikum.utils.HttpMethod;
import at.technikum.utils.Request;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class UserHandler extends AHandler {
    public UserHandler() {
        super(new UserController(),"/users/");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.request = new Request(this.route, exchange);

        if(this.request.errorCode != 0)
            this.errorResponse(this.request.errorCode);

        if(this.request.isIndexRequest)
            this.sendResponse(this.controller.index());

        if(this.request.httpMethod.equals(HttpMethod.POST))
            this.sendResponse(this.controller.store());

        if(this.request.isShowRequest)
            this.sendResponse(this.controller.show(this.request.resourceId));

        if(this.request.isUpdateRequest)
            this.sendResponse(this.controller.update(this.request.resourceId));

        // Handle more requests

        this.errorResponse(404);
    }
}
