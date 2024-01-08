package at.technikum.models.card;

import at.technikum.models.AModel;
import at.technikum.enums.ECardType;
import at.technikum.enums.EElementType;

public abstract class ACard extends AModel {
    public String getPublicId() {
        return publicId;
    }

    protected String publicId;

    public ECardType getType() {
        return type;
    }

    protected ECardType type;

    public String getName() {
        return name;
    }

    protected String name;

    public int getDamage() {
        return damage;
    }

    protected final int damage;

    public EElementType getElementType() {
        return elementType;
    }

    protected EElementType elementType;

    public int getPackageId() {
        return packageId;
    }

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
