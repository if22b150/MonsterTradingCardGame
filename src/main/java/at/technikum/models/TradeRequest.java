package at.technikum.models;

import at.technikum.enums.ECardType;

public class TradeRequest extends AModel {
    protected String publicId;
    protected int cardId;
    protected ECardType cardType;
    protected int minimumDamage;

    public TradeRequest(String publicId, int cardId, ECardType cardType, int minimumDamage) {
        this.publicId = publicId;
        this.cardId = cardId;
        this.cardType = cardType;
        this.minimumDamage = minimumDamage;
    }
}
