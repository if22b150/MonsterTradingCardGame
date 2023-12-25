package at.technikum.server.controller;

import at.technikum.models.User;
import at.technikum.server.mappers.UserMapper;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.requests.StoreUserRequest;
import at.technikum.server.requests.UpdateUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserController {
    private static final UserRepository userRepository = new UserRepository();

    // normally we pass the id here, but curl script demands username...
    public Response show(User user) {
        return new Response(HttpStatus.OK, EContentType.JSON, UserMapper.userToJson(user));
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

    public Response edit(String username, String body) {
        User user = userRepository.getByUsername(username);
        if(user == null)
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, null);

        ObjectMapper objectMapper = new ObjectMapper();
        UpdateUserRequest userRequest;
        // Parse the JSON into a Java object
        try {
            userRequest = objectMapper.readValue(body, UpdateUserRequest.class);
            if(!userRequest.isValid()) {
                return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.HTML, userRequest.getErrorMessages());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.CREATED, EContentType.JSON, UserMapper.userToJson(userRepository.edit(username, userRequest.getName(), userRequest.getBio(), userRequest.getImage())));
    }
}
