package at.technikum.server.handler;

import at.technikum.server.*;
import at.technikum.server.controller.DeckController;

import java.util.Objects;

public class DeckHandler extends AHandler{
    DeckController deckController = new DeckController();

    @Override
    public Response handleRequest(Request request) {
        // DeckHandler handles only GET and PUT (via auth token) route
        if(request.getPathPart(1) != null)
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);

        if (request.getMethod().equals(HttpMethod.PUT))
            return this.deckController.setDeck(request.getBody() != null ? request.getBody() : "{}", requestUser);
        else
            return this.deckController.index(requestUser, Objects.equals(request.getParam("format"), "plain"));
    }
}
