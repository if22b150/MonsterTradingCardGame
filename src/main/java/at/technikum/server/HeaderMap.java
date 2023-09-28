package at.technikum.server;

import java.util.HashMap;
import java.util.Map;

public class HeaderMap {
    public static final String CONTENT_LENGTH_HEADER = "Content-Length";
    public static final String HEADER_NAME_VALUE_SEPARATOR = ":";

    private Map<String, String> headers = new HashMap<>();

    /**
     * Adds a header to the map.
     *
     * @param headerLine The header line to add in the format "name: value".
     */
    public void addHeader(String headerLine) {
        if (headerLine != null && headerLine.contains(HEADER_NAME_VALUE_SEPARATOR)) {
            String[] split = headerLine.split(HEADER_NAME_VALUE_SEPARATOR, 2);
            headers.put(split[0], split[1].trim());
        }
    }

    /**
     * Retrieves the value of a specific header.
     *
     * @param headerName The name of the header.
     * @return The header value or null if not found.
     */
    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    /**
     * Retrieves the content length from the headers.
     *
     * @return The content length or 0 if not found or invalid.
     */
    public int getContentLength() {
        String headerValue = headers.get(CONTENT_LENGTH_HEADER);
        if (headerValue != null) {
            try {
                return Integer.parseInt(headerValue);
            } catch (NumberFormatException e) {
                // Handle invalid content length header value gracefully
            }
        }
        return 0;
    }

    /**
     * Prints the headers for debugging purposes.
     */
    public void print() {
        System.out.println(headers);
    }
}
