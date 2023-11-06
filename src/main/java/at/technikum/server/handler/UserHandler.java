package at.technikum.server.handler;

import at.technikum.server.controller.UserController;
import at.technikum.server.*;

public class UserHandler extends AHandler{
    UserController userController = new UserController();

    @Override
    public Response handleRequest(Request request) {
        if(!this.allowedMethods.contains(request.getMethod()))
            return new Response(HttpStatus.METHOD_NOT_ALLOWED, EContentType.JSON, "[]");

        if(request.getPathPart(1) != null) {
            switch (request.getMethod()) {
                case GET -> {
                    // normally we pass the id here, but curl script demands username...
                    return this.userController.show(request.getPathPart(1, false));
                }
                case PUT -> {
                    // normally we pass the id here, but curl script demands username...
                    return this.userController.edit(request.getPathPart(1, false), request.getBody() != null ? request.getBody() : "{}");
                }
            }
        }

        switch (request.getMethod()) {
            case GET -> {
                // normally we pass the id here, but curl script demands username...
                return this.userController.index();
            }
            case POST -> {
                // readValue of ObjectMapper can't handle null body, so I convert them to empty JSON
                return this.userController.store(request.getBody() != null ? request.getBody() : "{}");
            }
        }

        return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, "[]");
    }
}
