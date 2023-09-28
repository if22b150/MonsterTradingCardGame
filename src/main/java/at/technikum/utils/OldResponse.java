package at.technikum.utils;

public class OldResponse {
    private int status;
    private String message;
    // is always json in this application
    private String contentType;
    private String content;

    public OldResponse(int statusCode, String content) {
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
