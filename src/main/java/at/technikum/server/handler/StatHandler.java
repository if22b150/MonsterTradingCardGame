package at.technikum.server.handler;

import at.technikum.server.*;
import at.technikum.server.controller.DeckController;
import at.technikum.server.controller.StatController;

import java.util.Objects;

public class StatHandler extends AHandler{
    StatController statController = new StatController();

    @Override
    public Response handleRequest(Request request) {
        // StatHandler handles only GET route
        if(request.getPathPart(1) != null)
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);

        if(!isAuthorized(request))
            return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, HttpStatus.UNAUTHORIZED.message);

        return this.statController.show(requestUser);
    }
}

