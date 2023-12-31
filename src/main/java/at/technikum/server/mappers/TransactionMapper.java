package at.technikum.server.mappers;

import at.technikum.models.Package;
import at.technikum.models.Transaction;
import at.technikum.models.card.ACard;
import at.technikum.repositories.packages.IPackageRepository;
import at.technikum.repositories.packages.PackageRepository;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class TransactionMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final IPackageRepository packageRepository = new PackageRepository();
    private static final IUserRepository userRepository = new UserRepository();

    public static String transactionToJson(Transaction transaction) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            // Create JSON object
            ObjectNode transactionJson = objectMapper.createObjectNode();
            transactionJson.put("id", transaction.getId());

            // Create JSON array for cards
            JsonNode userJson = objectMapper.valueToTree(userRepository.get(transaction.getUserId()));

            ObjectNode packageJson = objectMapper.createObjectNode();
            packageJson.put("id", transaction.getPackageId());
            // Create JSON array for cards
            ArrayNode cardsJson = objectMapper.createArrayNode();
            for (ACard card : (new Package(transaction.getPackageId())).getCards()) {
                // Convert each card to JSON representation
                JsonNode cardJson = objectMapper.valueToTree(card);
                cardsJson.add(cardJson);
            }
            packageJson.set("cards", cardsJson);

            transactionJson.set("user", userJson);
            transactionJson.set("package", packageJson);

            // Convert the package JSON to a string
            return objectMapper.writeValueAsString(transactionJson);
        } catch (IOException e) {
            // Handle the exception appropriately
            return e.getMessage(); // or return an error message
        }
    }
}
