package at.technikum.models.card;

import at.technikum.models.AModel;
import at.technikum.utils.enums.EElementType;

public abstract class ACard extends AModel {
    protected String publicId;
    protected String name;
    protected final int damage;
    protected EElementType elementType;
    protected int packageId;

    public Integer getUserId() {
        return userId;
    }

    protected Integer userId;

    public boolean isDeck() {
        return deck;
    }

    protected boolean deck;

    public ACard(String publicId, String name, int damage, EElementType elementType) {
        this.publicId = publicId;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
    }

    public ACard(int id, String publicId, String name, int damage, EElementType elementType, int packageId) {
        this.id = id;
        this.publicId = publicId;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
        this.packageId = packageId;
    }

    public ACard(int id, String publicId, String name, int damage, EElementType elementType, int packageId, int userId, boolean deck) {
        this.id = id;
        this.publicId = publicId;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
        this.packageId = packageId;
        this.userId = userId;
        this.deck = deck;
    }
}
