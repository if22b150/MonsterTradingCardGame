package at.technikum.enums;

public enum EContentType {
    PLAIN_TEXT("text/plain"),
    HTML("text/html"),
    JSON("application/json");

    public final String type;

    EContentType(String type) {
        this.type = type;
    }

}
