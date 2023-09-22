package at.technikum.models.card;

import at.technikum.models.AModel;
import at.technikum.utils.enums.EElementType;

public abstract class ACard extends AModel {
    protected String name;
    protected final int damage;
    protected EElementType elementType;

    public ACard(String name, int damage, EElementType elementType) {
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
    }
}
