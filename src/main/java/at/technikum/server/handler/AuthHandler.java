package at.technikum.server.handler;

import at.technikum.server.controller.AuthController;
import at.technikum.server.controller.UserController;
import at.technikum.server.*;

public class AuthHandler extends AHandler{
    AuthController authController = new AuthController();

    @Override
    public Response handleRequest(Request request) {
        if(!this.allowedMethods.contains(request.getMethod()))
            return new Response(HttpStatus.METHOD_NOT_ALLOWED, EContentType.JSON, "[]");

        // AuthHandler handles only POST sessions route

        if(request.getPathPart(1) != null) {
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, "[]");
        }

        // readValue of ObjectMapper can't handle null body, so I convert them to empty JSON
        return this.authController.login(request.getBody() != null ? request.getBody() : "{}");
    }
}
