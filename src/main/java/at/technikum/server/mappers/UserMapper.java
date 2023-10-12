package at.technikum.server.mappers;

import at.technikum.models.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class UserMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static String userToJson(User user) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return objectMapper.writeValueAsString(user);
        } catch (IOException e) {
            // Handle the exception appropriately
            return e.getMessage(); // or return an error message
        }
    }

    public static String usersToJson(ArrayList<User> users) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return objectMapper.writeValueAsString(users);
        } catch (IOException e) {
            // Handle the exception appropriately
            return e.getMessage(); // or return an error message
        }
    }
}
