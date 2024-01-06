package at.technikum.server.controller;

import at.technikum.utils.CardName;
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
        if(Objects.equals(name, CardName.Dragon.name)
        || Objects.equals(name, CardName.FireElf.name)
        || Objects.equals(name, CardName.FireSpell.name))
            return EElementType.FIRE;
        if(Objects.equals(name, CardName.Kraken.name)
        || Objects.equals(name, CardName.WaterSpell.name)
        || Objects.equals(name, CardName.WaterGoblin.name))
            return EElementType.WATER;

        return EElementType.NORMAL;
    }
}
