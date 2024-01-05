package at.technikum.server;

/**
 * Enum representing HTTP status codes with their corresponding messages.
 */
public enum HttpStatus {
    OK(200, "[\"OK\"]"),
    CREATED(201, "[\"Created\"]"),
    ACCEPTED(202, "[\"Accepted\"]"),
    NO_CONTENT(204, "[\"No Content\"]"),
    BAD_REQUEST(400, "[\"Bad Request\"]"),
    UNAUTHORIZED(401, "[\"Unauthorized\"]"),
    FORBIDDEN(403, "[\"Forbidden\"]"),
    NOT_FOUND(404, "[\"Not Found\"]"),
    METHOD_NOT_ALLOWED(405, "[\"Method Not Allowed\"]"),
    REQUEST_TIMEOUT(408, "[\"Request Timed Out\"]"),
    CONFLICT(409, "[\"Conflict\"]"),
    UNPROCESSABLE_ENTITY(422, "[\"Unprocessable Entity\"]"),
    INTERNAL_SERVER_ERROR(500, "[\"Internal Server Error\"]"),
    NOT_IMPLEMENTED(501, "[\"Not Implemented\"]");

    /**
     * The HTTP status code.
     */
    public final int code;

    /**
     * The corresponding status message.
     */
    public final String message;

    /**
     * Constructor to initialize the HTTP status code and message.
     *
     * @param code    The HTTP status code.
     * @param message The corresponding status message.
     */
    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
