package at.technikum.server.handler;

import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Request;
import at.technikum.server.Response;
import at.technikum.server.controller.BattleController;

public class BattleHandler extends AHandler{
    BattleController battleController = new BattleController();

    @Override
    public Response handleRequest(Request request) {
        // BattleHandler handles only POST route
        if(request.getPathPart(1) != null)
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);

        return this.battleController.store(requestUser);
    }
}
