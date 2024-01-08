package at.technikum.server;

import at.technikum.enums.HttpMethod;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedReader;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestTests {
    Request request;

    @BeforeAll
    void setup() {
        request = new Request(
            HttpMethod.GET,
            "/deck/kienboec?format=plain"
        );
    }

    @Test
    void testGetServiceRoute() {
        String expected = "/deck";
        assertEquals(expected, request.getServiceRoute());
    }

    @Test
    void testGetPathPart() {
        String expected = "/kienboec";
        assertEquals(expected, request.getPathPart(1));

        assertNull(request.getPathPart(2));
    }

    @Test
    void testGetParam() {
        String expected = "plain";
        assertNull(request.getParam("sort"));
        assertEquals(expected, request.getParam("format"));
    }

    @Test
    void testGetMethod() {
        assertEquals(HttpMethod.GET, request.getMethod());
    }
}
