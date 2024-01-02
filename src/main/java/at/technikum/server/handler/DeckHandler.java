package at.technikum.server.handler;

import at.technikum.server.*;
import at.technikum.server.controller.DeckController;

public class DeckHandler extends AHandler{
    DeckController deckController = new DeckController();

    @Override
    public Response handleRequest(Request request) {
        if(!this.allowedMethods.contains(request.getMethod()))
            return new Response(HttpStatus.METHOD_NOT_ALLOWED, EContentType.JSON, HttpStatus.METHOD_NOT_ALLOWED.message);

        // DeckHandler handles only GET and PUT (via auth token) route
        if(request.getPathPart(1) != null)
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);

        if(!isAuthorized(request))
            return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, HttpStatus.UNAUTHORIZED.message);

        if (request.getMethod().equals(HttpMethod.PUT))
            return this.deckController.setDeck(request.getBody() != null ? request.getBody() : "{}", requestUser);
        else
            return this.deckController.index(requestUser, request.getParam("format") == "plain");
    }
}