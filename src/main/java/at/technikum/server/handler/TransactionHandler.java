package at.technikum.server.handler;

import at.technikum.server.*;
import at.technikum.server.controller.TransactionController;

public class TransactionHandler extends AHandler{
    TransactionController transactionController = new TransactionController();

    @Override
    public Response handleRequest(Request request) {
        if(!this.allowedMethods.contains(request.getMethod()))
            return new Response(HttpStatus.METHOD_NOT_ALLOWED, EContentType.JSON, "[]");

        // TransactionHandler handles only POST transactions/packages route
        if(request.getPathPart(1) == null || (!request.getPathPart(1).equals("/packages") || request.getPathPart(2) != null)) {
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, "[]");
        }

        if(!isAuthorized(request))
            return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, "[]");

        return this.transactionController.store(requestUser);

        // readValue of ObjectMapper can't handle null body, so I convert them to empty JSON
//        return this.transactionController.store(request.getBody() != null ? request.getBody() : "{}");
    }
}
