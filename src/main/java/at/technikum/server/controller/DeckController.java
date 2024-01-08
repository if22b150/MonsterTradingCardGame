package at.technikum.server.controller;

import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.repositories.card.CardRepository;
import at.technikum.repositories.card.ICardRepository;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.mappers.CardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Objects;

public class DeckController {
    private static final ICardRepository cardRepository = new CardRepository();

    public Response index(User user, boolean inPlain) {
        return new Response(HttpStatus.OK, EContentType.JSON, CardMapper.cardsToJson(cardRepository.getUserDeck(user.getId())));
    }

    public Response setDeck(String body, User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] cardIds;
        ArrayList<ACard> cards = new ArrayList<>();

        // validate IDs
        try {
            cardIds = objectMapper.readValue(body, String[].class);
            if(cardIds.length != 4)
                return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.HTML, "Exactly 4 cards have to be set.");
            for (String cardId: cardIds) {
                ACard card = cardRepository.getByPublicId(cardId);
                if(card == null)
                    return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.HTML, "Card " + cardId + " does not exist.");
                if(card.getUserId() == null || !Objects.equals(card.getUserId(), user.getId()))
                    return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.HTML, "Card " + cardId + " does not belong to user.");
                cards.add(card);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // unset old deck
        for(ACard card : cardRepository.getUserDeck(user.getId())) {
            cardRepository.setDeck(card.getId(), false);
        }
        // set deck
        for(ACard card : cards) {
            cardRepository.setDeck(card.getId(), true);
        }

        return new Response(HttpStatus.OK, EContentType.JSON, CardMapper.cardsToJson(cardRepository.getUserDeck(user.getId())));
    }
}
