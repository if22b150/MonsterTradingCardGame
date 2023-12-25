package at.technikum.utils.enums;

public enum ECardType {
    MONSTER("monster"),
    SPELL("spell");

    /**
     * The type.
     */
    public final String type;

    /**
     * Constructor to initialize the HTTP status code and message.
     *
     * @param type The card type.
     */
    ECardType(String type) {
        this.type = type;
    }
}
