package at.technikum.server.handler;

import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.models.Session;
import at.technikum.models.User;
import at.technikum.repositories.session.SessionRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.controller.UserController;
import at.technikum.server.*;

public class UserHandler extends AHandler{
    UserController userController = new UserController();
    private static final UserRepository userRepository  = new UserRepository();;
    private static final SessionRepository sessionRepository = new SessionRepository();

    @Override
    public Response handleRequest(Request request) {
        if(request.getPathPart(1) != null) {
            // check if user exists
            User user = userRepository.getByUsername(request.getPathPart(1, false));
            if(user == null)
                return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);

            // check if request is authorized
            String authorization[] = request.getHeaderMap().getHeader("Authorization").split("Bearer ");
            String token = null;
            if(authorization.length < 2 || (token = authorization[1]).isEmpty())
                return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, HttpStatus.UNAUTHORIZED.message);

            if(!authorizeUser(user, token))
                return new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, HttpStatus.UNAUTHORIZED.message);

            switch (request.getMethod()) {
                case GET -> {
                    // normally we pass the id here, but curl script demands username...
                    return this.userController.show(user);
                }
                case PUT -> {
                    // normally we pass the id here, but curl script demands username...
                    return this.userController.edit(request.getPathPart(1, false), request.getBody() != null ? request.getBody() : "{}");
                }
            }
        }

        switch (request.getMethod()) {
            case GET -> {
                return this.userController.index();
            }
            case POST -> {
                // readValue of ObjectMapper can't handle null body, so I convert them to empty JSON
                return this.userController.store(request.getBody() != null ? request.getBody() : "{}");
            }
        }

        return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, HttpStatus.NOT_FOUND.message);
    }

    public boolean authorizeUser(User user, String token) {
        Session session = sessionRepository.getByUser(user.getId());
        return token != null && session != null && token.equals(session.getToken());
    }
}
