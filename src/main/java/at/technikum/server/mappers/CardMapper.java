package at.technikum.server.mappers;

import at.technikum.models.User;
import at.technikum.models.card.ACard;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class CardMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static String cardsToJson(ArrayList<ACard> cards) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return objectMapper.writeValueAsString(cards);
        } catch (IOException e) {
            // Handle the exception appropriately
            return e.getMessage(); // or return an error message
        }
    }

    public static String deckToPlainText(User user, ArrayList<ACard> cards) {
        String r = "Deck of " + user.getUsername();
        for(ACard c : cards) {
            r += "\n    " + c.getName() + " (" + c.getDamage() + ")";
        }
        return r;
    }
}
