package at.technikum.server.handler;

import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Request;
import at.technikum.server.Response;
import at.technikum.server.controller.SessionController;

public class SessionHandler extends AHandler{
    SessionController sessionController = new SessionController();

    @Override
    public Response handleRequest(Request request) {
        if(!this.allowedMethods.contains(request.getMethod()))
            return new Response(HttpStatus.METHOD_NOT_ALLOWED, EContentType.JSON, HttpStatus.METHOD_NOT_ALLOWED.message);

        if(request.getPathPart(1) != null) {
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);
        }

        switch (request.getMethod()) {
            case POST -> {
                // readValue of ObjectMapper can't handle null body, so I convert them to empty JSON
                return this.sessionController.store(request.getBody() != null ? request.getBody() : "{}");
            }
        }

        return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);
    }
}
