package at.technikum.server.handler;

import at.technikum.server.controller.PackageController;
import at.technikum.server.*;

public class PackageHandler extends AHandler{
    PackageController packageController = new PackageController();

    @Override
    public Response handleRequest(Request request) {
        if(!this.allowedMethods.contains(request.getMethod()))
            return new Response(HttpStatus.METHOD_NOT_ALLOWED, EContentType.JSON, HttpStatus.METHOD_NOT_ALLOWED.message);

        // PackageHandler handles only POST package route
        if(request.getPathPart(1) != null) {
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);
        }

        if(!isAuthorized(request, "admin"))
            return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, HttpStatus.UNAUTHORIZED.message);

        // readValue of ObjectMapper can't handle null body, so I convert them to empty JSON
        return this.packageController.store(request.getBody() != null ? request.getBody() : "{}");
    }
}
