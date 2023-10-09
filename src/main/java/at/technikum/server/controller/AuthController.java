package at.technikum.server.controller;

import at.technikum.mappers.UserMapper;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.requests.auth.LoginRequest;
import at.technikum.server.requests.user.StoreUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthController {
//    private static final UserRepository userRepository = new UserRepository();

    public Response login(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginUser;
        // Parse the JSON into a Java object
        try {
            loginUser = objectMapper.readValue(body, LoginRequest.class);
            if(!loginUser.isValid()) {
                return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.HTML, loginUser.getErrorMessages());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.CREATED, EContentType.JSON, UserMapper.userToJson(loginUser.getUser()));
    }
}

