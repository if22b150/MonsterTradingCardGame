package at.technikum.server.handler;

import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Request;
import at.technikum.server.Response;
import at.technikum.server.controller.CardController;

public class CardHandler extends AHandler{
    CardController cardController = new CardController();

    @Override
    public Response handleRequest(Request request) {
        if(!this.allowedMethods.contains(request.getMethod()))
            return new Response(HttpStatus.METHOD_NOT_ALLOWED, EContentType.JSON, "[]");

        // CardHandler handles only GET route
        if(request.getPathPart(1) != null) {
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, "[]");
        }

        if(!isAuthorized(request))
            return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, "[]");

        return this.cardController.index(requestUser);
    }
}