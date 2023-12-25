package at.technikum.server.controller;

import at.technikum.models.Package;
import at.technikum.models.card.ACard;
import at.technikum.repositories.card.CardRepository;
import at.technikum.repositories.card.ICardRepository;
import at.technikum.repositories.packages.IPackageRepository;
import at.technikum.repositories.packages.PackageRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.mappers.PackageMapper;
import at.technikum.server.requests.StoreCardRequest;
import at.technikum.utils.enums.ECardType;
import at.technikum.utils.enums.EElementType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class PackageController {
    private static final ICardRepository cardRepository = new CardRepository();
    private static final IPackageRepository packageRepository = new PackageRepository();
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

        Package newPackage = packageRepository.create();

        ArrayList<ACard> cards = new ArrayList<>();
        for(StoreCardRequest c : packageRequest) {
            cards.add(cardRepository.create(c.getId(), c.getName(), c.getDamage(), c.getName().contains("Spell") ? ECardType.SPELL : ECardType.MONSTER, EElementType.FIRE, newPackage.getId()));
        }
        return new Response(HttpStatus.CREATED, EContentType.JSON, PackageMapper.packageToJson(newPackage));
    }
}
