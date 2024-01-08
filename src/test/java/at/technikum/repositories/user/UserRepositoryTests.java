package at.technikum.repositories.user;

import at.technikum.models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTests {
    private static final IUserRepository userRepository = new UserRepository();

    User testUser;

    @BeforeAll
    void setup() {
        testUser = userRepository.create("testuser", "testpassword");
    }

    @Test
    void testCreate() {
        assertNotNull(testUser);
        assertNotNull(testUser.getId());
        assertEquals("testuser", testUser.getUsername());
        assertEquals("testpassword", testUser.getPassword());
    }

    @Test
    void testGet() {
        User user = userRepository.get(testUser.getId());
        assertEquals(user.getId(), testUser.getId());
    }

    @Test
    void testGetByUsername() {
        User user = userRepository.getByUsername(testUser.getUsername());
        assertEquals(user.getId(), testUser.getId());
    }

    @Test
    void testEdit() {
        User user = userRepository.edit(testUser.getUsername(), "TESTER", "BIO", ":-(", 100);
        testUser = userRepository.get(testUser.getId());

        assertEquals(user.getId(), testUser.getId());
        assertEquals(user.getName(), testUser.getName());
        assertEquals(user.getBio(), testUser.getBio());
        assertEquals(user.getImage(), testUser.getImage());
        assertEquals(user.getCoins(), testUser.getCoins());
    }

    @AfterAll()
    void deleteUser() {
        userRepository.delete(testUser.getId());
    }
}
