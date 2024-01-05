package at.technikum.models.card;

import at.technikum.utils.enums.ECardType;
import at.technikum.utils.enums.EElementType;

public class SpellCard extends ACard {
    public SpellCard(int id, String publicId, String name, int damage, EElementType elementType, int packageId) {
        super(id, publicId, name, damage, elementType, packageId);
        type = ECardType.SPELL;
    }
    public SpellCard(int id, String publicId, String name, int damage, EElementType elementType, int packageId, int userId, boolean deck) {
        super(id, publicId, name, damage, elementType, packageId, userId, deck);
        type = ECardType.SPELL;
    }
}
