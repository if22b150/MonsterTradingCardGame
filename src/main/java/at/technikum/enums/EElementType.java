package at.technikum.enums;

public enum EElementType {
    FIRE("fire"),
    WATER("water"),
    NORMAL("normal");

    /**
     * The type.
     */
    public final String type;

    /**
     * Constructor to initialize the HTTP status code and message.
     *
     * @param type The card type.
     */
    EElementType(String type) {
        this.type = type;
    }
}
