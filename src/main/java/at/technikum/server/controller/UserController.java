package at.technikum.server.controller;

import at.technikum.server.mappers.UserMapper;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.requests.StoreUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserController {
    private static final UserRepository userRepository = new UserRepository();

    // normally we pass the id here, but curl script demands username...
    public Response show(String username) {
        return new Response(HttpStatus.OK, EContentType.JSON, UserMapper.userToJson(userRepository.getByUsername(username)));
    }

    public Response index() {
        return new Response(HttpStatus.OK, EContentType.JSON, UserMapper.usersToJson(userRepository.all()));
    }

    public Response store(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        StoreUserRequest user;
        // Parse the JSON into a Java object
        try {
            user = objectMapper.readValue(body, StoreUserRequest.class);
            if(!user.isValid()) {
                return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.HTML, user.getErrorMessages());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.CREATED, EContentType.JSON, UserMapper.userToJson(userRepository.create(user.getUsername(), user.getPassword())));
    }
}
