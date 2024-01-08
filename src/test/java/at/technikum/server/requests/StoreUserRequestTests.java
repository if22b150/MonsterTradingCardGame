package at.technikum.server.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StoreUserRequestTests {
    String body;
    StoreUserRequest userRequest;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    void setup() {
        body = "{\"Username\":\"testuser\",\"Password\":\"testpassword\"}";
        try {
            userRequest = objectMapper.readValue(body, StoreUserRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetUsername() {
        String expected = "testuser";
        assertEquals(expected, userRequest.getUsername());
    }

    @Test
    void testGetPassword() {
        String expected = "testpassword";
        assertEquals(expected, userRequest.getPassword());
    }

    @Test
    void testIsValid() {
        assertTrue(userRequest.isValid());

        String body2 = "{\"Username\":\"admin\",\"Password\":\"testpassword\"}";
        StoreUserRequest userRequest2;
        try {
            userRequest2 = objectMapper.readValue(body2, StoreUserRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // username already exists
        assertFalse(userRequest2.isValid());

        body2 = "{\"Username\":\"\",\"Password\":\"testpassword\"}";
        try {
            userRequest2 = objectMapper.readValue(body2, StoreUserRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // empty username
        assertFalse(userRequest2.isValid());

        body2 = "{\"Username\":\"testuser\",\"Password\":\"123\"}";
        try {
            userRequest2 = objectMapper.readValue(body2, StoreUserRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // password too short
        assertFalse(userRequest2.isValid());
    }
}
