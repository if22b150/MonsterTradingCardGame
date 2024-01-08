package at.technikum.server.mappers;

import at.technikum.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserMapperTests {
    User user;

    @BeforeAll
    void setup() {
        user = new User(
                1,
                "testuser",
                "User Name",
                "bio",
                ":)",
                20,
                "password"
        );
    }

    @Test
    void testUserToJson() {
        String expected = "{\"id\":1,\"username\":\"testuser\",\"name\":\"User Name\",\"bio\":\"bio\",\"image\":\":)\",\"coins\":20}";
        assertEquals(expected, UserMapper.userToJson(user));
    }
}
