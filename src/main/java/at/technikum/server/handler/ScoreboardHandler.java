package at.technikum.server.handler;

import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Request;
import at.technikum.server.Response;
import at.technikum.server.controller.ScoreboardController;

public class ScoreboardHandler extends AHandler{
    ScoreboardController scoreboardController = new ScoreboardController();

    @Override
    public Response handleRequest(Request request) {
        // StatHandler handles only GET route
        if(request.getPathPart(1) != null)
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);

        if(!isAuthorized(request))
            return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, HttpStatus.UNAUTHORIZED.message);

        return this.scoreboardController.show();
    }
}

