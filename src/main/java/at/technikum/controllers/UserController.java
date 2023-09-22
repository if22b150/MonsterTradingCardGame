package at.technikum.controllers;

import at.technikum.mappers.UserMapper;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.utils.Response;

public class UserController extends AController {
    private static final IUserRepository userRepository = new UserRepository();

    @Override
    public Response index() {
        return new Response(200, UserMapper.usersToJson(userRepository.all()));
    }

    @Override
    public Response show(int userId) {
        System.out.println("Show user " + userId);
        return new Response(200, UserMapper.userToJson(userRepository.get(userId)));
    }

    @Override
    public Response store() {
        System.out.println("Create User");
        return new Response(201, "");
    }

    @Override
    public Response update(int userId) {
        System.out.println("Update User " + userId);
        return new Response(200, "");
    }

    @Override
    public Response destroy(int id) {
        System.out.println("Delete User " + id);
        return new Response(204, "");
    }
}
