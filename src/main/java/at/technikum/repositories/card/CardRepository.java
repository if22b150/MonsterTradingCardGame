package at.technikum.repositories.card;

import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.models.card.MonsterCard;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.utils.enums.EElementType;

import java.util.ArrayList;

public class CardRepository implements ICardRepository {
    @Override
    public ArrayList<ACard> all() {
        ACard c1 = new MonsterCard("99f8f8dc-e25e-4a95-aa2c-782823f36e2a", "Monster", 20, EElementType.FIRE);
        ArrayList<ACard> l = new ArrayList<ACard>();
        l.add(c1);
        return l;
    }

    @Override
    public ACard get(int id) {
        return new MonsterCard("99f8f8dc-e25e-4a95-aa2c-782823f36e2a", "Monster", 20, EElementType.FIRE);
    }

    @Override
    public ACard create(String hashId, String name, int damage, EElementType type) {
        return new MonsterCard(hashId, name, damage, type);
    }
}
