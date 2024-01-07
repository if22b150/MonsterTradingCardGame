package at.technikum.server.controller;

import at.technikum.server.mappers.UserMapper;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.requests.auth.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthController {
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

