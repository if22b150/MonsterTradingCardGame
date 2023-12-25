package at.technikum.repositories.card;

import at.technikum.models.card.ACard;
import at.technikum.repositories.IRepository;
import at.technikum.utils.enums.ECardType;
import at.technikum.utils.enums.EElementType;

public interface ICardRepository {
    ACard create(String publicId, String name, int damage, ECardType cardType, EElementType elementType, int packageId);
}