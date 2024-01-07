package at.technikum.server.handler;

import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Request;
import at.technikum.server.Response;
import at.technikum.server.controller.CardController;

public class CardHandler extends AHandler{
    CardController cardController = new CardController();

    @Override
    public Response handleRequest(Request request) {
        // CardHandler handles only GET route
        if(request.getPathPart(1) != null) {
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);
        }

        return this.cardController.index(requestUser);
    }
}
