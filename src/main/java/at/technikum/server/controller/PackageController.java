package at.technikum.server.controller;

import at.technikum.enums.ECardName;
import at.technikum.models.Package;
import at.technikum.models.card.ACard;
import at.technikum.repositories.card.CardRepository;
import at.technikum.repositories.card.ICardRepository;
import at.technikum.repositories.packages.IPackageRepository;
import at.technikum.repositories.packages.PackageRepository;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.mappers.PackageMapper;
import at.technikum.server.requests.StoreCardRequest;
import at.technikum.enums.ECardType;
import at.technikum.enums.EElementType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Objects;

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
            cards.add(cardRepository.create(c.getId(), c.getName(), c.getDamage(), c.getName().contains("Spell") ? ECardType.SPELL : ECardType.MONSTER, getElementTypeBasedOnName(c.getName()), newPackage.getId()));
        }
        return new Response(HttpStatus.CREATED, EContentType.JSON, PackageMapper.packageToJson(newPackage));
    }

    public static EElementType getElementTypeBasedOnName(String name) {
        if(Objects.equals(name, ECardName.Dragon.name)
        || Objects.equals(name, ECardName.FireElf.name)
        || Objects.equals(name, ECardName.FireSpell.name))
            return EElementType.FIRE;
        if(Objects.equals(name, ECardName.Kraken.name)
        || Objects.equals(name, ECardName.WaterSpell.name)
        || Objects.equals(name, ECardName.WaterGoblin.name))
            return EElementType.WATER;

        return EElementType.NORMAL;
    }
}
