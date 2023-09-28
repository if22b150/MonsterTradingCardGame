package at.technikum.controller;

import at.technikum.mappers.UserMapper;
import at.technikum.repositories.user.UserRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;

public class UserController {
    private static final UserRepository userRepository = new UserRepository();

    // normally we pass the id here, but curl script demands username...
    public Response show(String username) {
        return new Response(HttpStatus.OK, EContentType.JSON, UserMapper.userToJson(userRepository.getByUsername(username)));
    }

    public Response index() {
        return new Response(HttpStatus.OK, EContentType.JSON, UserMapper.usersToJson(userRepository.all()));
    }
}
