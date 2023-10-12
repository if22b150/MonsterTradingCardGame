package at.technikum.repositories.card;

import at.technikum.models.card.ACard;
import at.technikum.repositories.IRepository;
import at.technikum.utils.enums.EElementType;

public interface ICardRepository extends IRepository<ACard> {
    ACard create(String hashId, String name, int damage, EElementType type);
}