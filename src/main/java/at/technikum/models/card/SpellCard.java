package at.technikum.models.card;

import at.technikum.utils.enums.EElementType;

public class SpellCard extends ACard {
    public SpellCard(String hashId, String name, int damage, EElementType elementType) {
        super(hashId, name, damage, elementType);
    }
}
