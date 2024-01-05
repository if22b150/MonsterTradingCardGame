package at.technikum.models.card;

import at.technikum.utils.enums.ECardType;
import at.technikum.utils.enums.EElementType;

public class MonsterCard extends ACard {
    public MonsterCard(int id, String publicId, String name, int damage, EElementType elementType, int packageId) {
        super(id, publicId, name, damage, elementType, packageId);
        type = ECardType.MONSTER;
    }
    public MonsterCard(int id, String publicId, String name, int damage, EElementType elementType, int packageId, int userId, boolean deck) {
        super(id, publicId, name, damage, elementType, packageId, userId, deck);
        type = ECardType.MONSTER;
    }
}
