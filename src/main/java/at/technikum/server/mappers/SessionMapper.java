package at.technikum.server.mappers;

import at.technikum.models.Session;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SessionMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static String sessionToJson(Session session) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return objectMapper.writeValueAsString(session);
        } catch (IOException e) {
            // Handle the exception appropriately
            return e.getMessage(); // or return an error message
        }
    }
}
