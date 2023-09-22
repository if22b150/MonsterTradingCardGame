package at.technikum.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Response {
    private int status;
    private String message;
    // is always json in this application
    private String contentType;
    private String content;

    public Response(int statusCode, String content) {
        this.status = statusCode;
        this.content = content;
        this.contentType = "application/json";
    }

    public int getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContent() {
        return content;
    }
}
