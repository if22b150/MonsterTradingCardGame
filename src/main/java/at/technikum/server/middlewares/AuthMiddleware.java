package at.technikum.server.middlewares;

import at.technikum.models.Session;
import at.technikum.models.User;
import at.technikum.repositories.session.ISessionRepository;
import at.technikum.repositories.session.SessionRepository;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Request;
import at.technikum.server.Response;
import at.technikum.server.handler.IHandler;

import java.util.ArrayList;

public class AuthMiddleware implements IMiddleware {
    private static final ISessionRepository sessionRepository = new SessionRepository();
    private static final IUserRepository userRepository = new UserRepository();

    ArrayList<String> allowedUsers;

    public AuthMiddleware() {
        this.allowedUsers = null;
    }
    public AuthMiddleware(ArrayList<String> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }

    @Override
    public Response handle(Request request, IHandler handler) {
        Response unauthorizedResponse = new Response(HttpStatus.UNAUTHORIZED, EContentType.JSON, HttpStatus.UNAUTHORIZED.message);

        if(request.getHeaderMap().getHeader("Authorization") == null)
            return unauthorizedResponse;

        // check if request is authorized
        String authorization[] = request.getHeaderMap().getHeader("Authorization").split("Bearer ");
        String token = null;
        Session session = null;
        User user = null;
        if(     authorization.length < 2
                || (token = authorization[1]).isEmpty()
                || (session = sessionRepository.getByToken(token)) == null
                || (user = userRepository.get(session.getUserId())) == null)
            return unauthorizedResponse;

        handler.setRequestUser(user);
        return this.allowedUsers == null || this.allowedUsers.contains(user.getUsername()) ? null : unauthorizedResponse;
    }
}
