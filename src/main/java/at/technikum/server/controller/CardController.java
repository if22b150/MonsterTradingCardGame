package at.technikum.server.controller;

import at.technikum.models.User;
import at.technikum.repositories.card.CardRepository;
import at.technikum.repositories.card.ICardRepository;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.mappers.CardMapper;

public class CardController {
    private static final ICardRepository cardRepository = new CardRepository();

    public Response index(User user) {
        return new Response(HttpStatus.OK, EContentType.JSON, CardMapper.cardsToJson(cardRepository.getAllByUser(user.getId())));
    }
}
