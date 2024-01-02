package at.technikum.repositories.card;

import at.technikum.models.card.ACard;
import at.technikum.repositories.IRepository;
import at.technikum.utils.enums.ECardType;
import at.technikum.utils.enums.EElementType;

import java.util.ArrayList;

public interface ICardRepository {
    ACard get(int id);
    ACard getByPublicId(String publicId);

    ACard create(String publicId, String name, int damage, ECardType cardType, EElementType elementType, int packageId);

    ArrayList<ACard> getAllByUser(int userId);

    ArrayList<ACard> getUserDeck(int userId);

    ACard setDeck(int id, boolean deck);
}