package at.technikum.models.card;

import at.technikum.models.AModel;
import at.technikum.utils.enums.EElementType;

public abstract class ACard extends AModel {
    protected String hashId;
    protected String name;
    protected final int damage;
    protected EElementType elementType;

    public ACard(String hashId, String name, int damage, EElementType elementType) {
        this.hashId = hashId;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
    }
}
