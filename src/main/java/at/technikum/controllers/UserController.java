package at.technikum.controllers;

import at.technikum.mappers.UserMapper;
import at.technikum.model.User;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class UserController {
    private static final IUserRepository userRepository = new UserRepository();

    public static void index(HttpExchange exchange) throws IOException {
        sendResponse(exchange, UserMapper.usersToJson(userRepository.all()));
    }

    public static void show(HttpExchange exchange, int userId) throws IOException {
        sendResponse(exchange, UserMapper.userToJson(userRepository.get(userId)));
    }

    public static void store(HttpExchange exchange) throws IOException {
        System.out.println("Create User");
    }

    public static void destroy(HttpExchange exchange) throws IOException {
        System.out.println("Delete User");
    }

    private static void sendResponse(HttpExchange exchange, String response)  throws IOException {
        try {
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            exchange.sendResponseHeaders(405, 0);
            exchange.close();
        }
    }
}
