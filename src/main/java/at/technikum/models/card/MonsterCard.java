package at.technikum.models.card;

import at.technikum.utils.enums.EElementType;

public class MonsterCard extends ACard {
    public MonsterCard(String name, int damage, EElementType elementType) {
        super(name, damage, elementType);
    }
}
