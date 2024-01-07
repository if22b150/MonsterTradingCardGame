package at.technikum.server.handler;

import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.controller.AuthController;
import at.technikum.server.*;

public class AuthHandler extends AHandler{
    AuthController authController = new AuthController();

    @Override
    public Response handleRequest(Request request) {
        // AuthHandler handles only POST sessions route
        if(request.getPathPart(1) != null) {
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);
        }

        // readValue of ObjectMapper can't handle null body, so I convert them to empty JSON
        return this.authController.login(request.getBody() != null ? request.getBody() : "{}");
    }
}
