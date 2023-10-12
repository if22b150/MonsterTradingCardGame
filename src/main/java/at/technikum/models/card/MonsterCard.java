package at.technikum.models.card;

import at.technikum.utils.enums.EElementType;

public class MonsterCard extends ACard {
    public MonsterCard(String hashId, String name, int damage, EElementType elementType) {
        super(hashId, name, damage, elementType);
    }
}
