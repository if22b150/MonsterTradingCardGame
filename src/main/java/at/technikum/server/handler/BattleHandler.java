package at.technikum.server.handler;

import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.*;
import at.technikum.server.controller.BattleController;

public class BattleHandler extends AHandler{
    BattleController battleController = new BattleController();

    @Override
    public Response handleRequest(Request request) {
        // BattleHandler handles only POST route
        if(request.getPathPart(1) != null)
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);

        // Check if the client can enter the battle queue
        boolean enteredQueue = BattleManager.enterBattleLobby(requestUser);

        if (enteredQueue) {
            // Client entered the battle queue successfully
            return new Response(HttpStatus.OK, EContentType.JSON, "[\"Entered the battle queue.\"]");
        } else {
            // Check if the client has timed out
            if (!BattleManager.isClientInQueue(requestUser)) {
                return new Response(HttpStatus.REQUEST_TIMEOUT, EContentType.JSON, "[\"Timeout: No battle partner found.\"]");
            }

            // Client is already in the queue
            return new Response(HttpStatus.FORBIDDEN, EContentType.JSON, "[\"Cannot enter the battle queue.\"]");
        }
    }
}
