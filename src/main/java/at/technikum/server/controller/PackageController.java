package at.technikum.server.controller;

import at.technikum.models.card.ACard;
import at.technikum.repositories.card.CardRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.mappers.CardMapper;
import at.technikum.server.requests.StoreCardRequest;
import at.technikum.utils.enums.EElementType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class PackageController {
    private static final CardRepository cardRepository = new CardRepository();
    public Response store(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        StoreCardRequest[] packageRequest;
        // Parse the JSON into a Java object
        try {
            packageRequest = objectMapper.readValue(body, StoreCardRequest[].class);
            for (StoreCardRequest card: packageRequest) {
                if(!card.isValid()) {
                    return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.HTML, card.getErrorMessages());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ArrayList<ACard> cards = new ArrayList<>();
        for(StoreCardRequest c : packageRequest) {
            cards.add(cardRepository.create(c.getId(), c.getName(), c.getDamage(), EElementType.FIRE));
        }

        return new Response(HttpStatus.CREATED, EContentType.JSON, CardMapper.cardsToJson(cards));
    }
}
