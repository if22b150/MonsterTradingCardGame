package at.technikum.server.handler;

import at.technikum.models.Session;
import at.technikum.models.User;
import at.technikum.repositories.session.SessionRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.controller.PackageController;
import at.technikum.server.*;

import java.util.Objects;

public class PackageHandler extends AHandler{
    PackageController packageController = new PackageController();
    private static final UserRepository userRepository  = new UserRepository();;
    private static final SessionRepository sessionRepository = new SessionRepository();

    @Override
    public Response handleRequest(Request request) {
        if(!this.allowedMethods.contains(request.getMethod()))
            return new Response(HttpStatus.METHOD_NOT_ALLOWED, EContentType.JSON, "[]");

        // PackageHandler handles only POST package route
        if(request.getPathPart(1) != null) {
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, "[]");
        }

        if(request.getHeaderMap().getHeader("Authorization") == null)
            return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, "[]");

        // check if request is authorized
        String authorization[] = request.getHeaderMap().getHeader("Authorization").split("Bearer ");
        String token = null;
        Session session = null;
        User user = null;
        if(     authorization.length < 2
                || (token = authorization[1]).isEmpty()
                || (session = sessionRepository.getByToken(token)) == null
                || (user = userRepository.get(session.getUserId())) == null
                || !Objects.equals(user.getUsername(), "admin"))
            return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, "[]");

        // readValue of ObjectMapper can't handle null body, so I convert them to empty JSON
        return this.packageController.store(request.getBody() != null ? request.getBody() : "{}");
    }
}
