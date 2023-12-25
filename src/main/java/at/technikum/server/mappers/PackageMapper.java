package at.technikum.server.mappers;

import at.technikum.models.Package;
import at.technikum.models.card.ACard;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class PackageMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static String packageToJson(Package packageModel) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            // Create JSON object
            ObjectNode packageJson = objectMapper.createObjectNode();
            packageJson.put("id", packageModel.getId());

            // Create JSON array for cards
            ArrayNode cardsJson = objectMapper.createArrayNode();
            for (ACard card : packageModel.getCards()) {
                // Convert each card to JSON representation
                JsonNode cardJson = objectMapper.valueToTree(card);
                cardsJson.add(cardJson);
            }

            // Add the array of cards to the package JSON
            packageJson.set("cards", cardsJson);

            // Convert the package JSON to a string
            return objectMapper.writeValueAsString(packageJson);
        } catch (IOException e) {
            // Handle the exception appropriately
            return e.getMessage(); // or return an error message
        }
    }
}
