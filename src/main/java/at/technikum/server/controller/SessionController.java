package at.technikum.server.controller;

import at.technikum.repositories.session.SessionRepository;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.mappers.SessionMapper;
import at.technikum.server.requests.StoreSessionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SessionController {
    private static final SessionRepository sessionRepository = new SessionRepository();

    public Response store(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        StoreSessionRequest sessionRequest;
        // Parse the JSON into a Java object
        try {
            sessionRequest = objectMapper.readValue(body, StoreSessionRequest.class);
            if(!sessionRequest.isValid()) {
                return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.HTML, sessionRequest.getErrorMessages());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String token = sessionRequest.getUsername() + "-mtcgToken";

        return new Response(HttpStatus.CREATED, EContentType.JSON, SessionMapper.sessionToJson(sessionRepository.create(sessionRequest.getUser().getId(), token)));
    }
}
